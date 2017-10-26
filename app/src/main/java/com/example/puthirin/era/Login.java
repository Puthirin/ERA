package com.example.puthirin.era;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class Login extends AppCompatActivity{
    private Button login,register;
    Intent intent;
    EditText email, password;
    private static final String TAG = "Login";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email =  (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        progressDialog = new ProgressDialog(this);
        String URL = "http://192.168.100.105/user_login";
        progressDialog.setCancelable(false);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(email.getText().toString(),password.getText().toString());
            }


        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regiser = new Intent(getApplicationContext(), Register.class);
                startActivity(regiser);
            }
        });
    }

    private void loginUser(final String email, final String password) {
        String cancel_reg_tag = "Login";
        progressDialog.setMessage("Successful");
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response"+response.toString());
                hideDialog();
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");
                    if (!error){
                        String user = jsonObject.getJSONObject("user").getString("name");
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.putExtra("username",user);
                        startActivity(intent);
                        finish();
                    }else {
                        String errorMsg = jsonObject.getString("error");
                        Toast.makeText(getApplicationContext(),errorMsg, Toast.LENGTH_SHORT).show();
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "Login Error: "+ volleyError.getMessage());
                Toast.makeText(getApplicationContext(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest,cancel_reg_tag);
    }

    private void hideDialog() {
        if (progressDialog.isShowing())progressDialog.show();
    }
    private void showDialog() {
        if (progressDialog.isShowing())progressDialog.dismiss();
    }


}
