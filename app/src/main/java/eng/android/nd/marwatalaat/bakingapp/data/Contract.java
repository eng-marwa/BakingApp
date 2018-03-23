package eng.android.nd.marwatalaat.bakingapp.data;


import android.net.Uri;
import android.provider.BaseColumns;

import com.google.common.collect.ImmutableList;


public final class Contract {

    static final String AUTHORITY = "eng.marwa.baking";
    static final String PATH_BAKING = "baking";
    static final String PATH_RECIPE = "baking/*";
    private static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

    private Contract() {
    }

    @SuppressWarnings("unused")
    public static final class Baking implements BaseColumns {

        public static final Uri URI = BASE_URI.buildUpon().appendPath(PATH_BAKING).build();
        public static final String COLUMN_ID = "_ID";
        public static final String COLUMN_RECIPE_NAME = "recipe_name";
        public static final String COLUMN_RECIPE_ID = "recipe_id";
        public static final String COLUMN_RECIPE_SERVING = "recipe_serving";
        public static final String COLUMN_RECIPE_INGREDIENT = "recipe_ingredient";
        public static final String COLUMN_RECIPE_STEPS = "recipe_steps";


        public static final int POSITION_ID = 0;
        public static final int POSITION_RECIPE_NAME = 1;
        public static final int POSITION_RECIPE_ID = 2;
        public static final int POSITION_RECIPE_SERVING = 3;
        public static final int POSITION_RECIPE_INGREDIENT = 4;
        public static final int POSITION_RECIPE_STEPS = 5;


        public static final ImmutableList<String> BAKING_COLUMNS = ImmutableList.of(
                _ID,
                COLUMN_RECIPE_NAME,
                COLUMN_RECIPE_ID,
                COLUMN_RECIPE_SERVING,
                COLUMN_RECIPE_INGREDIENT,
                COLUMN_RECIPE_STEPS

        );
        static final String TABLE_NAME = "baking";

        public static Uri makeUriForRecipe(String recipeId) {
            return URI.buildUpon().appendPath(recipeId).build();
        }

        static String getTalentFromUri(Uri queryUri) {
            return queryUri.getLastPathSegment();
        }


    }

}
