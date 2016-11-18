package pub.terry.lab_8;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    TextInputEditText nameTextView, birthdayTextView, giftTextView;
    private Database database;
    final int REQUEST_CODE = 1;

    void makeToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        nameTextView = (TextInputEditText) findViewById(R.id.name);
        birthdayTextView = (TextInputEditText) findViewById(R.id.birthday);
        giftTextView = (TextInputEditText) findViewById(R.id.gift);
        database = DatabaseSingleton.getDatabase(this);
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameTextView.getText().toString();
                if (name.isEmpty()) {
                    makeToast("Name could not be empty.");
                } else {
                    String birthday = birthdayTextView.getText().toString();
                    String gift = giftTextView.getText().toString();
                    if (database.insert(name, birthday, gift)) {
                        setResult(REQUEST_CODE);
                        finish();
                    } else {
                        makeToast("Name is duplicated.");
                    }
                }
            }
        });


    }
}
