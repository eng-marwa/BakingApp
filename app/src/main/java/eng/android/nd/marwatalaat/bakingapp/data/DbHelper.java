package eng.android.nd.marwatalaat.bakingapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


class DbHelper extends SQLiteOpenHelper {


    private static final String NAME = "Baking.db";
    private static final int VERSION =3;


    DbHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String builder = "CREATE TABLE " + Contract.Baking.TABLE_NAME + " ("
                + Contract.Baking._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Contract.Baking.COLUMN_RECIPE_NAME + " TEXT NOT NULL, "
                + Contract.Baking.COLUMN_RECIPE_ID + " INTEGER NOT NULL, "
                + Contract.Baking.COLUMN_RECIPE_SERVING + " INTEGER NOT NULL, "
                + Contract.Baking.COLUMN_RECIPE_INGREDIENT + " TEXT NOT NULL, "
                + Contract.Baking.COLUMN_RECIPE_STEPS + " TEXT NOT NULL );";

        db.execSQL(builder);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS " + Contract.Baking.TABLE_NAME);
        onCreate(db);
    }
}
