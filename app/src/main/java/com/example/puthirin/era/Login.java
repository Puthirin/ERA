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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


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
    String URL_login= "http://192.168.100.105:8000/user_login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email =  (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        progressDialog = new ProgressDialog(this);



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
        progressDialog.setMessage("Successful");
        showDialog();
        JSONObject params = new JSONObject();
        try {

            params.put("email",email);
            params.put("password",password);
        }catch (JSONException e){

        }

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL_login, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Toast.makeText(Login.this, "gg", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(Login.this, volleyError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }

    private void hideDialog() {
        if (progressDialog.isShowing())progressDialog.show();
    }
    private void showDialog() {
        if (progressDialog.isShowing())progressDialog.dismiss();
    }


}
