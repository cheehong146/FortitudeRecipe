package com.example.fortituderecipe;

import android.content.Context;

import com.example.fortituderecipe.Objects.Recipe;
import com.example.fortituderecipe.Objects.RecipeType;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public final class ParseXML {

    private final String ns = null;
    private Context mContext;

    public ParseXML(Context mContext) {
        this.mContext = mContext;
    }

    public ArrayList<Recipe> parseRecipeXML() throws NullPointerException {
        XmlPullParserFactory parserFactory;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream is = mContext.getResources().openRawResource(R.raw.recipes);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);

            return processParsingRecipe(parser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();//error handling TODO
        } catch (IOException e) {
            e.printStackTrace();//error handling TODO
        }
        return null;
    }


    public ArrayList<RecipeType> parseRecipeTypeXML() throws NullPointerException {
        XmlPullParserFactory parserFactory;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream is = mContext.getResources().openRawResource(R.raw.recipetypes);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);

            return processParseRecipeType(parser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();//error handling TODO
        } catch (IOException e) {
            e.printStackTrace();//error handling TODO
        }
        return null;
    }

    public ArrayList<Recipe> processParsingRecipe(XmlPullParser parser) throws IOException, XmlPullParserException {
        Recipe recipe = null;
        int eventType = parser.getEventType();

        ArrayList<Recipe> recipes = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String eltName = null;

            switch (eventType) {
                case XmlPullParser.START_TAG:
                    eltName = parser.getName();
                    if ("recipes".equals(eltName)) {
                        recipes = new ArrayList<>();
                    } else if ("recipe".equals(eltName) && recipes != null) {
                        recipe = new Recipe();
                        recipes.add(recipe);
                    } else if ("title".equals(eltName)) {
                        recipe.setTitle(readTitle(parser));
                    } else if ("type".equals(eltName)) {
                        recipe.setType(readType(parser));
                    } else if ("description".equals(eltName)) {
                        recipe.setDescription(readDescription(parser));
                    } else if ("timetaken".equals(eltName)) {
                        recipe.setTimeTaken(readTimetaken(parser));
                    } else if ("imageurl".equals(eltName)) {
                        recipe.setImageUrl(readImageUrl(parser));
                    } else if ("ingredients".equals(eltName)) {
                        recipe.setIngredients(readIngredient(parser));
                    } else if ("instruction".equals(eltName)) {
                        recipe.setInstructions(readInstruction(parser));
                    }
            }
            eventType = parser.next();
        }
        return recipes;
    }

    public ArrayList<RecipeType> processParseRecipeType(XmlPullParser parser) throws IOException, XmlPullParserException {
        int eventType = parser.getEventType();

        ArrayList<RecipeType> recipeTypeArrayList = null;
        RecipeType recipeType = null;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String eltName = null;

            switch (eventType) {
                case XmlPullParser.START_TAG:
                    eltName = parser.getName();
                    if ("recipetypes".equals(eltName)) {
                        recipeTypeArrayList = new ArrayList<>();
                    } else if ("recipetype".equals(eltName) && recipeTypeArrayList != null) {
                        recipeType = new RecipeType();
                        readRecipeType(parser, recipeType);
                        recipeTypeArrayList.add(recipeType);
                    }
                    break;
            }
            eventType = parser.next();
        }

        return recipeTypeArrayList;
    }

    private String readType(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "type");
        String type = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "type");
        return type;
    }

    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "title");
        return title;
    }

    private String readDescription(XmlPullParser parser) throws
            IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "description");
        String description = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "description");
        return description;
    }

    private String readTimetaken(XmlPullParser parser) throws
            IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "timetaken");
        String timetaken = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "timetaken");
        return timetaken;
    }

    private String readImageUrl(XmlPullParser parser) throws
            IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "imageurl");
        String imageUrl = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "imageurl");
        return imageUrl;
    }

    private String readIngredient(XmlPullParser parser) throws
            IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "ingredients");
        String ingredient = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "ingredients");
        return ingredient;
    }

    private String readInstruction(XmlPullParser parser) throws
            IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "instruction");
        String instruction = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "instruction");
        return instruction;
    }

    private String readName(XmlPullParser parser) throws
            IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "name");
        String name = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "name");
        return name;
    }

    private String readRecipeType(XmlPullParser parser, RecipeType recipeType) throws
            IOException, XmlPullParserException {
        String id = "";
        parser.require(XmlPullParser.START_TAG, ns, "recipetype");
        String tag = parser.getName();
        if (tag.equals("recipetype")) {
            id = parser.getAttributeValue(null, "id");
            recipeType.setmId(id);
            parser.nextTag();
            recipeType.setName(readName(parser));
            parser.nextTag();
        }
        parser.require(XmlPullParser.END_TAG, ns, "recipetype");
        return id;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

}
