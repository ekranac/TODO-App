package activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.ziga.todoapp.R;
import com.parse.ParseObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class NewTodoActivity extends ActionBarActivity {


    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Creates back button in action bar- !Need to set parent activity in manifest

        final EditText input = (EditText) findViewById(R.id.input);
        final ImageButton cross = (ImageButton) findViewById(R.id.cross);
        Button buttonSubmit = (Button) findViewById(R.id.submit_button);

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(input.length() > 0)
                {
                    cross.setVisibility(View.VISIBLE);
                }
                else
                {
                    cross.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText("");
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(input.length() != 0)
                {
                    ParseObject object = new ParseObject("ToDo");
                    object.put("Content", input.getText().toString());

                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    object.put("Created", dateFormat.format(date));
                    object.put("Checked", false);

                    object.pinInBackground();

                    input.setText("");
                }
                else
                {
                    Log.e("Empty", "BITCH");
                }
            }
        });

    }


}