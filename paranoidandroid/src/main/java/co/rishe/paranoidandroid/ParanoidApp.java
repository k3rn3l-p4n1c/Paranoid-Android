package co.rishe.paranoidandroid;


import android.app.Application;
import android.content.Context;

import java.util.LinkedHashMap;
import java.util.Map;

import co.rishe.graphql.GraphClient;
import co.rishe.graphql.GraphModel;
import co.rishe.paranoidandroid.linkage.Linkage;
import co.rishe.paranoidandroid.linkage.LinkagePolicy;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Paranoid android app
 */
public class ParanoidApp extends Application {

    private Scheduler defaultSubscribeScheduler;
    private GraphClient graphClient;
    private Map<Long, Linkage> linkageMap;


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

    public void setDefaultSubscribeScheduler(Scheduler scheduler) {
        this.defaultSubscribeScheduler = scheduler;
    }

    public void link(Long id, GraphModel query, LinkagePolicy policy) {
        if (linkageMap == null) {
            linkageMap = new LinkedHashMap<>();
        }

        linkageMap.put(id, new Linkage<>(query.getClass(), policy, this));
    }

    public Linkage getLinkage(Long id){
        return linkageMap.get(id);
    }
}
