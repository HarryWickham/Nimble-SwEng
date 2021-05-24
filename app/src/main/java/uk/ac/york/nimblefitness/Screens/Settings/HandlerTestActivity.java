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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.facebook.share.Share;
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
import java.util.regex.Pattern;

import uk.ac.york.nimblefitness.HelperClasses.ShareService;
import uk.ac.york.nimblefitness.MediaHandlers.Graphics.ShapeType;
import uk.ac.york.nimblefitness.MediaHandlers.Graphics.ShapeView;
import uk.ac.york.nimblefitness.MediaHandlers.Text.TextLayout;
import uk.ac.york.nimblefitness.MediaHandlers.Text.TextModule;
import uk.ac.york.nimblefitness.MediaHandlers.Text.TextType;
import uk.ac.york.nimblefitness.MediaHandlers.Video.VideoLayout;
import uk.ac.york.nimblefitness.MediaHandlers.Video.VideoType;
import uk.ac.york.nimblefitness.R;

public class HandlerTestActivity extends AppCompatActivity {
    ShapeView shapeView;
    ArrayList<ShapeType> shapeTypes;
    ArrayList<TextType> textTypes;
    ArrayList<VideoType> videoTypes;
    Button openFileBrowser, downloadXMLFile;
    FrameLayout frameLayout;

    private static final int STORAGE_PERMISSION_CODE = 101;

