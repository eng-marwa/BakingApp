package eng.android.nd.marwatalaat.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eng.android.nd.marwatalaat.bakingapp.R;
import eng.android.nd.marwatalaat.bakingapp.model.Ingredient;
import eng.android.nd.marwatalaat.bakingapp.model.Step;
import eng.android.nd.marwatalaat.bakingapp.ui.RecipeIngredient;

/**
 * Created by MarwaTalaat on 5/20/2017.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private Context mContext;
    private List<Ingredient> ingredients;

    public IngredientAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public IngredientAdapter.IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ingredient_card, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientAdapter.IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        holder.quantityView.setText(String.valueOf(ingredient.getQuantity()));
        holder.measureView.setText(String.valueOf(ingredient.getMeasure()));
        holder.ingredientView.setText(String.valueOf(ingredient.getIngredient()));

    }

    @Override
    public int getItemCount() {
        return ingredients != null ? ingredients.size() : 0;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.quantity)
        TextView quantityView;
        @BindView(R.id.measure)
        TextView measureView;
        @BindView(R.id.ingredient)
        TextView ingredientView;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
