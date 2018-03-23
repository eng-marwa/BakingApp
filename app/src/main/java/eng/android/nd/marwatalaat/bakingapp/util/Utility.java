package eng.android.nd.marwatalaat.bakingapp.util;

import android.content.Context;
import android.util.DisplayMetrics;

import com.danikula.videocache.HttpProxyCacheServer;

import java.util.List;

import eng.android.nd.marwatalaat.bakingapp.model.Recipe;


/**
 * Created by MarwaTalaat on 5/20/2017.
 */


public class Utility {
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 270);
        return noOfColumns;
    }

    public static HttpProxyCacheServer getProxy(Context context) {
        return new HttpProxyCacheServer.Builder(context)
                .build();
    }


}

