package com.example.puthirin.era;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private  static final String TAG = "Register";
    private static final String URL_Register = "http://192.168.0.117:8000/user_register";
    ProgressDialog progressDialog;
    private EditText firstName, lastName,tel,email, password;
    private Button Register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        tel = (EditText) findViewById(R.id.tel);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        Register = (Button) findViewById(R.id.submit);


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

    }

    private void submitForm() {
        registerUser(firstName.getText().toString(),lastName.getText().toString(),tel.getText().toString(),email.getText().toString(), password.getText().toString());
    }

    private void registerUser(final String fname, final String lname, final String tel, final String email, final String pw) {
        
        String cancel_req_tag = "register";
        
        progressDialog.setMessage("Adding you");
        showDialog();
        
        StringRequest strReq = new StringRequest(Request.Method.GET, URL_Register, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d(TAG, "Regiser Response: "+ s.toString());
                hideDialog();
                
                try {
                    JSONObject object = new JSONObject(s);
                    boolean error = object.getBoolean("error");
                    if (!error){
                        String user = object.getJSONObject("email").getString("email");
                        Toast.makeText(getApplicationContext(),"welcome" + user+"You are successful", Toast.LENGTH_LONG).show();
                        
                        Intent intent = new Intent(Register.this,Login.class);
                        startActivity(intent);
                        finish();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG,"Register error :"+ volleyError.getMessage());
                Toast.makeText(getApplicationContext(),volleyError.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("firstName",fname);
                params.put("lastName",lname);
                params.put("tel",tel);
                params.put("email",email);
                params.put("password",pw);
                return super.getParams();
            }
        };
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,cancel_req_tag);
        
    }

    private void hideDialog() {
        if (!progressDialog.isShowing()){
            progressDialog.show();
        }
    }
    
    private void showDialog() {
        if (!progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }



}
