package com.example.roberto.hilda_app;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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

        // see the list:
        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clienteSeleccionado = clientes.get(position);
                //set text to edit text
                edtCliente.setText(clienteSeleccionado.getCliente());
            }
        });

        // Adding event for button Add
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PostData(edtCliente.getText().toString()).execute(Common.getAddressAPI());
            }
        });

        //This function needs a selected client for deleting from the List View

        btnEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new PutData(edtCliente.getText().toString()).execute(
                        Common.getAddressSingle(clienteSeleccionado));
            }
        });

        // Delete a selected client
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteData(clienteSeleccionado).execute(
                        Common.getAddressSingle(clienteSeleccionado));
            }
        });





    }

    // Function for processing the data coming from DB
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

    // Function to add new user
    class PostData extends AsyncTask<String, String, String>{
        ProgressDialog pd = new ProgressDialog(Nuevo_Cliente.this);
        String clientName;

        public PostData(String clientName) {
            this.clientName = clientName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setTitle("Por favor espere... ");
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String urlString = params[0];

            HTTPDataHandler hh = new HTTPDataHandler();
            String json = "(\"cliente\": \"" + clientName + "\")";
            hh.PostHTTPData(urlString,json);
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            // Refresh Data
            new GetData().execute(Common.getAddressAPI());


            pd.dismiss();
        }
    }

    //Function to edit an existing Client
    class PutData extends AsyncTask<String,String,String> {

        ProgressDialog pd = new ProgressDialog(Nuevo_Cliente.this);
        String clientName;

        public PutData(String clientName) {
            this.clientName = clientName;
        }

        @Override
        protected String doInBackground(String... params) {
            String urlString = params[0];
            HTTPDataHandler hh = new HTTPDataHandler();
            String json="{\"user\":\""+ clientName +"\"}";
            hh.PutHTTPData(urlString,json);
            return "";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setTitle("Espere por favor");
            pd.show();
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Refresh Data
            new GetData().execute(Common.getAddressAPI());
            pd.dismiss();
        }
    }

    //Function to delete already user
    class DeleteData extends AsyncTask<String,String,String> {
        ProgressDialog pd = new ProgressDialog(Nuevo_Cliente.this);
        Cliente cliente;

        public DeleteData(Cliente cliente) {
            this.cliente = cliente;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setTitle("Espere por favor.");
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String urlString = params[0];
            HTTPDataHandler hh = new HTTPDataHandler();
            String json = "{\"user\":\"" + cliente.getCliente() + "\"}";
            hh.DeleteHTTPData(urlString, json);
            return "";
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Refresh Data
            new GetData().execute(Common.getAddressAPI());
            pd.dismiss();
        }
    }

}
