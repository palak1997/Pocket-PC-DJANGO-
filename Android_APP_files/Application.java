package com.example.yash.splash;

import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;

public class Application extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @InjectView(R.id.input_id) EditText _uniqueid;
    NavigationView navigationView = null;
    Toolbar toolbar = null;
    EditText attachfield ;
    String activefragment="Email";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);

        //Set the fragment initailly
        final MainFragment fragment = new MainFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();

        Button bt = (Button)findViewById(R.id.submit_button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String displaytext="";
                EditText ed1 = (EditText)findViewById(R.id.downloadURL);
                EditText ed2 = (EditText)findViewById(R.id.emailTo);
                EditText ed3 = (EditText)findViewById(R.id.subjectEmail);
                EditText ed4 = (EditText)findViewById(R.id.emailMessageBody);
                EditText ed5 = (EditText)findViewById(R.id.attachmmentEmailView);
                EditText ed6 = (EditText)findViewById(R.id.statusFacebook);
                EditText ed7 = (EditText)findViewById(R.id.attachmentField);
                EditText ed8 = (EditText)findViewById(R.id.tweetText);
                EditText ed9 = (EditText)findViewById(R.id.attachmmentTwitterView);
                //String session =_uniqueid.getText().toString();
                if (activefragment.equals("Email")) {
                    displaytext += "Email";
                    displaytext += "+";
                    displaytext += ed2.getText().toString(); //to
                    displaytext += "+";
                    displaytext += ed3.getText().toString(); //subject
                    displaytext += "+";
                    displaytext += ed4.getText().toString(); //body
                    displaytext += "+";
                    displaytext += ed5.getText().toString(); //files
                    //displaytext += "<end>";
                } else if (activefragment.equals("Facebook")) {
                    displaytext += "Facebook";
                    displaytext += "+";
                    if(ed7.getText().toString().equals("")){
                        displaytext+="updatestatus+";
                    }
                    else{
                        displaytext+="uploadpicture+";
                    }
                    displaytext += ed6.getText().toString(); //facebook status
                    displaytext += "+";
                    displaytext += ed7.getText().toString(); //facebook attachment
                    //displaytext += "<end>";
                } else if (activefragment.equals("Twitter")) {
                    displaytext += "Twitter";
                    displaytext += "+";
                    if(ed9.getText().toString().equals("")){
                        displaytext+="tweetpost+";
                    }
                    else{
                        displaytext+="tweetimage+";
                    }
                    displaytext += ed8.getText().toString(); //twitter twwet
                    displaytext += "+";
                    displaytext += ed9.getText().toString(); //twitter attachment
                    //displaytext += "<end>";
                } else if (activefragment.equals("DownloadImage")) {
                    displaytext += "DownloadImage";
                    displaytext += "+";
                    displaytext += ed1.getText().toString();
                    //displaytext += "<end>";
                } else if(activefragment.equals("InteractView")){
                    return;
                }
                final String requested = displaytext ;
                Toast.makeText(getApplicationContext(),displaytext,Toast.LENGTH_LONG).show();
                //TODO:- make url dynamic
                String session = getIntent().getExtras().getString("name_of_client");
                String URL = "http://192.168.43.224:7000/rango/session/"+session+"/";
                Toast.makeText(getApplicationContext(),"Attempting for : " +URL,Toast.LENGTH_SHORT).show();
                try{
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
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
                            data.put("Requested_Android",requested);
                            return data;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
                catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"cannot send data",Toast.LENGTH_SHORT).show();
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.application, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            //E-mail fragment
            activefragment = "Email";
            MainFragment fragment = new MainFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_gallery) {
            //Facebook fragment
            activefragment = "Facebook";
            FacebookFragment fragment = new FacebookFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_slideshow) {
            //Twitter fragment
            activefragment = "Twitter";
            TwitterFragment fragment = new TwitterFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_manage) {
            //Download Image
            activefragment = "DownloadImage";
            DownloadFragment fragment = new DownloadFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,fragment);
            fragmentTransaction.commit();
        } /*else if (id == R.id.nav_share) {

        } */else if (id == R.id.nav_send) {
            //Interaction session
            activefragment = "InteractView";
            String session = getIntent().getExtras().getString("name_of_client");
            Interactive interactive = new Interactive();
            Bundle bundle = new Bundle();
            bundle.putString("sessionName",session);
            interactive.setArguments(bundle);
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,interactive);
            fragmentTransaction.commit();
            String URL = "http://192.168.51.54:7000/rango/session/"+session+"/";


            Toast.makeText(getApplicationContext(),"Attempting for : " +URL,Toast.LENGTH_SHORT).show();
            try{
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
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
                        data.put("Ask_Android","ask_show_dir");
                        return data;
                    }
                };
                requestQueue.add(stringRequest);
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setAttachmentVisible(View v){
        attachfield = (EditText)findViewById(R.id.attachmentField);
        attachfield.setVisibility(View.VISIBLE);
    }

    public void setEmailAttachmentVisible(View v){
        EditText ed = (EditText)findViewById(R.id.attachmmentEmailView);
        ed.setVisibility(View.VISIBLE);
    }

    public void setTwitterAttachmentVisible(View v){
        EditText ed = (EditText)findViewById(R.id.attachmmentTwitterView);
        ed.setVisibility(View.VISIBLE);
    }
}
