package com.example.pixelcombat.xml;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.example.pixelcombat.enums.ExceptionGroup;
import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.exception.parser.XmlParseErrorException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CharacterParser {

    private Map<String, ArrayList<Bitmap>> character = new HashMap<>();
    private ArrayList<ArrayList<Float>> times = new ArrayList<>();
    private ArrayList<Float> time = new ArrayList<>();
    private ArrayList<Boolean> loop = new ArrayList<>();
    private ArrayList<Integer> loopIndizes = new ArrayList<>();
    private Map<String, Boolean> airBools = new HashMap<>();
    private Map<String, Integer> airIndices = new HashMap<>();

    private boolean readingSprites = false;
    private boolean readingAnimation = false;
    private boolean readingIMG = false;
    private String animation = "";
    private Context context;
    private  XmlPullParser parser;

    public CharacterParser(Context context, String fileName) throws Exception {
        this.context = context;
        XmlPullParserFactory parserFactory;
        try {
            parserFactory =  XmlPullParserFactory.newInstance();
            parser = parserFactory.newPullParser();
            InputStream is  = context.getAssets().open(fileName);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
            parser.setInput(is,null);
        } catch (XmlPullParserException  | IOException e) {
            throw new XmlParseErrorException(MessageFormat.format(ExceptionGroup.PARSER.getDescription(),fileName)+e.getMessage());
        }

    }

    public void parseXMLData() throws XmlPullParserException, IOException, XmlParseErrorException {
        int eventType = parser.getEventType();
        while(eventType != XmlPullParser.END_DOCUMENT){
            String eltName;

            switch(eventType){
                case XmlPullParser.START_TAG:
                    eltName = parser.getName();
                    if("sprites".equals(eltName) && !readingSprites){
                        readingSprites = true;
                    }else if (readingSprites) {
                        if (!readingAnimation) {
                            readingAnimation = true;

                            time = new ArrayList<>();
                            character.put(eltName, new ArrayList<Bitmap>());
                            animation = eltName;

                            int loopIndex = Integer.parseInt(parser.getAttributeValue(null, "loopIndex"));
                            boolean loops = Boolean.parseBoolean(parser.getAttributeValue(null, "loops"));
                            int airIndex = Integer.parseInt(parser.getAttributeValue(null, "airIndex"));
                            boolean airBool = Boolean.parseBoolean(parser.getAttributeValue(null, "airBool"));

                            Log.i("Info","Created image animation: " + eltName);
                            Log.i("Info","loopIndex "+ loopIndex);
                            Log.i("Info","loops "+ loops);
                            Log.i("Info","airIndex "+ airIndex);
                            Log.i("Info","airBool "+ airBool);

                            airBools.put(animation, airBool);
                            airIndices.put(animation, airIndex);
                            loop.add(loops);
                            loopIndizes.add(loopIndex);


                        } else {
                            if ( "img".equals(eltName)) {
                                String fileName = "";
                                try {
                                    int key = Integer.parseInt(parser.getAttributeValue(null, "key"));
                                    float duration = Float.parseFloat(parser.getAttributeValue(null, "duration"));
                                    fileName = parser.nextText();
                                    Uri file = Uri.parse("android.resource://com.example.pixelcombat/raw/" + fileName.replace(".png", ""));
                                    InputStream is = context.getContentResolver().openInputStream(file);
                                    BitmapFactory.Options options = new BitmapFactory.Options();
                                    options.inPreferredConfig = Bitmap.Config.RGB_565;

                                    Bitmap bitmap = BitmapFactory.decodeResourceStream(ScreenProperty.CURRENT_CONTEXT.getResources(), null, is, null, options);

                                    character.get(animation).add(key, bitmap);
                                    Log.i("Info", "Loaded image " + fileName + " at position " + key + " with duration: " + duration + " in " + animation);
                                    time.add(duration);
                                }
                                catch(Exception e){
                                    throw new XmlParseErrorException(MessageFormat.format(ExceptionGroup.PARSER.getDescription(),fileName)+"\n"+e.getMessage());
                                }

                            }
                            readingIMG = true;
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (readingSprites ) {
                        if (readingAnimation ) {
                            if (readingIMG ) {
                                readingIMG = false;
                                Log.i("Info","Die Animation für "+ animation+ " ist durch.");
                                times.add(time);
                                readingAnimation = false;
                            }
                        } else {
                            readingSprites = false;
                        }
                    }
                    break;
            }
            eventType = parser.next();
        }
    }

    public Map<String, ArrayList<Bitmap>> getCharacter() {
        return character;
    }

    public ArrayList<ArrayList<Float>> getTimes() {
        return times;
    }

    public ArrayList<Boolean> getLoop() {
        return loop;
    }

    public ArrayList<Integer> getLoopIndizes() {
        return loopIndizes;
    }

}
