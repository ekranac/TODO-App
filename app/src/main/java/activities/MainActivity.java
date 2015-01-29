package activities;

import android.app.Activity;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ziga.todoapp.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.List;

import adapters.ListAdapter;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = (ListView) findViewById(R.id.listview_main);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("ToDo");
        query.fromLocalDatastore();
        query.whereEqualTo("Checked", false);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    ListAdapter.fillList(getApplicationContext(), list, listView);
                    for(int i = 0; i < list.size(); i++)
                    {
                        Log.i("ITEM", list.get(i).get("Content").toString());
                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("ToDo");
                query.fromLocalDatastore();
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> list, ParseException e) {
                        if (e == null) {
                            for(int i = 0; i < list.size(); i++)
                            {
                                if(listView.getItemAtPosition(position).toString() == list.get(i).getString("Content"))
                                {
                                    ParseObject object = list.get(i);
                                    object.put("Checked", true);
                                    object.saveInBackground();
                                }

                                Log.i("listView.getItemAtPosition(position).toString()", listView.getItemAtPosition(position).toString());
                                Log.i("list.get(i).getString('Content')", list.get(i).getString("Content"));
                            }
                        } else {
                            Log.d("score", "Error: " + e.getMessage());
                        }
                    }
                });

                // TODO
                // delete all with Checked=true... probably shouldn't be too hard
                //http://stackoverflow.com/questions/23608373/how-to-delete-particular-record-from-parse-table-in-android

                // You're a shit programmer, your apps are shit, everything you do is SHIT, you useless fuck... Go kill yourself
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add) {
            Intent intent = new Intent(MainActivity.this, NewTodoActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}