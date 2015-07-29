package com.example.rukshani.trip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Created by rukshani on 7/24/2015.
 */

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        Parse.initialize(this, "e8zwpzFHlfYtfzW6pMbs2KLSjyB8WKYcT5c7kJz4", "7QypGcqL7rGNBT6P0Lu4pRpZOtY6yym3paccs36A");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            String[] placeArray ={
                    "Adam's Peak",
                    "Anuradhapura",
                    "Arcade",
                    "Arugam bay",
                    "Dehiwala zoo",
                    "Dunhinda fall",
                    "Gangarame Temple",
                    "Hakgala",
                    "Hikkaduwa",
                    "Hortan plain",
                    "Jaffna",
                    "knuckles",
                    "Nelum Pokuna",
                    "Nilaweli",
                    "Peradeniya Garden",
                    "Nuwara Eliya",
                    "Pinnawala Elephant Orphanage",
                    "Pinnawala Open Zoo",
                    "Ritigala",
                    "Sigiriya",
                    "Sinharaja",
                    "Temple Of Tooth Relic",
                    "Trinco Bay",
                    "Unawatuna",
                    "Yala",
                    "Yapahuwa"
            };
            List<String> places=new ArrayList<String>(Arrays.asList(placeArray));

            final ArrayAdapter<String> placesAdapter = new ArrayAdapter<String>(
                    getActivity(),
                    R.layout.list_item_places,
                    R.id.list_item_places_textview,
                    places);

            ListView listView= (ListView) rootView.findViewById(R.id.listview_forecast);
            listView.setAdapter(placesAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String forecast=placesAdapter.getItem(position);

                ParseObject gameScore = new ParseObject("Place");
//                gameScore.put("PName", 1337);
//                gameScore.put("PDes", "Sean Plott");
//                gameScore.put("Elevation", 99);
//                gameScore.put("PLat", 929);
//                gameScore.put("PLong", 989);
//                gameScore.saveInBackground();


                final ParseQuery<ParseObject> query = ParseQuery.getQuery("Place");
                query.whereEqualTo("PName", forecast);
                query.findInBackground(new FindCallback<ParseObject>() {

//                    String des="";
//                    int elev=0;
//                    long lat=0;
//                    long lon=0;
                    String imgpath="";

                    @Override
                    public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {

                        if (e == null) {

                            int i = 0;
                            for (ParseObject obj : parseObjects) {
//                                des = obj.getString("PDes");
//                                elev=obj.getInt("Elevation");
//                                lat=obj.getLong("PLat");
//                                lon=obj.getLong("PLong");
                                imgpath=obj.getString("ImagePath");
                                i++;
                            }
                          //  Log.d("score", "Retrieved............ " +  parseObjects.size() + " scores "+" Description:"+des+" Elevation:"+elev+" Lat:"+lat+" Lon:"+lon+" Imagepath:"+imgpath);//3
//
                        } else {
                            Log.d("score", "Error: " + e.getMessage());
                        }
                    }
                });
                Intent  intent=new Intent(getActivity(),DetailActivity.class).putExtra(Intent.EXTRA_TEXT,forecast);
                startActivity(intent);

                Toast.makeText(getActivity(),forecast,Toast.LENGTH_SHORT).show();
                }
            });
            return rootView;
        }
    }
}