    protected void onHandleIntent() {
        Log.i("TAG", "onHandleIntent: ");
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Tutorialspoint.com");
        startActivity(Intent.createChooser(sharingIntent, "Sharing"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_test);

        shapeView = findViewById(R.id.shapeView);
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
        openFileBrowser = findViewById(R.id.openFileBrowser);
        downloadXMLFile = findViewById(R.id.downloadXMLFile);
        frameLayout = findViewById(R.id.handlerTestFrame);

        /*TextLayout textLayout = new TextLayout("Hello World", TextModule.fontFamily.sans_serif, "52", "#00ccff", TextModule.styleFamily.italic, 200, 1400, frameLayout, this);
        textLayout.writeText();

        VideoLayout videoLayout = new VideoLayout("https://www-users.york.ac.uk/~hew550/testvideo.mp4",1000,1000,200,200,"Video", 5,false,frameLayout,this);
        videoLayout.PlayVideo();*/

        downloadXMLFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new ShareService("Subject", "Text", "Title").ShareContent());
                /*Uri uri = Uri.parse("https://www-users.york.ac.uk/~hew550/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);*/
            }
        });
        openFileBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startXMLParsing("filePath");
                new MaterialFilePicker()
                        .withActivity(HandlerTestActivity.this)
                        .withRequestCode(1000)
                        //.withCloseMenu(true)
                        .withFilter(Pattern.compile(".*\\.(xml)$"))
                        //.withFilterDirectories(false)
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

    private void parseXML(XmlPullParser parser) throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        ShapeType shapeType = null;
        TextType textType = null;
        VideoType videoType = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name;
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    shapeTypes = new ArrayList();
                    textTypes = new ArrayList();
                    videoTypes = new ArrayList();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    switch (name) {
                        case "slide":
                            Log.i("slide: ", "STARTING DOCUMENT");
                            break;
                        case "shape":
                            shapeType = new ShapeType();
                            shapeType.setShape_type(parser.getAttributeValue(null, "type"));
                            shapeType.setxStart(Integer.parseInt(parser.getAttributeValue(null, "xstart")));
                            shapeType.setyStart(Integer.parseInt(parser.getAttributeValue(null, "ystart")));
                            shapeType.setWidth(Integer.parseInt(parser.getAttributeValue(null, "width")));
                            shapeType.setHeight(Integer.parseInt(parser.getAttributeValue(null, "height")));
                            if(parser.getAttributeValue(null, "fillcolour") != null){
                                shapeType.setColour(Color.parseColor(parser.getAttributeValue(null, "fillcolour")));
                            }else{shapeType.setColour(Color.parseColor("#000000"));}
                            if(parser.getAttributeValue(null, "duration") != null){
                                shapeType.setDuration(Integer.parseInt(parser.getAttributeValue(null, "duration")));
                            }else{shapeType.setDuration(0);}
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
                            if(parser.getAttributeValue(null, "linecolour") != null){
                                shapeType.setColour(Color.parseColor(parser.getAttributeValue(null, "linecolour")));
                            }else{shapeType.setColour(Color.parseColor("#000000"));}
                            if(parser.getAttributeValue(null, "duration") != null){
                                shapeType.setDuration(Integer.parseInt(parser.getAttributeValue(null, "duration")));
                            }else{shapeType.setDuration(0);}


                            break;
                        case "text":
                            textType = new TextType();
                            textType.setStyle(TextModule.styleFamily.normal);
                            textType.setXstart(Integer.parseInt(parser.getAttributeValue(null,"xstart")));
                            textType.setYstart(Integer.parseInt(parser.getAttributeValue(null,"ystart")));
                            if(parser.getAttributeValue(null, "font") != null){
                                textType.setFont(TextModule.fontFamily.valueOf(parser.getAttributeValue(null, "font")));
                            }else{textType.setFont(TextModule.fontFamily.monospace);}
                            if(parser.getAttributeValue(null, "fontcolour") != null){
                                textType.setFontcolour(parser.getAttributeValue(null, "fontcolour"));
                            }else{textType.setFontcolour("#000000");}
                            if(parser.getAttributeValue(null, "fontsize") != null){
                                textType.setFontsize(parser.getAttributeValue(null, "fontsize"));
                            }else{textType.setFontsize("20");}
                            break;
                        /*case "b":
                            assert textType != null;
                            textType.setStyle(TextModule.styleFamily.bold);
                            break;
                        case "i":
                            assert textType != null;
                            textType.setStyle(TextModule.styleFamily.italic);
                            //Not sure how to implement both italics and bold or some not bold/italic some bold/italic @todo
                            break;*/
                        case "video":
                            videoType = new VideoType();
                            videoType.setUriPath(String.valueOf(parser.getAttributeValue(null, "urlname")));
                            videoType.setStarttime(Integer.parseInt(parser.getAttributeValue(null, "starttime")));
                            videoType.setLoop(Boolean.parseBoolean(parser.getAttributeValue(null, "loop")));
                            videoType.setXstart(Integer.parseInt(parser.getAttributeValue(null, "xstart")));
                            videoType.setYstart(Integer.parseInt(parser.getAttributeValue(null, "ystart")));
                            if(parser.getAttributeValue(null, "width") != null) {
                                videoType.setWidth(Integer.parseInt(parser.getAttributeValue(null, "width")));
                            }else{videoType.setWidth(0);}
                            if(parser.getAttributeValue(null, "height") != null) {
                                videoType.setHeight(Integer.parseInt(parser.getAttributeValue(null, "height")));
                            }else{videoType.setHeight(0);}
                            videoTypes.add(videoType);
                            Log.i("videoTypes :", String.valueOf(videoType.getUriPath()));
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + name);
                    }
                    if(textType != null) {
                        //Log.i("text :", String.valueOf(parser.nextText()));
                        textType.setText(String.valueOf(parser.nextText()));
                        Log.i("textType :", String.valueOf(textType.getText()));
                        textTypes.add(textType);
                        break;
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if ((name.equalsIgnoreCase("shape") || name.equalsIgnoreCase("line")) && shapeType != null) {
                        shapeTypes.add(shapeType);
                    }
                    else if (name.equalsIgnoreCase("video") && videoType != null) {

                    }
                    else if (name.equalsIgnoreCase("text") && textType != null) {
                        Log.i("END_TAG: textType :", String.valueOf(textType.getText()));
                        textTypes.add(textType);
                    }
            }
            eventType = parser.next();
        }
    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(HandlerTestActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(HandlerTestActivity.this,
                    new String[]{permission},
                    requestCode);
        } else {
            Toast.makeText(HandlerTestActivity.this,
                    "Permission already granted",
                    Toast.LENGTH_SHORT)
                    .show();
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
                Toast.makeText(HandlerTestActivity.this,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(HandlerTestActivity.this,
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

            //InputStream inputStream = new FileInputStream(new File(getApplicationContext().getFilesDir().getPath() + "/data.xml"));
            InputStream inputStream = new FileInputStream(new File(filePath));

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);

            parseXML(parser);

            for (ShapeType shapeType : shapeTypes) {
                if (shapeType.getShape_type().equals("RECTANGLE") | shapeType.getShape_type().equals("OVAL")) {
                    if (shapeType.getColour() == 0) {
                        shapeView.addShape(shapeType.getxStart(), shapeType.getyStart(), shapeType.getHeight(), shapeType.getWidth(), shapeType.getShading(), shapeType.getShape_type(), shapeType.getDuration());
                    } else {
                        shapeView.addShape(shapeType.getxStart(), shapeType.getyStart(), shapeType.getHeight(), shapeType.getWidth(), shapeType.getColour(), shapeType.getShape_type(), shapeType.getDuration());

                    }
                } else if (shapeType.getShape_type().equals("LINE")) {
                    shapeView.addLine(shapeType.getxStart(), shapeType.getyStart(), shapeType.getxEnd(), shapeType.getyEnd(), shapeType.getColour(), shapeType.getDuration());
                }
            }
            for (TextType textType : textTypes) {
                TextLayout textLayout = new TextLayout(textType.getText(),textType.getFont(),textType.getFontsize(),textType.getFontcolour(),textType.getStyle(),textType.getXstart(),textType.getYstart(), frameLayout, this);
                textLayout.writeText();
            }
            for (VideoType videoType : videoTypes) {
                VideoLayout videoLayout = new VideoLayout(videoType.getUriPath(),videoType.getWidth(),videoType.getHeight(),videoType.getXstart(),videoType.getYstart(),videoType.getId(),videoType.getStarttime(),videoType.isLoop(), frameLayout, this);
                videoLayout.PlayVideo();
            }

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }
}