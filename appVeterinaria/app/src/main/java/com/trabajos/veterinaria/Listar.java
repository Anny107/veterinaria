package com.trabajos.veterinaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Listar extends AppCompatActivity {

    ListView lvMascotas;
    private List<String> dataList = new ArrayList<>();
    private List<Integer> id = new ArrayList<>();
    int idcliente;
    private CustomAdapter customAdapter;
    final String URL = "http://192.168.18.20/veterinaria/controllers/mascota.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        loadUI();
        Bundle datos = this.getIntent().getExtras();
        if(datos != null){
            idcliente = datos.getInt("idcliente");
            traerDatos(idcliente);
        }
        lvMascotas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                abrirDetalle(id.get(position));
            }
        });
    }

    private void abrirDetalle(int idmascota){

        Intent intent = new Intent(getApplicationContext(), DetalleMascota.class);
        intent.putExtra("idmascota", idmascota);
        startActivity(intent);
    }
    private void traerDatos(int idcliente){
        id.clear();
        dataList.clear();
        customAdapter = new CustomAdapter(this,dataList);
        lvMascotas.setAdapter(customAdapter);

        Uri.Builder nuevaURL = Uri.parse(URL).buildUpon();
        nuevaURL.appendQueryParameter("operacion", "listarMascotas");
        nuevaURL.appendQueryParameter("idcliente", String.valueOf(idcliente));
        String URLActualizada = nuevaURL.build().toString();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URLActualizada, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    for(int i=0; i<response.length(); i++){
                        JSONObject jsonObject = new JSONObject(response.getString(i));
                        dataList.add(jsonObject.getString("nombre"));
                        id.add(jsonObject.getInt("idmascota"));
                    }
                    customAdapter.notifyDataSetChanged();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", toString());
            }
        });
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }
    private void loadUI(){
        lvMascotas = findViewById(R.id.lvMascotas);
    }

}