package eng.android.nd.marwatalaat.bakingapp.sync;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import java.util.List;

import eng.android.nd.marwatalaat.bakingapp.api.BakingAPI;
import eng.android.nd.marwatalaat.bakingapp.api.BakingClient;
import eng.android.nd.marwatalaat.bakingapp.model.Recipe;
import eng.android.nd.marwatalaat.bakingapp.ui.MainActivity;
import eng.android.nd.marwatalaat.bakingapp.util.NetworkUtil;
import eng.android.nd.marwatalaat.bakingapp.util.Utility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public final class RecipeSyncJob {

    private static final int ONE_OFF_ID = 2;
    private static final int PERIOD = 300000;
    private static final int INITIAL_BACKOFF = 10000;
    private static final int PERIODIC_ID = 1;
    private static Context mContext;

    private RecipeSyncJob() {
    }

    static void getRecipes(final Context context) {
        Timber.d("Running sync job");


        if (NetworkUtil.networkUp(context)) {
            ((MainActivity)mContext).getRecipeList();

        }
    }

    private static void schedulePeriodic(Context context) {
        Timber.d("Scheduling a periodic task");


        JobInfo.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            builder = new JobInfo.Builder(PERIODIC_ID, new ComponentName(context, RecipeJobService.class));


            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .setPeriodic(PERIOD)
                    .setBackoffCriteria(INITIAL_BACKOFF, JobInfo.BACKOFF_POLICY_EXPONENTIAL);


            JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

            scheduler.schedule(builder.build());
        }
    }


    public static synchronized void initialize(final Context context) {
        mContext = context;
        schedulePeriodic(context);
        syncImmediately(context);

    }

    public static synchronized void syncImmediately(Context context) {

        if (NetworkUtil.networkUp(context)) {
            Intent nowIntent = new Intent(context, RecipeIntentService.class);
            context.startService(nowIntent);
        } else {
            //construct the job
            JobInfo.Builder builder = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                builder = new JobInfo.Builder(ONE_OFF_ID, new ComponentName(context, RecipeJobService.class));


                builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                        .setBackoffCriteria(INITIAL_BACKOFF, JobInfo.BACKOFF_POLICY_EXPONENTIAL);


                JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
                scheduler.schedule(builder.build());


            }
        }
    }


}
