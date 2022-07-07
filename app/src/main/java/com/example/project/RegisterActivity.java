package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.DataBase.DBHelper;
import com.example.project.Modals.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {

    //binding layout to activity using butter knife
    @BindView(R.id.txtMail)
    EditText txtMail;
    @BindView(R.id.txtPass)
    EditText txtPass;
    @BindView(R.id.txtConPass)
    EditText txtConPass;
    @BindView(R.id.txtName)
    EditText txtName;
    @BindView(R.id.txtType)
    Spinner txtType;
    @BindView(R.id.gotoSignin)
    TextView gotoSignin;
    @BindView(R.id.btn_register)
    Button btn_register;

    DBHelper DB;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
        DB = new DBHelper(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.userType_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txtType.setAdapter(adapter);

        gotoSignin.setOnClickListener(v-> startActivity(new Intent(this,LoginActivity.class))); // go to register activity
        btn_register.setOnClickListener(v-> {

            final String email = txtMail.getText().toString();
            final String password = txtPass.getText().toString();
            final String conPassword = txtConPass.getText().toString();
            final String name = txtName.getText().toString();
            final String type = txtType.getSelectedItem().toString();

            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

            if (TextUtils.isEmpty(name)) {
                txtName.setError("User name is Required.");
                return;
            }
            if (TextUtils.isEmpty(email)) {
                txtMail.setError("E-mail is Required.");
                return;
            }
            if (DB.checkUserEmail(email)) {
                txtMail.setError("Email is already exist.");
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
            if (TextUtils.isEmpty(conPassword)) {
                txtConPass.setError("Conform password again.");
                return;
            }
            if (!conPassword.equals(password)) {
                txtConPass.setError("Conform password is not match the password.");
                txtConPass.setText(null);
                return;
            }
            if (TextUtils.isEmpty(type)) {
                Toast.makeText(this, "Select the user type", Toast.LENGTH_SHORT).show();
                return;
            }
            user = new User(name,email,password,type);

            signUp(user);
        });
    }

    /**
     * Sign up function
     * @param user
     */
    private void signUp(User user) {
        Boolean addUser = DB.saveUser(user);
        if(addUser == false)
            Toast.makeText(this, "Error! : User added fail", Toast.LENGTH_SHORT).show();
        else
            startActivity(new Intent(this,HomeActivity.class));
    }
}