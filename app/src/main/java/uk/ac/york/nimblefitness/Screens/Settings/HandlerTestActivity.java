package uk.ac.york.nimblefitness.Screens.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

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

import uk.ac.york.nimblefitness.MediaHandlers.Graphics.ShapeType;
import uk.ac.york.nimblefitness.MediaHandlers.Graphics.ShapeView;
import uk.ac.york.nimblefitness.R;
import uk.ac.york.nimblefitness.Screens.MainActivity;

public class HandlerTestActivity extends AppCompatActivity {
    ShapeView shapeView;
    ArrayList<ShapeType> shapeTypes;
    Button openFileBrowser, downloadXMLFile;

    private static final int STORAGE_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_test);

        shapeView = findViewById(R.id.shapeView);
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
        openFileBrowser = findViewById(R.id.openFileBrowser);
        downloadXMLFile = findViewById(R.id.downloadXMLFile);

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

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name;
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    shapeTypes = new ArrayList();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    switch (name) {
                        case "shape":
                            shapeType = new ShapeType();
                            shapeType.setShape_type(parser.getAttributeValue(null, "type"));
                            shapeType.setxStart(Integer.parseInt(parser.getAttributeValue(null, "xstart")));
                            shapeType.setyStart(Integer.parseInt(parser.getAttributeValue(null, "ystart")));
                            shapeType.setWidth(Integer.parseInt(parser.getAttributeValue(null, "width")));
                            shapeType.setHeight(Integer.parseInt(parser.getAttributeValue(null, "height")));
                            shapeType.setColour(Color.parseColor(parser.getAttributeValue(null, "fillcolour")));
                            shapeType.setDuration(Integer.parseInt(parser.getAttributeValue(null, "duration")));
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
                            shapeType.setColour(Color.parseColor(parser.getAttributeValue(null, "linecolour")));
                            shapeType.setDuration(Integer.parseInt(parser.getAttributeValue(null, "duration")));
                            break;
                        case "text":


                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if ((name.equalsIgnoreCase("shape") || name.equalsIgnoreCase("line")) && shapeType != null) {
                        shapeTypes.add(shapeType);
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

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }
}