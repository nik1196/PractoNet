package com.example.nikhil.practonet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DiagnoseActivity extends AppCompatActivity {
    TextView diagnoseText, linksText, testsText, docText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnose);
        diagnoseText = (TextView) findViewById(R.id.diagnosetext);
        linksText = (TextView) findViewById(R.id.linkstext);
        testsText = (TextView) findViewById(R.id.teststext);
        docText = (TextView) findViewById(R.id.doctext);
        Bundle bundle = getIntent().getExtras();
        String artStr = bundle.get("articles").toString();
        artStr = artStr.replaceAll(";","");
        artStr = artStr.replaceAll(",","\n");

        diagnoseText.setText(bundle.get("likely").toString());
        linksText.setText(artStr);
        if(!bundle.get("testing").toString().isEmpty())
            testsText.setText(bundle.get("testing").toString());
        docText.setText(bundle.get("doc").toString());
    }


}
