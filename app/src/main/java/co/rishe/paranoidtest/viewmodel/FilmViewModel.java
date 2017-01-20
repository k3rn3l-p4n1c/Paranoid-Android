package co.rishe.paranoidtest.viewmodel;

import android.databinding.ObservableField;

import java.util.List;

import co.rishe.paranoidandroid.mvvm.ResourceActivity;
import co.rishe.paranoidandroid.mvvm.ViewModel;
import co.rishe.paranoidtest.resource.FilmQuery;

public class FilmViewModel extends ViewModel<FilmQuery> {
    public ObservableField<String> title;
    public ObservableField<String> openingCrawl;
    public ObservableField<String> director;
    public ObservableField<List<String>> producers;
    public ObservableField<String> created;

    public FilmViewModel(ResourceActivity activity) {
        super(activity, FilmQuery.class);
        title = new ObservableField<>("title");
        openingCrawl = new ObservableField<>();
        director = new ObservableField<>();
        producers = new ObservableField<>();
        created = new ObservableField<>();
    }


    @Override
    public void onCompleted(FilmQuery data) {
        FilmQuery.Film film = data.film;
        title.set(film.title);
        openingCrawl.set(film.openingCrawl);
        director.set(film.director);
        producers.set(film.producers);
        created.set(film.created);
    }
}
