package co.rishe.paranoidandroid.linkage;

import android.content.Context;
import android.util.Log;

import java.io.InvalidObjectException;
import java.util.Observable;

import co.rishe.graphql.GraphClient;
import co.rishe.graphql.GraphModel;
import co.rishe.paranoidandroid.ParanoidApp;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * With this class you can link model to resource and have update data when you need
 */
public class Linkage<Model extends GraphModel> extends Observable {
    Model data;
    Class<Model> modelClass;
    Context context;
    LinkagePolicy policy;
    Subscription subscription;


    public Linkage(Class<Model> modelClass, LinkagePolicy policy, Context context) {
        this.modelClass = modelClass;
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

    public void fetch() {
        ParanoidApp application = ParanoidApp.get(context);
        GraphClient graphClient = application.getGraphClient();
        GraphClient.GraphRequest<Model> request = graphClient.createRequest(modelClass);
        Log.w("Query:", request.getQuery().getQueryString());

        subscription = request.promise()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Subscriber<Model>() {
                    @Override
                    public void onCompleted() {
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
