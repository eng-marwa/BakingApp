package eng.android.nd.marwatalaat.bakingapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eng.android.nd.marwatalaat.bakingapp.model.Recipe;
import eng.android.nd.marwatalaat.bakingapp.ui.RecipeDetail;

/**
 * Created by MarwaTalaat on 5/20/2017.
 */

public class SharedUtil {
    public static void saveRecipe(Context context, Recipe recipe) {
        Gson gson = new Gson();
        String recipeString = gson.toJson(recipe);
        context.getSharedPreferences("recipe", Context.MODE_PRIVATE).edit().putString("recipe", recipeString).commit();
    }

    public static void saveRecipeImage(Context context, int recipeImage) {
        context.getSharedPreferences("recipe", Context.MODE_PRIVATE).edit().putInt("recipeImage", recipeImage).commit();
    }

    public static int getRecipeImage(Context context) {
        return context.getSharedPreferences("recipe", Context.MODE_PRIVATE).getInt("recipeImage", 0);
    }

    public static Recipe getRecipe(Context context) {
        String recipeString = context.getSharedPreferences("recipe", Context.MODE_PRIVATE).getString("recipe", null);
        Gson gson = new Gson();
        return gson.fromJson(recipeString, Recipe.class);
    }

    public static void saveId(Context mContext, String name , int id) {
        mContext.getSharedPreferences("fav", Context.MODE_PRIVATE).edit().putInt(name, id).commit();

    }

    public static boolean isSavedInShared(Context context, String name){
        String value = context.getSharedPreferences("fav", Context.MODE_PRIVATE).getString("name", null);
        return value == null ? false : true;
    }

    public static void removeId(Context mContext, String name) {
        mContext.getSharedPreferences("fav", Context.MODE_PRIVATE).edit().remove(name).apply();

    }

    public static int getSavedId(Context mContext, String name) {
        return mContext.getSharedPreferences("fav", Context.MODE_PRIVATE).getInt(name, 0);
    }

    public static  Map<String,Integer> getAll(Context mContext){
        Map<String,Integer> fav = (Map<String, Integer>) mContext.getSharedPreferences("fav", Context.MODE_PRIVATE).getAll();
        for(Map.Entry<String,Integer> entry : fav.entrySet()){
            Log.d("map values",entry.getKey() + ": " + entry.getValue().toString());
        }
        return fav;
    }
}
