package eng.android.nd.marwatalaat.bakingapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by MarwaTalaat on 3/20/2017.
 */

public class NetworkUtil {
    public static boolean networkUp(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context. getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
