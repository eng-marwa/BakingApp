package eng.android.nd.marwatalaat.bakingapp.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eng.android.nd.marwatalaat.bakingapp.R;
import eng.android.nd.marwatalaat.bakingapp.adapter.BakingAdapter;
import eng.android.nd.marwatalaat.bakingapp.api.BakingAPI;
import eng.android.nd.marwatalaat.bakingapp.api.BakingClient;
import eng.android.nd.marwatalaat.bakingapp.data.Contract;
import eng.android.nd.marwatalaat.bakingapp.model.Ingredient;
import eng.android.nd.marwatalaat.bakingapp.model.Recipe;
import eng.android.nd.marwatalaat.bakingapp.model.Step;
import eng.android.nd.marwatalaat.bakingapp.sync.RecipeSyncJob;
import eng.android.nd.marwatalaat.bakingapp.util.NetworkUtil;
import eng.android.nd.marwatalaat.bakingapp.util.SharedUtil;
import eng.android.nd.marwatalaat.bakingapp.util.Utility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private BakingAdapter adapter;
    private List<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (!NetworkUtil.networkUp(this)) {
            getSupportLoaderManager().initLoader(0, null, this);

        }
        RecipeSyncJob.initialize(this);
        adapter = new BakingAdapter(this);
        setupRecycler();


    }

    private void setupRecycler() {
        int mNoOfColumns = Utility.calculateNoOfColumns(getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, mNoOfColumns);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void getRecipeList() {

            BakingAPI api = BakingClient.getRetrofitClient().create(BakingAPI.class);
            Call<List<Recipe>> call = api.getRecipes();
            call.enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    recipes = response.body();
                    adapter.setRecipes(recipes);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {

                }
            });
        }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                Contract.Baking.URI,
                Contract.Baking.BAKING_COLUMNS.toArray(new String[]{}),
                null, null, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Gson gson = new Gson();
        List<Recipe> recipes = new ArrayList<>();
        if (data.moveToFirst()) {
            do {
                int recipeId = data.getInt(data.getColumnIndex(Contract.Baking.COLUMN_RECIPE_ID));
                String recipeName = data.getString(data.getColumnIndex(Contract.Baking.COLUMN_RECIPE_NAME));
                String ingredients = data.getString(data.getColumnIndex(Contract.Baking.COLUMN_RECIPE_INGREDIENT));
                int serving = data.getInt(data.getColumnIndex(Contract.Baking.COLUMN_RECIPE_SERVING));
                ArrayList<Ingredient> ingredientsList = gson.fromJson(ingredients, new TypeToken<ArrayList<Ingredient>>() {
                }.getType());
                String steps = data.getString(data.getColumnIndex(Contract.Baking.COLUMN_RECIPE_STEPS));
                ArrayList<Step> stepsList = gson.fromJson(steps, new TypeToken<ArrayList<Step>>() {
                }.getType());
                recipes.add(new Recipe(recipeId, recipeName, ingredientsList, stepsList, serving));
            } while (data.moveToNext());
        }
        adapter.setRecipes(recipes);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.setRecipes(null);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null)
        {
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable("state");
            recyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);


        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("state", recyclerView.getLayoutManager().onSaveInstanceState());
    }

}
