package com.mameen.egpay_task.utils;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.EditText;

import com.mameen.egpay_task.MainActivity;
import com.mameen.egpay_task.models.CustomView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@SuppressLint("AppCompatCustomView")
public class CustomEditText extends EditText {

    final Calendar myCalendar = Calendar.getInstance();

    private String required = "no";

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(final Context context, CustomView viewData) {
        super(context);

        this.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        this.setId(viewData.getId());
        this.setHint(viewData.getName());
        this.required = viewData.getRequired();

        String defaultValue = viewData.getDefault_value();
        if (defaultValue != null) {
            this.setText(defaultValue);
        }

        String type = viewData.getType();
        switch (type) {
            case "string":
                this.setInputType(InputType.TYPE_CLASS_TEXT);
                this.setImeOptions(EditorInfo.IME_ACTION_DONE);
                break;
            case "number":
                this.setInputType(InputType.TYPE_CLASS_NUMBER);
                this.setImeOptions(EditorInfo.IME_ACTION_DONE);
                break;
            case "date":
                this.setClickable(true);
                this.setImeOptions(EditorInfo.IME_ACTION_DONE);
                this.setInputType(InputType.TYPE_NULL);
                this.setFocusable(false);
                this.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                  int dayOfMonth) {
                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH, monthOfYear);
                                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                updateLabel();
                            }

                        }, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });
                break;
            case "textarea":
                this.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                break;
        }
    }

    private void updateLabel() {
        String myFormat = "yyyy-mm-dd"; // In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        this.setText(sdf.format(myCalendar.getTime()));
    }

}
