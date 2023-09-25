package com.trabajos.veterinaria;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class Mascotas extends AppCompatActivity {

    Spinner spAnimal, spRaza;
    EditText etNombreMascota, etColorMascota;
    Button btnRegistrarMascota;
    RadioGroup rgGenero;
    RadioButton rbMacho, rbHembra;
    final String URL = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mascotas);
    }
}