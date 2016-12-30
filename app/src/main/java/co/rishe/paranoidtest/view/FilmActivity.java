package co.rishe.paranoidtest.view;

import android.os.Bundle;

import java.io.InvalidObjectException;

import co.rishe.paranoidandroid.ParanoidApp;
import co.rishe.paranoidandroid.mvvm.ResourceActivity;
import co.rishe.paranoidandroid.mvvm.ResourceObserver;
import co.rishe.paranoidtest.R;
import co.rishe.paranoidtest.databinding.FilmActivityBinding;
import co.rishe.paranoidtest.viewmodel.FilmViewModel;

@ResourceObserver(
        data_binding = FilmActivityBinding.class,
        view_model = FilmViewModel.class,
        layout = R.layout.film_activity)
public class FilmActivity extends ResourceActivity<FilmViewModel, FilmActivityBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParanoidApp app = (ParanoidApp) getApplication();
        try {
            System.out.println(app.getLinkage(1L).get());
        } catch (InvalidObjectException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCompleted() {

    }
}
