package com.example.roberto.hilda_app;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.roberto.hilda_app.Class.Cliente;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Nuevo_Cliente extends AppCompatActivity {

    ListView lstView;
    Button btnAdd, btnEdit, btnDelete;
    EditText edtCliente;
    Cliente clienteSeleccionado = null;
    List<Cliente> clientes = new ArrayList<Cliente>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_cliente);
        //setContentView(R.layout.new_client);
        lstView = (ListView)findViewById(R.id.lstView);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnEdit = (Button)findViewById(R.id.btnEdit);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        edtCliente = (EditText)findViewById(R.id.edtClientName);

        // Load Data when app is opened
        new GetData().execute(Common.getAddressAPI());
    }

    class GetData extends AsyncTask<String,Void,String>{
        ProgressDialog pd = new ProgressDialog(Nuevo_Cliente.this);

        // before execution
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setTitle("Espere por favor ...");
            pd.show();
        }

        // Running the process
        @Override
        protected String doInBackground(String... params) {
            String stream = null;
            String urlString = params[0];


            HTTPDataHandler http = new HTTPDataHandler();
            stream = http.GetHTTPData(urlString);
            return stream;
        }

        // after execution
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            // Using Gson to parse Json document to Class
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Cliente>>(){}.getType();
            // parse s to list
            clientes = gson.fromJson(s,listType);
            // Create an adapter
            CustomAdapter adapter = new CustomAdapter(getApplicationContext(),clientes);
            // Set adapter to ListView
            lstView.setAdapter(adapter);



            pd.dismiss();
        }


    }
}
