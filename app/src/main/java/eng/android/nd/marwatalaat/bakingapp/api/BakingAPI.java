package eng.android.nd.marwatalaat.bakingapp.api;

import java.util.List;

import eng.android.nd.marwatalaat.bakingapp.model.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by MarwaTalaat on 5/18/2017.
 */

public interface BakingAPI {
    @GET("baking.json")
    public Call<List<Recipe>> getRecipes();
}
