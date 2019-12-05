package com.example.aman.breast_friendapp;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class screening extends AppCompatActivity {
    int i, a, b, c, d, e, t;
    private static final String KEY_AGE = "age";
    private String age;
    String result;
    String p;
    RequestQueue requestQueue;
    private ProgressDialog pDialog;
    String url = "http://192.168.0.101/android/calculation.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screening);
        Spinner spinner1 = findViewById(R.id.spinner1);
        Spinner spinner2 = findViewById(R.id.spinner2);
        Spinner spinner3 = findViewById(R.id.spinner3);
        Spinner spinner4 = findViewById(R.id.spinner4);
        //EditText age = findViewById(R.id.ageinput);
        Button start = findViewById(R.id.btnstart);
        final EditText etage = findViewById(R.id.ageinput);
        requestQueue = Volley.newRequestQueue(getApplicationContext());


        String[] values1 =
                {"Round", "Oval", "Lobular", "Irregular"};
        String[] values2 =
                {"Circumscribe", "Microlobulated", "Obsecure", "Ill-Defined", "Spiculated"};
        String[] values3 =
                {"High", "Iso", "Low", "Fat-Containing"};
        String[] values4 =
                {"1", "2", "3", "4", "5"};


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, values1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, values2);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, values3);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, values4);

        ///
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner1.setAdapter(adapter1);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner2.setAdapter(adapter2);
        adapter3.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner3.setAdapter(adapter3);
        adapter4.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner4.setAdapter(adapter4);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                switch (i) {
                    case 0:
                        //  Toast.makeText(getApplicationContext(),"value is "+i,Toast.LENGTH_SHORT).show();
                        break;

                    case 1:
                        //  Toast.makeText(getApplicationContext(),"value is "+i,Toast.LENGTH_SHORT).show();
                        break;

                    case 2:
                        // Toast.makeText(getApplicationContext(),"value is "+i,Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        //  Toast.makeText(getApplicationContext(),"value is "+i,Toast.LENGTH_SHORT).show();
                        break;
                }
                a = i + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                switch (i) {
                    case 0:
                        Toast.makeText(getApplicationContext(), "value is " + i, Toast.LENGTH_SHORT).show();
                        break;

                    case 1:
                        //   Toast.makeText(getApplicationContext(),"value is "+i,Toast.LENGTH_SHORT).show();
                        break;

                    case 2:
                        // Toast.makeText(getApplicationContext(),"value is "+i,Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        //  Toast.makeText(getApplicationContext(),"value is "+i,Toast.LENGTH_SHORT).show();
                        break;
                }
                b = i + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                switch (i) {
                    case 0:
                        // Toast.makeText(getApplicationContext(),"value is "+i,Toast.LENGTH_SHORT).show();
                        break;

                    case 1:
                        // Toast.makeText(getApplicationContext(),"value is "+i,Toast.LENGTH_SHORT).show();
                        break;

                    case 2:
                        //  Toast.makeText(getApplicationContext(),"value is "+i,Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        //  Toast.makeText(getApplicationContext(),"value is "+i,Toast.LENGTH_SHORT).show();
                        break;
                }
                c = i + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                switch (i) {
                    case 0:
                        // Toast.makeText(getApplicationContext(),"value is "+i,Toast.LENGTH_SHORT).show();
                        break;

                    case 1:
                        // Toast.makeText(getApplicationContext(),"value is "+i,Toast.LENGTH_SHORT).show();
                        break;

                    case 2:
                        // Toast.makeText(getApplicationContext(),"value is "+i,Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        //Toast.makeText(getApplicationContext(),"value is "+i,Toast.LENGTH_SHORT).show();
                        break;
                }
                d = i + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayLoader();
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String result = response.toString();
                        try {
                            JSONObject obj = new JSONObject(result);
                            // JSONObject predict = obj.getJSONObject("prediction");
                            p = obj.getString("prediction");
                            int t = Integer.parseInt(p);
                            Intent intent = new Intent(screening.this, Resultactivity.class);
                            intent.putExtra("value", t);
                            startActivity(intent);

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        // Toast.makeText(getApplicationContext(),p,Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        age = etage.getText().toString();

                        int e = Integer.parseInt(age);
                        parameters.put("Score", String.valueOf(d));
                        parameters.put("Age", String.valueOf(e));
                        //parameters.put("age",etage.getText().toString());
                        parameters.put("Shape", String.valueOf(a));
                        parameters.put("Margin", String.valueOf(b));
                        parameters.put("Density", String.valueOf(c));

                        return parameters;


                    }
                };
                requestQueue.add(request);


            }
        });


    }

    private void displayLoader() {
        pDialog = new ProgressDialog(screening.this);
        pDialog.setMessage("Getting Result.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }
}

