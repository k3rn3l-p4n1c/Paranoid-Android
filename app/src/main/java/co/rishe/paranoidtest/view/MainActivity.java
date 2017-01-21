package co.rishe.paranoidtest.view;

import android.os.Bundle;

import co.rishe.paranoidandroid.ParanoidApp;
import co.rishe.paranoidandroid.mvvm.ResourceActivity;
import co.rishe.paranoidandroid.mvvm.ResourceObserver;
import co.rishe.paranoidtest.R;
import co.rishe.paranoidtest.databinding.MainActivityBinding;
import co.rishe.paranoidtest.viewmodel.MainViewModel;

@ResourceObserver(
        data_binding = MainActivityBinding.class,
        view_model = MainViewModel.class,
        layout = R.layout.main_activity)
public class MainActivity extends ResourceActivity<MainViewModel, MainActivityBinding> {

    static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParanoidApp app = (ParanoidApp) getApplication();
//        app.link(1L, new Films(), new PeriodicLinkage());
    }

}
