package uk.ac.york.nimblefitness.Screens.Routines;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.util.Log;

import com.google.gson.Gson;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReferenceArray;

import uk.ac.york.nimblefitness.HelperClasses.Exercise;
import uk.ac.york.nimblefitness.HelperClasses.Routine;
import uk.ac.york.nimblefitness.MediaHandlers.Audio.AudioType;
import uk.ac.york.nimblefitness.MediaHandlers.Graphics.ShapeType;
import uk.ac.york.nimblefitness.MediaHandlers.Images.ImageLayout;
import uk.ac.york.nimblefitness.MediaHandlers.Images.ImageType;
import uk.ac.york.nimblefitness.MediaHandlers.Text.TextLayout;
import uk.ac.york.nimblefitness.MediaHandlers.Text.TextModule;
import uk.ac.york.nimblefitness.MediaHandlers.Text.TextType;
import uk.ac.york.nimblefitness.MediaHandlers.Video.VideoLayout;
import uk.ac.york.nimblefitness.MediaHandlers.Video.VideoType;
import uk.ac.york.nimblefitness.R;

public class RoutineData {
    Routine routine;
    int resourceFile;
    String defaultBackgroundColour;
    TextModule.fontFamily font;
    String fontColour;
    String lineColour;
    String fillColour;
    int fontSize;
    Context context;
    ArrayList<Routine> routineArrayList = new ArrayList<>();
    ArrayList<Exercise> exerciseArrayList = new ArrayList<>();
    ArrayList<TextLayout> textLayoutArrayList = new ArrayList<>();
    ArrayList<ShapeType> shapeTypeArrayList = new ArrayList<>();
    ArrayList<AudioType> audioTypeArrayList = new ArrayList<>();



    public RoutineData(Context context, int resourceFile) {
        this.context = context;
        this.resourceFile = resourceFile;
    }

    public ArrayList<Routine> getRoutine() {
        startXMLParsing();

        return routineArrayList;
    }

    void startXMLParsing() {
        XmlPullParserFactory pullParserFactory;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            InputStream inputStream = context.getResources().openRawResource(resourceFile);

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);

