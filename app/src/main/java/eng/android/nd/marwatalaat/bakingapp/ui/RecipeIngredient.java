package eng.android.nd.marwatalaat.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import eng.android.nd.marwatalaat.bakingapp.R;
import eng.android.nd.marwatalaat.bakingapp.adapter.IngredientAdapter;
import eng.android.nd.marwatalaat.bakingapp.model.Ingredient;
import eng.android.nd.marwatalaat.bakingapp.model.Recipe;
import eng.android.nd.marwatalaat.bakingapp.util.SharedUtil;

public class RecipeIngredient extends AppCompatActivity {
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.photo)
    ImageView thumbinals;
    private IngredientAdapter adapter;
    private Recipe recipe;
    private ArrayList<Ingredient> ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_ingredient);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        adapter = new IngredientAdapter(this);

        Intent intent = getIntent();
        recipe = intent.hasExtra("recipe") ? (Recipe) intent.getParcelableExtra("recipe") : null;
        int recipeImage = intent.hasExtra("recipeImage") ? intent.getIntExtra("recipeImage", 0) : 0;
        if (recipe == null || recipeImage == 0) {
            recipe = SharedUtil.getRecipe(this);
            recipeImage = SharedUtil.getRecipeImage(this);
        }
        ingredients = recipe.getIngredients();
        collapsingToolbarLayout.setTitle(recipe.getName());
        if (TextUtils.isEmpty(recipe.getImage())) {
            thumbinals.setImageResource(recipeImage);
        } else {
            Picasso.with(this)
                    .load(recipe.getImage())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(thumbinals);
        }
      setupRecyclerView();


    }

    private void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter.setIngredients(recipe.getIngredients());
        recyclerView.setAdapter(adapter);
    }

}


