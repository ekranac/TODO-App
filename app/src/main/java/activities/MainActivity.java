package activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ziga.todoapp.R;
import adapters.ListAdapter;
import helpers.OtherHelper;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = (ListView) findViewById(R.id.listview_main);

        ListAdapter.fillList(getApplicationContext(), listView);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {

                OtherHelper.deleteItem(listView, position);
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_out_right);
                animation.setDuration(250);
                view.setBackgroundColor(getResources().getColor(R.color.pale_green));
                ((TextView) view).setTextColor(getResources().getColor(R.color.white));
                view.startAnimation(animation);
                view.setVisibility(view.INVISIBLE);
                OtherHelper.deleteCheckedItems();
                ListAdapter.fillList(getApplicationContext(), listView);
            }
        });


    }

    @Override
    public void onRestart()
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