package co.rishe.paranoidtest.view;

import android.os.Bundle;

import co.rishe.paranoidandroid.ResourceActivity;
import co.rishe.paranoidandroid.ResourceObserver;
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
    }

    @Override
    public void onCompleted() {

    }
}
