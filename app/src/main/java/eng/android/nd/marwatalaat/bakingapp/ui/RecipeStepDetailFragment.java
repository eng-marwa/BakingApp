package eng.android.nd.marwatalaat.bakingapp.ui;


import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eng.android.nd.marwatalaat.bakingapp.R;
import eng.android.nd.marwatalaat.bakingapp.model.Step;

import static android.view.Gravity.FILL;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeStepDetailFragment extends Fragment {
    @BindView(R.id.playerView)
    SimpleExoPlayerView mPlayerView;
    @BindView(R.id.short_desc)
    TextView shortDesc;
    @BindView(R.id.desc)
    TextView desc;
    private ArrayList<Step> steps;
    private int position;
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.next)
    Button next;
    private Step step;

    @OnClick(R.id.back)
    public void previousStep() {
        Log.i("positin prev", "" + position);
        if (position > 0) {
            back.setEnabled(true);
            viewSteps(steps.get(--position));
        } else if (position == 0) {
            back.setEnabled(false);
        }
    }

    @OnClick(R.id.next)
    public void nextStep() {
        if (position < steps.size() - 1) {
            next.setEnabled(true);
            viewSteps(steps.get(++position));
        } else if (position == steps.size() - 1) {
            next.setEnabled(false);
        }

    }

    private SimpleExoPlayer mExoPlayer;

    public RecipeStepDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);
        ButterKnife.bind(this, v);
        mPlayerView = (SimpleExoPlayerView) v.findViewById(R.id.playerView);

        // Load the thumbnail image mark as the background image.
        //mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));

        // Initialize the player.
        String video = step.getVideoURL();
        initializePlayer(Uri.parse(video));
        shortDesc.setText(step.getShortDescription());
        desc.setText(step.getDescription());
        return v;
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            Handler handler = new Handler();
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mPlayerView.setResizeMode(FILL);
        }
    }

    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    public static RecipeStepDetailFragment getInstance(ArrayList<Step> steps, int position) {
        RecipeStepDetailFragment recipeStepDetailFragment = new RecipeStepDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("step", steps);
        bundle.putInt("position", position);
        recipeStepDetailFragment.setArguments(bundle);
        return recipeStepDetailFragment;
    }


    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        Bundle bundle = getArguments();
        setRetainInstance(true);

        if (bundle != null) {
            steps = bundle.getParcelableArrayList("step");
            position = bundle.getInt("position");
            if (steps != null) {
                step = steps.get(position);

            }
        }
    }

    private void viewSteps(Step step) {

        initializePlayer(Uri.parse(step.getVideoURL()));
        shortDesc.setText(step.getShortDescription());
        desc.setText(step.getDescription());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mExoPlayer != null) {
            releasePlayer();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mExoPlayer != null) {
            releasePlayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mExoPlayer != null) {
            releasePlayer();
        }
    }
}
