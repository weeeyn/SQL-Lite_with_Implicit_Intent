package com.activity.sql_litewithimplicitintent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<String> {

    Context c;
    String[] id;
    String[] fn;
    String[] mi;
    String[] ln;
    String[] em;
    String[] con;

    LayoutInflater inflater;

    public CustomAdapter(Context context, String[] id, String[] fname, String[] mi, String[] lname, String[] email, String[] contact) {

        super(context, R.layout.row_list_view, id);

        this.c = context;
        this.fn = fname;
        this.mi = mi;
        this.ln = lname;
        this.em = email;
        this.con = contact;
    }

    // DECLARES VIEWS IN INFLATER
    public class ViewHolder {
        TextView tvFname, tvMI, tvLname, tvEmail, tvContact;
    }

    // GET VIEW
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_list_view, null);
        }

        ViewHolder holder = new ViewHolder();
        holder.tvFname = (TextView) convertView.findViewById(R.id.rowFname);
        holder.tvMI = (TextView) convertView.findViewById(R.id.rowMI);
        holder.tvLname = (TextView) convertView.findViewById(R.id.rowLname);
        holder.tvEmail = (TextView) convertView.findViewById(R.id.rowEmail);
        holder.tvContact = (TextView) convertView.findViewById(R.id.rowContact);

        holder.tvFname.setText(fn[position]);
        holder.tvMI.setText(mi[position]);
        holder.tvLname.setText(ln[position]);
        holder.tvEmail.setText(em[position]);
        holder.tvContact.setText(con[position]);

        return convertView;
    }

}
