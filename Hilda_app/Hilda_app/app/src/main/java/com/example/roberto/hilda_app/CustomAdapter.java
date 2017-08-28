package com.example.roberto.hilda_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.roberto.hilda_app.Class.Cliente;

import java.util.List;

/**
 * Created by Roberto on 8/27/2017.
 */

public class CustomAdapter extends BaseAdapter{
    private Context mContext;
    private List<Cliente> lsClients;

    public CustomAdapter(Context mContext, List<Cliente> lsClients) {
        this.mContext = mContext;
        this.lsClients = lsClients;
    }

    @Override
    public int getCount() {
        return lsClients.size();
    }

    @Override
    public Object getItem(int position) {
        return lsClients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row, null);

        TextView txtCliente = (TextView)view.findViewById(R.id.txtClient);
        txtCliente.setText(lsClients.get(position).getCliente());

        return view;
    }
}
