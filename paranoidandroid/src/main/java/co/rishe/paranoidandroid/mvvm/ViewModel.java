package co.rishe.paranoidandroid.mvvm;

import android.content.Context;
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
public abstract class ViewModel<Model extends GraphModel> {

    protected Model data;
    private Class<Model> queryClass;

    protected Context context;
    protected Subscription subscription;


    public ViewModel(Context context, Class<Model> qClass) {
        this.context = context;
        this.queryClass = qClass;
        loadData();
    }

    public Model getData() {
        return data;
    }

    private void loadData() {
        ParanoidApp application = ParanoidApp.get(context);
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
                        ViewModel.this.onCompleted();
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
        context = null;
    }

    public abstract void onCompleted();

}
