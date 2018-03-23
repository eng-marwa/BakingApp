package eng.android.nd.marwatalaat.bakingapp.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eng.android.nd.marwatalaat.bakingapp.R;
import eng.android.nd.marwatalaat.bakingapp.data.Contract;
import eng.android.nd.marwatalaat.bakingapp.model.Recipe;
import eng.android.nd.marwatalaat.bakingapp.ui.RecipeDetail;
import eng.android.nd.marwatalaat.bakingapp.ui.RecipeIngredient;
import eng.android.nd.marwatalaat.bakingapp.util.SharedUtil;

import static eng.android.nd.marwatalaat.bakingapp.data.Contract.Baking.COLUMN_RECIPE_ID;

/**
 * Created by MarwaTalaat on 5/17/2017.
 */

public class BakingAdapter extends RecyclerView.Adapter<BakingAdapter.RecipeViewHolder> {
    private List<Recipe> mRecipes;
    private Context mContext;
    private int recipesImage[];
    private List<Integer> ids;
    private boolean b = false;

    public void setRecipes(List<Recipe> mRecipes) {
        this.mRecipes = mRecipes;
        notifyDataSetChanged();

    }

    public BakingAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recipe_card, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecipeViewHolder holder, final int position) {
        final Recipe recipe = mRecipes.get(position);
        recipesImage = new int[]{R.mipmap.nutella, R.mipmap.brownies, R.mipmap.yellowcake, R.mipmap.cheesecake};
        holder.titleView.setText(recipe.getName());
        if(SharedUtil.isSavedInShared(mContext,recipe.getName())){
            int savedId = SharedUtil.getSavedId(mContext,recipe.getName());
            System.out.println(savedId+" ======    "+recipe.getId()
            );
            if(savedId== recipe.getId()) {
                b = true;
                holder.favButton.setImageResource(R.mipmap.ic_fav_done);
            }else {
                b = false;
                holder.favButton.setImageResource(R.mipmap.ic_fav);
            }

        }


        if (TextUtils.isEmpty(recipe.getImage())) {
            holder.thumbnailView.setBackgroundResource(recipesImage[recipe.getId() - 1]);

        } else {
            Picasso.with(mContext)
                    .load(recipe.getImage())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(holder.thumbnailView);
        }
        holder.servingsView.setText(mContext.getString(R.string.serving) + "   " + String.valueOf(recipe.getServings()));
        holder.stepView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, RecipeDetail.class);
                i.putExtra("recipe", recipe);
                i.putExtra("recipeImage", recipesImage[position]);
                mContext.startActivity(i);

            }
        });
        holder.ingredientsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, RecipeIngredient.class);
                i.putExtra("recipe", recipe);
                i.putExtra("recipeImage", recipesImage[position]);
                SharedUtil.saveRecipe(mContext, recipe);
                SharedUtil.saveRecipeImage(mContext, recipesImage[position]);
                Log.i("tag", recipe.getIngredients() == null ? "y" : "n");
                mContext.startActivity(i);

            }
        });
        holder.favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!b) {
                    Gson gson = new Gson();
                    String ingredients = gson.toJson(recipe.getIngredients());
                    String steps = gson.toJson(recipe.getSteps());
                    ContentValues posts = new ContentValues();
                    posts.put(COLUMN_RECIPE_ID, recipe.getId());
                    posts.put(Contract.Baking.COLUMN_RECIPE_NAME, recipe.getName());
                    posts.put(Contract.Baking.COLUMN_RECIPE_SERVING, recipe.getServings());
                    posts.put(Contract.Baking.COLUMN_RECIPE_INGREDIENT, ingredients);
                    posts.put(Contract.Baking.COLUMN_RECIPE_STEPS, steps);
                    mContext.getContentResolver()
                            .insert(
                                    Contract.Baking.makeUriForRecipe(String.valueOf(recipe.getId())), posts);
                    SharedUtil.saveId(mContext,recipe.getName(),recipe.getId());
                    holder.favButton.setImageResource(R.mipmap.ic_fav_done);
                    Toast.makeText(mContext, R.string.toast_Saved, Toast.LENGTH_LONG).show();
                }else{
                    mContext.getContentResolver().delete(Contract.Baking.URI,COLUMN_RECIPE_ID+"=?",new String[]{String.valueOf(recipe.getId())});
                    Toast.makeText(mContext, R.string.deleted, Toast.LENGTH_LONG).show();
                    SharedUtil.removeId(mContext,recipe.getName());

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecipes != null ? mRecipes.size() : 0;
    }




    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_text2)
        TextView servingsView;
        @BindView(R.id.recipe_image)
        ImageView thumbnailView;
        @BindView(R.id.recipe_title)
        TextView titleView;
        @BindView(R.id.recipe_explore)
        TextView stepView;
        @BindView(R.id.recipe_share)
        TextView ingredientsView;
        @BindView(R.id.fav)
        ImageView favButton;

        public RecipeViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
