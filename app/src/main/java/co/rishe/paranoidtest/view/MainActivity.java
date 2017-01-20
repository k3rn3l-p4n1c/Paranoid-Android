package co.rishe.paranoidtest.view;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import co.rishe.paranoidandroid.ParanoidApp;
import co.rishe.paranoidandroid.mvvm.ResourceActivity;
import co.rishe.paranoidandroid.mvvm.ResourceObserver;
import co.rishe.paranoidandroid.mvvm.recyclerview.adapter.binder.ItemBinder;
import co.rishe.paranoidandroid.mvvm.recyclerview.adapter.binder.ItemBinderBase;
import co.rishe.paranoidtest.BR;
import co.rishe.paranoidtest.R;
import co.rishe.paranoidtest.databinding.MainActivityBinding;
import co.rishe.paranoidtest.viewmodel.ItemFilmViewModel;
import co.rishe.paranoidtest.viewmodel.MainViewModel;

@ResourceObserver(
        data_binding = MainActivityBinding.class,
        view_model = MainViewModel.class,
        layout = R.layout.main_activity)
public class MainActivity extends ResourceActivity<MainViewModel, MainActivityBinding>  {

    static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupRecyclerView(dataBinding.filmsRecyclerView);
        ParanoidApp app = (ParanoidApp) getApplication();
//        app.link(1L, new Films(), new PeriodicLinkage());
    }

    @Override
    public void onCompleted() {

//        FilmAdapter adapter =
//                (FilmAdapter) dataBinding.filmsRecyclerView.getAdapter();
//        Log.e("Main act", dataBinding.getViewModel().getData().allFilms.films.size()+"");
//        adapter.setRepositories(dataBinding.getViewModel().getData().allFilms.films);
//        adapter.notifyDataSetChanged();
    }


    private void setupRecyclerView(RecyclerView recyclerView) {
//        FilmAdapter adapter = new FilmAdapter();
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    public ItemBinder<ItemFilmViewModel> itemViewBinder()
    {
        Log.e(TAG, "itemViewBinder");
        return new ItemBinderBase<>(BR.itemFilmViewModel, R.layout.item_film);
    }

}
