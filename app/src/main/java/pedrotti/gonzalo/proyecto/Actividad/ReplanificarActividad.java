package pedrotti.gonzalo.proyecto.Actividad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import pedrotti.gonzalo.proyecto.Clima.InformacionClimatica;
import pedrotti.gonzalo.proyecto.Lote.Lote;
import pedrotti.gonzalo.proyecto.ProyectoCultivo.DetalleActividad;
import pedrotti.gonzalo.proyecto.ProyectoCultivo.ProyectoCultivo;
import pedrotti.gonzalo.proyecto.R;
import pedrotti.gonzalo.proyecto.Siembra;

public class ReplanificarActividad extends AppCompatActivity implements View.OnClickListener {

    private DetalleActividad detalleActividad;
    private ProyectoCultivo proyecto;
    private Lote lote;

    private String f1,f2,h1,h2;
    private EditText etf1, etf2, eth1, eth2;
    private Button btnActualizarActividad , btnRecomendacionMomento ;
    private TextView etActividad;

    private String actividad ;

    private TextView tvf1,tvf2;

    private Button btnVerFechas;

    private ImageButton btnFechaInicioNueva;
    private ImageButton btnFechaFinNueva;
    private ImageButton btnHoraInicioNueva;
    private ImageButton btnHoraFinNueva;

    //Valores seteados
    private String hora_inicio_seleccionada;
    private String fecha_inicio_Seleccionada;
    private String hora_fin_seleccionada;
    private String fecha_fin_Seleccionada;

    Date fecha_hora_inicial=null;
    Date fecha_hora_final=null;

    Date fecha1_modificar= null;
    Date fecha2_modificar=null;

    public DetalleActividad getDetalleActividad() {
        return detalleActividad;
    }

    public void setDetalleActividad(DetalleActividad detalleActividad) {
        this.detalleActividad = detalleActividad;
    }

    public Date getFecha1_modificar() {
        return fecha1_modificar;
    }

    public void setFecha1_modificar(Date fecha1_modificar) {
        this.fecha1_modificar = fecha1_modificar;
    }

    public Date getFecha2_modificar() {
        return fecha2_modificar;
    }

    public void setFecha2_modificar(Date fecha2_modificar) {
        this.fecha2_modificar = fecha2_modificar;
    }

    //Concatenacion de las 4 anteriores
    private String fecha_hora_inicio;
    private String fecha_hora_fin;

