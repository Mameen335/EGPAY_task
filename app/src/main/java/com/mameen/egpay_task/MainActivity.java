package com.mameen.egpay_task;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mameen.egpay_task.models.CustomView;
import com.mameen.egpay_task.models.Multiple;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    static final String TAG = MainActivity.class.getSimpleName();


    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        creatViews();
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

    private void creatViews() {
        LinearLayout lyParent = (LinearLayout) findViewById(R.id.lyParent);

        String jsonResponse = loadJSONFromAsset();
        // GSON and its API.
        Gson gson = new GsonBuilder().create();
        CustomView[] customViews = gson.fromJson(jsonResponse, CustomView[].class);

        for (CustomView viewData : customViews) {

            if (viewData.getType().equals("select")) {
                lyParent.addView(createSpinner(viewData));
            } else {

                lyParent.addView(createEditText(viewData));
            }
        }
    }

    private Spinner createSpinner(CustomView viewData) {
        Spinner spin = new Spinner(MainActivity.this);

        spin.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        spin.setId(viewData.getId());

        Gson gson = new GsonBuilder().create();
        Multiple[] multiples = gson.fromJson(viewData.getMultiple(), Multiple[].class);

        ArrayAdapter<Multiple> spinnerArrayAdapter = new ArrayAdapter<Multiple>(MainActivity.this
                , android.R.layout.simple_spinner_dropdown_item, multiples);
        spin.setAdapter(spinnerArrayAdapter);

        return spin;
    }

    private EditText createEditText(CustomView viewData) {
        final EditText et = new EditText(MainActivity.this);

        et.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        et.setId(viewData.getId());
        et.setHint(viewData.getName());

        String defaultValue = viewData.getDefault_value();
        if (defaultValue != null) {
            et.setText(defaultValue);
        }

        String type = viewData.getType();
        switch (type) {
            case "string":
                et.setInputType(InputType.TYPE_CLASS_TEXT);
                et.setImeOptions(EditorInfo.IME_ACTION_DONE);
                break;
            case "number":
                et.setInputType(InputType.TYPE_CLASS_NUMBER);
                et.setImeOptions(EditorInfo.IME_ACTION_DONE);
                break;
            case "date":
                et.setClickable(true);
                et.setImeOptions(EditorInfo.IME_ACTION_DONE);
                et.setInputType(InputType.TYPE_NULL);
                et.setFocusable(false);
                et.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                  int dayOfMonth) {
                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH, monthOfYear);
                                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                updateLabel(et);
                            }

                        }, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });
                break;
            case "textarea":
                et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                break;
        }

        return et;
    }

    private void updateLabel(EditText editText) {
        String myFormat = "yyyy-mm-dd"; // In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        editText.setText(sdf.format(myCalendar.getTime()));
    }

}
