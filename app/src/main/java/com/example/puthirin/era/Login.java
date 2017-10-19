package com.example.puthirin.era;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity implements View.OnClickListener{
    Button btn;
    Intent intent;
    EditText email, password;
    TextView Register;
    String URL="http://192.168.100.105:8000/user_login";
    StringRequest request;
    RequestQueue requestQueue;
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        requestQueue = Volley.newRequestQueue(this);
        email = (EditText)findViewById(R.id.eamil);
        password = (EditText)findViewById(R.id.password);
        btn = (Button)findViewById(R.id.login);
        Register =(TextView) findViewById(R.id.register);
        btn.setOnClickListener(this);
        Register.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {

        userLogin(email.getText().toString(), password.getText().toString());
    }

    private void userLogin(final String email,final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (s.trim().equals("success")) {
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    intent.putExtra(EMAIL, email);
                    intent.putExtra(PASSWORD, password);
                    startActivity(intent);
                } else {
                    Toast.makeText(Login.this, s, Toast.LENGTH_SHORT).show();
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(Login.this, volleyError.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("email", email);
                map.put("password", password);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
