package pedrotti.gonzalo.proyecto.Actividad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import pedrotti.gonzalo.proyecto.Constantes;
import pedrotti.gonzalo.proyecto.ProyectoCultivo.DetalleActividad;
import pedrotti.gonzalo.proyecto.ProyectoCultivo.ProyectoCultivo;
import pedrotti.gonzalo.proyecto.R;

public class ResultadoActividad extends AppCompatActivity implements View.OnClickListener {

    //Valores seteados
    private String descripcion;

    private static final String url = "http://"+ Constantes.ip+"/miCampoWeb/mobile/getInicioActividad.php?detalle_actividad_id=";

    Date fecha_hora_inicial=null;
    Date fecha_hora_final=null;

    private String hora_fin_seleccionada;
    private String fecha_fin_Seleccionada;


    //Concatenacion de las 2 anteriores
    private String fecha_hora_inicio;
    private String fecha_hora_fin;

    //Las obtenidas de los exitText
    private String f2,h2;

    private Button btnGuardarResultado;
    ImageButton btnf1,btnf2,btnh1,btnh2;
    private EditText etFecha1Real, etFecha2Real,etHora2Real,etDescripcion,etFechaInicio;

    private ProyectoCultivo proyecto;
    private DetalleActividad detalleSeleccionado;

    private int dia,mes,anio, hora, minutos;

    private int variedad_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_actividad);

        setTitle("Fin de Actividad");

        //Botones de hora y fecha Para el Inicio
//        btnf1= (ImageButton)findViewById(R.id.btnf1a);
//        btnh1= (ImageButton)findViewById(R.id.btnh1a);

        //EditText
//        etFecha1Real = (EditText)findViewById(R.id.etf1a);
//        etHora1Real = (EditText)findViewById(R.id.eth1a);


        //Botones de fecha y hora PAra el fin
        btnf2= (ImageButton)findViewById(R.id.btnf2a);
        btnh2= (ImageButton)findViewById(R.id.btnh2a);

        etFecha2Real = (EditText)findViewById(R.id.etf2a);
        etHora2Real = (EditText)findViewById(R.id.eth2a);

        etDescripcion = (EditText)findViewById(R.id.etDescripcionA);

        btnGuardarResultado = (Button)findViewById(R.id.btnGuardarActividad);

        //Se recibe desde DetalleProyecto
        Bundle bundle = getIntent().getExtras();
        proyecto = bundle.getParcelable("DATOS_PROYECTO");


        //Se recibe desde DetalleProyecto
        Bundle bundle2 = getIntent().getExtras();
        detalleSeleccionado = bundle2.getParcelable("DETALLE_SELECCIONADO");

//        btnf1.setOnClickListener(this);
        btnf2.setOnClickListener(this);
//        btnh1.setOnClickListener(this);
        btnh2.setOnClickListener(this);

        etFechaInicio= (EditText)findViewById(R.id.etFechaInicio);

        btnGuardarResultado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarResultado();
            }
        });

        cargarFechaInicio();

        Date fechaActual = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
        String fecha = sdf.format(fechaActual);
