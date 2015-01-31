package activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ziga.todoapp.R;

import java.util.ArrayList;

import adapters.ListAdapter;
import helpers.OtherHelper;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = (ListView) findViewById(R.id.listview_main);

        ListAdapter.fillList(getApplicationContext(), listView);

        listView.setEmptyView( findViewById( R.id.empty_list_view ) );


        // TODO
        // Check if ListView is empty- if it is, display "NO TODOS"

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {

                int colorBackground = getResources().getColor(R.color.pale_green);
                int colorText = getResources().getColor(R.color.white);

                OtherHelper.deleteItem(listView, position, getApplicationContext(), view, colorBackground, colorText);
                OtherHelper.deleteCheckedItems();
                ListAdapter.fillList(getApplicationContext(), listView);
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