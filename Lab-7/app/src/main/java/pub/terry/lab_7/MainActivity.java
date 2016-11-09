package pub.terry.lab_7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button okButton, clearButton, resetButton;
    TextInputLayout newPasswordLayout, confirmPasswordLayout;
    TextInputEditText newPasswordInput, confirmPasswordInput;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    final String KEY = "KEY";

    void init() {
        okButton = (Button) findViewById(R.id.okBtn);
        clearButton = (Button) findViewById(R.id.clearBtn);
        resetButton = (Button) findViewById(R.id.resetBtn);
        newPasswordInput = (TextInputEditText) findViewById(R.id.newPasswordInput);
        confirmPasswordInput = (TextInputEditText) findViewById(R.id.confirmPasswordInput);
        newPasswordLayout = (TextInputLayout) findViewById(R.id.newPasswordLayout);
        confirmPasswordLayout = (TextInputLayout) findViewById(R.id.confirmPasswordLayout);
        sharedPreferences = getSharedPreferences(KEY, MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    void setOnClickListener() {
        if (sharedPreferences.getString(KEY, null) != null) {
            newPasswordLayout.setVisibility(View.INVISIBLE);
            confirmPasswordLayout.setHint("Password");
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String passwordInput = confirmPasswordInput.getText().toString();
                    if (Objects.equals(passwordInput, sharedPreferences.getString("password", null))) {
                        makeToast("OK!");
                        Intent intent = new Intent(MainActivity.this, FileActivity.class);
                        startActivity(intent);
                    } else {
                        makeToast("Invalid Password.");
                    }
                }
            });
        } else {
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String newPassword = newPasswordInput.getText().toString(),
                            confirmPassword = confirmPasswordInput.getText().toString();
                    if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                        makeToast("Password cannot be empty.");
                    } else if (Objects.equals(newPassword, confirmPassword)) {
                        editor.putString(KEY, newPassword).apply();
                        makeToast("Saved.");
                        finish();
                    } else {
                        makeToast("Password Mismatch.");
                    }
                }
            });
        }
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newPasswordInput.setText("");
                confirmPasswordInput.setText("");
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.remove(KEY).apply();
                makeToast("Clear.");
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setOnClickListener();
    }

    void makeToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
