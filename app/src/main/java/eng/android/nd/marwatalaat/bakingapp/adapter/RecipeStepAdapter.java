package eng.android.nd.marwatalaat.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eng.android.nd.marwatalaat.bakingapp.R;
import eng.android.nd.marwatalaat.bakingapp.model.Step;
import eng.android.nd.marwatalaat.bakingapp.ui.RecipeDetail;
import eng.android.nd.marwatalaat.bakingapp.ui.RecipeStepDetail;

/**
 * Created by MarwaTalaat on 5/19/2017.
 */

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.StepViewHolder> {
    private Context mContext;
    private ArrayList<Step> steps;

    public RecipeStepAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.step_card, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        Step step = steps.get(position);
        holder.stepTitle.setText(mContext.getString(R.string.onestep) +" "+ (step.getId().intValue() + 1));
        holder.stepDesc.setText(step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return steps != null ? steps.size() : 0;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
        notifyDataSetChanged();

    }


    class StepViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.step_title)
        TextView stepTitle;
        @BindView(R.id.step_desc)
        TextView stepDesc;

        public StepViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   ((RecipeDetail)mContext).viewStepAtPostion(steps,getAdapterPosition());

                }
            });
        }
    }
}
