package com.example.roberto.hilda_app;

import android.system.Os;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.UnknownServiceException;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;


/**
 * Created by Roberto on 8/27/2017.
 */

public class HTTPDataHandler {
    static String stream=null;

    public HTTPDataHandler(){

    }

    // Get the streamed data from urlString
    public String GetHTTPData(String urlString){
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

            // Check the connection status
            // response == 200 then HTTP is OK
            if(urlConnection.getResponseCode() == 200){
                InputStream in_stream =  new BufferedInputStream(urlConnection.getInputStream());

                //reading the buffered input stream
                BufferedReader rdr = new BufferedReader(new InputStreamReader(in_stream));
                StringBuilder sb =  new StringBuilder();
                String line;
                while( (line=rdr.readLine()) != null){
                    sb.append(line);
                }
                stream = sb.toString();
                urlConnection.disconnect();

            }
            else{
                // TODO: Only if is needed
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return stream;
    }

    public void PostHTTPData(String urlString, String json){
        try{
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            byte[] out = json.getBytes(StandardCharsets.UTF_8);
            int length = out.length;

            urlConnection.setFixedLengthStreamingMode(length);
            urlConnection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            urlConnection.connect();;
            try(OutputStream os = urlConnection.getOutputStream())
            {
                os.write(out);
            }
            InputStream response = urlConnection.getInputStream();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void PutHTTPData(String urlString, String newValue){
        try {
            // Define the connection for posting
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("PUT");
            urlConnection.setDoOutput(true);

            // Define size of package
            byte[] out = newValue.getBytes(StandardCharsets.UTF_8);
            int length = out.length;

            urlConnection.setFixedLengthStreamingMode(length);
            urlConnection.setRequestProperty("Content=Type", "application/java; charset=UTF-8" );
            urlConnection.connect();;
            try(OutputStream os = urlConnection.getOutputStream()){
                os.write(out);
            }
            InputStream response = urlConnection.getInputStream();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void DeleteHTTPData(String urlString, String json){
        try {
            // Define the connection for posting
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("DELETE");
            urlConnection.setDoOutput(true);

            // Define size of package
            byte[] out = json.getBytes(StandardCharsets.UTF_8);
            int length = out.length;

            urlConnection.setFixedLengthStreamingMode(length);
            urlConnection.setRequestProperty("Content=Type", "application/java; charset=UTF-8" );
            urlConnection.connect();;
            try(OutputStream os = urlConnection.getOutputStream()){
                os.write(out);
            }
            InputStream response = urlConnection.getInputStream();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
