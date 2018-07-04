package com.danielaburbano.stroopermartes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class Configuracion extends AppCompatActivity implements View.OnClickListener{
    RadioButton rbtnTiempo, rbtnIntentos;
    EditText txttimepo;
    public static int modoGame, tiempo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        inizialite();
        SharedPreferences juego = getSharedPreferences("juego",Context.MODE_PRIVATE);
        modoGame = juego.getInt("modo",0);
        tiempo = juego.getInt("tiempo",0);
        insertarDatos();
    }

    private void inizialite() {
        rbtnTiempo = findViewById(R.id.rbtnTiempo);
        rbtnIntentos = findViewById(R.id.rbtnIntentos);
        rbtnTiempo.setOnClickListener(this);
        rbtnIntentos.setOnClickListener(this);
        txttimepo = findViewById(R.id.txtTiempoPalabra);

    }

    public int input_mode_game(){
        int tmp=0;
        if (rbtnTiempo.isChecked()){
            tmp=1;
        }
        if (rbtnIntentos.isChecked()){
            tmp=2;
        }
        return tmp;
    }

    public void insertarDatos(){
        if (modoGame==1){
            rbtnTiempo.setChecked(true);
        }else {
            if (modoGame==2){
                rbtnIntentos.setChecked(true);
            }else {
                rbtnTiempo.setChecked(true);
            }
        }

        if (tiempo>0){
            txttimepo.setText(Integer.toString(tiempo));
        }

    }



    public void salir(View view) {
        insertarDatos();
        modoGame = input_mode_game();
        tiempo= Integer.parseInt(txttimepo.getText().toString());
        if (tiempo>1 && tiempo<10){
            guardarSesion();
            Intent intent = new Intent(Configuracion.this,JuegoC.class);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, "No se puede guardar", Toast.LENGTH_SHORT).show();
        }
    }
    public void guardarSesion(){
        SharedPreferences juego= getSharedPreferences("juego", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = juego.edit();
        editor.putInt("modo",modoGame);
        editor.putInt("tiempo",tiempo);
        editor.commit();
    }


    @Override
    public void onClick(View view) {
        
    }

    public void ir(View view) {
        Intent intent = new Intent(Configuracion.this,Menu.class);
        startActivity(intent);
        finish();
    }
}
