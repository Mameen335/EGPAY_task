package com.mameen.egpay_task;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mameen.egpay_task.models.CustomView;
import com.mameen.egpay_task.models.Multiple;
import com.mameen.egpay_task.utils.CustomEditText;
import com.mameen.egpay_task.utils.CustomSpinner;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    static final String TAG = MainActivity.class.getSimpleName();

    private CustomView[] customViews;

    private LinearLayout lyParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);


        lyParent = new LinearLayout(this);
        lyParent.setGravity(Gravity.CENTER_HORIZONTAL);
        lyParent.setOrientation(LinearLayout.VERTICAL);

        creatViews(lyParent);

        setContentView(lyParent);
    }

    //Load JSON file from Assets folder.
    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void creatViews(LinearLayout lyParent) {

        String jsonResponse = loadJSONFromAsset();
        // GSON and its API.
        Gson gson = new GsonBuilder().create();
        customViews = gson.fromJson(jsonResponse, CustomView[].class);

        for (CustomView viewData : customViews) {

            if (viewData.getType().equals("select")) {
                lyParent.addView(new CustomSpinner(MainActivity.this, viewData));
            } else {
                lyParent.addView(new CustomEditText(MainActivity.this, viewData));
            }
        }

        Button btnSendData = new Button(MainActivity.this);

        btnSendData.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        btnSendData.setText("Send Data");
        btnSendData.setOnClickListener(btnSendDataListener);

        lyParent.addView(btnSendData);
    }

    View.OnClickListener btnSendDataListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (validateData()) {
                String url = "http://101.amrbdr.com/";
                StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //This code is executed if the server responds, whether or not the response contains data.
                        //The String 'response' contains the server's response.

                        Log.e(TAG, "Response: " + response);
                    }
                }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //This code is executed if there is an error.
                        Log.e(TAG, "Error: " + error.getMessage());
                    }
                }) {
                    protected Map<String, String> getParams() {
                        Map<String, String> MyData = new HashMap<String, String>();

                        for (int i = 0; i < customViews.length; i++) {
                            CustomView viewData = customViews[i];
                            if (viewData.getType().equals("select")) {
                                MyData.put(viewData.getId() + ""
                                        , ((CustomSpinner) lyParent.getChildAt(i)).getSelectedItem().toString());
                            } else {
                                MyData.put(viewData.getId() + ""
                                        , ((CustomEditText) lyParent.getChildAt(i)).getText().toString());
                            }
                        }


                        return MyData;
                    }
                };

                Volley.newRequestQueue(MainActivity.this).add(MyStringRequest);
            }
        }
    };

    private Boolean validateData() {

        boolean result = true;

        for (int i = 0; i < customViews.length; i++) {
            CustomView viewData = customViews[i];
            if ((!viewData.getType().equals("select")) && viewData.getRequired().equals("yes")) {

                CustomEditText et = (CustomEditText) lyParent.getChildAt(i);

                if (!et.isValidField()) {
                    result = false;
                }
            }
        }

        return result;
    }

}
