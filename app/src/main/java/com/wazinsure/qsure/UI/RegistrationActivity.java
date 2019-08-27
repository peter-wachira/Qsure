package com.wazinsure.qsure.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wazinsure.qsure.R;
import com.wazinsure.qsure.Service.PostService;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        PostService postServiceRegister = new PostService();
        postServiceRegister.register(fullname, id_no, mobile_no, email, resultUri, userName, password);
    }






}
