package eng.android.nd.marwatalaat.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eng.android.nd.marwatalaat.bakingapp.R;
import eng.android.nd.marwatalaat.bakingapp.model.Step;

public class RecipeStepDetail extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);
        ButterKnife.bind(this);
        if (null != toolbar) {
            setSupportActionBar(toolbar);
            //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        if (intent.hasExtra("step")) {
            ArrayList<Step> steps = intent.getParcelableArrayListExtra("step");
            int position = intent.getExtras().getInt("position");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, RecipeStepDetailFragment.getInstance(steps,position)).commit();
        }


    }

}
