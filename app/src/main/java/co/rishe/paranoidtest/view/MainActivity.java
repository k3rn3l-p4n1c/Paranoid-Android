package co.rishe.paranoidtest.view;

import android.os.Bundle;

import java.io.InvalidObjectException;

import co.rishe.paranoidandroid.linkage.PeriodicLinkage;
import co.rishe.paranoidandroid.mvvm.ResourceActivity;
import co.rishe.paranoidandroid.mvvm.ResourceObserver;
import co.rishe.paranoidtest.Paranoia;
import co.rishe.paranoidtest.R;
import co.rishe.paranoidtest.databinding.MainActivityBinding;
import co.rishe.paranoidtest.resource.Films;
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
        Paranoia app = (Paranoia) getApplication();
        app.link(1L, new Films(), new PeriodicLinkage());


    }

}
