package com.example.roberto.hilda_app;

import android.support.annotation.NonNull;

import com.example.roberto.hilda_app.Class.Cliente;

/**
 * Created by Roberto on 8/27/2017.
 */

public class Common {
    private static String DB_NAME = "viveres_jessica";
    private static String COLLECTION_NAME = "clientes";
    public static String API_KEY =  "OmmG_RuD67sMybxczGiP0xhhPV9goumd";

    // Build a string for accessing to the DB
    public static String getAddressSingle(Cliente cliente){
        String baseUrl = String.format("https://api.mlab.com/api/1/databases/%s/collections/%s",
                DB_NAME,COLLECTION_NAME);
        StringBuilder stringBuilder =  new StringBuilder(baseUrl);
        stringBuilder.append("/"+cliente.get_id().getOid()+"?apiKey"+API_KEY);
        return stringBuilder.toString();
    }

    public static String getAddressAPI(){
        String baseUrl = String.format("https://api.mlab.com/api/1/databases/%s/collections/%s",
                DB_NAME,COLLECTION_NAME);
        StringBuilder stringBuilder =  new StringBuilder(baseUrl);
        stringBuilder.append("?apiKey="+API_KEY);
        return stringBuilder.toString();
    }

}
