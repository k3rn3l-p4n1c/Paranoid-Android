package co.rishe.paranoidandroid;


import android.app.Application;
import android.content.Context;

import java.util.LinkedHashMap;
import java.util.Map;

import co.rishe.graphql.ResourceClient;
import co.rishe.graphql.implementation.GraphClient;
import co.rishe.graphql.ResourceModel;
import co.rishe.graphql.implementation.GraphClientBuilder;
import co.rishe.paranoidandroid.linkage.Linkage;
import co.rishe.paranoidandroid.linkage.LinkagePolicy;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Paranoid android app
 */
public abstract class ParanoidApp extends Application {

    private Scheduler defaultSubscribeScheduler;
    private ResourceClient resourceClient;
    private Map<Long, Linkage> linkageMap;

    public static ParanoidApp get(Context context) {
        return (ParanoidApp) context.getApplicationContext();
    }

    public ResourceClient getResourceClient() {
        if (resourceClient == null) {
            resourceClient = new GraphClientBuilder()
                    .setUrl(url())
                    .build();
        }
        return resourceClient;
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

    public void link(Long id, ResourceModel query, LinkagePolicy policy) {
        if (linkageMap == null) {
            linkageMap = new LinkedHashMap<>();
        }

        linkageMap.put(id, new Linkage<>(query.getClass(), policy, this));
    }

    public Linkage getLinkage(Long id) {
        return linkageMap.get(id);
    }

    abstract public String url();
}
