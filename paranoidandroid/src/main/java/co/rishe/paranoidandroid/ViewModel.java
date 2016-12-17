package co.rishe.paranoidandroid;

import android.util.Log;

import co.rishe.graphql.GraphClient;
import co.rishe.graphql.GraphQuery;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Interface that every ViewModel must implement
 */
public abstract class ViewModel<Model extends GraphQuery> {

    protected Model data;

    protected ResourceActivity activity;
    protected Subscription subscription;


    public ViewModel(ResourceActivity activity) {
        this.activity = activity;
        loadData();
    }

    abstract public Model getData();

    private void loadData() {
        ParanoidApp application = ParanoidApp.get(activity);
        GraphClient graphClient = application.getGraphClient();
        Model films = getData();
        GraphClient.GraphRequest<Model> request = graphClient.getRequest(films);
        Log.e("Query:", request.getQuery().getQueryString());

        subscription = request.promise()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Subscriber<Model>() {
                    @Override
                    public void onCompleted() {
                        Log.e("On comp", data.toString());
                        activity.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Model data) {
                        ViewModel.this.data = data;
                    }
                });
    }

    public void destroy() {
        if (subscription != null && !subscription.isUnsubscribed()) subscription.unsubscribe();
        subscription = null;
        activity = null;
    }
}
