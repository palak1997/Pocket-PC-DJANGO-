package com.example.yash.splash;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */

public class Interactive extends Fragment {

    String CheckURL="http://192.168.43.224:7000/rango/session/";
    ArrayList<String> items = new ArrayList<>();
    String ask="";
    Button bt;
    Button back;
    public Interactive() {

    }


    ArrayAdapter<String> adapter;
    ListView lv;
    String toask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View V = inflater.inflate(R.layout.fragment_interactive, container, false);

        try {
            String sessionname = getArguments().getString("sessionName");
            CheckURL += sessionname + "/";

            ListView lv = (ListView) V.findViewById(R.id.listView);
            toask = "ask_show_dir";
            GetContent getContent = new GetContent();
            getContent.execute();

            adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(getActivity(),items.get(position),Toast.LENGTH_SHORT).show();
                    if (items.get(position).equals(items.get(position - 1))) {
                        //continue;
                    } else {
                        toask += "+";
                        toask += items.get(position);
                        String URL = CheckURL;
                        try {
                            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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
                                    data.put("Ask_Android", toask);
                                    return data;
                                }
                            };
                            requestQueue.add(stringRequest);
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
        }


        bt = (Button) V.findViewById(R.id.refresh);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.notifyDataSetChanged();
                if (ask.toString().equals("")) {
                    GetContent getContent = new GetContent();
                    getContent.execute();
                } else {
                    GetFiles getFiles = new GetFiles();
                    getFiles.execute();
                    //Toast.makeText(getContext(),"ask is : " + ask.toString(),Toast.LENGTH_LONG).show();
                }
                adapter.notifyDataSetChanged();
            }
        });

        back = (Button) V.findViewById(R.id.backbutton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] prev = toask.split("\\+");
                int to=prev.length;
                toask="";
                if(to>=1) {
                    for (int i = 0; i < to - 1; i++) {
                        toask += prev[i];
                        toask += "+";
                    }
                }
                Toast.makeText(getActivity(),"String to ask : "+toask,Toast.LENGTH_SHORT).show();
                String URL = CheckURL;
                try {
                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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
                            data.put("Ask_Android", toask);
                            return data;
                        }
                    };
                    requestQueue.add(stringRequest);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        return V;
    }

    public class GetContent extends AsyncTask<Void,Void,Void> {

        GetContent(){
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect(CheckURL).get();
                Elements cont = doc.select("p[id=return-values]");
                ask = cont.text();
                Log.d("content downloaded",ask);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            String[] idata = ask.split(" ");
            int i;
            for(i=0;i<idata.length;i++){
                items.add(idata[i]);
                Log.d("splited",idata[i]);
            }
            adapter.notifyDataSetChanged();
        }
    }

    public class GetFiles extends AsyncTask<Void,Void,Void>{ // Get files and folders

        GetFiles(){

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect(CheckURL).get();
                Elements folder = doc.select("p[id=donwload-folders]");
                Elements file = doc.select("p[id=download-files]");
                ask = folder.text();
                ask += "Files are-@";
                ask += file.text();
                Log.d("content downloaded",ask);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            items.clear();
            String[] idata = ask.split("@");
            int i;
            for(i=0;i<idata.length;i++){
                items.add(idata[i]);
                Log.d("splited",idata[i]);
            }
            adapter.notifyDataSetChanged();
        }
    }
}
