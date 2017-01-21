package co.rishe.paranoidandroid.mvvm;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import co.rishe.graphql.GraphClient;
import co.rishe.graphql.GraphModel;
import co.rishe.paranoidandroid.ParanoidApp;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Interface that every ViewModel must implement
 */
public abstract class ViewModel<Model extends GraphModel> extends BaseObservable{

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
        GraphClient graphClient = application.getGraphClient();
        GraphClient.GraphRequest<Model> request = graphClient.createRequest(queryClass);
        Log.w("Query:", request.getQuery().getQueryString());

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
