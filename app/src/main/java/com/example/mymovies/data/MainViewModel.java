package com.example.mymovies.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainViewModel extends AndroidViewModel {

    private static MovieDatabase database;
    private LiveData<List<Movie>> movies;
    private LiveData<List<FavoriteMovie>> favoriteMovies;

    public MainViewModel(@NonNull Application application) {
        super(application);
        database = MovieDatabase.getInstance(getApplication());
        movies = database.movieDAO().getAllMovies();
        favoriteMovies = database.movieDAO().getAllFavoriteMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public Movie getMovieById(int id) {
        try {
            return new GetMovieByIdTask().execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public FavoriteMovie getFavoriteMovieById(int id) {
        try {
            return new GetFavoriteMovieByIdTask().execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LiveData<List<FavoriteMovie>> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void deleteAllMovies() {
        new DeleteAllMoviesTask().execute();
    }

    public void deleteMovie(Movie movie) {
        new DeleteMoviesTask().execute(movie);
    }

    public void insertMovie(Movie movie) {
        new InsertMovieTask().execute(movie);
    }

    public void deleteFavoriteMovie(FavoriteMovie favoriteMovie) {
        new DeleteFavoriteMoviesTask().execute(favoriteMovie);
    }

    public void insertFavoriteMovie(FavoriteMovie favoriteMovie) {
        new InsertFavoriteMovieTask().execute(favoriteMovie);
    }

    private static class InsertMovieTask extends AsyncTask<Movie, Void, Void> {
        @Override
        protected Void doInBackground(Movie... movies) {
            if (movies != null && movies.length > 0) {
                database.movieDAO().insertMovie(movies[0]);
            }
            return null;
        }
    }

    private static class DeleteMoviesTask extends AsyncTask<Movie, Void, Void> {
        @Override
        protected Void doInBackground(Movie... movies) {
            if (movies != null && movies.length > 0) {
                database.movieDAO().deleteMovie(movies[0]);
            }
            return null;
        }
    }

    private static class InsertFavoriteMovieTask extends AsyncTask<FavoriteMovie, Void, Void> {
        @Override
        protected Void doInBackground(FavoriteMovie... movies) {
            if (movies != null && movies.length > 0) {
                database.movieDAO().insertFavoriteMovie(movies[0]);
            }
            return null;
        }
    }

    private static class DeleteFavoriteMoviesTask extends AsyncTask<FavoriteMovie, Void, Void> {
        @Override
        protected Void doInBackground(FavoriteMovie... movies) {
            if (movies != null && movies.length > 0) {
                database.movieDAO().deleteFavoriteMovie(movies[0]);
            }
            return null;
        }
    }

    private static class DeleteAllMoviesTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... movies) {
            database.movieDAO().deleteAllMovies();
            return null;
        }
    }

    private static class GetMovieByIdTask extends AsyncTask<Integer, Void, Movie> {
        @Override
        protected Movie doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return database.movieDAO().getMovieById(integers[0]);
            }
            return null;
        }
    }

    private static class GetFavoriteMovieByIdTask extends AsyncTask<Integer, Void, FavoriteMovie> {
        @Override
        protected FavoriteMovie doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return database.movieDAO().getFavoriteMovieById(integers[0]);
            }
            return null;
        }
    }
}
