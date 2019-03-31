package com.mameen.egpay_task.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mameen.egpay_task.MainActivity;
import com.mameen.egpay_task.models.CustomView;
import com.mameen.egpay_task.models.Multiple;

@SuppressLint("AppCompatCustomView")
public class CustomSpinner extends Spinner {

    private CustomView viewData;

    public CustomSpinner(Context context) {
        super(context);
    }

    public CustomSpinner(Context context, CustomView viewData) {
        super(context);

        this.viewData = viewData;

        this.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        this.setId(viewData.getId());

        Gson gson = new GsonBuilder().create();
        Multiple[] multiples = gson.fromJson(viewData.getMultiple(), Multiple[].class);

        ArrayAdapter<Multiple> spinnerArrayAdapter = new ArrayAdapter<Multiple>(context
                , android.R.layout.simple_spinner_dropdown_item, multiples);
        this.setAdapter(spinnerArrayAdapter);
    }
}
