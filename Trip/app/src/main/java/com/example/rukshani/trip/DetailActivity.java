package com.example.rukshani.trip;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


public class DetailActivity extends ActionBarActivity {

    static String forecastStr;

    public DetailActivity() {
        forecastStr = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
            Intent intent=getActivity().getIntent();
            final View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            if (intent!=null&& intent.hasExtra(Intent.EXTRA_TEXT)){
                 forecastStr=intent.getStringExtra(Intent.EXTRA_TEXT);
                ((TextView)rootView.findViewById(R.id.detail_text)).setText(forecastStr);

                Log.d("name from first............",forecastStr);//1
            }


            final ParseQuery<ParseObject> query = ParseQuery.getQuery("Place");
            query.whereEqualTo("PName", forecastStr);
            query.findInBackground(new FindCallback<ParseObject>() {

                ImageView imageView = (ImageView) rootView.findViewById(R.id.image_View);

                String images ="";

                @Override
                public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {

                    if (e == null) {
                        int i = 0;
                        for (ParseObject obj : parseObjects) {
                            images = obj.getString("ImagePath");
                            i++;
                        }

                        Log.d("score", "Retrieved " + parseObjects.size() + " scores " +"image: " + images);
                        imageView.setImageURI(Uri.parse(images));

                    } else {
                        Log.d("score", "Error: " + e.getMessage());
                    }
                }
            });

            Button btn_weather= (Button) rootView.findViewById(R.id.btn_weather);
            btn_weather.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent weatherIntent=new Intent(getActivity(), weatherMainActivity.class).putExtra(Intent.EXTRA_TEXT,forecastStr);
                    startActivity(weatherIntent);
                }
            });
            return rootView;
        }
    }
}