            parseXML(parser);

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }

    void parseXML(XmlPullParser parser) throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        ShapeType shapeType = null;
        TextType textType = null;
        VideoType videoType = null;
        AudioType audioType = null;
        ImageType imageType = null;
        Routine routine = null;
        Exercise exercise = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name;
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    switch (name) {
                        case "defaults":
                            defaultBackgroundColour = parser.getAttributeValue(null, "backgroundcolour");
                            font = TextModule.fontFamily.valueOf(parser.getAttributeValue(null, "font"));
                            fontColour = parser.getAttributeValue(null, "fontcolour");
                            lineColour = parser.getAttributeValue(null, "linecolour");
                            fillColour = parser.getAttributeValue(null, "fillcolour");
                            fontSize = Integer.parseInt(parser.getAttributeValue(null, "fontsize"));
                            break;
                        case "slide":
                        case "slideshow":
                            break;
                        case "routine":
                            routine = new Routine();
                            routine.setRoutineImage(parser.getAttributeValue(null, "routineImage"));
                            routine.setRoutineName(parser.getAttributeValue(null, "routineName"));
                            routine.setRoutineSummary(parser.getAttributeValue(null, "routineSummary"));
                            routine.setRating(Integer.parseInt(parser.getAttributeValue(null, "rating")));
                            routine.setSets(Integer.parseInt(parser.getAttributeValue(null, "sets")));
                            routine.setRestBetweenSets(Integer.parseInt(parser.getAttributeValue(null, "restBetweenSets")));
                            routine.setSetsRemaining(Integer.parseInt(parser.getAttributeValue(null, "sets")));
                            routine.setCurrentExercise(0);
                            break;
                        case "exercise":
                            exercise = new Exercise();
                            exercise.setExerciseName(parser.getAttributeValue(null, "exerciseName"));
                            exercise.setExerciseDescription(parser.getAttributeValue(null, "exerciseDescription"));
                            exercise.setRepType(parser.getAttributeValue(null, "repType"));
                            exercise.setReps(Integer.parseInt(parser.getAttributeValue(null, "reps")));
                            exercise.setTimePerRep(Integer.parseInt(parser.getAttributeValue(null, "timePerRep")));
                            exercise.setMovesPerRep(Float.parseFloat(parser.getAttributeValue(null, "movesPerRep")));
                            exercise.setRestAfterFinish(Integer.parseInt(parser.getAttributeValue(null, "restAfterFinish")));
                            exercise.setColour(Color.parseColor(parser.getAttributeValue(null, "colour")));
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
                            }else{
                                shapeType.setColour(Color.parseColor(fillColour));
                            }
                            if(parser.getAttributeValue(null, "duration") != null){
                                shapeType.setDuration(Integer.parseInt(parser.getAttributeValue(null, "duration")));
                            }else{
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
                            if(parser.getAttributeValue(null, "linecolour") != null){
                                shapeType.setColour(Color.parseColor(parser.getAttributeValue(null, "linecolour")));
                            }else{
                                shapeType.setColour(Color.parseColor(lineColour));
                            }
                            if(parser.getAttributeValue(null, "duration") != null){
                                shapeType.setDuration(Integer.parseInt(parser.getAttributeValue(null, "duration")));
                            }else{
                                shapeType.setDuration(0);
                            }
                            break;
                        case "text":
                            textType = new TextType();
                            textType.setStyle(TextModule.styleFamily.normal);
                            textType.setXstart(Integer.parseInt(parser.getAttributeValue(null,"xstart")));
                            textType.setYstart(Integer.parseInt(parser.getAttributeValue(null,"ystart")));
                            if(parser.getAttributeValue(null, "font") != null){
                                textType.setFont(TextModule.fontFamily.valueOf(parser.getAttributeValue(null, "font")));
                            }else{
                                textType.setFont(font);
                            }
                            if(parser.getAttributeValue(null, "fontcolour") != null){
                                textType.setFontcolour(parser.getAttributeValue(null, "fontcolour"));
                            }else{
                                textType.setFontcolour(fontColour);
                            }
                            if(parser.getAttributeValue(null, "fontsize") != null){
                                textType.setFontsize(parser.getAttributeValue(null, "fontsize"));
                            }else{
                                textType.setFontsize(String.valueOf(fontSize));
                            }
                            break;
                        case "b":
                            assert textType != null;
                            if (textType.getStyle() == TextModule.styleFamily.italic || textType.getStyle() == TextModule.styleFamily.bold_italic) {
                                textType.setStyle(TextModule.styleFamily.bold_italic);
                            } else{
                                textType.setStyle(TextModule.styleFamily.bold);
                            }
                            break;
                        case "i":
                            assert textType != null;
                            if (textType.getStyle() == TextModule.styleFamily.bold || textType.getStyle() == TextModule.styleFamily.bold_italic) {
                                textType.setStyle(TextModule.styleFamily.bold_italic);
                            } else{
                                textType.setStyle(TextModule.styleFamily.italic);
                            }
                            //Not sure how to implement both italics and bold or some not bold/italic some bold/italic @todo
                            break;
                        case "video":
                            videoType = new VideoType();
                            videoType.setUriPath(String.valueOf(parser.getAttributeValue(null, "urlname")));
                            videoType.setStarttime(Integer.parseInt(parser.getAttributeValue(null, "starttime")));
                            videoType.setLoop(Boolean.parseBoolean(parser.getAttributeValue(null, "loop")));
                            videoType.setXstart(Integer.parseInt(parser.getAttributeValue(null, "xstart")));
                            videoType.setYstart(Integer.parseInt(parser.getAttributeValue(null, "ystart")));
                            if(parser.getAttributeValue(null, "width") != null) {
                                videoType.setWidth(Integer.parseInt(parser.getAttributeValue(null, "width")));
                            }else{
                                videoType.setWidth(0);
                            }
                            if(parser.getAttributeValue(null, "height") != null) {
                                videoType.setHeight(Integer.parseInt(parser.getAttributeValue(null, "height")));
                            }else{
                                videoType.setHeight(0);
                            }
                            break;
                        case "audio":
                            audioType = new AudioType();
                            audioType.setUrl(String.valueOf(parser.getAttributeValue(null, "urlname")));
                            audioType.setLoop(Boolean.parseBoolean(parser.getAttributeValue(null, "loop")));
                            if(parser.getAttributeValue(null, "starttime") != null) {
                                audioType.setStarttime(Integer.parseInt(parser.getAttributeValue(null, "starttime")));
                            }else{
                                audioType.setStarttime(0);
                            }
                            if(parser.getAttributeValue(null, "id") != null) {
                                audioType.setId(String.valueOf(parser.getAttributeValue(null, "id")));
                            }else{
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
                            if(parser.getAttributeValue(null, "duration") != null) {
                                imageType.setImageDuration(Integer.parseInt(parser.getAttributeValue(null, "duration")));
                            }else{
                                imageType.setImageDuration(0);
                            }
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + name);
                    }
                    break;
                case XmlPullParser.TEXT:
                    if (textType != null && !parser.getText().equals("null")) {
                        switch(textType.getStyle()) {
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
                        if (shapeType.getShape_type().equals("RECTANGLE") | shapeType.getShape_type().equals("OVAL")) {
                            if (shapeType.getColour() == 0) {
                                shapeTypeArrayList.add(new ShapeType(shapeType.getxStart(), shapeType.getyStart(), shapeType.getWidth(), shapeType.getHeight(), 0, shapeType.getShape_type(), shapeType.getShading(), shapeType.getDuration()));
                                //shapeView.addShape(shapeType.getxStart(), shapeType.getyStart(), shapeType.getHeight(), shapeType.getWidth(), shapeType.getShading(), shapeType.getShape_type(), shapeType.getDuration());
                            } else {
                                shapeTypeArrayList.add(new ShapeType(shapeType.getxStart(), shapeType.getyStart(), shapeType.getWidth(), shapeType.getHeight(), shapeType.getColour(), shapeType.getShape_type(), null, shapeType.getDuration()));

                                //shapeView.addShape(shapeType.getxStart(), shapeType.getyStart(), shapeType.getHeight(), shapeType.getWidth(), shapeType.getColour(), shapeType.getShape_type(), shapeType.getDuration());

                            }
                        } else if (shapeType.getShape_type().equals("LINE")) {
                            shapeTypeArrayList.add(new ShapeType(shapeType.getxStart(), shapeType.getyStart(), shapeType.getWidth(), shapeType.getHeight(), shapeType.getColour(), shapeType.getShape_type(), null, shapeType.getDuration()));

                            //shapeView.addLine(shapeType.getxStart(), shapeType.getyStart(), shapeType.getxEnd(), shapeType.getyEnd(), shapeType.getColour(), shapeType.getDuration());
                        }
                        shapeType = null;
                    }
                    else if (name.equalsIgnoreCase("video") && videoType != null) {

                        exercise.setExerciseVideo(new VideoLayout(videoType.getUriPath(),videoType.getWidth(),videoType.getHeight(),videoType.getXstart(),videoType.getYstart(),videoType.getId(),videoType.getStarttime(),videoType.isLoop(), null, null));

                        //VideoLayout videoLayout = new VideoLayout(videoType.getUriPath(),videoType.getWidth(),videoType.getHeight(),videoType.getXstart(),videoType.getYstart(),videoType.getId(),videoType.getStarttime(),videoType.isLoop(), frameLayout, this);
                        //videoLayout.PlayVideo();
                        videoType = null;
                    }
                    else if (name.equalsIgnoreCase("text") && textType != null) {

                        textLayoutArrayList.add(new TextLayout(textType.getText(),textType.getFont(),textType.getFontsize(),textType.getFontcolour(),textType.getXstart(),textType.getYstart(), null,null));
                        //TextLayout textLayout = new TextLayout(textType.getText(),textType.getFont(),textType.getFontsize(),textType.getFontcolour(),textType.getXstart(),textType.getYstart(), frameLayout, this);
                        //textLayout.writeText();
                        textType = null;
                    }
                    else if (name.equalsIgnoreCase("audio") && audioType != null){

                        audioTypeArrayList.add(new AudioType(audioType.getUrl(),audioType.getStarttime(),audioType.isLoop(),audioType.getId(), null));

                        //AudioType audioType1 = new AudioType(audioType.getUrl(),audioType.getStarttime(),audioType.isLoop(),audioType.getId(), this);
                        //audioType1.play();
                        audioType = null;
                    }
                    else if (name.equalsIgnoreCase("image") && imageType != null){

                        exercise.setMuscleGroupImage(new ImageLayout(imageType.getXCoordinate(),imageType.getYCoordinate(), imageType.getImageWidth(), imageType.getImageHeight(), imageType.getImageDuration(), imageType.getImageSource(),null, null));

                        //ImageLayout imageLayout = new ImageLayout(imageType.getXCoordinate(),imageType.getYCoordinate(), imageType.getImageWidth(), imageType.getImageHeight(), imageType.getImageDuration(), imageType.getImageSource(),frameLayout, this);
                        imageType = null;
                    }
                    else if (name.equalsIgnoreCase("b") && textType != null){
                        textType.setStyle(TextModule.styleFamily.normal);
                    }
                    else if (name.equalsIgnoreCase("i") && textType != null){
                        textType.setStyle(TextModule.styleFamily.normal);
                    }
                    else if (name.equalsIgnoreCase("routine") && routine != null){
                        routine.setExerciseArrayList((ArrayList<Exercise>) exerciseArrayList.clone());
                        routineArrayList.add(routine);
                        exerciseArrayList.clear();
                        routine = null;
                    }
                    else if (name.equalsIgnoreCase("exercise") && exercise != null){
                        exercise.setAudioTypes((ArrayList<AudioType>) audioTypeArrayList.clone());
                        exercise.setBackgroundShapes((ArrayList<ShapeType>) shapeTypeArrayList.clone());
                        exercise.setTextLayouts((ArrayList<TextLayout>) textLayoutArrayList.clone());
                        exerciseArrayList.add(exercise);
                        audioTypeArrayList.clear();
                        shapeTypeArrayList.clear();
                        textLayoutArrayList.clear();
                        exercise = null;
                    }

            }
            eventType = parser.next();
        }
    }
}
