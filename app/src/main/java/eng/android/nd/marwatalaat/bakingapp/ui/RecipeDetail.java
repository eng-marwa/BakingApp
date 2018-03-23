package eng.android.nd.marwatalaat.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eng.android.nd.marwatalaat.bakingapp.R;
import eng.android.nd.marwatalaat.bakingapp.model.Recipe;
import eng.android.nd.marwatalaat.bakingapp.model.Step;
import eng.android.nd.marwatalaat.bakingapp.util.SharedUtil;

public class RecipeDetail extends AppCompatActivity {
    private static final String ACTION_DATA_UPDATED = "com.udacity.marwatalaat.ACTION_DATA_UPDATED";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.photo)
    ImageView thumbinals;
    private boolean mTwoPane;
    private String recipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);

        if(savedInstanceState==null){
            Log.i("save","save Ins null");
        }else{
            Log.i("save","not null");
        }
        Intent dataUpdatedIntent = new Intent(ACTION_DATA_UPDATED);
        sendBroadcast(dataUpdatedIntent);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Recipe recipe = intent.hasExtra("recipe") ? (Recipe) intent.getParcelableExtra("recipe") : null;
        int recipeImage = intent.hasExtra("recipeImage") ? intent.getIntExtra("recipeImage", 0) : 0;
        if(null != SharedUtil.getRecipe(this)){
        if (recipe == null || recipeImage == 0) {
            recipe = SharedUtil.getRecipe(this);
            recipeImage = SharedUtil.getRecipeImage(this);
        }
        if (recipe == null  ) {
            recipeName = SharedUtil.getRecipe(this).getName();
        }else{
           recipeName =  recipe.getName();
        }
            collapsingToolbarLayout.setTitle(recipeName);

        if (TextUtils.isEmpty(recipe.getImage())) {
            thumbinals.setImageResource(recipeImage);
        } else {
            Picasso.with(this)
                    .load(recipe.getImage())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(thumbinals);
        }
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.mContainer, RecipeDetailFragment.getInstance(recipe)).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (null != findViewById(R.id.container)) {
            mTwoPane = true;
        } else {
            mTwoPane = false;
        }

    }

    public void viewStepAtPostion(ArrayList<Step> steps, int position) {
        if (mTwoPane) {
            //tablet
            getSupportFragmentManager().beginTransaction().replace(R.id.container, RecipeStepDetailFragment.getInstance(steps,position)).commit();
        } else {
            Intent i = new Intent(RecipeDetail.this, RecipeStepDetail.class);
            i.putParcelableArrayListExtra("step", steps);
            i.putExtra("position", position);
            System.out.println("intent   " + steps == null ? "y" : "n");
            startActivity(i);
        }

    }
}
