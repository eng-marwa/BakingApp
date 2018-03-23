package eng.android.nd.marwatalaat.bakingapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MarwaTalaat on 5/18/2017.
 */

public class BakingClient {
    public static Retrofit getRetrofitClient() {
        return new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
