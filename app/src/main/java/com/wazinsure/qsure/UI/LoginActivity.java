package com.wazinsure.qsure.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.wazinsure.qsure.Constants.Constants;
import com.wazinsure.qsure.R;
import com.wazinsure.qsure.Service.PostService;


import android.app.ProgressDialog;
import android.os.Handler;
import android.sax.StartElementListener;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.BindView;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    public String status ="";



    Retrofit.Builder builder = new Retrofit.Builder()
                 .baseUrl(Constants.BASE_URL)
                  .addConverterFactory(GsonConverterFactory.create());


    @BindView(R.id.usernamelogin) EditText _usernameText;
    @BindView(R.id.passwordlogin) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup) TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    login();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void login() throws IOException, InterruptedException {
        Log.d(TAG, "Login trouble shoot");


        if (!validate()) {
            onLoginFailed();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme);


        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();


        // TODO: Implement authentication logic here.


        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        try {
                            userSignin();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                },1000);

    }



    private void userSignin() throws IOException, InterruptedException {
        String user_name = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();
        PostService postService = new PostService();
        postService.login(user_name, password);

       if (status.equals("")){
           onLoginSuccess();
           finish();
       }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }



    public boolean validate() {
        boolean valid = true;

        String password = _passwordText.getText().toString();

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
