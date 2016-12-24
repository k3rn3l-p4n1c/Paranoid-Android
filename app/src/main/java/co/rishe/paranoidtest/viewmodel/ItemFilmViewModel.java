package co.rishe.paranoidtest.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import co.rishe.paranoidandroid.ResourceActivity;
import co.rishe.paranoidandroid.ViewModel;
import co.rishe.paranoidtest.resource.FilmQuery;
import co.rishe.paranoidtest.resource.Films;
import co.rishe.paranoidtest.resource.Films.AllFilms.Film;
import co.rishe.paranoidtest.view.FilmActivity;


/**
 * View model for each item in the repositories RecyclerView
 */
public class ItemFilmViewModel extends ViewModel<Films> {

    private Film film;
    private Context context;

    public ItemFilmViewModel(ResourceActivity activity, Film film) {
        super(activity);
        this.film = film;
        this.context = activity;
        //loadData();
    }

    public String getTitle() {
        return film.title;
    }

    public String getDirector() {
        return film.director;
    }

    public String getReleaseDate() {
        return film.releaseDate;
    }

    public void onItemClick(View view) {
        FilmQuery.Film.__id__ = film.id;

        context.startActivity(new Intent(context, FilmActivity.class));
    }

    // Allows recycling ItemRepoViewModels within the recyclerview adapter
    public void setFilm(Film film) {
        this.film = film;
        //notifyChange();
    }

    @Override
    public Films getData() {
        return new Films();
    }

    @Override
    public void destroy() {
        //In this case destroy doesn't need to do anything because there is not async calls
    }

    @Override
    public void onCompleted() {

    }

}
