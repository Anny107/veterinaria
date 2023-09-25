package com.trabajos.veterinaria;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    Button btIniciarSesion;
    EditText etDni, etClave;
    String dni, clave;
    final String URL = "http://192.168.18.20/veterinaria/controllers/cliente.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadUI();
        btIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarCajas();
            }
        });
    }

    private void validarCajas(){
        dni = etDni.getText().toString().trim();
        clave = etClave.getText().toString().trim();
        if(dni.isEmpty()){
            etDni.setError("Ingrese DNI");
            etDni.requestFocus();
        } else if (clave.isEmpty()) {
            etClave.setError("Ingrese clave");
            etClave.requestFocus();
        }else{
            ingresar();
        }
    }
    private void ingresar(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mostrarMensaje("No se puede acceder");
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("operacion", "login");
                parametros.put("dni", dni);
                parametros.put("claveAcceso", clave);
                return parametros;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }
    private  void mostrarMensaje(String mensaje){
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
    }
    private void loadUI(){
        btIniciarSesion = findViewById(R.id.btIniciarSesion);
        etDni = findViewById(R.id.etDni);
        etClave = findViewById(R.id.etClave);
    }


}