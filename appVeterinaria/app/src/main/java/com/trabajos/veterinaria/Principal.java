package com.trabajos.veterinaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Principal extends AppCompatActivity {

    Button btCliente, btMascota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        loadUI();
        btCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivitys(Cliente.class);
            }
        });
        btMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivitys(Mascotas.class);
            }
        });
    }

    private void abrirActivitys(Class activity){
        startActivity(new Intent(getApplicationContext(), activity));
    }
    private void loadUI(){
        btCliente = findViewById(R.id.btAbrirActivityCliente);
        btMascota = findViewById(R.id.btAbrirActivityMascota);
    }
}