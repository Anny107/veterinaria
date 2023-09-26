package com.trabajos.veterinaria;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Cliente extends AppCompatActivity {

    EditText etApellidos, etNombres, etDNI, etClave;
    Button btGuardarCliente;
    String apellidos, nombres, dni, clave;

    boolean iniciarsesion = false;
    final String URL = "http://192.168.59.186/veterinaria/controllers/cliente.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        loadUI();
        btGuardarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarCajas();
            }
        });
    }

    private void validarCajas(){
        apellidos = etApellidos.getText().toString().trim();
        nombres = etNombres.getText().toString().trim();
        dni = etDNI.getText().toString().trim();
        clave = etClave.getText().toString().trim();
        if(apellidos.isEmpty()){
            etApellidos.setError("Complete el campo");
            etApellidos.requestFocus();
        }else  if(nombres.isEmpty()){
            etNombres.setError("Complete el campo");
            etNombres.requestFocus();
        }else if(dni.isEmpty()){
            etDNI.setError("Complete el campo");
            etDNI.requestFocus();
        }else if(clave.isEmpty()){
            etClave.setError("Complete el campo");
            etClave.requestFocus();
        }else {
            mostrarPregunta();
        }

    }
    private void registrarCliente(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("")){
                    mostrarMensaje("Registrado correctamente");
                    etApellidos.requestFocus();
                    iniciarsesion=true;
                    if(iniciarsesion){
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error de comunicación", error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<>();
                parametros.put("operacion", "registrarCliente");
                parametros.put("apellidos", apellidos);
                parametros.put("nombres", nombres);
                parametros.put("dni", dni);
                parametros.put("claveAcceso", clave);
                return  parametros;
            }
        };
        RequestQueue requestQueue =Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void mostrarPregunta(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Veterinaria");
        dialogo.setMessage("¿Está seguro de guardar los datos?");
        dialogo.setCancelable(false);

        //Aceptar / cancelar
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                registrarCliente();
            }
        });
        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        //Mostrar diálogo
        dialogo.show();
    }
    private  void mostrarMensaje(String mensaje){
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
    }
    private void loadUI(){
        etApellidos = findViewById(R.id.etApellidos);
        etNombres = findViewById(R.id.etNombres);
        etDNI = findViewById(R.id.etRegistrarDni);
        etClave = findViewById(R.id.etRegistrarClave);

        btGuardarCliente = findViewById(R.id.btGuardarCliente);
    }
}