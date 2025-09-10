package com.example.login_project;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    Button btnlogin,btnsignup;
    TextInputEditText textemail,textpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnlogin=findViewById(R.id.btnlogin);
        btnsignup=findViewById(R.id.btnsignup);
        textemail=findViewById(R.id.textemail);
        textpassword=findViewById(R.id.textpassword);

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Login.this,Signup.class));

            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://mafiul.shop/login.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.contains("VALID LOGIN")){

                            SharedPreferences sharedPreferences = getSharedPreferences("myapp",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("email",textemail.getText().toString());
                            editor.apply();

                            startActivity(new Intent(Login.this,MainActivity.class));
                            finish();

                        }

                        new AlertDialog.Builder(Login.this)
                                .setTitle("Server Response")
                                .setMessage(response)
                                .create().show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        new AlertDialog.Builder(Login.this)
                                .setTitle("Server Response")
                                .setMessage("Error")
                                .create().show();

                    }
                }){

                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map mymap = new HashMap<String,String>();
                        try {
                            mymap.put("email",MyMethods.encryptdata(textemail.getText().toString()));
                            mymap.put("password",MyMethods.encryptdata(textpassword.getText().toString()));
                            mymap.put("key",MyMethods.MY_KEY);

                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                        return mymap;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
                requestQueue.add(stringRequest);

            }
        });



    }
}