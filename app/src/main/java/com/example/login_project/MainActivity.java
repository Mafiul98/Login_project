package com.example.login_project;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("myapp",MODE_PRIVATE);

        try {
            MyMethods.MY_KEY = MyMethods.encryptdata("mafi223344");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String email = sharedPreferences.getString("email","");
        if (email.length()<=0){

            startActivity(new Intent(MainActivity.this,Login.class));
            finish();
        }

    }
//========================jason object===============================================

    private void ObjectRequest(){

        String url = "";
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("key",MyMethods.MY_KEY);
            jsonObject.put("email",sharedPreferences.getString("email",""));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String image = response.getString("image");
                    String result = response.getString("result");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError Error) {

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Server Response")
                        .setMessage(Error.getMessage())
                        .create().show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsonObjectRequest);



    }


}