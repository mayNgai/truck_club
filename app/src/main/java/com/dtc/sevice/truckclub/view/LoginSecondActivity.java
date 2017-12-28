package com.dtc.sevice.truckclub.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.helper.GlobalVar;
import com.dtc.sevice.truckclub.model.TblMember;
import com.dtc.sevice.truckclub.presenters.LoginSecondPresenter;
import com.dtc.sevice.truckclub.service.ApiService;
import com.dtc.sevice.truckclub.view.driver.activity.DriverRegisterActivity;
import com.dtc.sevice.truckclub.view.user.activity.UserRegisterActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by admin on 9/20/2017 AD.
 */

public class LoginSecondActivity extends AppCompatActivity implements View.OnClickListener{
    private Button UserRegisterBtn;
    private ImageButton UserLoginBtn;
    private EditText UserUsernameEdt,UserPasswordEdt;
    private TextView txt_authen;
    public String str_user_name,str_password,str_device_id,token;
    public int member_type;
    public static String str_authen = "",str_member_type = "";
    private Activity _activity;
    private ApiService mApiService;
    private LoginSecondPresenter mUserLoginSecondPresenter;
    public static TblMember tblMember;

    ///connect facebook //////////
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private boolean flagAutoLogin = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ////connect facebook ////
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login_secound);
        Intent extra = getIntent();
        if (extra != null) {
            if(extra.hasExtra("authen"))
                str_authen = extra.getStringExtra("authen");
            if(extra.hasExtra("member_type")) {
                flagAutoLogin = true;
                member_type = extra.getIntExtra("member_type", 0);
            }if(extra.hasExtra("face_book_id"))
                id = extra.getStringExtra("face_book_id");
            if(extra.hasExtra("user_name"))
                str_user_name = extra.getStringExtra("user_name");
            if(extra.hasExtra("password"))
                str_password = extra.getStringExtra("password");
        }
        init();
        if(flagAutoLogin)
            setAutoLogin();
    }

    private void setAutoLogin(){
        if(member_type == 0){
            UserUsernameEdt.setText(str_user_name);
            UserPasswordEdt.setText(str_password);
            setLogin();
        }else if(member_type == 1){
            callService();
        }
    }
    private void setLogin(){
        try {
//            if(str_authen.equalsIgnoreCase(getString(R.string.txtDriver))){
//                Intent i = new Intent(LoginSecondActivity.this, DriverMainActivity2.class);
//                startActivity(i);
//                finish();
//            }else {
                boolean cancel = false;
                View focusView = null;
                member_type = 0;
                str_user_name = UserUsernameEdt.getText().toString().trim();
                str_password = UserPasswordEdt.getText().toString().trim();

                if(str_user_name.length()==0){
                    UserUsernameEdt.setError(getString(R.string.error_invalid_useer_name));
                    focusView = UserUsernameEdt;
                    cancel = true;
                }else if(str_password.length()==0){
                    UserPasswordEdt.setError(getString(R.string.error_invalid_pass));
                    focusView = UserPasswordEdt;
                    cancel = true;

                }

                if(cancel) {
                    focusView.requestFocus();
                }else {
                    callService();
                }
//            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void init() {
        _activity = LoginSecondActivity.this;
        UserRegisterBtn = (Button)findViewById(R.id.UserRegisterBtn);
        UserLoginBtn = (ImageButton)findViewById(R.id.UserLoginBtn);
        UserUsernameEdt = (EditText)findViewById(R.id.UserUsernameEdt);
        UserPasswordEdt = (EditText)findViewById(R.id.UserPasswordEdt);
        txt_authen = (TextView)findViewById(R.id.txt_authen);
        UserRegisterBtn.setOnClickListener(this);
        UserLoginBtn.setOnClickListener(this);
        txt_authen.setText(str_authen);
        str_device_id = GlobalVar.getDeviceID(LoginSecondActivity.this);
        loginButton = (LoginButton)findViewById(R.id.facebook_login);
        loginFacebook();
    }

    public String id = "",email="",first_name = "",last_name = "",gender = "",birthday = "";
    private void loginFacebook(){
        try {
            loginButton.setReadPermissions(Arrays.asList("email", "user_birthday","public_profile"));
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {

                    GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            try {
                                Log.i("LoginActivity", response.toString());
//                                Bundle bFacebookData = getFacebookData(object);
                                id = object.getString("id");
                                if(object.has("first_name"))
                                    first_name = object.getString("first_name");
                                if(object.has("last_name"))
                                    last_name = object.getString("last_name");
                                if(object.has("email"))
                                    email = object.getString("email");
                                if(object.has("gender"))
                                    gender = object.getString("gender");
                                if(object.has("birthday"))
                                    birthday = object.getString("birthday");
                                member_type = 1;
                                callService();

                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                    request.setParameters(parameters);
                    request.executeAsync();

                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onError(FacebookException error) {
                    Log.e("Error connect Facebook",error.toString());
                }
            });

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callService(){
        try {
            token = FirebaseInstanceId.getInstance().getToken();
            if(str_authen.equalsIgnoreCase("Driver"))
                FirebaseMessaging.getInstance().subscribeToTopic("news");
            tblMember = new TblMember();
            tblMember.setUser_name(str_user_name);
            tblMember.setPassword(str_password);
            tblMember.setAuthority(str_authen);
            tblMember.setMember_type(member_type);
            tblMember.setDevice_id(str_device_id);
            tblMember.setFace_book_id(id);
            tblMember.setToken_firebase(token);
            mApiService = new ApiService();
            mUserLoginSecondPresenter = new LoginSecondPresenter(LoginSecondActivity.this,mApiService);
            mUserLoginSecondPresenter.loadLogin();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.UserRegisterBtn:
                if(str_authen.equalsIgnoreCase(getString(R.string.txtUser))){
                    Intent i = new Intent(_activity, UserRegisterActivity.class);
                    i.putExtra("authen",R.string.txtUser);
                    startActivity(i);
                    finish();
                }else if(str_authen.equalsIgnoreCase(getString(R.string.txtDriver))){
                    Intent i = new Intent(_activity, DriverRegisterActivity.class);
                    i.putExtra("authen",R.string.txtDriver);
                    startActivity(i);
                    finish();
                }

                break;
            case R.id.UserLoginBtn:
                setLogin();
                break;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent i = new Intent(LoginSecondActivity.this, LoginFirstActivity.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
