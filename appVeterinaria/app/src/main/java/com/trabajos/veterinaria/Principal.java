package com.trabajos.veterinaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Principal extends AppCompatActivity {

    Button btVerMascota, btAbrirActivityMascota, btCerrarSesion;

    int idcliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        loadUI();
        Bundle datos = this.getIntent().getExtras();
        if(datos !=null){
            idcliente = datos.getInt("idcliente");
            Log.e("Respuesta", String.valueOf(idcliente));
            //Toast.makeText(getApplicationContext(), idcliente,Toast.LENGTH_SHORT).show();
        }
        btVerMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivitys(Listar.class);
            }
        });
        btAbrirActivityMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivitys(Mascotas.class);

            }
        });
        btCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                abrirActivitys(Login.class);
            }
        });
    }
    public void abrirActivitys(Class activity){
        Intent intent = new Intent(getApplicationContext(), activity);
        intent.putExtra("idcliente", idcliente);
        startActivity(intent);
    }
    public void loadUI(){
        btVerMascota = findViewById(R.id.btVerMascotas);
        btAbrirActivityMascota = findViewById(R.id.btAbrirActivityMascota);
        btCerrarSesion = findViewById(R.id.btCerraSesion);
    }
}