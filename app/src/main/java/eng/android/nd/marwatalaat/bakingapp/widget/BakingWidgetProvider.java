package eng.android.nd.marwatalaat.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import java.util.Map;
import java.util.Set;

import eng.android.nd.marwatalaat.bakingapp.R;
import eng.android.nd.marwatalaat.bakingapp.ui.MainActivity;
import eng.android.nd.marwatalaat.bakingapp.util.SharedUtil;

import static eng.android.nd.marwatalaat.bakingapp.widget.BakingWidgetService.recipeName;


/**
 * Created by MarwaTalaat on 3/16/2017.
 */

public class BakingWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context ctxt, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(ctxt, appWidgetManager, appWidgetIds);

            for (int appWidgetId : appWidgetIds) {
                Intent svcIntent = new Intent(ctxt, BakingWidgetService.class);
                svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
                svcIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

                RemoteViews widget = new RemoteViews(ctxt.getPackageName(), R.layout.baking_widget);

                widget.setRemoteAdapter(R.id.baking_widget_listView, svcIntent);

                Intent clickIntent = new Intent(ctxt, MainActivity.class);
                PendingIntent clickPI = PendingIntent.getActivity(ctxt, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                widget.setPendingIntentTemplate(R.id.baking_widget_listView, clickPI);
                widget.setOnClickPendingIntent(R.id.widget_toolbar, clickPI
                );

                appWidgetManager.updateAppWidget(appWidgetId, widget);
            }
        }


    @Override
    public void onReceive(@NonNull Context context, @NonNull Intent intent) {
        super.onReceive(context, intent);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(context, getClass()));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.baking_widget_listView);
    }

}