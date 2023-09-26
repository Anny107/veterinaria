package com.trabajos.veterinaria;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Mascotas extends AppCompatActivity {
    Spinner spAnimal, spRaza;
    EditText etNombreMascota, etColorMascota;
    Button btnRegistrarMascota;
    RadioGroup rgGenero;
    RadioButton rbMacho, rbHembra;
    int idcliente, idraza;
    String nombre, color, genero;
    final String URLAnimal = "http://192.168.59.186/veterinaria/controllers/animal.php";
    final String URLMascota = "http://192.168.59.186/veterinaria/controllers/mascota.php";
    private ArrayList<Animal> animal = new ArrayList<>();
    private ArrayList<Raza> raza = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mascotas);
        loadUI();

        Bundle datos = this.getIntent().getExtras();
        if(datos !=null){
            idcliente = datos.getInt("idcliente");
        }
        listadoAnimales();
        spAnimal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position>0){
                    Animal animalSelect = animal.get(position);
                    int idanimal = animalSelect.getValue();
                    razasListar(idanimal);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnRegistrarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarCajas();
            }
        });
    }
    private void validarCajas(){
        nombre = etNombreMascota.getText().toString().trim();
        color = etColorMascota.getText().toString().trim();

        if (nombre.isEmpty()){
            etNombreMascota.setError("Complete el campo");
        } else if (color.isEmpty()) {
            etColorMascota.setError("Complete el campo");
        }else {
            preguntar();
        }
    }
    private void preguntar(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Veterinaria");
        dialogo.setMessage("¿Esta seguro registrar a su mascota?");
        dialogo.setCancelable(false);

        dialogo.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                registrarMascota();
            }
        });
        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        dialogo.show();
    }
    private void registrarMascota(){
        if(rgGenero.getCheckedRadioButtonId() == R.id.rbMacho){
            genero = rbMacho.getText().toString();
        }else if(rgGenero.getCheckedRadioButtonId() == R.id.rbHembra){
            genero = rbHembra.getText().toString();
        }
        StringRequest request = new StringRequest(Request.Method.POST, URLMascota, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(), "Registrado Correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Listar.class);
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                Log.d("Error de volley", error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("operacion", "registrarMascota");
                parametros.put("idcliente", String.valueOf(idcliente));
                parametros.put("idraza", String.valueOf(idraza));
                parametros.put("nombre", nombre);
                parametros.put("fotografia", "");
                parametros.put("color", color);
                parametros.put("genero", genero);

                return parametros;
            }
        };
        //Enviamos la solicitud al WS
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void listadoAnimales(){
        Uri.Builder nuevaURL = Uri.parse(URLAnimal).buildUpon();
        nuevaURL.appendQueryParameter("operacion", "listarAnimales");
        String URLActualizada = nuevaURL.build().toString();
        if (animal.isEmpty()) {
            animal.add(new Animal(0, "Seleccione"));
        }
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URLActualizada, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if(response.length() > 1){
                    Log.d("animales", response.toString());
                   for(int i=0; i < response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            //Log.i("Datos", jsonObject.getString("nombreanimal"));
                            int value = jsonObject.getInt("idanimal");
                            String item = jsonObject.getString("nombreanimal");
                            //Log.d("animales", jsonObject.toString());

                            animal.add(new Animal(value, item));
                        }catch (JSONException e){
                            Log.d("Error json",e.toString());
                        }
                    }
                    ArrayAdapter<Animal> adapter = new ArrayAdapter<>(Mascotas.this, android.R.layout.simple_spinner_item, animal);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spAnimal.setAdapter(adapter);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
            }
        }
        );
        Volley.newRequestQueue(this).add(jsonArrayRequest);

    }

    private void razasListar(int idanimal){
        Uri.Builder nuevaURL = Uri.parse(URLAnimal).buildUpon();
        nuevaURL.appendQueryParameter("operacion", "filtrarRaza");
        nuevaURL.appendQueryParameter("idanimal", String.valueOf(idanimal));
        String URLActualizada = nuevaURL.build().toString();
        if (raza.isEmpty()) {
            raza.add(new Raza(0, "Seleccione"));
        }
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URLActualizada, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if(response.length() > 1){
                    for(int i=0; i < response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            int value = jsonObject.getInt("idraza");
                            String item = jsonObject.getString("nombreraza");
                            raza.add(new Raza(value, item));
                        }catch (JSONException e){
                            Log.d("Error json",e.toString());
                        }
                    }
                    ArrayAdapter<Raza> adapter = new ArrayAdapter<>(Mascotas.this, android.R.layout.simple_spinner_item, raza);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spRaza.setAdapter(adapter);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
            }
        }
        );
        Volley.newRequestQueue(this).add(jsonArrayRequest);

    }
    private void loadUI(){
        //Radio group y Radio Button
        rgGenero = findViewById(R.id.rgGeneroMascota);
        rbMacho = findViewById(R.id.rbMacho);
        rbHembra = findViewById(R.id.rbHembra);
        //Boton
        btnRegistrarMascota = findViewById(R.id.btnRegistrarMascota);
        //EditText
        etNombreMascota = findViewById(R.id.etNombreMascota);
        etColorMascota = findViewById(R.id.etColorMascota);
        //Spinner
        spAnimal = findViewById(R.id.spAnimal);
        spRaza = findViewById(R.id.spRaza);
        ArrayAdapter<Raza> razaArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, raza);
        razaArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRaza.setAdapter(razaArrayAdapter);

        //tIENES QUE BORRAR ESTE COMENTARIO:)
    }
}