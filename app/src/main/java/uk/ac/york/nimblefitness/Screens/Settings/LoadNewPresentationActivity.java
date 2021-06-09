package uk.ac.york.nimblefitness.Screens.Settings;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import uk.ac.york.nimblefitness.HelperClasses.Slide;
import uk.ac.york.nimblefitness.MediaHandlers.AbstractLayout;
import uk.ac.york.nimblefitness.MediaHandlers.Audio.AudioType;
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

public class LoadNewPresentationActivity extends AppCompatActivity {
    private static final int STORAGE_PERMISSION_CODE = 101;
    ShapeView shapeView;
    Button openFileBrowser, downloadXMLFile;
    FrameLayout frameLayout;
    Map<String, Slide> slides = new HashMap<>();
    String defaultBackgroundColour;
    TextModule.fontFamily font;
    String fontColour;
    String lineColour;
    String fillColour;
    String slideID;
    int slideDuration;
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


        downloadXMLFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www-users.york.ac.uk/~hew550/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        openFileBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startXMLParsing("filePath");
                new MaterialFilePicker()
                        .withActivity(LoadNewPresentationActivity.this)
                        .withRequestCode(1000)
                        .withFilter(Pattern.compile(".*\\.(xml)$"))
                        .withHiddenFiles(true)
                        .start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            startXMLParsing(filePath);
        }
    }

    void parseXML(XmlPullParser parser) throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        ShapeType shapeType = null;
        TextType textType = null;
        VideoType videoType = null;
        AudioType audioType = null;
        ImageType imageType = null;
        Slide slide = null;
        ArrayList<AbstractLayout> abstractLayouts = new ArrayList<>();

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
                            defaultBackgroundColour = parser.getAttributeValue(null,
                                                                        "backgroundcolour");
                            font = TextModule.fontFamily.valueOf(parser.getAttributeValue(null, "font"));
                            fontColour = parser.getAttributeValue(null, "fontcolour");
                            lineColour = parser.getAttributeValue(null, "linecolour");
                            fillColour = parser.getAttributeValue(null, "fillcolour");
                            fontSize = Integer.parseInt(parser.getAttributeValue(null, "fontsize"));
                            break;
                        case "slide":
                            slide = new Slide();
                            slideID = parser.getAttributeValue(null, "id");
                            slide.setDuration(Integer.parseInt(parser.getAttributeValue(null, "duration")));
                            break;
                        case "shape":
                            shapeType = new ShapeType();
                            shapeType.setShape_type(parser.getAttributeValue(null, "type"));
                            shapeType.setxStart(Integer.parseInt(parser.getAttributeValue(null, "xstart")));
                            shapeType.setyStart(Integer.parseInt(parser.getAttributeValue(null, "ystart")));
                            shapeType.setWidth(Integer.parseInt(parser.getAttributeValue(null, "width")));
                            shapeType.setHeight(Integer.parseInt(parser.getAttributeValue(null, "height")));
                            if (parser.getAttributeValue(null, "fillcolour") != null) {
                                shapeType.setColour(Color.parseColor(parser.getAttributeValue(null, "fillcolour")));
                            } else {
                                shapeType.setColour(Color.parseColor("#000000"));
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
                            shapeType.setxStart(Integer.parseInt(parser.getAttributeValue(null, "xstart")));
                            shapeType.setyStart(Integer.parseInt(parser.getAttributeValue(null, "ystart")));
                            shapeType.setxEnd(Integer.parseInt(parser.getAttributeValue(null, "xend")));
                            shapeType.setyEnd(Integer.parseInt(parser.getAttributeValue(null, "yend")));
                            if (parser.getAttributeValue(null, "linecolour") != null) {
                                shapeType.setColour(Color.parseColor(parser.getAttributeValue(null, "linecolour")));
                            } else {
                                shapeType.setColour(Color.parseColor("#000000"));
                            }
                            if (parser.getAttributeValue(null, "duration") != null) {
                                shapeType.setDuration(Integer.parseInt(parser.getAttributeValue(null, "duration")));
                            } else {
                                shapeType.setDuration(0);
                            }
                            break;
                        case "text":
                            textType = new TextType();
                            textType.setStyle(TextModule.styleFamily.normal);
                            textType.setxStart(Integer.parseInt(parser.getAttributeValue(null, "xstart")));
                            textType.setyStart(Integer.parseInt(parser.getAttributeValue(null, "ystart")));
                            if (parser.getAttributeValue(null, "font") != null) {
                                textType.setFont(TextModule.fontFamily.valueOf(parser.getAttributeValue(null, "font")));
                            } else {
                                textType.setFont(TextModule.fontFamily.monospace);
                            }
                            if (parser.getAttributeValue(null, "fontcolour") != null) {
                                textType.setFontColour(parser.getAttributeValue(null, "fontcolour"));
                            } else {
                                textType.setFontColour("#000000");
                            }
                            if (parser.getAttributeValue(null, "fontsize") != null) {
                                textType.setFontSize(parser.getAttributeValue(null, "fontsize"));
                            } else {
                                textType.setFontSize("20");
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
                            //Not sure how to implement both italics and bold or some not bold/italic some bold/italic @todo
                            break;
                        case "video":
                            videoType = new VideoType();
                            videoType.setUriPath(String.valueOf(parser.getAttributeValue(null, "urlname")));
                            videoType.setStartTime(Integer.parseInt(parser.getAttributeValue(null, "starttime")));
                            videoType.setLoop(Boolean.parseBoolean(parser.getAttributeValue(null, "loop")));
                            videoType.setxStart(Integer.parseInt(parser.getAttributeValue(null, "xstart")));
                            videoType.setyStart(Integer.parseInt(parser.getAttributeValue(null, "ystart")));
                            if (parser.getAttributeValue(null, "width") != null) {
                                videoType.setWidth(Integer.parseInt(parser.getAttributeValue(null, "width")));
                            } else {
                                videoType.setWidth(0);
                            }
                            if (parser.getAttributeValue(null, "height") != null) {
                                videoType.setHeight(Integer.parseInt(parser.getAttributeValue(null, "height")));
                            } else {
                                videoType.setHeight(0);
                            }
                            Log.i("videoTypes :", String.valueOf(videoType.getUriPath()));
                            break;
                        case "audio":
                            audioType = new AudioType();
                            audioType.setUrl(String.valueOf(parser.getAttributeValue(null, "urlname")));
                            audioType.setLoop(Boolean.parseBoolean(parser.getAttributeValue(null, "loop")));
                            if (parser.getAttributeValue(null, "starttime") != null) {
                                audioType.setStarttime(Integer.parseInt(parser.getAttributeValue(null, "starttime")));
                            } else {
                                audioType.setStarttime(0);
                            }
                            if (parser.getAttributeValue(null, "id") != null) {
                                audioType.setId(String.valueOf(parser.getAttributeValue(null, "id")));
                            } else {
                                audioType.setId("");
                            }
                            break;
                        case "image":
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
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + name);
                    }
                    break;
                case XmlPullParser.TEXT:
                    if (textType != null && !parser.getText().equals("null")) {

                        //textType.addText(parser.getText());

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
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if ((name.equalsIgnoreCase("shape") || name.equalsIgnoreCase("line")) && shapeType != null) {
                        ShapeLayout shapeLayout = new ShapeLayout(shapeType.getyStart(),shapeType.getxStart(),shapeType.getWidth(),shapeType.getHeight(),shapeType.getColour(),shapeType.getxEnd(),shapeType.getyEnd(),shapeType.getShape_type(),shapeType.getShading(),shapeType.getDuration(), this,frameLayout);
                        abstractLayouts.add(shapeLayout);
                        shapeType = null;
                    } else if (name.equalsIgnoreCase("video") && videoType != null) {
                        VideoLayout videoLayout = new VideoLayout(videoType.getUriPath(), videoType.getWidth(), videoType.getHeight(), videoType.getxStart(), videoType.getyStart(), videoType.getId(), videoType.getStartTime(), videoType.isLoop(), frameLayout, this);
                        abstractLayouts.add(videoLayout);
                        videoType = null;
                    } else if (name.equalsIgnoreCase("text") && textType != null) {
                        TextLayout textLayout = new TextLayout(textType.getText(), textType.getFont(), textType.getFontSize(), textType.getFontColour(), textType.getxStart(), textType.getyStart(), frameLayout, this);
                        abstractLayouts.add(textLayout);
                        textType = null;
                    } else if (name.equalsIgnoreCase("audio") && audioType != null) {
                        AudioType audioType1 = new AudioType(audioType.getUrl(), audioType.getStarttime(), audioType.isLoop(), audioType.getId(), this);
                        abstractLayouts.add(audioType1);
                        audioType = null;
                    } else if (name.equalsIgnoreCase("image") && imageType != null) {
                        ImageLayout imageLayout = new ImageLayout(imageType.getXCoordinate(), imageType.getYCoordinate(), imageType.getImageWidth(), imageType.getImageHeight(), imageType.getImageDuration(), imageType.getImageSource(), frameLayout, this);
                        abstractLayouts.add(imageLayout);
                        imageType = null;
                    } else if (name.equalsIgnoreCase("b") && textType != null) {
                        textType.setStyle(TextModule.styleFamily.normal);
                    } else if (name.equalsIgnoreCase("i") && textType != null) {
                        textType.setStyle(TextModule.styleFamily.normal);
                    } else if (name.equalsIgnoreCase("slide") && abstractLayouts != null) {
                        slide.setAbstractLayouts(abstractLayouts);
                        slides.put(slideID, slide);
                        slide = null;
                        abstractLayouts = null;
                    }

            }
            eventType = parser.next();
        }
    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(LoadNewPresentationActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(LoadNewPresentationActivity.this,
                    new String[]{permission},
                    requestCode);
        }
    }

    // This function is called when the user accepts or decline the permission.
    // Request Code is used to check which permission called this function.
    // This request code is provided when the user is prompt for permission.

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super
                .onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(LoadNewPresentationActivity.this,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(LoadNewPresentationActivity.this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    void startXMLParsing(String filePath) {
        XmlPullParserFactory pullParserFactory;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            InputStream inputStream = new FileInputStream(new File(filePath));

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);

            parseXML(parser);

            for (AbstractLayout abstractLayout : slides.get("Slide1").getAbstractLayouts()){
                abstractLayout.draw();
            }

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }
}