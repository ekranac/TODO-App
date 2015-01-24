package adapters;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ziga.todoapp.R;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ListAdapter {

    public static void fillList(Context context, List<ParseObject> list, ListView listView)
    {
        String[] contentArray = new String[list.size()];
        for(int i = 0; i < list.size(); i++)
        {
            contentArray[i] = list.get(i).getString("Content");
        }

        // Transforms raw data from Array to ArrayList so it can be implemented to adapter later
        List<String> contentList = new ArrayList<String>(Arrays.asList(contentArray));

        // Adapter set
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(
                // Get context of the activity
                context,
                // List item layout
                R.layout.list_item,
                // ID of the TextView to populate
                R.id.list_item_textview,
                // ArrayList data
                contentList
        );

        listView.setAdapter(mAdapter);
    }
}
