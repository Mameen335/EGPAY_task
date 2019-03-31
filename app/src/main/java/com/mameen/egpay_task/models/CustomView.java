package com.mameen.egpay_task.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CustomView implements Serializable {

        @SerializedName("id")
        @Expose
        private int Id;

        @SerializedName("name")
        @Expose
        private String name;

        @SerializedName("required")
        @Expose
        private String required;

        @SerializedName("type")
        @Expose
        private String type;

        @SerializedName("default_value")
        @Expose
        private String default_value;

        @SerializedName("multiple")
        @Expose
        private String multiple;

        @SerializedName("sort")
        @Expose
        private String sort;
}
