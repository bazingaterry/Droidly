package pub.terry.lab_7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileActivity extends AppCompatActivity {

    Button saveButton, loadButton, clearButton;
    EditText editText;
    final String FILENAME = "TEXTFILE";

    void init() {
        saveButton = (Button) findViewById(R.id.saveBtn);
        loadButton = (Button) findViewById(R.id.loadBtn);
        clearButton = (Button) findViewById(R.id.clearBtn);
        editText = (EditText) findViewById(R.id.textInput);
    }

    void setOnClickListener() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = editText.getText().toString();
                try (FileOutputStream fileOutputStream = openFileOutput(FILENAME, MODE_PRIVATE)) {
                    fileOutputStream.write(input.getBytes());
                    makeToast("Save successfully.");
                } catch (IOException ex) {
                    makeToast("Fail to save file.");
                }
            }
        });
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try (FileInputStream fileInputStream = openFileInput(FILENAME)) {
                    byte[] contents = new byte[fileInputStream.available()];
                    fileInputStream.read(contents);
                    editText.setText(new String(contents));
                    makeToast("Read successfully.");
                } catch (IOException ex) {
                    makeToast("Fail to read file.");
                }
            }
        });
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        init();
        setOnClickListener();
    }

    void makeToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
