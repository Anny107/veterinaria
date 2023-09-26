package com.trabajos.veterinaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Principal extends AppCompatActivity {

    Button btVerMascota, btAbrirActivityMascota;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        loadUI();
        btVerMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivitys(Listar.class);
            }
        });
        btAbrirActivityMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abrirActivitys(Mascotas.class);
                Intent intent = new Intent(getApplicationContext(), Mascotas.class);
                startActivity(intent);
            }
        });
    }
    public void abrirActivitys(Class activity){
        Intent intent = new Intent(getApplicationContext(), activity);
        startActivity(intent);
    }
    public void loadUI(){
        btVerMascota = findViewById(R.id.btVerMascotas);
        btAbrirActivityMascota = findViewById(R.id.btAbrirActivityMascota);
    }
}