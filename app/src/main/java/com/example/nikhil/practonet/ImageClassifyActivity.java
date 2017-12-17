package com.example.nikhil.practonet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ImageClassifyActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    EditText editText;
    TextView textView;
    Button heartAttack;
    ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_classify);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editText = (EditText) findViewById(R.id.editText);
        heartAttack = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView5);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
    }
    public void makeRequest(String url, JSONObject jsonObject, final RequestQueue requestQueue){
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    //display reply from server
                    public void onResponse(JSONObject response) {
                        Log.d("response", response.toString());
                        try{
                            String resStr = response.get("likely").toString();
                            String artStr = response.get("articles").toString();
                            String testStr = response.get("testing").toString();
                            String docStr = response.get("doc").toString();
                            Intent intent = new Intent(getApplicationContext(), DiagnoseActivity.class);
                            intent.putExtra("likely",resStr);
                            intent.putExtra("articles",artStr);
                            intent.putExtra("testing", testStr);
                            intent.putExtra("doc", docStr);
                            startActivity(intent);
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                        requestQueue.stop();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });


        Log.d("event", "making req");
        requestQueue.add(jsObjRequest); //add request to request queue
    }

    public void onHeartAttack(View view){
        HashMap<String, String> hashMap = new HashMap<String, String>();
        String pid = editText.getText().toString();
        if(!pid.isEmpty()) {
            hashMap.put("pid", pid);
            String url = "http:/192.168.8.69:8000/read";
            JSONObject jsonObject = new JSONObject(hashMap);
            requestQueue = Volley.newRequestQueue(ImageClassifyActivity.this);
            makeRequest(url, jsonObject, requestQueue);
        }
        else{
            Toast.makeText(getApplicationContext(), "PID cannot be empty!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onKidney(View view){
        HashMap<String, String> hashMap = new HashMap<String, String>();
        String pid = editText.getText().toString();
        if(!pid.isEmpty()) {
            hashMap.put("pid", pid);
            String url = "http:/192.168.8.69:8000/kidney";
            JSONObject jsonObject = new JSONObject(hashMap);
            requestQueue = Volley.newRequestQueue(ImageClassifyActivity.this);
            makeRequest(url, jsonObject, requestQueue);
        }
        else{
            Toast.makeText(getApplicationContext(), "PID cannot be empty!", Toast.LENGTH_SHORT).show();
        }
    }

}
