package eng.android.nd.marwatalaat.bakingapp.sync;

import android.app.IntentService;
import android.content.Intent;

import timber.log.Timber;


public class RecipeIntentService extends IntentService {

    public RecipeIntentService() {
        super(RecipeIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Timber.d("Intent handled");
        RecipeSyncJob.getRecipes(getApplicationContext());
    }
}
