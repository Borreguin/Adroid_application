package com.example.roberto.hilda_app.Class;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Roberto on 8/27/2017.
 */

public class Id {
    @SerializedName("$oid")
    private String oid;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }
}
