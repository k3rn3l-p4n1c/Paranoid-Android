package co.rishe.paranoidandroid;


import android.app.Application;
import android.content.Context;

import co.rishe.graphql.GraphClient;
import rx.Scheduler;
import rx.schedulers.Schedulers;
/**
 * Created by Bardia on 12/17/16.
 */
public class ParanoidApp extends Application {

    private Scheduler defaultSubscribeScheduler;
    private GraphClient graphClient;

    public static ParanoidApp get(Context context) {
        return (ParanoidApp) context.getApplicationContext();
    }

    public GraphClient getGraphClient() {
        if (graphClient == null) {
            graphClient = new GraphClient("http://graphql-swapi.parseapp.com/");
        }
        return graphClient;
    }

    public Scheduler defaultSubscribeScheduler() {
        if (defaultSubscribeScheduler == null) {
            defaultSubscribeScheduler = Schedulers.io();
        }
        return defaultSubscribeScheduler;
    }

    //User to change scheduler from tests
    public void setDefaultSubscribeScheduler(Scheduler scheduler) {
        this.defaultSubscribeScheduler = scheduler;
    }
}
