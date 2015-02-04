package activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ziga.todoapp.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import adapters.ListAdapter;
import helpers.OtherHelper;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = (ListView) findViewById(R.id.listview_main);
        ListAdapter.fillList(getApplicationContext(), listView);

        try{
            Thread.sleep(100);
        } catch(InterruptedException e){
            Log.e("ERROR", e.toString());
        }

        ParseQuery<ParseObject> query = ParseQuery.getQuery("ToDo");
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> list,
                             ParseException e) {
                if (e == null) {
                    try{
                        Log.i("ITEM 0", list.get(0).get("Content").toString());
                    } catch(Exception e1)
                    {
                        Log.e("ERROR", e1.toString());
                        setContentView(R.layout.layout_nothing); // HAHA, IT WORKS :D
                    }

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {;

                int colorBackground = getResources().getColor(R.color.pale_green);
                int colorText = getResources().getColor(R.color.white);

                OtherHelper.deleteItem(listView, position, getApplicationContext(), view, colorBackground, colorText);
                OtherHelper.deleteCheckedItems();
                ListAdapter.fillList(getApplicationContext(), listView);

                ParseQuery<ParseObject> query = ParseQuery.getQuery("ToDo");
                query.fromLocalDatastore();
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> list,
                                     ParseException e) {
                        if (e == null) {
                            try{
                                Log.i("ITEM 0", list.get(1).get("Content").toString()); // Get element on index 1 because the deletion on index 0 isn't detected as deleted yet. It does the trick.
                            } catch(Exception e1) {
                                Log.e("ERROR", e1.toString());
                                setContentView(R.layout.layout_nothing);
                            }

                        } else {
                            Log.d("score", "Error: " + e.getMessage());
                        }
                    }
                });
            }


        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "LONG CLICKED", Toast.LENGTH_SHORT).show();
                return true;
            }
        });


    }

    @Override
    public void onRestart() // in case you press back button on the bottom- without this it doesn't refresh
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
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

            OtherHelper.deleteCheckedItems();

            Intent intent = new Intent(MainActivity.this, NewTodoActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}