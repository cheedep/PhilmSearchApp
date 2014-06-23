package com.cheedep.philmsearch.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cheedep.philmsearch.R;
import com.cheedep.philmsearch.model.Movie;
import com.cheedep.philmsearch.model.Person;
import com.cheedep.philmsearch.services.GenericSeeker;
import com.cheedep.philmsearch.services.MovieSeeker;
import com.cheedep.philmsearch.services.PersonSeeker;

import java.util.ArrayList;


public class MainActivity extends Activity {

    //API Key: d07f4f6aab5e70d630f77205edcd335f

    EditText searchText;
    Button searchButton;
    RadioGroup searchRadioGroup;
    RadioButton movieRadioButton;
    RadioButton personRadioButton;

    private GenericSeeker<Movie> movieSeeker = new MovieSeeker();
    private GenericSeeker<Person> personSeeker = new PersonSeeker();

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findAllViewsById();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = searchText.getText().toString().trim();

                if(query.equals(""))
                    Toast.makeText(getBaseContext(), "Enter a search text", Toast.LENGTH_LONG).show();
                else{
                    performSearch(query);
                }
            }
        });

        searchText.setOnFocusChangeListener(new DefaultTextOnFocusChangeListener(getString(R.string.search)));
    }

    private void findAllViewsById() {
        searchText = (EditText)findViewById(R.id.search_edit_text);
        searchButton = (Button)findViewById(R.id.search_button);
        searchRadioGroup = (RadioGroup)findViewById(R.id.search_radio_group);
        movieRadioButton = (RadioButton) findViewById(R.id.philm_radio_button);
        personRadioButton = (RadioButton) findViewById(R.id.philmi_star_radio_button);
    }

    private void performSearch(String query) {

        progressDialog = ProgressDialog.show(MainActivity.this,
                "Please wait...", "Retrieving data...", true, true);

        if (movieRadioButton.isChecked()) {
            MovieSearchTask task = new MovieSearchTask();
            task.execute(query);
            progressDialog.setOnCancelListener(new CancelTaskOnCancelListener(task));
        }
        else if (personRadioButton.isChecked()) {
            PersonSearchTask task = new PersonSearchTask();
            task.execute(query);
            progressDialog.setOnCancelListener(new CancelTaskOnCancelListener(task));
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class DefaultTextOnFocusChangeListener implements View.OnFocusChangeListener
    {
        String defaultText;
        public DefaultTextOnFocusChangeListener(String defaultText)
        {
            this.defaultText = defaultText;
        }

        @Override
        public void onFocusChange(View view, boolean hasFocus) {

            if(view instanceof EditText){
                EditText v = (EditText)view;
                String sText = v.getText().toString().trim();
                if(hasFocus){
                    if(sText.equals(defaultText)){
                        v.setText("");
                        v.setTextColor(Color.parseColor("#00FF00"));
                    }
                }
                else{
                    if(sText.equals("")){
                        v.setText(defaultText);
                        v.setTextColor(Color.parseColor("#efefef"));
                    }
                }
            }
        }
    }

    private class MovieSearchTask extends AsyncTask<String, Void, ArrayList<Movie>> {

        @Override
        protected ArrayList<Movie> doInBackground(String... strings) {
            String query = strings[0];
            return movieSeeker.find(query);
        }

        @Override
        protected void onPostExecute(final ArrayList<Movie> movieList) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(progressDialog != null) {
                     progressDialog.dismiss();
                        progressDialog = null;
                    }
                    if(movieList != null) {
                        Intent intent = new Intent(MainActivity.this, MoviesListActivity.class);
                        intent.putExtra("movies", movieList);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    private class PersonSearchTask extends AsyncTask<String, Void, ArrayList<Person>> {

        @Override
        protected ArrayList<Person> doInBackground(String... strings) {
            String query = strings[0];
            return personSeeker.find(query);
        }

        @Override
        protected void onPostExecute(final ArrayList<Person> personList) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(progressDialog != null) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                    if(personList != null) {
                        Intent intent = new Intent(MainActivity.this, PersonListActivity.class);
                        intent.putExtra("persons", personList);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    private class CancelTaskOnCancelListener implements DialogInterface.OnCancelListener{

        private AsyncTask<?,?,?> task;

        private CancelTaskOnCancelListener(AsyncTask<?,?,?> task){
            this.task = task;
        }

        @Override
        public void onCancel(DialogInterface dialogInterface) {
            if(task != null){
                task.cancel(true);
            }
        }
    }
}


