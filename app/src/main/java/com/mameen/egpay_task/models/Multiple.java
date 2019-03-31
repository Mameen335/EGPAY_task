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

    //to display object as a string in spinner
    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Multiple){
            Multiple m = (Multiple )obj;
            if(m.getName().equals(name) && m.getValue()==value ) return true;
        }

        return false;
    }
}
