package pedrotti.gonzalo.proyecto.Siembra;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;
import pedrotti.gonzalo.proyecto.Actividad.ResultadoActividadRequest;
import pedrotti.gonzalo.proyecto.Constantes;
import pedrotti.gonzalo.proyecto.ProyectoCultivo.DetalleActividad;
import pedrotti.gonzalo.proyecto.ProyectoCultivo.ProyectoCultivo;
import pedrotti.gonzalo.proyecto.R;
import pedrotti.gonzalo.proyecto.Variedad.Variedad;

public class ResultadoSiembra extends AppCompatActivity implements View.OnClickListener , AdapterView.OnItemSelectedListener{

    private AsyncHttpClient cliente;
    private Spinner spVariedadesSiembra;
    ArrayList<Variedad> variedadesList;
    private static final String urlFecha = "http://"+ Constantes.ip+"/miCampoWeb/mobile/getInicioActividad.php?detalle_actividad_id=";

    //Valores seteados
    private String hora_inicio_seleccionada;
    private String fecha_inicio_Seleccionada;
    private String hora_fin_seleccionada;
    private String fecha_fin_Seleccionada;
    private String descripcion;
    private double dosis_utilizada;

    Date fecha_hora_inicial=null;
    Date fecha_hora_final=null;

    //Concatenacion de las 4 anteriores
    private String fecha_hora_inicio;
    private String fecha_hora_fin;

    //Las obtenidas de los exitText
    private String f2,h2;

    private Button btnGuardarResultado;
    ImageButton btnf1,btnf2,btnh1,btnh2;
    private EditText etFecha1Real, etFecha2Real, etHora1Real,etHora2Real,etDescripcion, etDosisUtilizada,etFechaInicioSiembra;


    private ProyectoCultivo proyecto;
    private DetalleActividad detalleSeleccionado;

    private int dia,mes,anio, hora, minutos;

    private int variedad_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_siembra);

        //Botones de hora y fecha Para el Inicio
