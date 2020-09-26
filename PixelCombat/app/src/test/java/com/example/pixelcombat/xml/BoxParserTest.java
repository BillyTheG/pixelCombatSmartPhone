package com.example.pixelcombat.xml;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.pixelcombat.exception.parser.XmlParseErrorException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

@RunWith(RobolectricTestRunner.class)
@Config(assetDir = "/assets")
public class BoxParserTest {

    public BoxParser parser;
    public Context context;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        parser = new BoxParser(context, "Ruffy_Boxes.xml");
    }


    @Test
    public void parseOkay() throws XmlParseErrorException, XmlPullParserException, IOException {
        parser.parseXMLData();
    }


}
