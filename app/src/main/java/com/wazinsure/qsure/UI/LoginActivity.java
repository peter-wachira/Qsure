package com.wazinsure.qsure.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.wazinsure.qsure.Constants.Constants;
import com.wazinsure.qsure.R;



import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Looper;
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
import es.dmoral.toasty.Toasty;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.wazinsure.qsure.Constants.Constants;
import com.wazinsure.qsure.UI.LoginActivity;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import static android.content.ContentValues.TAG;




public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    



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
                    loginInit();
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

    public void loginInit() throws IOException, InterruptedException {
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
        String passworD = _passwordText.getText().toString();
        login(user_name,passworD);

    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {

        new Handler(Looper.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {

                Toasty.success(getBaseContext(), " Login Success!", Toast.LENGTH_SHORT, true).show();

            }
        });

        Intent intent = new Intent( this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onLoginFailed() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {

                Toasty.error(getBaseContext(), "Login Failed!", Toast.LENGTH_SHORT, true).show();
            }
        });
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

    //    User login method
    public void login(String username, String password) throws IOException {
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        String url = Constants.LOGIN + "/login";
        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("username", username);
            postdata.put("password", password);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        //creating an new post request
        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage();
                Log.w("failure Response", mMessage);
                call.cancel();;
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String mMessage = response.body().string();

                Log.e(TAG, mMessage);

                JSONObject responseJSON = null;

                try {

                    responseJSON = new JSONObject(mMessage);
                    String loginStatus = responseJSON.getString("status");
                    String status =loginStatus;

                    if (status.equals("success")){
                      onLoginSuccess();
                    }
                    else if (status!="success"){
                        onLoginFailed();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public static void backgroundThreadShortToast(final Context context, final String msg) {
        if (context != null && msg != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
