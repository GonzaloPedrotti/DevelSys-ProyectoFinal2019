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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import pedrotti.gonzalo.proyecto.Constantes;
import pedrotti.gonzalo.proyecto.ProyectoCultivo.DetalleActividad;
import pedrotti.gonzalo.proyecto.ProyectoCultivo.ProyectoCultivo;
import pedrotti.gonzalo.proyecto.R;

public class InicioActividad extends AppCompatActivity implements View.OnClickListener {

    private DetalleActividad detalleSeleccionado;
    private ProyectoCultivo proyectoSeleccionado;

    private ImageButton btnfi,btnhi;
    private EditText etFecha1Real,etHora1Real,etActividad;
    private Button btnGuardarInicio;

    private String hora_inicio_seleccionada;
    private String fecha_inicio_Seleccionada;

    Date fecha_hora_inicial=null;

    //Concatenacion de las  anteriores
    private String fecha_hora_inicio;

    //Las obtenidas de los exitText
    private String f1,h1;

    private int dia,mes,anio, hora, minutos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_actividad);

        //Se recibe desde DetalleActividad
        Bundle bundle = getIntent().getExtras();
        detalleSeleccionado = bundle.getParcelable("DETALLE_SELECCIONADO");

        //Se recibe desde DetalleActividad
        Bundle bundle2 = getIntent().getExtras();
        proyectoSeleccionado = bundle2.getParcelable("DATOS_PROYECTO");

        setTitle(detalleSeleccionado.getActividad());

        etActividad = (EditText)findViewById(R.id.etActividad);
        btnfi = (ImageButton)findViewById(R.id.btnfi);
        btnhi = (ImageButton)findViewById(R.id.btnhi);

        etFecha1Real = (EditText)findViewById(R.id.etf1i);
        etHora1Real = (EditText)findViewById(R.id.eth1i);

        btnGuardarInicio = (Button)findViewById(R.id.btnGuardarInicio);

        etActividad.setText(detalleSeleccionado.getActividad());

        btnfi.setOnClickListener(this);
        btnhi.setOnClickListener(this);

        btnGuardarInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarInicio();
            }
        });

        Date fechaActual = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
        String fecha = sdf.format(fechaActual);
//        Toast.makeText(this, "Fecha Actual: " + fecha, Toast.LENGTH_SHORT).show();
        etFecha1Real.setText(fecha);
    }

    void guardarInicio(){
        f1 = etFecha1Real.getText().toString();
        h1 = etHora1Real.getText().toString();

        fecha_hora_inicio = (getFecha_inicio_Seleccionada()+ " " + getHora_inicio_seleccionada());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        fecha_hora_inicial=null;

        try {
            fecha_hora_inicial = sdf.parse(fecha_hora_inicio);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        AlertDialog.Builder alertaReg = new AlertDialog.Builder(this);
        alertaReg.setTitle("Registro")
                .setMessage("¿Guardar Resultado?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(f1.isEmpty()  || h1.isEmpty()){
                            Toast.makeText(InicioActividad.this, "Indique Fechas y Horas de Inicio y Fin", Toast.LENGTH_SHORT).show();
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
                                                Toast.makeText(getApplicationContext(), "Actividad Iniciada con Éxito", Toast.LENGTH_LONG).show();
                                                InicioActividad.this.finish();
                                            } else {
                                                Toast.makeText(InicioActividad.this, "Ya existe una actividad para el momento elegido", Toast.LENGTH_SHORT).show();
//                                                        AlertDialog.Builder alerta = new AlertDialog.Builder(NuevaActividad.this);
//                                                        alerta.setMessage("Ya existe una activida para el momento indicado").setNegativeButton("Entendido", null).setTitle("Información de Registro de Actividad").setIcon(R.drawable.logo).create().show();
                                            }
                                        } catch (JSONException e) {
                                            e.getMessage();
                                            Toast.makeText(getApplicationContext(), "Ocurrió un Error con el Servidor", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                };//End Response
                                InicioActividadRequest r = new InicioActividadRequest(detalleSeleccionado.getDetalle_actividad_id(),fecha_hora_inicio, respuesta);
                                RequestQueue cola = Volley.newRequestQueue(InicioActividad.this);
                                cola.add(r);
                            }//else del Response

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

    class InicioActividadRequest extends StringRequest {

        private static final String ruta = "http://" + Constantes.ip + "//miCampoWeb/mobile/iniciarActividad.php";

        private Map<String, String> parametros;

        public InicioActividadRequest(int detalle_actividad_id, String fecha_inicio_real, Response.Listener<String> listener) {
            super(Request.Method.POST, ruta, listener, null);
            parametros = new HashMap<>();
            parametros.put("detalle_actividad_id", detalle_actividad_id + "");
            parametros.put("fecha_inicio_real", fecha_inicio_real + "");
        }
        protected Map<String, String> getParams() {
            return parametros;
        }
    }

    private String getHora_inicio_seleccionada() {
        return  hora_inicio_seleccionada;
    }

    private String getFecha_inicio_Seleccionada() {
        return fecha_inicio_Seleccionada;
    }


    public void setHora_inicio_seleccionada(String hora_inicio_seleccionada) {
        this.hora_inicio_seleccionada = hora_inicio_seleccionada;
    }

    public void setFecha_inicio_Seleccionada(String fecha_inicio_Seleccionada) {
        this.fecha_inicio_Seleccionada = fecha_inicio_Seleccionada;
    }


    @Override
    public void onClick(View v) {
        if(v==btnfi){
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH) ;
            anio = c.get(Calendar.YEAR);

            //Picker de Fecha
            DatePickerDialog datePickerDialog = new DatePickerDialog(InicioActividad.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    etFecha1Real.setText(dayOfMonth+"-"+ (month+1) +"-"+year);
                    setFecha_inicio_Seleccionada(year+"-"+(month+1)+"-"+dayOfMonth);
//                    Toast.makeText(ResultadoActividad.this, "Fecha Inicio Seleccionada es: " + getFecha_inicio_Seleccionada(), Toast.LENGTH_SHORT).show();
                }
            },anio,mes,dia);

            //Seteamos fecha mínima al día actual
            datePickerDialog.setTitle("Fecha de Inicio Real");
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
//            datePickerDialog.getDatePicker().setMaxDate();
            datePickerDialog.show();
        }


        if(v==btnhi){
            final Calendar c= Calendar.getInstance();
            hora =c.get(Calendar.HOUR_OF_DAY);
            minutos =c.get(Calendar.MINUTE);

            //Picker de Hora
            TimePickerDialog timePickerDialog = new TimePickerDialog(InicioActividad.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    etHora1Real.setText(hourOfDay+":"+minute + " hs");
                    setHora_inicio_seleccionada(hourOfDay+":"+minute+":00");
//                    Toast.makeText(ResultadoActividad.this, "Hora Inicio Seleccionada es: " + getHora_inicio_seleccionada(), Toast.LENGTH_SHORT).show();

                }
            },hora,minutos,false);


            timePickerDialog.setTitle("Hora de Inicio Real");
            timePickerDialog.show();
        }
    }
}


