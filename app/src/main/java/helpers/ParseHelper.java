package helpers;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.SaveCallback;


public class ParseHelper extends Application{

    String appId = "WiqAlHYJXe4c4S42DTS9NfDrQe7HKNqWf0ypmpoI";
    String deviceId = "RJk9Q116isx3q4DEHnjToLxC5jrj5RQDQbZ9S6oy";

    public void onCreate() {
        Parse.enableLocalDatastore(getApplicationContext());
        Parse.initialize(this, appId, deviceId);

        // This part below is super important- it registers your device to receive push notifications
        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });
    }

}