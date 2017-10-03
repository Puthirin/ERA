package com.example.puthirin.era;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity implements View.OnClickListener{
    Button btn;
    Intent intent;
    EditText email, password;
    String URL="192.168.1.125/user_login";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText)findViewById(R.id.eamil);
        password = (EditText)findViewById(R.id.password);
        btn = (Button)findViewById(R.id.login);
        btn.setOnClickListener(this);


//        btn = (Button)findViewById(R.id.login);
//        intent = new Intent(this, MainActivity.class);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(intent);
//            }
//        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login:
                onLonin();
                break;

        }
    }
    public void onLonin(){
        final String Email = email.getText().toString();
        String Password = password.getText().toString();
        if(!Email.isEmpty() && !Password.isEmpty()){
            onRequest();
            intent = new Intent(this,MainActivity.class);
            btn = (Button) findViewById(R.id.login);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(intent);
                }
            });
        }
        else {
            Toast.makeText(this, "no", Toast.LENGTH_SHORT).show();
        }
    }
    public void onRequest(){
        JSONObject param = new JSONObject();
        try {
            param.put("gmail", email.getText().toString());
            param.put("password", password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, param, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Toast.makeText(Login.this, ""+jsonObject.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(Login.this, ""+volleyError.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
