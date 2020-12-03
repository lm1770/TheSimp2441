package com.example.thesimp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.thesimp.adapters.PersonajesAdapter;
import com.example.thesimp.dto.Frases;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Spinner spinnerCantidad;
    private Button mostrarBtn;
    private ListView frasesLv;
    private List<Frases> frases = new ArrayList<>();
    private ArrayAdapter<Frases> adapter;
    private PersonajesAdapter personajesAdapter;
    private RequestQueue queue;


    @Override
    protected void onResume() {
        super.onResume();

        setContentView(R.layout.activity_main);
        this.setSupportActionBar((Toolbar) findViewById( R.id.toolbar));
        this.mostrarBtn = findViewById(R.id.MostrarBtn);

        //spinner cantidad
        spinnerCantidad = findViewById(R.id.IdCantidadSpinner);
        ArrayAdapter<CharSequence> adapterC=ArrayAdapter.createFromResource(this, R.array.spinnerCantidad,android.R.layout.simple_spinner_item);
        spinnerCantidad.setAdapter(adapterC);
        //fin spinner cantidad

        queue = Volley.newRequestQueue(this);
        this.frasesLv = findViewById(R.id.Idfrases_list_view);
        this.personajesAdapter = new PersonajesAdapter(this,R.layout.list_frases,this.frases);
        this.frasesLv.setAdapter(this.personajesAdapter);



        this.mostrarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> errores = new ArrayList<>();
                String cantidad = spinnerCantidad.getSelectedItem().toString().trim();


                int val = 0;

                try {
                    val = Integer.parseInt(spinnerCantidad.getSelectedItem().toString().trim());

                    //String url = "https://thesimpsonsquoteapi.glitch.me/quotes?count=num"+val;
                    //String nombre = "";
                    String url = "https://rickandmortyapi.com/api/character";
                    String nombre = "results";

                    JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(MainActivity.this, "entre al Json : " , Toast.LENGTH_SHORT).show();
                            try {
                                frases.clear();;
                                Frases[] arr = new Gson().fromJson(response.getString(nombre), Frases[].class);

                                frases.addAll(Arrays.asList(arr));
                                Toast.makeText(MainActivity.this, "entre al try : ", Toast.LENGTH_SHORT).show();
                            }catch (Exception ex){
                                frases.clear();
                                Log.e("PERSONAJE_FRAGMENT", "Error de peticion");
                                Toast.makeText(MainActivity.this, "entre a la excepcion : " + ex, Toast.LENGTH_SHORT).show();

                            }finally {
                                personajesAdapter.notifyDataSetChanged();
                                Toast.makeText(MainActivity.this, "toy en el finally : " , Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            frases.clear();
                            Log.e("PERSONAJE_FRAGMENT", "Error de respuesta");
                            personajesAdapter.notifyDataSetChanged();

                        }
                    });
                    queue.add(jsonReq);



                }catch (NumberFormatException ex){
                    errores.add("Seleccione un valor");
                    //Toast.makeText(MainActivity.this, "hola : " + cantidad, Toast.LENGTH_SHORT).show();

                }

                if(errores.isEmpty()){

                    Toast.makeText(MainActivity.this,"Agregado exitosamente", Toast.LENGTH_SHORT).show();

                }else{
                    mostrarErrores(errores);
                }


            }
        });




    }



    private void mostrarErrores(List<String> errores){
        String mensaje ="";
        for(String e: errores){
            mensaje+=  "-" + e +"\n";
        }
        //mostrar mensaje de alerta
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        alertBuilder.setTitle("Error de validacion").setMessage(mensaje).setPositiveButton("Aceptar",null).create().show();

    }

}