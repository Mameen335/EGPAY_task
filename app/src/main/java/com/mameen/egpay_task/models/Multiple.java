package com.mameen.egpay_task.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Multiple implements Serializable {

    @SerializedName("value")
    @Expose
    private String value;

    @SerializedName("name")
    @Expose
    private String name;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
