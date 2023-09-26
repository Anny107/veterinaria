package com.trabajos.veterinaria;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    Button btIniciarSesion, btRegistro;
    EditText etDni, etClave;
    String dni, clave;
    int idcliente;
    final String URL = "http://192.168.18.20/veterinaria/controllers/cliente.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadUI();
        btIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingresar();
            }
        });
        btRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivity(Cliente.class);
            }
        });
    }

    private void ingresar(){
        dni = etDni.getText().toString().trim();
        clave = etClave.getText().toString().trim();

        Uri.Builder nuevaURL = Uri.parse(URL).buildUpon();
        nuevaURL.appendQueryParameter("operacion", "login");
        nuevaURL.appendQueryParameter("dni", dni);
        nuevaURL.appendQueryParameter("claveAcceso", clave);

        String URLFija = nuevaURL.build().toString();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URLFija, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(response != null){
                    try{
                        Log.e("Respuesta", response.toString());
                        if (response.getBoolean("login")){
                            mostrarMensaje("Inicio exitoso");
                            Toast.makeText(getApplicationContext(), "Iniciando sesión...", Toast.LENGTH_SHORT).show();
                            idcliente = response.getInt("idcliente");
                            Intent intent = new Intent(getApplicationContext(), Principal.class);
                            intent.putExtra("idcliente",idcliente);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(), response.getString("mensaje"), Toast.LENGTH_SHORT).show();
                            Log.e("Inicio sesion", response.getString("mensaje"));
                        }
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }else{
                    Log.e("Error", "No hay respuesta");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
            }
        });
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
    private void abrirActivity(Class activity){
        Intent intent = new Intent(getApplicationContext(), activity);
        startActivity(intent);
    }
    private  void mostrarMensaje(String mensaje){
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
    }
    private void loadUI(){
        btIniciarSesion = findViewById(R.id.btIniciarSesion);
        btRegistro = findViewById(R.id.btnRegistro);
        etDni = findViewById(R.id.etDni);
        etClave = findViewById(R.id.etClave);
    }


}