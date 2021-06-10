package uk.ac.york.nimblefitness.Screens.Settings;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import uk.ac.york.nimblefitness.HelperClasses.Slide;
import uk.ac.york.nimblefitness.MediaHandlers.AbstractLayout;
import uk.ac.york.nimblefitness.MediaHandlers.Audio.AudioType;
import uk.ac.york.nimblefitness.MediaHandlers.Button.ButtonLayout;
import uk.ac.york.nimblefitness.MediaHandlers.Button.ButtonType;
import uk.ac.york.nimblefitness.MediaHandlers.Button.TextButtonType;
import uk.ac.york.nimblefitness.MediaHandlers.Graphics.ShapeLayout;
import uk.ac.york.nimblefitness.MediaHandlers.Graphics.ShapeType;
import uk.ac.york.nimblefitness.MediaHandlers.Graphics.ShapeView;
import uk.ac.york.nimblefitness.MediaHandlers.Images.ImageLayout;
import uk.ac.york.nimblefitness.MediaHandlers.Images.ImageType;
import uk.ac.york.nimblefitness.MediaHandlers.Text.TextLayout;
import uk.ac.york.nimblefitness.MediaHandlers.Text.TextModule;
import uk.ac.york.nimblefitness.MediaHandlers.Text.TextType;
import uk.ac.york.nimblefitness.MediaHandlers.Video.VideoLayout;
import uk.ac.york.nimblefitness.MediaHandlers.Video.VideoType;
import uk.ac.york.nimblefitness.R;

/**
 * The activity that can load an XML file that follows the PWS and display all components that
 * are laid out in the PWS
 */

