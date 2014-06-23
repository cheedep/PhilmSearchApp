package com.cheedep.philmsearch.ui;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.cheedep.philmsearch.R;
import com.cheedep.philmsearch.model.Movie;

import java.util.ArrayList;

public class MoviesListActivity extends ListActivity {

    private ArrayList<Movie> moviesList;
    private MoviesAdapter moviesAdapter;
    //private ArrayAdapter<Movie> moviesArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.movies_layout);

        //moviesList = (ArrayList<Movie>)getIntent().getSerializableExtra("movies");
        //moviesArrayAdapter = new ArrayAdapter<Movie>(MoviesListActivity.this, android.R.layout.simple_list_item_1, moviesList);

        //setListAdapter(moviesArrayAdapter);

        //NOTE: Do not pass the actual moviesList to the constructor pass new ArrayList<Movie>. why??
        moviesAdapter = new MoviesAdapter(MoviesListActivity.this, R.layout.movie_data_row, new ArrayList<Movie>());
        moviesList = (ArrayList<Movie>)getIntent().getSerializableExtra("movies");

        setListAdapter(moviesAdapter);


        if(moviesList != null && !moviesList.isEmpty()){
            moviesAdapter.notifyDataSetChanged();
            moviesAdapter.clear();
            for(int i = 0; i < moviesList.size(); i++)
                moviesAdapter.add(moviesList.get(i));
        }

        moviesAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(this, "Selected",Toast.LENGTH_SHORT);
    }
}
