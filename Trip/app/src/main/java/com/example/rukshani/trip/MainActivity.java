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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            String[] placeArray ={
                "Sigiriya",
                "Sinharaja",
                "Adams Peak",
                "Yala",
                "Galle Face",
                "Tooth Relic",
                "Arugam Bay",
                "Peradeniya Garden",
                "Nilaweli",
                "Trincolmalee",
                "IS",
                "Unawatuna",
                "Anuradhapura",
                "Yapahuwa"
            };
            List<String> places=new ArrayList<String>(Arrays.asList(placeArray));

            final ArrayAdapter<String> placesAdapter = new ArrayAdapter<String>(
                    getActivity(),
                    //id of list item layout
                    R.layout.list_item_places,
                    //id of the text view to populate
                    R.id.list_item_places_textview,
                    places);//name of the array list

            ListView listView= (ListView) rootView.findViewById(R.id.listview_forecast);
            listView.setAdapter(placesAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String forecast=placesAdapter.getItem(position);
//                    Intent  intent=new Intent(getActivity(),DetailActivity.class).putExtra(Intent.EXTRA_TEXT,forecast);

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

                    String des="";
                    int elev=0;
                    int lat=0;
                    int lon=0;
                    String imgpath="";
                   // String cityName ="";

                    @Override
                    public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {

                      //  Log.d("name from needed", forecastStr);
                        if (e == null) {

                            int i = 0;
                            for (ParseObject obj : parseObjects) {
                                des = obj.getString("PDes");
                                elev=obj.getInt("Elevation");
                                lat=obj.getInt("PLat");
                                lon=obj.getInt("PLong");
                                imgpath=obj.getString("ImagePath");
                                i++;
                            }
                            Log.d("score", "Retrieved............ " +  parseObjects.size() + " scores "+" Description:"+des+" Elevation:"+elev+" Lat:"+lat+" Lon:"+lon+" Imagepath:"+imgpath);//3
//                            Intent intent1=new Intent(getActivity(),DetailActivity.class);
                           // String codeString= String.valueOf(code);
//                            intent1.putExtra("codez",codeString);
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