    private int dia,mes,anio, hora, minutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replanificar_actividad);

        //Se recibe desde DetalleProyecto
        Bundle bundle = getIntent().getExtras();
        detalleActividad = bundle.getParcelable("DETALLE_SELECCIONADO");

        //Se recibe desde DetalleProyecto
        Bundle bundle2 = getIntent().getExtras();
        proyecto = bundle2.getParcelable("DATOS_PROYECTO");

        //Se recibe desde DetalleProyecto
        Bundle bundle3 = getIntent().getExtras();
        lote = bundle3.getParcelable("DATOS_LOTE");

        actividad = detalleActividad.getActividad();
        etActividad = (TextView)findViewById(R.id.tvActividadResultado);
        btnActualizarActividad = (Button)findViewById(R.id.btnActualizarActividad);
        etActividad.setText(detalleActividad.getActividad());

        btnFechaInicioNueva = (ImageButton)findViewById(R.id.btnFechaInicioNueva);
        btnFechaFinNueva = (ImageButton)findViewById(R.id.btnFechaFinNueva);
        btnHoraInicioNueva = (ImageButton) findViewById(R.id.btnHoraInicioNueva);
        btnHoraFinNueva = (ImageButton)findViewById(R.id.btnHoraFinNueva);

        btnFechaInicioNueva.setOnClickListener(this);
        btnFechaFinNueva.setOnClickListener(this);
        btnHoraInicioNueva.setOnClickListener(this);
        btnHoraFinNueva.setOnClickListener(this);


        etf1 = (EditText)findViewById(R.id.etf1);
        etf2 = (EditText)findViewById(R.id.etf2);
        eth1 = (EditText)findViewById(R.id.eth1);
        eth2 = (EditText)findViewById(R.id.eth2);

        btnRecomendacionMomento = (Button)findViewById(R.id.btnRecomendacionMomento);



        btnActualizarActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarActividad();
            }
        });

        btnRecomendacionMomento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent siembra  = new Intent(getApplicationContext(), InformacionClimatica.class);
                siembra.putExtra("DATOS_PROYECTO",proyecto);
                siembra.putExtra("DATOS_LOTE",lote);
                startActivity(siembra);
            }
        });

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

    public void actualizarActividad(){
        f1=etf1.getText().toString();
        f2=etf2.getText().toString();
        h1=eth1.getText().toString();
        h2=eth2.getText().toString();

        fecha_hora_inicio = (getFecha_inicio_Seleccionada()+ " " + getHora_inicio_seleccionada());
        fecha_hora_fin = (getFecha_fin_Seleccionada() + " " + getHora_fin_seleccionada());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        fecha_hora_inicial=null;
        fecha_hora_final=null;


        try {
            fecha_hora_inicial = sdf.parse(fecha_hora_inicio);
            fecha_hora_final= sdf.parse(fecha_hora_fin);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        AlertDialog.Builder alertaReg = new AlertDialog.Builder(ReplanificarActividad.this);
        alertaReg.setTitle("Modificación")
                .setMessage("¿Desea Modificar esta Actividad?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(f1.isEmpty() || f2.isEmpty() || h1.isEmpty() || h2.isEmpty()){
                            Toast.makeText(ReplanificarActividad.this, "Indique Fechas y Horas de Inicio y Fin", Toast.LENGTH_SHORT).show();
                        }else {
                            if (!(fecha_hora_inicial.before(fecha_hora_final))) {
                                Toast.makeText(ReplanificarActividad.this, "Controle las Fechas de Inicio y Fin", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Response.Listener<String> respuesta = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonRespuesta = new JSONObject(response);
                                            boolean ok = jsonRespuesta.getBoolean("success");
//
                                            if (ok == true) {
                                                Toast.makeText(getApplicationContext(), "Actividad Modificada con éxito", Toast.LENGTH_LONG).show();
                                                ReplanificarActividad.this.finish();
                                            } else {
                                                Toast.makeText(ReplanificarActividad.this, "Ya existe una actividad para el momento elegido", Toast.LENGTH_SHORT).show();
//                                                        AlertDialog.Builder alerta = new AlertDialog.Builder(NuevaActividad.this);
//                                                        alerta.setMessage("Ya existe una activida para el momento indicado").setNegativeButton("Entendido", null).setTitle("Información de Registro de Actividad").setIcon(R.drawable.logo).create().show();
                                            }
                                        } catch (JSONException e) {
                                            e.getMessage();
                                            Toast.makeText(getApplicationContext(), "Ocurrió un error", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                };//End Response
                                ActualizarActividadRequest r = new ActualizarActividadRequest(detalleActividad.getProyecto_cultivo_id(),detalleActividad.getDetalle_actividad_id(),fecha_hora_inicio,fecha_hora_fin, respuesta);
                                RequestQueue cola = Volley.newRequestQueue(ReplanificarActividad.this);
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

    @Override
    public void onClick(View v) {
        if(v==btnFechaInicioNueva){
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH) ;
            anio = c.get(Calendar.YEAR);

            //Picker de Fecha
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    etf1.setText(dayOfMonth+"-"+ (month+1) +"-"+year);
                    setFecha_inicio_Seleccionada(year+"-"+(month+1)+"-"+dayOfMonth);
                    Toast.makeText(ReplanificarActividad.this, "Fecha Inicio Seleccionada es: " + getFecha_inicio_Seleccionada(), Toast.LENGTH_SHORT).show();
                }
            },anio,mes,dia);

            //Seteamos fecha mínima al día actual
            datePickerDialog.setTitle("Fecha de Inicio");
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
//            datePickerDialog.getDatePicker().setMaxDate();
            datePickerDialog.show();
        }

        if(v==btnFechaFinNueva){
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH) ;
            anio = c.get(Calendar.YEAR);

            //Picker de Fecha
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    etf2.setText(dayOfMonth+"-"+ (month+1) +"-"+year);
                    setFecha_fin_Seleccionada(year+"-"+(month+1)+"-"+dayOfMonth);
                    Toast.makeText(ReplanificarActividad.this, "Fecha Fin Seleccionada es: " + getFecha_fin_Seleccionada(), Toast.LENGTH_SHORT).show();

                }
            },anio,mes,dia);

            //Seteamos fecha mínima al día actual
            datePickerDialog.setTitle("Fecha de Fin");
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
//            datePickerDialog.getDatePicker().setMaxDate();
            datePickerDialog.show();
        }

        if(v==btnHoraInicioNueva){
            final Calendar c= Calendar.getInstance();
            hora =c.get(Calendar.HOUR_OF_DAY);
            minutos =c.get(Calendar.MINUTE);

            //Picker de Hora
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    eth1.setText(hourOfDay+":"+minute + " hs");
                    setHora_inicio_seleccionada(hourOfDay+":"+minute+":00");
                    Toast.makeText(ReplanificarActividad.this, "Hora Inicio Seleccionada es: " + getHora_inicio_seleccionada(), Toast.LENGTH_SHORT).show();

                }
            },hora,minutos,false);


            timePickerDialog.setTitle("Hora de Inicio");
            timePickerDialog.show();
        }

        if(v==btnHoraFinNueva){
            final Calendar c= Calendar.getInstance();
            hora =c.get(Calendar.HOUR_OF_DAY);
            minutos =c.get(Calendar.MINUTE);

            //Picker de Hora
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    eth2.setText(hourOfDay+":"+minute + " hs");
                    setHora_fin_seleccionada(hourOfDay+":"+minute+":00");
                    Toast.makeText(ReplanificarActividad.this, "Hora fin Seleccionada es: " + getHora_fin_seleccionada(), Toast.LENGTH_SHORT).show();

                }
            },hora,minutos,false);


            timePickerDialog.setTitle("Hora de Fin");
            timePickerDialog.show();
        }
    }
}
