package co.rishe.paranoidtest.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import java.util.List;

import co.rishe.graphql.GraphQuery;
import co.rishe.paranoidandroid.ResourceActivity;
import co.rishe.paranoidandroid.ViewModel;
import co.rishe.paranoidtest.resource.FilmQuery;

/**
 * Created by Bardia on 12/18/16.
 */
public class FilmViewModel extends ViewModel {
    public ObservableField<String> title;
    public ObservableField<String> openingCrawl;
    public ObservableField<String> director;
    public ObservableField<String> producers;
    public ObservableField<String> created;

    private FilmQuery.Film film;
    public FilmViewModel(ResourceActivity activity) {
        super(activity);
    }


    @Override
    public GraphQuery getData() {
        return new FilmQuery();
    }
}
