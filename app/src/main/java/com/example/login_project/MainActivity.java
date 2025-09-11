package com.example.login_project;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    ImageView imageview;
    TextView tvdisplay;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageview=findViewById(R.id.imageview);
        tvdisplay=findViewById(R.id.tvdisplay);
        logout=findViewById(R.id.logout);
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
        }else {

            ObjectRequest();
        }


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email","");
                editor.apply();

                startActivity(new Intent(MainActivity.this,Login.class));
                finish();
            }
        });




    }
//========================jason object===============================================

    private void ObjectRequest(){

        String url = "https://mafiul.shop/home.php";
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

                    tvdisplay.setText(result);
                    Picasso.get().load(image).into(imageview);


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