package com.serloman.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.serloman.popularmovies.models.ParcelableDiscoverMovie;
import com.serloman.popularmovies.movieDetails.MovieDetailsActivity;
import com.serloman.popularmovies.movieList.BasicMovieListFragment;
import com.serloman.themoviedb_api.models.Movie;

public class MainActivity extends AppCompatActivity implements BasicMovieListFragment.OpenMovieListener, AdapterView.OnItemSelectedListener {

    public final static String ARG_MOVIE_TYPE_SELECTED = "ARG_MOVIE_TYPE_SELECTED";

    public final static int TYPE_POPULARITY = 0;
    public final static int TYPE_RATED = 1;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();

        initMovies();
    }

    private void initMovies(){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        int defaultType = sharedPref.getInt(ARG_MOVIE_TYPE_SELECTED, TYPE_POPULARITY);

        selectFragment(defaultType);
    }

    private void initToolbar(){
//        initSpinner();
        mToolbar = (Toolbar) findViewById(R.id.toolbarMain);

        this.setSupportActionBar(mToolbar);
    }

    @Deprecated
    private void initSpinner(){
        Spinner spinner = (Spinner) findViewById(R.id.mainActivitySpinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.type_movies_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_sort_by_popularity:
                selectFragment(TYPE_POPULARITY);
                return true;
            case R.id.action_sort_by_most_rated:
                selectFragment(TYPE_RATED);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void selectFragment(int type){

        Fragment fragment;

        switch (type){

            case TYPE_POPULARITY:
                fragment = PopularMoviesFragment.newInstance();
                break;
            case TYPE_RATED:
                fragment = RatedMoviesFragment.newInstance();
                break;

            default:
                fragment = PopularMoviesFragment.newInstance();
                break;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        updateSubtitle(type);
        savePreference(type);
    }

    private void savePreference(int type){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(ARG_MOVIE_TYPE_SELECTED, type);
        editor.commit();
    }

    private void updateSubtitle(int type){
        String title;

        switch (type){
            case TYPE_POPULARITY:
                title = getString(R.string.label_popularity);
                break;
            case TYPE_RATED:
                title = getString(R.string.label_most_rated);
                break;
            default:
                title = "undefined";
                break;
        }

        mToolbar.setSubtitle(title);
    }

    @Override
    public void openMovie(Movie movie) {
        Intent openMovieIntent = new Intent(this, MovieDetailsActivity.class);
        openMovieIntent.putExtra(MovieDetailsActivity.ARG_MOVIE_DATA, new ParcelableDiscoverMovie(movie));
        startActivity(openMovieIntent);
    }

    @Deprecated
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String name = parent.getItemAtPosition(position).toString();

        if(name.compareTo(getString(R.string.label_popularity))==0)
            selectFragment(TYPE_POPULARITY);
        else if(name.compareTo(getString(R.string.label_most_rated))==0)
            selectFragment(TYPE_RATED);
    }

    @Deprecated
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
