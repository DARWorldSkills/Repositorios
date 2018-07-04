package com.danielaburbano.stroopermartes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity implements View.OnClickListener{
    Button btnJugar, btnPuntaje, btnConfiguracion;
    public static int modo=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        inizialite();
        modo=0;
    }

    private void inizialite() {
        btnJugar = findViewById(R.id.btnJugar);
        btnPuntaje =  findViewById(R.id.btnPuntaje);
        btnConfiguracion = findViewById(R.id.btnConfiguracion);
        btnJugar.setOnClickListener(this);
        btnPuntaje.setOnClickListener(this);
        btnConfiguracion.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btnJugar:
                intent = new Intent(Menu.this, Juego.class);
                startActivity(intent);
                modo=1;
                break;

            case R.id.btnPuntaje:
                intent = new Intent(Menu.this, Puntaje.class);
                startActivity(intent);
                break;

            case R.id.btnConfiguracion:
                intent = new Intent(Menu.this, Configuracion.class);
                startActivity(intent);
                break;



        }
    }
}