public class LoadNewPresentationActivity extends AppCompatActivity {
    private static final int STORAGE_PERMISSION_CODE = 101;
    ShapeView shapeView;
    Button openFileBrowser, downloadXMLFile;
    FrameLayout frameLayout;
    Map<String, Slide> slides = new LinkedHashMap<>();
    String defaultBackgroundColour;
    TextModule.fontFamily textFont;
    TextButtonType.fontFamily buttonFont;
    String fontColour;
    String lineColour;
    String fillColour;
    String slideID;
    String buttonText;
    String buttonMediaID;
    String buttonSlideID;
    int fontSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_test);

        shapeView = findViewById(R.id.shapeView);
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
        openFileBrowser = findViewById(R.id.openFileBrowser);
        downloadXMLFile = findViewById(R.id.downloadXMLFile);
        frameLayout = findViewById(R.id.handlerTestFrame);


        //Opens a browser to allow the user to download an xml file from the internet
        downloadXMLFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www-users.york.ac.uk/~hew550/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        //Opens the file browser to allow the user to navigate to the xml file that is stored on
        // the phone
        openFileBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startXMLParsing("filePath");
                new MaterialFilePicker().withActivity(LoadNewPresentationActivity.this).withRequestCode(1000).withFilter(Pattern.compile(".*\\.(xml)$")).withHiddenFiles(true).start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //receives the path of the file that was selected by the user and the sends that path to
        // the XML parser
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            startXMLParsing(filePath);
        }
    }

    //The full XML -> object conversion
    void parseXML(XmlPullParser parser) throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        ShapeType shapeType = null;
        TextType textType = null;
        VideoType videoType = null;
        AudioType audioType = null;
        ImageType imageType = null;
        ButtonType buttonType = null;
        Slide slide = null;
        ArrayList<AbstractLayout> abstractLayouts = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name;
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    switch (name) {
                        case "slideshow":
                            break;
                        case "defaults":
                            //setting the default values to use in later tags
                            defaultBackgroundColour = parser.getAttributeValue(null,
                                    "backgroundcolour");
                            textFont =
                                    TextModule.fontFamily.valueOf(parser.getAttributeValue(null,
                                            "font"));
                            buttonFont =
                                    TextButtonType.fontFamily.valueOf(parser.getAttributeValue(null, "font"));
                            fontColour = parser.getAttributeValue(null, "fontcolour");
                            lineColour = parser.getAttributeValue(null, "linecolour");
                            fillColour = parser.getAttributeValue(null, "fillcolour");
                            fontSize = Integer.parseInt(parser.getAttributeValue(null, "fontsize"));
                            break;
                        case "slide":
                            slide = new Slide();
                            abstractLayouts = new ArrayList<>();
                            slideID = parser.getAttributeValue(null, "id");
                            if (parser.getAttributeValue(null, "duration") != null) {
                                slide.setDuration(Integer.parseInt(parser.getAttributeValue(null,
                                        "duration")));
                            }
                            break;
                        case "shape":
                            shapeType = new ShapeType();
                            shapeType.setShape_type(parser.getAttributeValue(null, "type"));
                            shapeType.setxStart(Integer.parseInt(parser.getAttributeValue(null,
                                    "xstart")));
                            shapeType.setyStart(Integer.parseInt(parser.getAttributeValue(null,
                                    "ystart")));
                            shapeType.setWidth(Integer.parseInt(parser.getAttributeValue(null,
                                    "width")));
                            shapeType.setHeight(Integer.parseInt(parser.getAttributeValue(null,
                                    "height")));
                            //checking none required tags and if not there set to the default value
                            if (parser.getAttributeValue(null, "fillcolour") != null) {
                                shapeType.setColour(Color.parseColor(parser.getAttributeValue(null, "fillcolour")));
                            } else {
                                shapeType.setColour(Color.parseColor(fillColour));
                            }
                            if (parser.getAttributeValue(null, "duration") != null) {
                                shapeType.setDuration(Integer.parseInt(parser.getAttributeValue(null, "duration")));
                            } else {
                                shapeType.setDuration(0);
                            }
                            break;
                        case "shading":
                            assert shapeType != null;
                            shapeType.setShading(new LinearGradient(Integer.parseInt(parser.getAttributeValue(null, "x1")), Integer.parseInt(parser.getAttributeValue(null, "y1")), Integer.parseInt(parser.getAttributeValue(null, "x2")), Integer.parseInt(parser.getAttributeValue(null, "y2")), Color.parseColor(parser.getAttributeValue(null, "colour1")), Color.parseColor(parser.getAttributeValue(null, "colour2")), Boolean.parseBoolean(parser.getAttributeValue(null, "cyclic")) ? Shader.TileMode.REPEAT : Shader.TileMode.CLAMP));
                            shapeType.setColour(0);
                            break;
                        case "line":
                            shapeType = new ShapeType();
                            shapeType.setShape_type("LINE");
                            shapeType.setxStart(Integer.parseInt(parser.getAttributeValue(null,
                                    "xstart")));
                            shapeType.setyStart(Integer.parseInt(parser.getAttributeValue(null,
                                    "ystart")));
                            shapeType.setxEnd(Integer.parseInt(parser.getAttributeValue(null,
                                    "xend")));
                            shapeType.setyEnd(Integer.parseInt(parser.getAttributeValue(null,
                                    "yend")));
                            if (parser.getAttributeValue(null, "linecolour") != null) {
                                shapeType.setColour(Color.parseColor(parser.getAttributeValue(null, "linecolour")));
                            } else {
                                shapeType.setColour(Color.parseColor(lineColour));
                            }
                            if (parser.getAttributeValue(null, "duration") != null) {
                                shapeType.setDuration(Integer.parseInt(parser.getAttributeValue(null, "duration")));
                            } else {
                                shapeType.setDuration(0);
                            }
                            break;
                        case "text":
                            if (buttonType != null) {
                                if (parser.getAttributeValue(null, "font") != null) {
                                    buttonType.setFont(TextButtonType.fontFamily.valueOf(parser.getAttributeValue(null, "font")));
                                } else {
                                    buttonType.setFont(buttonFont);
                                }
                                if (parser.getAttributeValue(null, "fontcolour") != null) {
                                    buttonType.setFontColour(parser.getAttributeValue(null,
                                            "fontcolour"));
                                } else {
                                    buttonType.setFontColour(fontColour);
                                }
                                if (parser.getAttributeValue(null, "fontsize") != null) {
                                    buttonType.setFontSize(parser.getAttributeValue(null,
                                            "fontsize"));
                                } else {
                                    buttonType.setFontSize(String.valueOf(fontSize));
                                }
                            } else {
                                textType = new TextType();
                                textType.setStyle(TextModule.styleFamily.normal);
                                textType.setxStart(Integer.parseInt(parser.getAttributeValue(null
                                        , "xstart")));
                                textType.setyStart(Integer.parseInt(parser.getAttributeValue(null
                                        , "ystart")));
                                if (parser.getAttributeValue(null, "font") != null) {
                                    textType.setFont(TextModule.fontFamily.valueOf(parser.getAttributeValue(null, "font")));
                                } else {
                                    textType.setFont(textFont);
                                }
                                if (parser.getAttributeValue(null, "fontcolour") != null) {
                                    textType.setFontColour(parser.getAttributeValue(null,
                                            "fontcolour"));
                                } else {
                                    textType.setFontColour(fontColour);
                                }
                                if (parser.getAttributeValue(null, "fontsize") != null) {
                                    textType.setFontSize(parser.getAttributeValue(null, "fontsize"
                                    ));
                                } else {
                                    textType.setFontSize(String.valueOf(fontSize));
                                }
                            }
                            break;
                        case "b":
                            assert textType != null;
                            if (textType.getStyle() == TextModule.styleFamily.italic || textType.getStyle() == TextModule.styleFamily.bold_italic) {
                                textType.setStyle(TextModule.styleFamily.bold_italic);
                            } else {
                                textType.setStyle(TextModule.styleFamily.bold);
                            }
                            break;
                        case "i":
                            assert textType != null;
                            if (textType.getStyle() == TextModule.styleFamily.bold || textType.getStyle() == TextModule.styleFamily.bold_italic) {
                                textType.setStyle(TextModule.styleFamily.bold_italic);
                            } else {
                                textType.setStyle(TextModule.styleFamily.italic);
                            }
                            break;
                        case "video":
                            videoType = new VideoType();
                            videoType.setUriPath(String.valueOf(parser.getAttributeValue(null,
                                    "urlname")));
                            videoType.setLoop(Boolean.parseBoolean(parser.getAttributeValue(null,
                                    "loop")));
                            videoType.setxStart(Integer.parseInt(parser.getAttributeValue(null,
                                    "xstart")));
                            videoType.setyStart(Integer.parseInt(parser.getAttributeValue(null,
                                    "ystart")));

                            if (parser.getAttributeValue(null, "id") != null) {
                                videoType.setId(parser.getAttributeValue(null, "id"));
                            } else {
                                videoType.setId(null);
                            }
                            if (parser.getAttributeValue(null, "starttime") != null) {
                                videoType.setStartTime(Integer.parseInt(parser.getAttributeValue(null, "starttime")));
                            } else {
                                videoType.setStartTime(0);
                            }

                            if (parser.getAttributeValue(null, "width") != null) {
                                videoType.setWidth(Integer.parseInt(parser.getAttributeValue(null
                                        , "width")));
                            } else {
                                videoType.setWidth(0);
                            }
                            if (parser.getAttributeValue(null, "height") != null) {
                                videoType.setHeight(Integer.parseInt(parser.getAttributeValue(null, "height")));
                            } else {
                                videoType.setHeight(0);
                            }
                            break;
                        case "audio":
                            audioType = new AudioType();
                            audioType.setUrl(String.valueOf(parser.getAttributeValue(null,
                                    "urlname")));
                            audioType.setLoop(Boolean.parseBoolean(parser.getAttributeValue(null,
                                    "loop")));
                            if (parser.getAttributeValue(null, "starttime") != null) {
                                audioType.setStarttime(Integer.parseInt(parser.getAttributeValue(null, "starttime")));
                            } else {
                                audioType.setStarttime(0);
                            }
                            if (parser.getAttributeValue(null, "id") != null) {
                                audioType.setId(parser.getAttributeValue(null, "id"));
                            } else {
                                audioType.setId(null);
                            }
                            break;
                        case "image":
                            if (buttonType != null) {
                                buttonType.setUrlname(String.valueOf(parser.getAttributeValue(null, "urlname")));
                            } else {
                                imageType = new ImageType();
                                imageType.setImageSource(String.valueOf(parser.getAttributeValue(null, "urlname")));
                                imageType.setXCoordinate(Integer.parseInt(parser.getAttributeValue(null, "xstart")));
                                imageType.setYCoordinate(Integer.parseInt(parser.getAttributeValue(null, "ystart")));
                                imageType.setImageHeight(Integer.parseInt(parser.getAttributeValue(null, "height")));
                                imageType.setImageWidth(Integer.parseInt(parser.getAttributeValue(null, "width")));
                                if (parser.getAttributeValue(null, "duration") != null) {
                                    imageType.setImageDuration(Integer.parseInt(parser.getAttributeValue(null, "duration")));
                                } else {
                                    imageType.setImageDuration(0);
                                }
                            }
                            break;
                        case "button":
                            buttonType = new ButtonType();
                            buttonType.setHeight(Integer.parseInt(parser.getAttributeValue(null,
                                    "height")));
                            buttonType.setWidth(Integer.parseInt(parser.getAttributeValue(null,
                                    "width")));
                            buttonType.setYstart(Integer.parseInt(parser.getAttributeValue(null,
                                    "ystart")));
                            buttonType.setXstart(Integer.parseInt(parser.getAttributeValue(null,
                                    "xstart")));
                            break;
                        case "slideid":
                            buttonSlideID = "new";
                            break;
                        case "mediaid":
                            buttonMediaID = "new";
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + name);
                    }
                    break;
                case XmlPullParser.TEXT:
                    if (buttonType != null && (!parser.getText().equals("null") || parser.getText() != null) && buttonSlideID == null && buttonMediaID == null) {
                        buttonText = parser.getText();

                    } else if (textType != null && !parser.getText().equals("null")) {
                        switch (textType.getStyle()) {
                            case normal:
                                textType.addText(parser.getText());
                                break;
                            case bold:
                                textType.addText("<b>" + parser.getText() + "</b>");
                                break;
                            case italic:
                                textType.addText("<i>" + parser.getText() + "</i>");
                                break;
                            case bold_italic:
                                textType.addText("<b><i>" + parser.getText() + "</i></b>");
                                break;
                        }
                    } else if (buttonSlideID != null && (!parser.getText().equals("null") || parser.getText() != null)) {
                        buttonSlideID = parser.getText();
                    } else if (buttonMediaID != null && (!parser.getText().equals("null") || parser.getText() != null)) {
                        buttonMediaID = parser.getText();
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if ((name.equalsIgnoreCase("shape") || name.equalsIgnoreCase("line")) && shapeType != null) {
                        ShapeLayout shapeLayout = new ShapeLayout(shapeType.getyStart(),
                                shapeType.getxStart(), shapeType.getWidth(),
                                shapeType.getHeight(), shapeType.getColour(), shapeType.getxEnd()
                                , shapeType.getyEnd(), shapeType.getShape_type(),
                                shapeType.getShading(), shapeType.getDuration(), this, frameLayout);
                        abstractLayouts.add(shapeLayout);
                        shapeType = null;
                    } else if (name.equalsIgnoreCase("video") && videoType != null) {
                        VideoLayout videoLayout = new VideoLayout(videoType.getUriPath(),
                                videoType.getWidth(), videoType.getHeight(),
                                videoType.getxStart(), videoType.getyStart(), videoType.getId(),
                                videoType.getStartTime(), videoType.isLoop(), frameLayout, this);
                        abstractLayouts.add(videoLayout);
                        videoType = null;
                    } else if (name.equalsIgnoreCase("text") && (textType != null || buttonType != null)) {
                        if (buttonType != null) {
                            buttonType.setText(buttonText);
                        } else {
                            TextLayout textLayout = new TextLayout(textType.getText(),
                                    textType.getFont(), textType.getFontSize(),
                                    textType.getFontColour(), textType.getxStart(),
                                    textType.getyStart(), frameLayout, this);
                            abstractLayouts.add(textLayout);
                            textType = null;
                        }
                    } else if (name.equalsIgnoreCase("audio") && audioType != null) {
                        AudioType audioType1 = new AudioType(audioType.getUrl(),
                                audioType.getStarttime(), audioType.isLoop(), audioType.getId(),
                                this);
                        abstractLayouts.add(audioType1);
                        audioType = null;
                    } else if (name.equalsIgnoreCase("image") && imageType != null) {
                        ImageLayout imageLayout = new ImageLayout(imageType.getXCoordinate(),
                                imageType.getYCoordinate(), imageType.getImageWidth(),
                                imageType.getImageHeight(), imageType.getImageDuration(),
                                imageType.getImageSource(), frameLayout, this);
                        abstractLayouts.add(imageLayout);
                        imageType = null;
                    } else if (name.equalsIgnoreCase("b") && textType != null) {
                        textType.setStyle(TextModule.styleFamily.normal);
                    } else if (name.equalsIgnoreCase("i") && textType != null) {
                        textType.setStyle(TextModule.styleFamily.normal);
                    } else if (name.equalsIgnoreCase("button") && buttonType != null) {
                        ButtonLayout buttonLayout = new ButtonLayout(buttonType.getXstart(),
                                buttonType.getYstart(), buttonType.getWidth(),
                                buttonType.getHeight(), buttonType.getSlideid(),
                                buttonType.getMediaid(), buttonType.getUrlname(),
                                buttonType.getFontSize(), buttonType.getFontColour(),
                                buttonType.getText(), buttonType.getFont(), frameLayout, this,
                                LoadNewPresentationActivity.this);
                        abstractLayouts.add(buttonLayout);
                    } else if (name.equalsIgnoreCase("slideid") && buttonSlideID != null && !buttonSlideID.equals("null")) {
                        buttonType.setSlideid(buttonSlideID);
                        buttonSlideID = null;
                    } else if (name.equalsIgnoreCase("mediaid") && buttonMediaID != null && !buttonMediaID.equals("null")) {
                        buttonType.setMediaid(buttonMediaID);
                        buttonMediaID = null;
                    } else if (name.equalsIgnoreCase("slide") && abstractLayouts != null) {
                        slide.setAbstractLayouts(abstractLayouts);
                        slides.put(slideID, slide);
                        slide = null;
                        abstractLayouts = null;
                        slideID = null;
                    }

            }
            eventType = parser.next();
        }
    }

    //Checks to see if the user has given permission for the app to read the phones internal
    // storeage
    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(LoadNewPresentationActivity.this, permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(LoadNewPresentationActivity.this,
                    new String[]{permission}, requestCode);
        }
    }

    // This function is called when the user accepts or decline the permission.
    // Request Code is used to check which permission called this function.
    // This request code is provided when the user is prompt for permission.

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(LoadNewPresentationActivity.this, "Storage Permission Granted",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoadNewPresentationActivity.this, "Storage Permission Denied",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Setting up the XML parser and drawing first page after parsing has been completed
    void startXMLParsing(String filePath) {
        XmlPullParserFactory pullParserFactory;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            InputStream inputStream = new FileInputStream(new File(filePath));

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);

            parseXML(parser);

            /*for (AbstractLayout abstractLayout : slides.get("Slide2").getAbstractLayouts()) {
                abstractLayout.draw();
            }*/

            Slide firstSlide = slides.values().iterator().next();


            //Draws the first slide
            frameLayout.removeAllViews();
            for (AbstractLayout abstractLayout : firstSlide.getAbstractLayouts()) {
                abstractLayout.draw();
            }

            //Remove the first slide and prints the second if they have delays
            if (firstSlide.getDuration() != 0) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        frameLayout.removeAllViews();
                        for (AbstractLayout abstractLayout :
                                slides.values().iterator().next().getAbstractLayouts()) {
                            abstractLayout.draw();
                        }
                    }
                }, firstSlide.getDuration());
            }

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }

    //allows the changing of slides using button components
    public void changeSlide(String slideID) {
        this.slideID = slideID;
        frameLayout.removeAllViews();
        for (AbstractLayout abstractLayout : slides.get(slideID).getAbstractLayouts()) {
            abstractLayout.draw();
        }
    }

    //allows the changing of media state by using a button component
    public void changeMedia(String mediaID) {
        for (AbstractLayout abstractLayout : slides.get(slideID).getAbstractLayouts()) {
            if (abstractLayout.getMediaId() == mediaID) {
                abstractLayout.playPause();
            }
        }
    }
}