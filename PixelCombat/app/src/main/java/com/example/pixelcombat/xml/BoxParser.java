package com.example.pixelcombat.xml;

import android.content.Context;
import android.util.Log;

import com.example.pixelcombat.enums.ExceptionGroup;
import com.example.pixelcombat.enums.GamePlayView;
import com.example.pixelcombat.exception.parser.XmlParseErrorException;
import com.example.pixelcombat.math.BoundingRectangle;
import com.example.pixelcombat.math.Vector2d;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

public class BoxParser {

    @Getter
    private Map<String, ArrayList<ArrayList<BoundingRectangle>>> boxes = new HashMap<String, ArrayList<ArrayList<BoundingRectangle>>>();
    private boolean readingBox = false;
    private boolean readingBoxList = false;

    private boolean readingSprites = false;
    private boolean readingAnimation = false;
    private boolean readingIMG = false;
    private String animation = "";
    private int picture = 0;
    private Context context;
    private XmlPullParser parser;

    public BoxParser(Context context, String fileName) throws Exception {
        this.context = context;
        XmlPullParserFactory parserFactory;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            parser = parserFactory.newPullParser();
            InputStream is = context.getAssets().open(fileName);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);
        } catch (XmlPullParserException | IOException e) {
            throw new XmlParseErrorException(MessageFormat.format(ExceptionGroup.PARSER.getDescription(), fileName) + e.getMessage());
        }

    }

    public BoxParser(Context context) throws Exception {
        this.context = context;
        XmlPullParserFactory parserFactory;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            parser = parserFactory.newPullParser();
        } catch (XmlPullParserException e) {
            throw e;
        }

    }

    public void parse(String fileName) throws XmlParseErrorException {
        try {
            InputStream is = context.getAssets().open(fileName);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);
            parseXMLData();
        } catch (XmlPullParserException | IOException e) {
            throw new XmlParseErrorException(MessageFormat.format(ExceptionGroup.PARSER.getDescription(), fileName) + e.getMessage());
        }
    }

    public void parseXMLData() throws XmlPullParserException, IOException, XmlParseErrorException {
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String eltName;

            switch (eventType) {
                case XmlPullParser.START_TAG:
                    eltName = parser.getName();
                    if ("boxes".equals(eltName) && !readingSprites) {
                        readingSprites = true;
                    } else if (readingSprites) {
                        if (!readingAnimation) {
                            readingAnimation = true;
                            boxes.put(eltName, new ArrayList<ArrayList<BoundingRectangle>>());
                            animation = eltName;
                            readingIMG = true;
                            Log.i("Info", "Created image animation: " + eltName);
                            System.out.println("Info: Created image animation: " + eltName);
                        } else {
                            if (readingBoxList == false) {
                                readingBoxList = true;
                                boxes.get(animation).add(new ArrayList<BoundingRectangle>());
                                picture = boxes.get(animation).size() - 1;
                                System.out.println("Info: Creating Boxes for Picture: " + picture + " in " + animation + " ...");
                                Log.i("Info", "Creating Boxes for Picture: " + picture + " in " + animation + " ...");
                            } else if (readingBox == false) {
                                int id = Integer.parseInt(parser.getAttributeValue(null, "id"));
                                float x = Float.parseFloat(parser.getAttributeValue(null, "x")) * GamePlayView.FIELD_SIZE;
                                float y = Float.parseFloat(parser.getAttributeValue(null, "y")) * GamePlayView.FIELD_SIZE;
                                float height = Float.parseFloat(parser.getAttributeValue(null, "height")) * GamePlayView.FIELD_SIZE;
                                float width = Float.parseFloat(parser.getAttributeValue(null, "width")) * GamePlayView.FIELD_SIZE;
                                boolean hurts = Boolean.parseBoolean(parser.getAttributeValue(null, "hurts"));

                                BoundingRectangle newBox = new BoundingRectangle(height, new Vector2d(x, y), width);
                                newBox.setHurts(hurts);
                                boxes.get(animation).get(picture).add(newBox);

                                Log.i("Info", "Loaded BoundaryRectangle with id " + id + " with height: " + height + " width: " +
                                        width + " and coordinates: (" + x + "," + y + ")");

                                System.out.println("Info: Loaded BoundaryRectangle with id " + id + " with height: " + height + " width: " +
                                        width + " and coordinates: (" + x + "," + y + ")");

                                readingBox = true;
                            }
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (readingSprites) {
                        if (readingAnimation) {
                            if (readingBoxList == true) {
                                if (readingBox == true) {
                                    readingBox = false;
                                } else {
                                    readingBoxList = false;
                                }
                            } else {
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


}
