package com.example.login_project;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Signup extends AppCompatActivity {

    ImageView imageprofile;
    TextView tvchangeimage;
    TextInputEditText inputemail,inputpassword,tvname;
    Button buttonsignup,buttonlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        imageprofile=findViewById(R.id.imageprofile);
        tvchangeimage=findViewById(R.id.tvchangeimage);
        inputemail=findViewById(R.id.inputemail);
        inputpassword=findViewById(R.id.inputpassword);
        tvname=findViewById(R.id.tvname);
        buttonlogin=findViewById(R.id.buttonlogin);
        buttonsignup=findViewById(R.id.buttonsignup);






        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Signup.this,Login.class));
                finish();

            }
        });

        buttonsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputemail.getText().toString();
                String password = inputpassword.getText().toString();
                String name = tvname.getText().toString();


                BitmapDrawable bitmapDrawable = (BitmapDrawable) imageprofile.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,50,outputStream);
                byte[] imagebyte = outputStream.toByteArray();
                String image = Base64.encodeToString(imagebyte,Base64.DEFAULT);
                

                String url = "https://mafiul.shop/signup.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.contains("Succesful")){

                            SharedPreferences sharedPreferences = getSharedPreferences("myapp",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("email",email);
                            editor.apply();

                            startActivity(new Intent(Signup.this,MainActivity.class));
                            finish();

                        }

                        new AlertDialog.Builder(Signup.this)
                                .setTitle("Server Response")
                                .setMessage(response)
                                .create().show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        new AlertDialog.Builder(Signup.this)
                                .setTitle("Server Response")
                                .setMessage(volleyError.getMessage())
                                .create().show();

                    }
                }){

                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map mymap = new HashMap<String,String>();
                        try {
                            mymap.put("email",MyMethods.encryptdata(inputemail.getText().toString()));
                            mymap.put("password",MyMethods.encryptdata(inputpassword.getText().toString()));

                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                        mymap.put("name",name);
                        mymap.put("image",image);
                        mymap.put("key",MyMethods.MY_KEY);

                        return mymap;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(Signup.this);
                requestQueue.add(stringRequest);

            }
        });

        ActivityResultLauncher<Intent> launcher =
               registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                   @Override
                   public void onActivityResult(ActivityResult result) {

                       if (result.getResultCode() == RESULT_OK){
                           Intent intent = result.getData();
                           Uri uri = intent.getData();
                           try {
                               Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                               imageprofile.setImageBitmap(bitmap);
                           } catch (IOException e) {
                               throw new RuntimeException(e);
                           }
                       }

                   }
               });

        tvchangeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImagePicker.with(Signup.this)
                        .maxResultSize(1000,1000)
                        .compress(1024)
                        .createIntent(new Function1<Intent, Unit>() {
                            @Override
                            public Unit invoke(Intent intent) {
                                launcher.launch(intent);
                                return null;
                            }
                        });

            }
        });


    }
}