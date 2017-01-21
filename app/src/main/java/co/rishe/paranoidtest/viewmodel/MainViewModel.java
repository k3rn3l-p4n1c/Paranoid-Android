package co.rishe.paranoidtest.viewmodel;

import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;

import co.rishe.paranoidandroid.mvvm.ResourceActivity;
import co.rishe.paranoidandroid.mvvm.ViewModel;
import co.rishe.paranoidandroid.mvvm.recyclerview.adapter.binder.ItemBinder;
import co.rishe.paranoidandroid.mvvm.recyclerview.adapter.binder.ItemBinderBase;
import co.rishe.paranoidtest.BR;
import co.rishe.paranoidtest.R;
import co.rishe.paranoidtest.resource.Films;

public class MainViewModel extends ViewModel<Films> {

    private static final String TAG = "MainViewModel";
    public ObservableInt recyclerViewVisibility;

    @Bindable
    public ObservableArrayList<ItemFilmViewModel> films;

    public MainViewModel(ResourceActivity activity) {
        super(activity, Films.class);

        Log.e("MainViewModel","Constructor");

        recyclerViewVisibility = new ObservableInt(View.INVISIBLE);
        films = new ObservableArrayList<>();
        recyclerViewVisibility.set(View.VISIBLE);

    }

    public ItemBinder<ItemFilmViewModel> itemViewBinder()
    {
        return new ItemBinderBase<>(BR.itemFilmViewModel, R.layout.item_film);
    }

    @Override
    public void onCompleted(Films updateData) {
        System.out.println(updateData.allFilms.films.size());
        for (Films.AllFilms.Film film :
                updateData.allFilms.films) {
            films.add(new ItemFilmViewModel(getActivity(), film));
        }
    }
}
