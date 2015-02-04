package adapters;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ziga.todoapp.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class ListAdapter {

    public static void applyAdapter(Context context, List<ParseObject> list, ListView listView)
    {
        ArrayList<String> strings = new ArrayList<String>();
        for(int i = 0; i < list.size(); i++)
        {
            strings.add(list.get(i).getString("Content"));
        }

        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(
                // Get context of the activity
                context,
                // List item layout
                R.layout.list_item,
                // ID of the TextView to populate
                R.id.list_item_textview,
                // ArrayList data
                strings
        );

        listView.setAdapter(mAdapter);

    }

    public static void fillList(final Context context, final ListView listView)
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ToDo");
        query.fromLocalDatastore();
        query.whereEqualTo("Checked", false);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    ListAdapter.applyAdapter(context, list, listView);
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

    }
}
