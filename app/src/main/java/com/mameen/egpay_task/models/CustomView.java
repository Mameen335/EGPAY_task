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

        public int getId() {
                return Id;
        }

        public void setId(int id) {
                Id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getRequired() {
                return required;
        }

        public void setRequired(String required) {
                this.required = required;
        }

        public String getType() {
                return type;
        }

        public void setType(String type) {
                this.type = type;
        }

        public String getDefault_value() {
                return default_value;
        }

        public void setDefault_value(String default_value) {
                this.default_value = default_value;
        }

        public String getMultiple() {
                return multiple;
        }

        public void setMultiple(String multiple) {
                this.multiple = multiple;
        }

        public String getSort() {
                return sort;
        }

        public void setSort(String sort) {
                this.sort = sort;
        }
}
