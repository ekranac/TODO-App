package activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.ziga.todoapp.R;
import com.parse.ParseObject;


public class NewTodoActivity extends ActionBarActivity {


    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

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
                    object.put("Checked", false);

                    object.pinInBackground();
                }
                else
                {
                    Log.e("Empty", "BITCH");
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_second, menu); // Different menu for this activity
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save_draft) {
            Toast.makeText(getApplicationContext(), "Saved as draft", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.view_drafts)
        {
            Intent intent = new Intent(NewTodoActivity.this, DraftsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}