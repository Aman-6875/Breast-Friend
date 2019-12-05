package com.example.aman.breast_friendapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class RegisterActivity extends AppCompatActivity {
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMPTY = "";
    private EditText etUsername;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private EditText etFullName;
    private String username;
    private String password;
    private String gender,g;
    private String age;
    int eage;
    private String lastname;
    private String phonenum;
    private String confirmPassword;
    private String fullName;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton,male,female;
    private ProgressDialog pDialog;
    String sex ="Female";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private String register_url = "http://192.168.0.101/newandroid/register.php";
    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());
        setContentView(R.layout.activity_register);



        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        final EditText etage = findViewById(R.id.age);
        final EditText etphon = findViewById(R.id.phone);
        final EditText etlastname = findViewById(R.id.lastname);
        //etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etFullName = findViewById(R.id.etFullName);
        radioSexGroup =findViewById(R.id.radioSex);

        male = (RadioButton) findViewById(R.id.radioMale);
        female = (RadioButton) findViewById(R.id.radioFemale);


//        if(male.isChecked()){
//            sex = (String) male.getText();
//        }


//        if(selectedId == R.id.radioMale) {
//            g = "Male";
//        }
//        else {
//            g = "Female";
//        }

        Button login = findViewById(R.id.btnRegisterLogin);
        Button register = findViewById(R.id.btnRegister);

        //Launch Login screen when Login Button is clicked
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                username = etUsername.getText().toString().toLowerCase().trim();


                password = etPassword.getText().toString().trim();
               // confirmPassword = etConfirmPassword.getText().toString().trim();
                fullName = etFullName.getText().toString().trim();

                age = etage.getText().toString().trim();//birthday

                phonenum = etphon.getText().toString().trim();
                lastname = etlastname.getText().toString().trim();
                //int eage =Integer.parseInt(age);
                int selectedId = radioSexGroup.getCheckedRadioButtonId();
                radioSexButton = findViewById(selectedId);
//                //Toast.makeText(RegisterActivity.this,
//                        radioSexButton.getText(), Toast.LENGTH_SHORT).show();
                gender = radioSexButton.getText().toString().trim();


                if (validateInputs()) {
                    registerUser();
                }

            }
        });

    }

    /**
     * Display Progress bar while registering
     */
    private void displayLoader() {
        pDialog = new ProgressDialog(RegisterActivity.this);
        pDialog.setMessage("Signing Up.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    /**
     * Launch Dashboard Activity on Successful Sign Up
     */
    private void loadDashboard() {
        Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
        startActivity(i);
        finish();

    }

    private void registerUser() {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, username);
            request.put(KEY_PASSWORD, password);
            request.put(KEY_FULL_NAME, fullName);
            request.put("gender",gender);
            request.put("age",age);
            request.put("phonenum",phonenum);
            request.put("lastname",lastname);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, register_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            //Check if user got registered successfully
                            if (response.getInt(KEY_STATUS) == 0) {
                                //Set the user session
                                session.loginUser(username,fullName);
                                loadDashboard();

                            }else if(response.getInt(KEY_STATUS) == 1){
                                //Display error message if username is already existsing
                                etUsername.setError("Email already taken!");
                                etUsername.requestFocus();

                            }else{
                                Toast.makeText(getApplicationContext(),
                                        response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

    /**
     * Validates inputs and shows error if any
     * @return
     */
    private boolean validateInputs() {

        if (KEY_EMPTY.equals(fullName)) {
            etFullName.setError("First Name cannot be empty");
            etFullName.requestFocus();
            return false;

        }
        if (KEY_EMPTY.equals(username)) {
            etUsername.setError("Email cannot be empty");
            etUsername.requestFocus();
            return false;
        }
        if (etUsername.getText().toString().trim().matches(emailPattern)) {
            //Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();

        } else {
            //Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
            etUsername.setError("Invalid email address");
            etUsername.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(password)) {
            etPassword.setError("Password cannot be empty");
            etPassword.requestFocus();
            return false;
        }

//        if (KEY_EMPTY.equals(confirmPassword)) {
//            etConfirmPassword.setError("Confirm Password cannot be empty");
//            etConfirmPassword.requestFocus();
//            return false;
//        }
//        if (!password.equals(confirmPassword)) {
//            etConfirmPassword.setError("Password and Confirm Password does not match");
//            etConfirmPassword.requestFocus();
//            return false;
//        }


        return true;
    }
}
