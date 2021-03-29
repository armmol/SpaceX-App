package com.example.restapiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnadd, btnref, btndel;
    EditText edt;
    RecyclerView rc;
    List<TableRow> data = new ArrayList<> (  );
    List<TableRow> olddata = new ArrayList<> (  );
    SpaceXDatabase db;
    LinearLayoutManager linearLayoutManager;
    MainAdapter mainAdapter;
    int flag =0;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        btnadd = findViewById (R.id.Add);
        btnref = findViewById (R.id.Refresh);
        btndel = findViewById (R.id.Delete);
        rc = findViewById (R.id.RVWeather);
        db = SpaceXDatabase.getObj (this);
        data = db.spaceXDao ().getALLRows ();
        linearLayoutManager = new LinearLayoutManager (this);
        rc.setLayoutManager (linearLayoutManager);

        btnadd.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick (View v) {
                // Instantiate the RequestQueue.
                String url ="https://api.spacexdata.com/v4/crew";

                JsonArrayRequest request = new JsonArrayRequest (Request.Method.GET, url, null, new Response.Listener<JSONArray> ( ) {
                    @Override
                    public void onResponse (JSONArray response) {
                        String name,wiki,agency,status,img;
                        try {
                            for(int i =0;i<response.length ();i++) {
                            JSONObject Jarr = response.getJSONObject (i) ;
                                name = Jarr.getString ("name");
                                wiki = Jarr.getString ("wikipedia");
                                agency = Jarr.getString ("agency");
                                status = Jarr.getString ("status");
                                img = Jarr.getString ("image");
                                if(data.contains (new TableRow (name,agency,img,wiki,status)))
                                    continue;
                                else{
                                    db.spaceXDao ( ).insert (new TableRow (name, agency, img, wiki, status));
                                    data = db.spaceXDao ().getALLRows ();
                                }
                            }
                            if(flag ==0) {
                                mainAdapter = new MainAdapter (MainActivity.this, data);
                                rc.setAdapter (mainAdapter);
                                Toast.makeText (MainActivity.this, "Data Added to Database", Toast.LENGTH_LONG).show ( );
                                flag=1;
                            }else
                                Toast.makeText (MainActivity.this, "Nothing to add", Toast.LENGTH_LONG).show ( );
                        } catch (JSONException e) {
                            Toast.makeText (MainActivity.this, "Error while adding data", Toast.LENGTH_LONG).show ( );
                            e.printStackTrace ( );
                        }

                    }
                }, new Response.ErrorListener ( ) {
                    @Override
                    public void onErrorResponse (VolleyError error) {
                        Toast.makeText (MainActivity.this,"Error while adding to Database",Toast.LENGTH_LONG).show ();
                    }
                });
                // Add the request to the RequestQueue.
                Msing.getInstance (MainActivity.this).addToRequestQueue (request);
            }
        });
        btnref.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick (View v) {
                String url ="https://api.spacexdata.com/v4/crew";
                JsonArrayRequest request = new JsonArrayRequest (Request.Method.GET, url, null, new Response.Listener<JSONArray> ( ) {
                    @Override
                    public void onResponse (JSONArray response) {
                            String name, wiki, agency, status, img;
                            try {
                                for (int i = 0; i < response.length ( ); i++) {
                                    JSONObject Jarr = response.getJSONObject (i);
                                    //List<JSONObject> Jarr = (List<JSONObject>) response.getJSONArray (0);
                                    name = Jarr.getString ("name");
                                    wiki = Jarr.getString ("wikipedia");
                                    agency = Jarr.getString ("agency");
                                    status = Jarr.getString ("status");
                                    img = Jarr.getString ("image");
                                    if (data.contains (new TableRow (name, agency, img, wiki, status)))
                                        continue;
                                    else {
                                        db.spaceXDao ( ).insert (new TableRow (name, agency, img, wiki, status));
                                        mainAdapter.notifyDataSetChanged ( );
                                        data = db.spaceXDao ( ).getALLRows ( );
                                    }

                                }
                                Toast.makeText (MainActivity.this, "Refreshed", Toast.LENGTH_LONG).show ( );
                            } catch (JSONException e) {
                                e.printStackTrace ( );
                            }
                        }
                        }, new Response.ErrorListener ( ) {
                            @Override
                            public void onErrorResponse (VolleyError error) {
                                Toast.makeText (MainActivity.this, "Error while adding to Database", Toast.LENGTH_LONG).show ( );
                            }
                        });
                        // Add the request to the RequestQueue.
                        Msing.getInstance (MainActivity.this).addToRequestQueue (request);
                    }
        });
        btndel.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick (View v) {
                db.spaceXDao ().deleteAllRows (data);
                data.clear ();
                data.addAll (db.spaceXDao ().getALLRows ());
                mainAdapter.notifyDataSetChanged ();
                Toast.makeText (MainActivity.this,"Deleted",Toast.LENGTH_SHORT).show();
                flag=0;
            }
        });
    }

}