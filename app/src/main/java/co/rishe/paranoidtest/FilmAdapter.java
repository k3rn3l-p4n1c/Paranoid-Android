package co.rishe.paranoidtest;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import co.rishe.paranoidandroid.mvvm.ResourceActivity;
import co.rishe.paranoidtest.databinding.ItemFilmBinding;
import co.rishe.paranoidtest.resource.Films.AllFilms.Film;
import co.rishe.paranoidtest.viewmodel.ItemFilmViewModel;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.RepositoryViewHolder> {

    private List<Film> repositories;

    public FilmAdapter() {
        this.repositories = Collections.emptyList();
    }

    public FilmAdapter(List<Film> repositories) {
        this.repositories = repositories;
    }

    public void setRepositories(List<Film> repositories) {
        this.repositories = repositories;
    }

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemFilmBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_film,
                parent,
                false);
        return new RepositoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RepositoryViewHolder holder, int position) {
        holder.bindRepository(repositories.get(position));
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    public static class RepositoryViewHolder extends RecyclerView.ViewHolder {
        final ItemFilmBinding binding;

        public RepositoryViewHolder(ItemFilmBinding binding) {
            super(binding.cardView);
            this.binding = binding;
        }

        void bindRepository(Film film) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new ItemFilmViewModel((ResourceActivity) itemView.getContext(), film));
            } else {
                binding.getViewModel().setFilm(film);
            }
        }
    }
}
