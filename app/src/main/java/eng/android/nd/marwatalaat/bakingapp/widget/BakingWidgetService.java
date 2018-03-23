package eng.android.nd.marwatalaat.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import eng.android.nd.marwatalaat.bakingapp.R;
import eng.android.nd.marwatalaat.bakingapp.data.Contract;
import eng.android.nd.marwatalaat.bakingapp.model.Ingredient;

/**
 * Created by MarwaTalaat on 3/16/2017.
 */

public class BakingWidgetService extends RemoteViewsService {


    public static String recipeName;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new BakingViewsFactory(this.getApplicationContext(),
                intent);
    }

    class BakingViewsFactory implements RemoteViewsFactory {

        private Context context;
        private int appWidgetId;
        private List<Ingredient> ingredients;

        public BakingViewsFactory(Context ctxt, Intent intent) {
            appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        }

        @Override
        public void onCreate() {
            // no-op
        }

        @Override
        public void onDestroy() {
            // no-op
        }

        @Override
        public int getCount() {
            System.out.println(ingredients!=null?ingredients.size():0);
            return ingredients!=null?ingredients.size():0;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews row = new RemoteViews(getApplicationContext().getPackageName(), R.layout.list_item_baking_widget);
            Ingredient ingredient = ingredients.get(position);
            double quantity = ingredient.getQuantity();
            String measure = ingredient.getMeasure();
            String name = ingredient.getIngredient();


            row.setTextViewText(R.id.textView_quantity, String.valueOf(quantity));
            row.setTextViewText(R.id.textView_measure, measure);
            row.setTextViewText(R.id.textView_ingredient, name);


            Intent fillInIntent = new Intent();
            Uri bakingUri = Contract.Baking.URI;
            fillInIntent.setData(bakingUri);
            fillInIntent.putExtra("ingredient", name);
            row.setOnClickFillInIntent(R.id.widget_toolbar, fillInIntent);


            return row;
        }

        @Override
        public RemoteViews getLoadingView() {
            return (null);
        }

        @Override
        public int getViewTypeCount() {
            return (1);
        }

        @Override
        public long getItemId(int position) {
            return (position);
        }

        @Override
        public boolean hasStableIds() {
            return (true);
        }

        @Override
        public void onDataSetChanged() {
            System.out.println("onDataSetChanged");
            String ingredientsList = null;
            Cursor cursor = getApplicationContext().getContentResolver().query(Contract.Baking.URI,
                    new String[]{Contract.Baking.COLUMN_RECIPE_NAME, Contract.Baking.COLUMN_RECIPE_INGREDIENT}, null, null, null);
            ingredients = new ArrayList<>(cursor.getCount());

            if (cursor.moveToFirst()) {
                do {
                    recipeName = cursor.getString(cursor.getColumnIndex(Contract.Baking.COLUMN_RECIPE_NAME));
                    ingredientsList = cursor.getString(cursor.getColumnIndex(Contract.Baking.COLUMN_RECIPE_INGREDIENT));
                } while (cursor.moveToNext());
            }


            Gson gson = new Gson();
            ingredients = gson.fromJson(ingredientsList, new TypeToken<ArrayList<Ingredient>>() {
            }.getType());
            cursor.close();
        }
    }
}


