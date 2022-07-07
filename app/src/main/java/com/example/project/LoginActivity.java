package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.DataBase.DBHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    //binding layout to activity using butter knife
    @BindView(R.id.txtMail)
    EditText txtMail;
    @BindView(R.id.txtPass)
    EditText txtPass;
    @BindView(R.id.btnGotoSignup)
    TextView btnGotoSignup;
    @BindView(R.id.btnSignin)
    Button btnSignin;

    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        DB = new DBHelper(this);

        btnGotoSignup.setOnClickListener(v-> startActivity(new Intent(this,RegisterActivity.class))); // go to register activity
        btnSignin.setOnClickListener(v-> {

            final String email = txtMail.getText().toString();
            final String password = txtPass.getText().toString();

            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

            if (TextUtils.isEmpty(email)) {
                txtMail.setError("E-mail is Required.");
                return;
            }
            if (!email.matches(emailPattern)) {
                txtMail.setError("Enter valid e-mail");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                txtPass.setError("Password is Required.");
                return;
            }

            signIn(email,password);

        });
    }

    /**
     * sign in function
     * @param email
     * @param password
     */
    private void signIn(String email, String password) {
        Boolean checkUser = DB.checkUserEmailAndPassword(email, password);
        if(checkUser == false)
            Toast.makeText(this, "User not found ! Check e-mali or password again", Toast.LENGTH_SHORT).show();
        else
            startActivity(new Intent(this,HomeActivity.class));
    }


}