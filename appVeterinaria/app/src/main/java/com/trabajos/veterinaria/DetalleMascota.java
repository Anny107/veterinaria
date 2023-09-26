package com.trabajos.veterinaria;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class DetalleMascota extends AppCompatActivity {

    TextView tvNombre,tvAnimal, tvRaza, tvColor, tvGenero;
    ImageView ivImagenMascota;
    int idmascota;
    final String URL = "http://192.168.59.186/veterinaria/controllers/mascota.php";
    final String imagenURL = "http://192.168.59.186/veterinaria/imagenes/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_mascota);
        loadUI();

        Bundle datos = this.getIntent().getExtras();
        if(datos !=null){
            idmascota = datos.getInt("idmascota");
        }
    datosMascota(idmascota);
    }
    private void datosMascota(int idmascota){
        Uri.Builder nuevaURL = Uri.parse(URL).buildUpon();
        nuevaURL.appendQueryParameter("operacion", "buscarMascota");
        nuevaURL.appendQueryParameter("idmascota", String.valueOf(idmascota));
        String URLActualizada = nuevaURL.build().toString();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URLActualizada, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("datos", response.getString("fotografia"));
                    tvNombre.setText(response.getString("nombre"));
                    tvAnimal.setText(response.getString("nombreanimal"));
                    tvRaza.setText(response.getString("nombreraza"));
                    obtenerImagen(response.getString("fotografia"));
                    tvColor.setText(response.getString("color"));
                    tvGenero.setText(response.getString("genero"));
                }catch (Exception e){
                    e.printStackTrace();
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
    private void obtenerImagen(String foto){
        String nuevaURL = imagenURL+foto;
        Log.e("Imagen", nuevaURL);
        ImageRequest imageRequest = new ImageRequest(nuevaURL, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                ivImagenMascota.setImageBitmap(response);
            }
        }, 0, 0, null, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error imagen", error.toString());
            }
        });
        Volley.newRequestQueue(this).add(imageRequest);
    }

    private void loadUI(){
        tvNombre = findViewById(R.id.tvNombreMascota);
        tvAnimal = findViewById(R.id.tvAnimal);
        tvRaza = findViewById(R.id.tvRaza);
        tvColor = findViewById(R.id.tvColorAnimal);
        tvGenero = findViewById(R.id.tvGenero);

        ivImagenMascota = findViewById(R.id.ivImagenMascota);
    }
}