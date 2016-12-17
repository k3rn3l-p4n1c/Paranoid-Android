package co.rishe.paranoidtest.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import co.rishe.paranoidandroid.ResourceActivity;
import co.rishe.paranoidandroid.ResourceObserver;
import co.rishe.paranoidtest.FilmAdapter;
import co.rishe.paranoidtest.R;
import co.rishe.paranoidtest.databinding.MainActivityBinding;
import co.rishe.paranoidtest.resource.Films;
import co.rishe.paranoidtest.viewmodel.MainViewModel;

@ResourceObserver(
        data_binding = MainActivityBinding.class,
        view_model = MainViewModel.class,
        layout = R.layout.main_activity)
public class MainActivity extends ResourceActivity<MainViewModel, MainActivityBinding> implements MainViewModel.DataListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupRecyclerView(dataBinding.filmsRecyclerView);
    }

    @Override
    public void onCompleted() {

        FilmAdapter adapter =
                (FilmAdapter) dataBinding.filmsRecyclerView.getAdapter();
        Log.e("Main act", dataBinding.getViewModel().getData().allFilms.films.size()+"");
        adapter.setRepositories(dataBinding.getViewModel().getData().allFilms.films);
        adapter.notifyDataSetChanged();
    }


    private void setupRecyclerView(RecyclerView recyclerView) {
        FilmAdapter adapter = new FilmAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onFilmsChanged(final List<Films.AllFilms.Film> films) {
    }
}
