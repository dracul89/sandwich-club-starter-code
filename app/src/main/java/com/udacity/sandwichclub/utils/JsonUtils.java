package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private JsonUtils(){} // private constructor

    private static final String TAG = JsonUtils.class.getSimpleName();
    private static final String JSON_OBJECT_NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";


    public static Sandwich parseSandwichJson(String json) {
        try { // this would better done with builder pattern
            JSONObject jsonObject = (JSONObject) new JSONTokener(json).nextValue();
            JSONObject names = jsonObject.getJSONObject(JSON_OBJECT_NAME);
            String mainName = names.getString(MAIN_NAME);
            JSONArray aka = names.getJSONArray(ALSO_KNOWN_AS);
            List<String> alsoKnownAs = getAliasList(aka);
            String placeOfOrigin = jsonObject.getString(PLACE_OF_ORIGIN);
            String description = jsonObject.getString(DESCRIPTION);
            String image = jsonObject.getString(IMAGE);
            List<String> ingredients = getIngredients(jsonObject.getJSONArray(INGREDIENTS));
            return new Sandwich(mainName,alsoKnownAs,placeOfOrigin,description,image,ingredients);
        } catch (JSONException e) {
            Log.e(TAG,"Json Parse Failed",e);
        }

        return null;
    }

    private static List<String> getIngredients(JSONArray jsonArray) {
        List<String> ingredients = new ArrayList<>();
        for (int i = 0; i < jsonArray.length();i++) {
            try {
                ingredients.add(jsonArray.getString(i));
            } catch (JSONException e) {
                Log.e(TAG,"Ingredients parse failed",e);
            }
        }
        return ingredients;
    }

    private static List<String> getAliasList(JSONArray aka) {
        List<String> alsoKnownAs = new ArrayList<>();
        for (int i = 0; i < aka.length(); i++) {
            try {
                alsoKnownAs.add(aka.getString(i));
            } catch (JSONException e) {
                Log.e(TAG,"Failed Parsing Names",e);
            }
        }
        return alsoKnownAs;
    }
}
