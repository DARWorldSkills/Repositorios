package com.davidpopayan.sena.guper.Fragments;


import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.davidpopayan.sena.guper.Controllers.ListaAprendices;
import com.davidpopayan.sena.guper.Controllers.Login;
import com.davidpopayan.sena.guper.Controllers.MainActivity;
import com.davidpopayan.sena.guper.R;
import com.davidpopayan.sena.guper.models.AprendizFicha;
import com.davidpopayan.sena.guper.models.Constantes;
import com.davidpopayan.sena.guper.models.Ficha;
import com.davidpopayan.sena.guper.models.Persona;
import com.davidpopayan.sena.guper.models.Rol;
import com.davidpopayan.sena.guper.models.RolPersona;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPermisoIn extends Fragment {

    //Declaración de Variables
    RequestQueue requestQueue;
    Spinner spFicha, spMotivo;
    Button btnEnviar;
    private static  final String Cero = "0";
    private static  final  String DOS_PUNTOS = ":";
    public final Calendar c = Calendar.getInstance();
    private int hora = c.get(Calendar.HOUR_OF_DAY);
    private int minuto = c.get(Calendar.MINUTE);
    ImageButton btnhora1, btnhora2;
    TextView txtFecha;
    EditText txthoras, txthoras2, txtmotivo;
    public static String fecha, horas, horasalida, motivo;

    public static List<Persona> aprendizListA = new ArrayList<>();
    List<AprendizFicha> aprendizFichaListA = new ArrayList<>();
    List<Rol> rolList = new ArrayList<>();
    List<RolPersona> rolPersonaAList = new ArrayList<>();
    boolean banderaFicha = false;

    Ficha fichaA;
    Date date = new Date();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public FragmentPermisoIn() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        btnEnviar.setEnabled(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        btnEnviar.setEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_permiso_in, container, false);
        rolList = MainActivity.rolList;
        inizialite(view);
        listarFichas();
        listarMotivos();
        requestQueue = Volley.newRequestQueue(getContext());
        boolean banderaFicha = false;
        obtenerFicha1();
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtmotivo.getText().length()>0 && (!txthoras.getText().toString().equals("hh:mm:ss"))
                        && (!txthoras2.getText().toString().equals("hh:mm:ss"))){
                    validarHora();

                }else {
                    Toast.makeText(getContext(), "Por Favor llene todos los campos", Toast.LENGTH_SHORT).show();
                    btnEnviar.setEnabled(true);
                }


            }
        });

        btnhora1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerHora11();
            }
        });

        btnhora2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fechapicker();
            }
        });

        spFicha.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txthoras.setText(getString(R.string.horato));
                txthoras2.setText(getString(R.string.hora));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    private void validarHora() {
        obtenerFicha1();
        if (banderaFicha==true) {
            Date horapermiso = new Date();
            DateFormat horapermisoFormart = new SimpleDateFormat("HH:mm");
            String horaPermisoS = txthoras2.getText().toString();
            Ficha ficha = fichaA;
            int horaMax = 0;
            int horaM = 0;
            if (ficha.getJornada().equals("Mañana")) {
                horaMax = 12;
                horaM = 7;
            }

            if (ficha.getJornada().equals("Tarde")) {
                horaMax = 18;
                horaM = 13;
            }

            if (ficha.getJornada().equals("Noche")) {
                horaMax = 20;
                horaM = 19;
            }


            Date horaActual = new Date();
            try {
                horapermiso = horapermisoFormart.parse(horaPermisoS);
                int horapermi = horapermiso.getHours();
                if (horaActual.getHours() >= horapermi && horapermi > horaM &&
                        horapermi < horaMax) {
                    mandarDatos();
                    btnEnviar.setEnabled(false);
                } else {
                    Toast.makeText(getContext(), "La hora del permiso no es correcta", Toast.LENGTH_SHORT).show();
                    btnEnviar.setEnabled(true);
                }
            } catch (ParseException e) {
                e.printStackTrace();

            }
        }else {
            Toast.makeText(getContext(), "Por favor presione de nuevo", Toast.LENGTH_SHORT).show();
        }
    }

    private void obtenerHora11() {
        obtenerFicha1();
        if (banderaFicha==true) {
            DateFormat format = new SimpleDateFormat("HH:mm");
            Date date = new Date();
            format.format(date);
            Ficha ficha = fichaA;
            int horaJ = 0;
            String[] split = format.toString().split(":");
            int horaP = 0;
            horaP = format.getCalendar().getTime().getHours();

            int horaPasar = 0;

            if (ficha.getJornada().equals("Mañana")) {
                horaJ = 13;

            }

            if (ficha.getJornada().equals("Tarde")) {
                horaJ = 19;
            }

            if (ficha.getJornada().equals("Noche")) {
                horaJ = 21;
            }

            horaPasar = horaJ - horaP;
            if (horaPasar < 1 || horaPasar > 6) {
                Toast.makeText(getContext(), "Solo puedes pedir permiso en horas de clase" + horaPasar, Toast.LENGTH_SHORT).show();
            } else {
                numberpicker1(horaPasar);
            }

            banderaFicha=false;
        }else {
            Toast.makeText(getContext(), "Por favor presione de nuevo", Toast.LENGTH_SHORT).show();
        }


    }



    private void fechapicker(){
        TimePickerDialog recoger = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                String hora =(hourOfDay <10) ? String.valueOf(Cero + hourOfDay) : String.valueOf(hourOfDay);
                String minuto = (minute <10) ? String.valueOf(Cero + minute) : String.valueOf(minute);
                String AM_PM;

                if (hourOfDay<12){
                    AM_PM = "a.m.";
                }else {
                    AM_PM = "p.m.";
                }
                txthoras2.setText(hora + DOS_PUNTOS + minuto);

            }
        }, hora , minuto , false);
        recoger.show();
    }


    private void numberpicker1(int vmax){
        NumberPicker mynumberpicker = new NumberPicker(getActivity());
        mynumberpicker.setMaxValue(vmax);
        mynumberpicker.setMinValue(0);
        NumberPicker.OnValueChangeListener myvaluechange = new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                txthoras.setText(""+newVal);
            }
        };
        mynumberpicker.setOnValueChangedListener(myvaluechange);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(mynumberpicker);
        builder.setTitle("Hora");
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }


    public void inizialite(View view){
        spFicha = view.findViewById(R.id.spinnerFichasIn);
        spMotivo = view.findViewById(R.id.spinnerMotivoIn);
        txtFecha = view.findViewById(R.id.fechaDelDiaIn);
        txthoras = view.findViewById(R.id.Hora1Instructor);
        txthoras2 = view.findViewById(R.id.Hora2Instructor);
        txtmotivo = view.findViewById(R.id.motivoporIn);
        btnEnviar = view.findViewById(R.id.btnEnviarPIn);
        btnhora1 = view.findViewById(R.id.btnHora1Instructor);
        btnhora2 = view.findViewById(R.id.btnHora2Instructor);

        txtFecha.setText(dateFormat.format(date));

    }


    public void mandarDatos(){
        fecha = txtFecha.getText().toString();
        horas = txthoras2.getText().toString();
        horasalida = txthoras.getText().toString();
        motivo = txtmotivo.getText().toString();
        obtenerFicha();


    }


    public void listarMotivos() {
        List<String> datos = new ArrayList<>();
        datos.add("Otro");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, datos);
        spMotivo.setAdapter(arrayAdapter);
        spMotivo.setEnabled(false);
    }


    public void listarFichas() {
        List<String> datos = new ArrayList<>();
        List<Ficha> fichaList = MainActivity.fichaListA;
        for (int i = 0; i < fichaList.size(); i++) {
            Ficha ficha = fichaList.get(i);
            datos.add(ficha.getNumeroFicha());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, datos);
        spFicha.setAdapter(adapter);
    }

    public void obtenerFicha() {
        String url = Constantes.urlFicha;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                Type type = new TypeToken<List<Ficha>>(){}.getType();
                List<Ficha> fichaList = gson.fromJson(response, type);
                Ficha ficha;
                for (int i = 0; i < fichaList.size(); i++) {
                    ficha = fichaList.get(i);
                    if (spFicha.getSelectedItem().toString().equals(ficha.getNumeroFicha())) {
                        fichaA = ficha;
                    }
                }

                listarAprendizFicha();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);
    }

    public void obtenerFicha1() {
        String url = Constantes.urlFicha;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                Type type = new TypeToken<List<Ficha>>(){}.getType();
                List<Ficha> fichaList = gson.fromJson(response, type);
                Ficha ficha;
                for (int i = 0; i < fichaList.size(); i++) {
                    ficha = fichaList.get(i);
                    if (spFicha.getSelectedItem().toString().equals(ficha.getNumeroFicha())) {
                        fichaA = ficha;
                    }
                }

                banderaFicha=true;

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);
    }



    public void listarAprendizFicha() {
        String url = Constantes.urlAprendizFicha;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                aprendizFichaListA.clear();
                Gson gson = new Gson();
                Type type = new TypeToken<List<AprendizFicha>>(){}.getType();
                List<AprendizFicha> aprendizFichaList = gson.fromJson(response, type);
                AprendizFicha aprendizFicha = new AprendizFicha();
                for (int i=0; i<aprendizFichaList.size(); i++){
                    aprendizFicha=aprendizFichaList.get(i);
                    if (fichaA.getUrl().equals(aprendizFicha.getFicha())){
                        aprendizFichaListA.add(aprendizFicha);


                    }
                }
                obtenerRolAprendiz();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue.add(stringRequest);
    }

    public void obtenerRolAprendiz(){
        String url = Constantes.urlRolPersona;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                rolPersonaAList.clear();
                Type type = new TypeToken<List<RolPersona>>(){}.getType();
                List<RolPersona> rolPersonaList = gson.fromJson(response,type);
                for (int i=0; i<rolPersonaList.size(); i++){
                    RolPersona rolPersona = rolPersonaList.get(i);
                    for (int j=0; j<rolList.size(); j++){
                        if (rolList.get(j).getRol().equals("APRENDIZ") && rolPersona.getRol().equals(rolList.get(j).getUrl())) {

                            rolPersonaAList.add(rolPersona);

                        }
                    }
                }
                listarAprendices();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);
    }

    public void listarAprendices(){
        String url = Constantes.urlPersona;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                aprendizListA.clear();
                Gson gson = new Gson();
                Type type = new TypeToken<List<Persona>>(){}.getType();
                List<Persona> personaList = gson.fromJson(response, type);
                for (int i=0; i<personaList.size(); i++){
                    Persona persona = personaList.get(i);
                    for (int j=0; j<aprendizFichaListA.size(); j++){
                        AprendizFicha aprendizFicha = aprendizFichaListA.get(j);
                        for (int k=0; k<rolPersonaAList.size(); k++){
                            RolPersona rolPersona = rolPersonaAList.get(k);
                            if (persona.getUrl().equals(aprendizFicha.getPersona())
                                    && rolPersona.getPersona().equals(persona.getUrl())){
                                aprendizListA.add(persona);

                            }

                        }
                    }
                }
                Intent intent = new Intent(getActivity(), ListaAprendices.class);
                startActivity(intent);
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);
    }



}
