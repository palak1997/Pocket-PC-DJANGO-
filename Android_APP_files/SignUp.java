package com.example.yash.splash;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;


import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignUp extends AppCompatActivity {
    private static final int REQUEST_SIGNUP = 0;
    boolean is_app = false;
    boolean is_pc = false;
    String CheckURL = "http://192.168.43.224:7000/rango";
    ProgressDialog progressDialog;
    @InjectView(R.id.input_name) EditText _nameText;
    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_signup) Button _signupButton;
    @InjectView(R.id.link_login) TextView _loginLink;
    @InjectView(R.id.fb_label) EditText _fbkey;
    @InjectView(R.id.input_id) EditText _uniqueid;
    @InjectView(R.id.app_option) CheckBox _app_option;
    @InjectView(R.id.pc_option) CheckBox _pc_option;
    String name;
    String email;
    String password;
    String uniqueId;
    String fbid;
    String pc;
    String app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.inject(this);
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        //Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }
        _app_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked()){
                    is_app = true;
                }
            }
        });

        _pc_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked()){
                    is_pc = true;
                }
            }
        });

        _signupButton.setEnabled(false);
        progressDialog = new ProgressDialog(SignUp.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String var = "";
        String var2 = "";
        if(is_pc){
            var = "ON";
        }
        else{
            var = "None";
        }

        if(is_app){
            var2 = "ON";
        }
        else{
            var2 = "None";
        }

        name = _nameText.getText().toString();
        email = _emailText.getText().toString();
        password = _passwordText.getText().toString();
        uniqueId = _uniqueid.getText().toString();
        fbid = _fbkey.getText().toString();
        pc = var;
        app = var2;
        final String URL = "http://192.168.43.224:7000/rango/add_user/";

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest StringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                protected Map<String, String> getParams() {
                    Map<String, String> data = new HashMap<String, String>();
                    data.put("NAME", name);
                    data.put("UNIQUEID",uniqueId);
                    data.put("EMAIL",email);
                    data.put("EMAILPASS",password);
                    data.put("FBOATH",fbid);
                    data.put("add_pc_attr",pc);
                    data.put("add_android_attr",app);
                    return data;
                }
            };
            requestQueue.add(StringRequest);
            Content c = new Content();
            SystemClock.sleep(2000);
            c.execute();
        }
        catch(Exception e){
            Toast.makeText(getApplicationContext(),"data not sent "+e.toString(),Toast.LENGTH_LONG).show();
        }


    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        Intent i = new Intent(getApplicationContext(),Application.class);
        i.putExtra("name_of_client",password);
        startActivity(i);
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 100) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    public class Content extends AsyncTask<Void,Void,Void>{
        private boolean found;
        Content(){
            found=false;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect(CheckURL).get();
                String title  = doc.title();
                /*System.out.println("title is "+title);*/
                //Toast.makeText(getApplicationContext(),"progress",Toast.LENGTH_SHORT).show();
                Elements links = doc.select("a[href]");
                for(Element link : links){
                    String ids = link.text();
                    Log.d("thisis",ids);
                    if(ids.equals(uniqueId)){
                        found=true;
                    }
                }
                //Toast.makeText(getApplicationContext(),"done",Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                //Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(found){
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"You are now logged in",Toast.LENGTH_SHORT).show();
                onSignupSuccess();
            }
            else{
                progressDialog.dismiss();
                onSignupFailed();
            }
        }
    }
}