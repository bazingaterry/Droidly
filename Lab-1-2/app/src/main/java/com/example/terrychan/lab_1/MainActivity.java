package com.example.terrychan.lab_1;

import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private String selectedUserType = "学生";
    private EditText usernameInput, passwordInput;
    private RadioGroup radioGroup;
    private Button loginBtn, signupBtn;
    private TextInputLayout usernameLayout, passwordLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getElement();
        setSelectResponse();
        setButtonDialog();
    }

    void getElement() {
        usernameInput = (EditText) findViewById(R.id.inputUsername);
        passwordInput = (EditText) findViewById(R.id.inputPassword);
        radioGroup = (RadioGroup) findViewById(R.id.select);
        loginBtn = (Button) findViewById(R.id.login);
        signupBtn = (Button) findViewById(R.id.signup);
        usernameLayout = (TextInputLayout) findViewById(R.id.usernameLayout);
        passwordLayout = (TextInputLayout) findViewById(R.id.passwordLayout);
    }

    void makeDialog(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case AlertDialog.BUTTON_POSITIVE:
                        makeSnackbar("对话框“确定”按钮被点击");
                        break;
                    case AlertDialog.BUTTON_NEGATIVE:
                        makeSnackbar("对话框“取消”按钮被点击");
                        break;
                }
            }
        };
        builder.setTitle("提示").setMessage(text);
        builder.setPositiveButton("确定", onClickListener).setNegativeButton("取消", onClickListener);
        builder.create().show();
    }

    void makeToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    void makeSnackbar(String text) {
        Snackbar.make(findViewById(R.id.activity_main), text, Snackbar.LENGTH_SHORT)
                .setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        makeToast("啊 Snackbar 被戳了一下!");
                    }
                }).setDuration(5000).show();
    }

    void setSelectResponse() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.select0:
                        selectedUserType = "学生";
                        break;
                    case R.id.select1:
                        selectedUserType = "教师";
                        break;
                    case R.id.select2:
                        selectedUserType = "社团";
                        break;
                    case R.id.select3:
                        selectedUserType = "管理者";
                        break;
                }
                makeSnackbar(String.format("%s身份被选中。", selectedUserType));
            }
        });
    }

    void setButtonDialog() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();
                if (Objects.equals(username, "") && Objects.equals(password, "")) {
                    usernameLayout.setErrorEnabled(true);
                    passwordLayout.setErrorEnabled(true);
                    usernameLayout.setError("用户名不能为空");
                    passwordLayout.setError("密码不能为空");
                } else if (Objects.equals(username, "")) {
                    usernameLayout.setErrorEnabled(true);
                    passwordLayout.setErrorEnabled(false);
                    usernameLayout.setError("用户名不能为空");
                } else if (Objects.equals(password, "")) {
                    passwordLayout.setErrorEnabled(true);
                    usernameLayout.setErrorEnabled(false);
                    passwordLayout.setError("密码不能为空");
                } else if (Objects.equals(username, "Android") && Objects.equals(password, "123456")) {
                    makeSnackbar("登录成功");
                    usernameLayout.setErrorEnabled(false);
                    passwordLayout.setErrorEnabled(false);
                } else {
                    makeSnackbar("登录失败");
                    usernameLayout.setErrorEnabled(false);
                    passwordLayout.setErrorEnabled(false);
                }
            }
        });
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeSnackbar(String.format("%s身份注册功能尚未开启", selectedUserType));
            }
        });
    }
}
