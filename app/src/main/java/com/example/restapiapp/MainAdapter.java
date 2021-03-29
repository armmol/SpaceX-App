package com.example.restapiapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<TableRow> data;
    private Activity context;
    private SpaceXDatabase db;

    public MainAdapter(Activity context, List<TableRow> data)
    {
        this.context = context;
        this.data = data;
        notifyDataSetChanged ();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate (R.layout.card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder (@NonNull ViewHolder holder, int position) {
        TableRow row = data.get(position);
        db = SpaceXDatabase.getObj (context);
        holder.name.setText (row.getName ());
        holder.status.setText (row.getStatus ());
        holder.wiki.setText (row.getWikipedia ());
        holder.agency.setText (row.getAgency ());
        holder.ibt.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick (View v) {
                TableRow row = data.get(holder.getAdapterPosition ());
                db.spaceXDao ().Delete (row);
                int pos = holder.getAdapterPosition ();
                data.remove (pos);
                notifyItemRemoved (pos);
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        String url =row.getImage ();
        ImageRequest request = new ImageRequest (url, new Response.Listener<Bitmap> ( ) {
            @Override
            public void onResponse (Bitmap response) {
                holder.img.setImageBitmap (response);
            }
        },0,0,ImageView.ScaleType.CENTER_CROP,null, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse (VolleyError error) {
                Toast.makeText (context,"Error",Toast.LENGTH_SHORT).show ();
                error.printStackTrace ();
            }
        });
        // Add the request to the RequestQueue.
        Msing.getInstance (context).addToRequestQueue (request);
    }

    @Override
    public int getItemCount () {
        return data.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,agency,wiki,status;
        ImageView img;
        ImageButton ibt;
        public ViewHolder (@NonNull View view) {
            super(view);
            name = view.findViewById (R.id.name);
            agency = view.findViewById (R.id.agency);
            wiki = view.findViewById (R.id.wiki);
            status = view.findViewById (R.id.status);
            img = view.findViewById (R.id.img);
            ibt = view.findViewById (R.id.ibtn);

        }
    }
}
