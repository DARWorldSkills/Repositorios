package com.davidpopayan.sena.guper.Controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.davidpopayan.sena.guper.R;
import com.davidpopayan.sena.guper.models.Constantes;

import java.util.HashMap;
import java.util.Map;

public class CambioDeContrasena extends AppCompatActivity {
    TextView txtContrasenaN;
    TextView txtConfirmar;
    Button btnGuardar;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_de_contrasena);
        inizialite();
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGuardar.setEnabled(false);
                cambiarContrasena();
            }
        });

    }

    private void cambiarContrasena() {
        String url= Constantes.urlCambiarContrasena;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                btnGuardar.setEnabled(true);
                Toast.makeText(CambioDeContrasena.this, "Por favor escriba igualmente la contraseña", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("new_password1",txtContrasenaN.getText().toString());
                parameters.put("new_password2",txtConfirmar.getText().toString());

                return parameters;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void inizialite() {
        requestQueue= Volley.newRequestQueue(this);
        txtContrasenaN=findViewById(R.id.txtContrasenaNueva);
        txtConfirmar=findViewById(R.id.txtConfirmarContrasena);
        btnGuardar = findViewById(R.id.btnGuardarContrasena);
    }
}
