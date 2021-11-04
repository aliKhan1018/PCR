package com.mak.pcr;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class FacultyLogin extends AppCompatActivity {
    TextInputLayout inputLayout_email, inputLayout_pswd;
    EditText edt_email, edt_pswd;
    CheckBox chkbox_rem;
    Button btn_login, btn_clear;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_login);


        inputLayout_email = findViewById(R.id.inputLayout_email);
        inputLayout_pswd = findViewById(R.id.inputLayout_pswd);
        edt_email = findViewById(R.id.edt_email);
        edt_pswd = findViewById(R.id.edt_pswd);
        chkbox_rem = findViewById(R.id.chkbox_rem);
        btn_login = findViewById(R.id.btn_login);
        btn_clear = findViewById(R.id.btn_clear);

        if(!Network.isNetworkAvailable(FacultyLogin.this)){
            new AlertDialog.Builder(FacultyLogin.this)
                    .setTitle("Internet Required!")
                    .setMessage("Please connect to the internet and try again")
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .setCancelable(false)
                    .show();
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _sEmail, _sPswd;

                _sEmail = edt_email.getText().toString().trim();
                _sPswd = edt_pswd.getText().toString().trim();

                if (TextUtils.isEmpty(_sEmail)) {
                    inputLayout_email.setError("Email is Required");
                    inputLayout_email.requestFocus();
                    return;
                }
                else{
                    inputLayout_email.setError(null);
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(_sEmail).matches())
                {
                    inputLayout_email.setError("Email is Required");
                    inputLayout_email.requestFocus();
                    return;
                }
                else{
                    inputLayout_email.setError(null);
                }

                if (TextUtils.isEmpty(_sPswd)) {
                    inputLayout_pswd.setError("Password is Required");
                    inputLayout_pswd.requestFocus();
                    return;
                }
                else{
                    inputLayout_pswd.setError(null);
                }
                if (_sPswd.length() != 6) {
                    inputLayout_pswd.setError("Password must be 6 characters");
                    inputLayout_pswd.requestFocus();
                    return;
                }
                else{
                    inputLayout_pswd.setError(null);
                }



            }
        });
    }
}