//        btnf1= (ImageButton)findViewById(R.id.btnf1);
//        btnh1= (ImageButton)findViewById(R.id.btnh1);
//
//        //EditText
//        etFecha1Real = (EditText)findViewById(R.id.etFecha1Real);
//        etHora1Real = (EditText)findViewById(R.id.etHora1Real);


        //Botones de fecha y hora PAra el fin
        btnf2= (ImageButton)findViewById(R.id.btnf2);
        btnh2= (ImageButton)findViewById(R.id.btnh2);

        etFecha2Real = (EditText)findViewById(R.id.etFecha2Real);
        etHora2Real = (EditText)findViewById(R.id.etHora2Real);

        etDescripcion = (EditText)findViewById(R.id.etDescripcionActividad);
        etDosisUtilizada = (EditText)findViewById(R.id.etDosisReal);

        btnGuardarResultado = (Button)findViewById(R.id.btnGuardarResultado);

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

        etFechaInicioSiembra= (EditText)findViewById(R.id.etFechaInicioSiembra);

        cliente = new AsyncHttpClient();
        spVariedadesSiembra = (Spinner)findViewById(R.id.spVariedadesSiembra);
        spVariedadesSiembra.setOnItemSelectedListener(this);
        variedadesList = new ArrayList<>();
        llenarSpinnerVariedades();

        btnGuardarResultado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarResultado();
            }
        });

        cargarFechaInicio();
    }

    void cargarFechaInicio(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlFecha + detalleSeleccionado.getDetalle_actividad_id(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject fecha = array.getJSONObject(i);

                                fecha_hora_inicio = fecha.getString("fecha_inicio_real");
                                etFechaInicioSiembra.setText(""+fecha_hora_inicio);
                                Toast.makeText(ResultadoSiembra.this, "Inicio: " + fecha_hora_inicio, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ResultadoSiembra.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void llenarSpinnerVariedades(){
        String url = "http://"+ Constantes.ip+"/miCampoWeb/mobile/getVariedades.php?cultivo_id="+proyecto.getCultivo_id();

        cliente.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    cargarSpinnerVariedades(new String(responseBody));
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void cargarSpinnerVariedades(String respuesta){
        try{
            JSONArray variedades = new JSONArray(respuesta);
            for (int i=0;i < variedades.length();i++){
                Variedad v = new Variedad();
                v.setCultivo(variedades.getJSONObject(i).getString("Cultivo"));
                v.setVariedad_id(variedades.getJSONObject(i).getInt("idvariedad"));
                v.setVariedad(variedades.getJSONObject(i).getString("variedad"));
//                v.setDescripcion(variedades.getJSONObject(i).getString("descripcion"));
//                v.setDias(variedades.getJSONObject(i).getInt("dias"));
//                v.setProfundidad(variedades.getJSONObject(i).getDouble("profundidad"));
//                v.setPeso(variedades.getJSONObject(i).getDouble("peso"));
//                v.setDensidad(variedades.getJSONObject(i).getInt("densidad"));
//                v.setNiveldezona(variedades.getJSONObject(i).getString("niveldezona"));
//                v.setZona(variedades.getJSONObject(i).getString("zona"));
//                v.setSiembra(variedades.getJSONObject(i).getString("siembra"));
                variedadesList.add(v);
            }

            Variedad v = new Variedad();
            v.setVariedad("Otra");
            v.setVariedad_id(100);
            variedadesList.add(v);

            ArrayAdapter<Variedad> adapter = new ArrayAdapter<Variedad>(this,android.R.layout.simple_dropdown_item_1line,variedadesList);
            spVariedadesSiembra.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void guardarResultado(){
        //Se debe cambiar estado de la actividad segun lo que el usuario seleccione php

        f2=etFecha2Real.getText().toString();
        h2=etHora2Real.getText().toString();
        descripcion = etDescripcion.getText().toString();
        String dosisString = etDosisUtilizada.getText().toString();

        variedad_id = getVariedad_id();

        if ((dosisString==null) || (dosisString.equals(""))) {
            dosis_utilizada=0;
        }else{
            dosis_utilizada = Double.parseDouble(dosisString);
        }

//        fecha_hora_inicio = (getFecha_inicio_Seleccionada()+ " " + getHora_inicio_seleccionada());
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
                        if(f2.isEmpty()  || h2.isEmpty()){
                            Toast.makeText(ResultadoSiembra.this, "Indique Fecha y Hora de Fin", Toast.LENGTH_SHORT).show();
                        }else {
                            if (!(fecha_hora_inicial.before(fecha_hora_final))) {
                                Toast.makeText(ResultadoSiembra.this, "Controle las Fechas de Inicio y Fin", Toast.LENGTH_SHORT).show();
                            }if(dosis_utilizada==0){
                                Toast.makeText(ResultadoSiembra.this, "Ingrese los Kg de semillas utilizados", Toast.LENGTH_SHORT).show();
                            }else{

//                                Toast.makeText(ResultadoSiembra.this, "dosis: " + dosis_utilizada + " detalle: " + detalleSeleccionado.getDetalle_actividad_id() + " var sel: " + getVariedad_id(), Toast.LENGTH_SHORT).show();

                                //Response para regisrarSiembra.php
                                Response.Listener<String> respuesta = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonRespuesta = new JSONObject(response);
                                            boolean ok = jsonRespuesta.getBoolean("success");
//
                                            if (ok == true) {
                                                Toast.makeText(getApplicationContext(), "Siembra Finalizada con Éxito", Toast.LENGTH_LONG).show();
                                                ResultadoSiembra.this.finish();
                                            } else {
                                                Toast.makeText(ResultadoSiembra.this, "No se puede registrar la Siembra", Toast.LENGTH_SHORT).show();
//                                                        AlertDialog.Builder alerta = new AlertDialog.Builder(NuevaActividad.this);
//                                                        alerta.setMessage("Ya existe una activida para el momento indicado").setNegativeButton("Entendido", null).setTitle("Información de Registro de Actividad").setIcon(R.drawable.logo).create().show();
                                            }
                                        } catch (JSONException e) {
                                            e.getMessage();
                                            Toast.makeText(getApplicationContext(), "Seleccione un Cultivo de la Lista", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                };//End Response
                                ResultadoActividadRequest r = new ResultadoActividadRequest(detalleSeleccionado.getDetalle_actividad_id(),fecha_hora_fin,descripcion,variedad_id,dosis_utilizada, respuesta);
                                RequestQueue cola = Volley.newRequestQueue(ResultadoSiembra.this);
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

    private int getVariedad_id() {
        return variedad_id;
    }

    private void setVariedad_id(int variedad_id){
        this.variedad_id = variedad_id;
    }

    public double getDosis_utilizada() {
        return dosis_utilizada;
    }

    public void setDosis_utilizada(double dosis_utilizada) {
        this.dosis_utilizada = dosis_utilizada;
    }

    public String getHora_inicio_seleccionada() {
        return hora_inicio_seleccionada;
    }

    public void setHora_inicio_seleccionada(String hora_inicio_seleccionada) {
        this.hora_inicio_seleccionada = hora_inicio_seleccionada;
    }

    public String getFecha_inicio_Seleccionada() {
        return fecha_inicio_Seleccionada;
    }

    public void setFecha_inicio_Seleccionada(String fecha_inicio_Seleccionada) {
        this.fecha_inicio_Seleccionada = fecha_inicio_Seleccionada;
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
//        if(v==btnf1){
//            final Calendar c = Calendar.getInstance();
//            dia = c.get(Calendar.DAY_OF_MONTH);
//            mes = c.get(Calendar.MONTH) ;
//            anio = c.get(Calendar.YEAR);
//
//            //Picker de Fecha
//            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//                @Override
//                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                    etFecha1Real.setText(dayOfMonth+"-"+ (month+1) +"-"+year);
//                    setFecha_inicio_Seleccionada(year+"-"+(month+1)+"-"+dayOfMonth);
//                    Toast.makeText(ResultadoSiembra.this, "Fecha Inicio Seleccionada es: " + getFecha_inicio_Seleccionada(), Toast.LENGTH_SHORT).show();
//                }
//            },anio,mes,dia);
//
//            //Seteamos fecha mínima al día actual
//            datePickerDialog.setTitle("Fecha de Inicio");
//            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
////            datePickerDialog.getDatePicker().setMaxDate();
//            datePickerDialog.show();
//        }

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
                    Toast.makeText(ResultadoSiembra.this, "Fecha Fin Seleccionada es: " + getFecha_fin_Seleccionada(), Toast.LENGTH_SHORT).show();

                }
            },anio,mes,dia);

            //Seteamos fecha mínima al día actual
            datePickerDialog.setTitle("Fecha de Fin");
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
//            datePickerDialog.getDatePicker().setMaxDate();
            datePickerDialog.show();
        }

//        if(v==btnh1){
//            final Calendar c= Calendar.getInstance();
//            hora =c.get(Calendar.HOUR_OF_DAY);
//            minutos =c.get(Calendar.MINUTE);
//
//            //Picker de Hora
//            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
//                @Override
//                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                    etHora1Real.setText(hourOfDay+":"+minute + " hs");
//                    setHora_inicio_seleccionada(hourOfDay+":"+minute+":00");
//                    Toast.makeText(ResultadoSiembra.this, "Hora Inicio Seleccionada es: " + getHora_inicio_seleccionada(), Toast.LENGTH_SHORT).show();
//
//                }
//            },hora,minutos,false);
//
//
//            timePickerDialog.setTitle("Hora de Inicio");
//            timePickerDialog.show();
//        }

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
                    Toast.makeText(ResultadoSiembra.this, "Hora fin Seleccionada es: " + getHora_fin_seleccionada(), Toast.LENGTH_SHORT).show();

                }
            },hora,minutos,false);


            timePickerDialog.setTitle("Hora de Fin");
            timePickerDialog.show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Variedad variedadSeleccionada = variedadesList.get(position);
        setVariedad_id(variedadSeleccionada.getVariedad_id());
        double kg = (variedadSeleccionada.getDensidad() * variedadSeleccionada.getPeso())/1000;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
