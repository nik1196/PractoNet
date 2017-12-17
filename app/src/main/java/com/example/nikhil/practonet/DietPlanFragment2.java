package com.example.nikhil.practonet;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import static android.app.Activity.RESULT_OK;

public class DietPlanFragment2 extends Fragment implements OnItemClickListener {

    private PlanListCursorAdapter planListAdapter;
    private PlanDatabaseHelper databaseHelper;
    private static final int ENTER_DATA_REQUEST_CODE = 1;
    private ListView listView;
    private Button createPlanButton;
    private static final String TAG = FitnessActivity.class.getSimpleName();
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diet_plan_fragment2, container, false);



        Log.d("frag database","creating");
        databaseHelper = new PlanDatabaseHelper(getActivity());

        Log.d("plan button","creating");
        createPlanButton = (Button)view.findViewById(R.id.createplanbutton);
        createPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity().getApplicationContext(), EnterPlanActivity.class), ENTER_DATA_REQUEST_CODE);
            }
        });

        Log.d("plan list","creating");
        listView = (ListView) view.findViewById(R.id.planlist);
        listView.setEmptyView(view.findViewById(android.R.id.empty));
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "clicked on item: " + position);
            }
        });

        // Database query can be a time consuming task ..
        // so its safe to call database query in another thread
        // Handler, will handle this stuff for you <img src="http://s0.wp.com/wp-includes/images/smilies/icon_smile.gif?m=1129645325g" alt=":)" class="wp-smiley">
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                planListAdapter = new PlanListCursorAdapter(getActivity().getApplicationContext(), databaseHelper.getAllData());
                listView.setAdapter(planListAdapter);
            }
        });



        return view;
    }

   /* @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }*/

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ENTER_DATA_REQUEST_CODE && resultCode == RESULT_OK) {

            databaseHelper.insertData(data.getExtras().getString("tag_person_name"), data.getExtras().getString("tag_person_pin"));

            planListAdapter.changeCursor(databaseHelper.getAllData());
        }
    }


}