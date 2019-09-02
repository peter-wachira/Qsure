package com.wazinsure.qsure.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wazinsure.qsure.Constants.Constants;
import com.wazinsure.qsure.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class RegistrationActivity extends AppCompatActivity {

    @BindView(R.id.fullname)
    EditText _fullnameText;
    @BindView(R.id.id_no)
    EditText _idnoText;
    @BindView(R.id.mobile_no)
    EditText _mobilenoText;
    @BindView(R.id.profile_url)
    ImageView _profileurlText;
    @BindView(R.id.email)
    EditText _emailText;
    @BindView(R.id.username)
    EditText _usernameText;
    @BindView(R.id.password)
    EditText _passwordText;
    @BindView(R.id.btn_addcustomer)
    Button _registerButton;
    @BindView(R.id.signin)
    TextView login_link;
    Uri resultUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);

   login_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Login activity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });


        _registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    registerNewUser();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // picking profile image from gallery
        _profileurlText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseProfileImage();
            }
        });
    }

    private void chooseProfileImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            final Uri imageUri = data.getData();
            resultUri = imageUri;
            _profileurlText.setImageURI(resultUri);
        }
    }
    private void registerNewUser() throws IOException, InterruptedException{
        String fullname = _fullnameText.getText().toString();
        String userName = _usernameText.getText().toString();
        String id_no = _idnoText.getText().toString();
        String mobile_no = _mobilenoText.getText().toString();
        String email = _emailText.getText().toString();
        String password= _passwordText.getText().toString();



        final ProgressDialog progressDialog = new ProgressDialog(this,
                R.style.AppTheme);


        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Adding customer...");
        progressDialog.show();
        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        try {
                            register(fullname, id_no, mobile_no, email, resultUri, userName, password);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                },4000);

    }


    public void onRegistrationSuccess() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                Toasty.success(getBaseContext(), "Registration Successful!", Toast.LENGTH_LONG, true).show();
                Toasty.success(getBaseContext(), "Logging you in", Toast.LENGTH_SHORT, true).show();

            }
        });
        Intent intent = new Intent( this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onRegistrationFailed() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                Toasty.warning(getBaseContext(), "Registration Failed", Toast.LENGTH_SHORT, true).show();

            }
        });
        _registerButton.setEnabled(true);
    }

    //    registering a  new user
    public void register(String fullname, String id_no, String mobile_no, String email, Uri profileurl, String username, String password) throws IOException {
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        String url = Constants.REGISTRATION + "/register";
        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("fullname", fullname);
            postdata.put("id_no", id_no);
            postdata.put("mobile_no", mobile_no);
            postdata.put("email", email);
            postdata.put("profileurl", profileurl);
            postdata.put("username", username);
            postdata.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                call.cancel();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String mMessage = response.body().string();
                Log.e(TAG, mMessage);
                JSONObject responseJSON = null;
                try {
                    responseJSON = new JSONObject(mMessage);

                    String registrationStatus = responseJSON.getString("status");
                    String status = registrationStatus;
                    if (status.equals("success")){
                        onRegistrationSuccess();
                    }
                    else if (status!="success"){
                        onRegistrationFailed();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
