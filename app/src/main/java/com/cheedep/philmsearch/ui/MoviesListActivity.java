package com.cheedep.philmsearch.ui;

import android.app.Dialog;
import android.app.ListActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.cheedep.philmsearch.R;
import com.cheedep.philmsearch.io.FlushedInputStream;
import com.cheedep.philmsearch.model.Movie;
import com.cheedep.philmsearch.services.HttpRetriever;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MoviesListActivity extends ListActivity {

    private ArrayList<Movie> moviesList;
    private MoviesAdapter moviesAdapter;
    //private ArrayAdapter<Movie> moviesArrayAdapter;

    private HttpRetriever httpRetriever = new HttpRetriever();

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
        Movie movie = moviesAdapter.getItem(position);
        showMoviePosterDialog(movie);
    }

    private void showMoviePosterDialog(Movie movie) {
        final Dialog posterDialog = new Dialog(this);
        posterDialog.setContentView(R.layout.movie_poster_dialog);
        posterDialog.setTitle(movie.name);

        ImageView posterImageView = (ImageView)posterDialog.findViewById(R.id.movie_poster);
        Button closeButton = (Button)posterDialog.findViewById(R.id.close_button);

        Bitmap posterImage;
        String posterUrl = movie.retrievePosterUrl();

        if(posterUrl !=null) {
            posterImage = fetchBitmapFromCache(posterUrl);
            if(posterImage == null){
                new BitmapDownloaderTask(posterImageView).execute(posterUrl);
            }
            else
                posterImageView.setImageBitmap(posterImage);
        }


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                posterDialog.dismiss();
            }
        });

        posterDialog.show();
    }

    private LinkedHashMap<String, Bitmap> bitmapCache = new LinkedHashMap<String, Bitmap>();

    private void addBitmapToCache(String url, Bitmap bitmap) {
        if (bitmap != null) {
            synchronized (bitmapCache) {
                bitmapCache.put(url, bitmap);
            }
        }
    }

    private Bitmap fetchBitmapFromCache(String url){
        synchronized (bitmapCache) {
            final Bitmap bitmap = bitmapCache.get(url);
            if (bitmap != null) {
                // Bitmap found in cache
                // Move element to first position, so that it is removed last
                bitmapCache.remove(url);
                bitmapCache.put(url, bitmap);
                return bitmap;
            }
        }
        return null;
    }

    private class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {

        private String url;
        private final WeakReference<ImageView> imageViewReference;

        public BitmapDownloaderTask(ImageView imageView){
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            url = strings[0];
            InputStream inputStream = httpRetriever.retrieveStream(url);
            if(inputStream == null)
                return null;
            return BitmapFactory.decodeStream(new FlushedInputStream(inputStream));
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(isCancelled())
                bitmap = null;
            addBitmapToCache(url, bitmap);
            if(imageViewReference != null){
                ImageView imageView = imageViewReference.get();
                if(imageView != null){
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }
}
