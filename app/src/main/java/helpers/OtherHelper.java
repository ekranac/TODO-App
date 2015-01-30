package helpers;


import android.app.Activity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class OtherHelper{

    public static void deleteItem(final ListView listView, final int position)
    {

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
                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    public static void deleteCheckedItems()
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ToDo");
        query.fromLocalDatastore();
        query.whereEqualTo("Checked", true);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    for(int i = 0; i<list.size(); i++)
                    {
                        list.get(i).unpinInBackground();
                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }
}
