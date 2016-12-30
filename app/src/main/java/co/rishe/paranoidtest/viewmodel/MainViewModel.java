package co.rishe.paranoidtest.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;

import co.rishe.paranoidandroid.mvvm.ResourceActivity;
import co.rishe.paranoidandroid.mvvm.ViewModel;
import co.rishe.paranoidtest.R;
import co.rishe.paranoidtest.resource.Films;

public class MainViewModel extends ViewModel<Films> {

    private static final String TAG = "MainViewModel";

    public ObservableInt infoMessageVisibility;
    public ObservableInt progressVisibility;
    public ObservableInt recyclerViewVisibility;
    public ObservableInt searchButtonVisibility;
    public ObservableField<String> infoMessage;

    public MainViewModel(ResourceActivity activity) {
        super(activity, Films.class);

        Log.e("MainViewModel","Constructor");

        infoMessageVisibility = new ObservableInt(View.VISIBLE);
        progressVisibility = new ObservableInt(View.INVISIBLE);
        recyclerViewVisibility = new ObservableInt(View.INVISIBLE);
        searchButtonVisibility = new ObservableInt(View.GONE);
        infoMessage = new ObservableField<>(activity.getString(R.string.default_info_message));
        recyclerViewVisibility.set(View.VISIBLE);

    }

    @Override
    public void onCompleted() {
        activity.onCompleted();
    }
}
