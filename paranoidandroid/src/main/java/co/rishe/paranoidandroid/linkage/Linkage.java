package co.rishe.paranoidandroid.linkage;

import android.content.Context;

import java.io.InvalidObjectException;
import java.util.Observable;

import co.rishe.graphql.ResourceClient;
import co.rishe.graphql.ResourceRequest;
import co.rishe.graphql.implementation.GraphClient;
import co.rishe.graphql.ResourceModel;
import co.rishe.paranoidandroid.ParanoidApp;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * With this class you can link model to resource and have update data when you need
 */
public class Linkage<Model extends ResourceModel> extends Observable {
    private Model data;
    private Class<Model> modelClass;
    private Context context;
    private LinkagePolicy policy;
    private Subscription subscription;


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
        ResourceClient client = application.getResourceClient();
        ResourceRequest<Model> request = client.createRequest(modelClass);

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
