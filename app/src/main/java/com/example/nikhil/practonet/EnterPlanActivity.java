package com.example.nikhil.practonet;

import android.app.Activity;
import android.content.Intent;
import android.media.SoundPool;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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

public class EnterPlanActivity extends Activity {

    EditText editTextPersonName;
    TextView textView;
    RequestQueue requestQueue;
    WebView webView;
    Spinner spinner;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_plan);

        editTextPersonName = (EditText) findViewById(R.id.et_person_name);
        spinner = (Spinner) findViewById(R.id.spinner2);
        webView = (WebView) findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        textView = (TextView) findViewById(R.id.textView3);
    }

    public void onCheck(View view){
        String messageText = editTextPersonName.getText().toString();
        if(!TextUtils.isEmpty(messageText)) {
            requestQueue = Volley.newRequestQueue(EnterPlanActivity.this);
            String url = "http:/192.168.8.69:8000/bmr";

            Log.d("event", "before req");
            //create query in json format
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put("pid", editTextPersonName.getText().toString());
            hashMap.put("intensity", spinner.getSelectedItem().toString());
            JSONObject jsonObject = new JSONObject(hashMap);
            makeRequest(url, jsonObject, requestQueue);
        }
        else{
            Toast.makeText(getApplicationContext(),"PID must not be empty!", Toast.LENGTH_SHORT).show();
        }
    }
    //send query to server and display the response
    public void makeRequest(String url, JSONObject jsonObject, final RequestQueue requestQueue){
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    //display reply from server
                    public void onResponse(JSONObject response) {
                        Log.d("response", response.toString());
                        try{
                            String messageText = response.get("bmr").toString();
                            String messageText1 = response.get("name").toString();
                            String messageText2= response.get("age").toString();
                            String messageText3 = response.get("height").toString();
                            String messageText4 = response.get("weight").toString();

                            textView.setText("Name: " + messageText1 + ", Age:" + messageText2 + ", Height:" + messageText3 + ", Weight:"+ messageText4 + ", BMR:"+messageText);
                            textView.setVisibility(View.VISIBLE);
                            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());

                            LinearLayout l1 = (LinearLayout) findViewById(R.id.linearlayout);
                            // now get a reference to your relativelayout inside it
                            RelativeLayout yourRL = (RelativeLayout)
                                    l1.findViewById(R.id.relativelayout);
                            // parameters are width, height, weight
                            yourRL.setLayoutParams(new
                                    LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1f));

                            webView.setVisibility(View.VISIBLE);
                            webView.loadUrl("https://www.google.com/search?&q="+(int)(Float.valueOf(messageText)/100)*100+"%20calorie%20diet");
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
}

