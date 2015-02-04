package helpers;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ziga.todoapp.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


public class OtherHelper{

    public static void deleteItem(final ListView listView, final int position, final Context context, final View view, final int colorBackground, final int colorText)
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

                Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_out_right);
                animation.setDuration(250);
                view.setBackgroundColor(colorBackground);
                ((TextView) view).setTextColor(colorText);
                view.startAnimation(animation);
                view.setVisibility(view.INVISIBLE);
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
