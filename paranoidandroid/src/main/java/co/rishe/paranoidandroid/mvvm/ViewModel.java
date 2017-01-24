package co.rishe.paranoidandroid.mvvm;

import android.databinding.BaseObservable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import co.rishe.graphql.ResourceClient;
import co.rishe.graphql.ResourceRequest;
import co.rishe.graphql.implementation.GraphClient;
import co.rishe.graphql.ResourceModel;
import co.rishe.paranoidandroid.ParanoidApp;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Interface that every ViewModel must implement
 */
public abstract class ViewModel<Model extends ResourceModel> extends BaseObservable{

    protected Model data;
    private Class<Model> queryClass;

    private ResourceActivity activity;
    protected Subscription subscription;


    public ViewModel(ResourceActivity activity, Class<Model> qClass) {
        this.activity = activity;
        this.queryClass = qClass;
        loadData();
    }

    public Model getData() {
        return data;
    }

    public ResourceActivity getActivity() {
        return activity;
    }

    private void loadData() {
        ParanoidApp application = ParanoidApp.get(activity);
        ResourceClient resourceClient = application.getResourceClient();
        ResourceRequest<Model> request = resourceClient.createRequest(queryClass);

        subscription = request.promise()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Subscriber<Model>() {
                    @Override
                    public void onCompleted() {
                        Log.e("On comp", data.toString());
                        ViewModel.this.onCompleted(data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Model data) {
                        ViewModel.this.data = (Model) data;
                    }
                });
    }

    public void destroy() {
        if (subscription != null && !subscription.isUnsubscribed()) subscription.unsubscribe();
        subscription = null;
        activity = null;
    }



    public LinearLayoutManager layoutManager() {
        return new LinearLayoutManager(this.activity);
    }

    public abstract void onCompleted(Model data);

}
