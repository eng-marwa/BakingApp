package eng.android.nd.marwatalaat.bakingapp.ui;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eng.android.nd.marwatalaat.bakingapp.R;
import eng.android.nd.marwatalaat.bakingapp.adapter.RecipeStepAdapter;
import eng.android.nd.marwatalaat.bakingapp.model.Recipe;
import eng.android.nd.marwatalaat.bakingapp.model.Step;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailFragment extends Fragment {
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    private RecipeStepAdapter adapter;

    public RecipeDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ButterKnife.bind(this,v);
        adapter = new RecipeStepAdapter(getActivity());
        setupRecycler();
        return v;
    }

    private void setupRecycler() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public static RecipeDetailFragment getInstance(Recipe recipe) {
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("recipe", recipe);
        recipeDetailFragment.setArguments(bundle);
        return recipeDetailFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle = getArguments();
        if (bundle != null) {
            Recipe recipe = bundle.getParcelable("recipe");
            if (recipe != null) {
                ArrayList<Step> steps = recipe.getSteps();
                adapter.setSteps(steps);
            }

            recyclerView.setAdapter(adapter);
        }

    }
}
