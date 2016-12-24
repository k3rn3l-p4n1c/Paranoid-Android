package co.rishe.paranoidandroid;

import android.content.Context;
import android.util.Log;

import java.io.InvalidObjectException;

import co.rishe.graphql.GraphClient;
import co.rishe.graphql.GraphQuery;

import java.util.Observable;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * With this class you can link model to resource and have update data when you need
 */
public class Linkage<Model extends GraphQuery> extends Observable {
    Model data;
    Context context;
    LinkagePolicy policy;
    protected Subscription subscription;


    public Linkage(Model model, LinkagePolicy policy, Context context) {
        this.data = model;
        this.context = context;
        this.policy = policy;

        this.policy.start(this);
    }

    public Model get() throws InvalidObjectException {
        if (policy.valid()) {
            return data;
        } else {
            throw new InvalidObjectException("Data is not valid");
        }
    }

    void fetch() {
        ParanoidApp application = ParanoidApp.get(context);
        GraphClient graphClient = application.getGraphClient();
        GraphClient.GraphRequest<Model> request = graphClient.getRequest(data);
        Log.w("Query:", request.getQuery().getQueryString());

        subscription = request.promise()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Subscriber<Model>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted");
                        notifyObservers();
                        policy.update(Linkage.this, data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Model data) {
                        Linkage.this.data = data;
                    }
                });
    }
}