//        Toast.makeText(this, "Fecha Actual: " + fecha, Toast.LENGTH_SHORT).show();
        etFecha2Real.setText(fecha);

    }

    void cargarFechaInicio(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + detalleSeleccionado.getDetalle_actividad_id(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject fecha = array.getJSONObject(i);

                                fecha_hora_inicio = fecha.getString("fecha_inicio_real");
                                etFechaInicio.setText(""+fecha_hora_inicio);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ResultadoActividad.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }

    void guardarResultado(){
        //Se debe cambiar estado de la actividad segun lo que el usuario seleccione php


        f2=etFecha2Real.getText().toString();
        h2=etHora2Real.getText().toString();
        descripcion = etDescripcion.getText().toString();

        fecha_hora_fin = (getFecha_fin_Seleccionada() + " " + getHora_fin_seleccionada());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        fecha_hora_final=null;

        try {
            fecha_hora_inicial = sdf.parse(fecha_hora_inicio);
            fecha_hora_final= sdf.parse(fecha_hora_fin);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        AlertDialog.Builder alertaReg = new AlertDialog.Builder(this);
        alertaReg.setTitle("Registro")
                .setMessage("¿Guardar Resultado?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if( f2.isEmpty() || h2.isEmpty()){
                            Toast.makeText(ResultadoActividad.this, "Indique Fechas y Horas de Inicio y Fin", Toast.LENGTH_SHORT).show();
                        }else {
                            if (!(fecha_hora_inicial.before(fecha_hora_final))) {
                                Toast.makeText(ResultadoActividad.this, "Controle las Fechas de Inicio y Fin", Toast.LENGTH_SHORT).show();
                            }
                            else{
//                                Response para regisrarSiembra.php
                                Response.Listener<String> respuesta = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonRespuesta = new JSONObject(response);
                                            boolean ok = jsonRespuesta.getBoolean("success");
//
                                            if (ok == true) {
                                                Toast.makeText(getApplicationContext(), "Actividad Finalizada con Éxito", Toast.LENGTH_LONG).show();
                                                ResultadoActividad.this.finish();
                                            } else {
                                                Toast.makeText(ResultadoActividad.this, "Ya existe una actividad para el momento elegido", Toast.LENGTH_SHORT).show();
//                                                        AlertDialog.Builder alerta = new AlertDialog.Builder(NuevaActividad.this);
//                                                        alerta.setMessage("Ya existe una activida para el momento indicado").setNegativeButton("Entendido", null).setTitle("Información de Registro de Actividad").setIcon(R.drawable.logo).create().show();
                                            }
                                        } catch (JSONException e) {
                                            e.getMessage();
                                            Toast.makeText(getApplicationContext(), "Ocurrió un Error con el Servidor", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                };//End Response
                                ResultadoActividadRequest r = new ResultadoActividadRequest(detalleSeleccionado.getDetalle_actividad_id(),fecha_hora_fin,descripcion,0,0, respuesta);
                                RequestQueue cola = Volley.newRequestQueue(ResultadoActividad.this);
                                cola.add(r);
                            }//else del Response
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();

    }

    public String getHora_fin_seleccionada() {
        return hora_fin_seleccionada;
    }

    public void setHora_fin_seleccionada(String hora_fin_seleccionada) {
        this.hora_fin_seleccionada = hora_fin_seleccionada;
    }

    public String getFecha_fin_Seleccionada() {
        return fecha_fin_Seleccionada;
    }

    public void setFecha_fin_Seleccionada(String fecha_fin_Seleccionada) {
        this.fecha_fin_Seleccionada = fecha_fin_Seleccionada;
    }


    @Override
    public void onClick(View v) {


        if(v==btnf2){
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH) ;
            anio = c.get(Calendar.YEAR);

            //Picker de Fecha
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    etFecha2Real.setText(dayOfMonth+"-"+ (month+1) +"-"+year);
                    setFecha_fin_Seleccionada(year+"-"+(month+1)+"-"+dayOfMonth);
//                    Toast.makeText(ResultadoActividad.this, "Fecha Fin Seleccionada es: " + getFecha_fin_Seleccionada(), Toast.LENGTH_SHORT).show();

                }
            },anio,mes,dia);

            //Seteamos fecha mínima al día actual
            datePickerDialog.setTitle("Fecha de Fin Real");
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
//            datePickerDialog.getDatePicker().setMaxDate();
            datePickerDialog.show();
        }

        if(v==btnh2){
            final Calendar c= Calendar.getInstance();
            hora =c.get(Calendar.HOUR_OF_DAY);
            minutos =c.get(Calendar.MINUTE);

            //Picker de Hora
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    etHora2Real.setText(hourOfDay+":"+minute + " hs");
                    setHora_fin_seleccionada(hourOfDay+":"+minute+":00");
//                    Toast.makeText(ResultadoActividad.this, "Hora fin Seleccionada es: " + getHora_fin_seleccionada(), Toast.LENGTH_SHORT).show();

                }
            },hora,minutos,false);

            timePickerDialog.setTitle("Hora de Fin Real");
            timePickerDialog.show();
        }
    }

}
