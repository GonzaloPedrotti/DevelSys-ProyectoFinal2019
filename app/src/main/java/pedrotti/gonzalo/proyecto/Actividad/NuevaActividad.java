package pedrotti.gonzalo.proyecto.Actividad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
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
import pedrotti.gonzalo.proyecto.Campo.TodosLosCampos;
import pedrotti.gonzalo.proyecto.Constantes;
import pedrotti.gonzalo.proyecto.Clima.InformacionClimatica;
import pedrotti.gonzalo.proyecto.Lote.Lote;
import pedrotti.gonzalo.proyecto.ProyectoCultivo.ProyectoCultivo;
import pedrotti.gonzalo.proyecto.R;
import pedrotti.gonzalo.proyecto.Variedad.Variedad;

public class NuevaActividad extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private AsyncHttpClient cliente;
    private AsyncHttpClient cliente2;
    private ProyectoCultivo proyecto;
    private Lote lote;

    //Valores seteados
    private String hora_inicio_seleccionada;
    private String fecha_inicio_Seleccionada;
    private String hora_fin_seleccionada;
    private String fecha_fin_Seleccionada;


    Date fecha_hora_inicial=null;
    Date fecha_hora_final=null;

    //Concatenacion de las 4 anteriores
    private String fecha_hora_inicio;
    private String fecha_hora_fin;

    //Las obtenidas de los editext
    private String f1,f2,h1,h2;


    private int actividad_seleccionada_id;

    private Spinner  spActividad, spVariedad;



    ArrayList<Actividad> actividadesList;
    ArrayList<Variedad> variedadesList;

    private Button btnRegistrarActividad, btnVerClima;
    private TextView tvVar;
    ImageButton  btnHora, btnFecha, btnHora2,btnFecha2;
    private EditText etFecha, etHora, etFecha2, etHora2;

    private int mes, dia , anio, hora , minutos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_actividad);

        setTitle(R.string.registroactividades);

        //Se recibe desde DetalleProyecto
        Bundle bundle = getIntent().getExtras();
        proyecto = bundle.getParcelable("DATOS_PROYECTO");

        //Se recibe desde DetalleProyecto
        Bundle bundle2 = getIntent().getExtras();
        lote = bundle2.getParcelable("DATOS_LOTE");
        
        cliente = new AsyncHttpClient();
        cliente2 = new AsyncHttpClient();


        spActividad = (Spinner) findViewById(R.id.spActividad);
        spActividad.setOnItemSelectedListener(this);

//        spVariedad = (Spinner) findViewById(R.id.spVariedad);
//        spVariedad.setVisibility(View.INVISIBLE);

//        tvVar = (TextView) findViewById(R.id.tvVar);
//        tvVar.setVisibility(View.INVISIBLE);

        actividadesList = new ArrayList<>();
//        variedadesList = new ArrayList<>();

        spActividad.setOnItemSelectedListener(this);

        //Para el Inicio
        btnFecha = (ImageButton) findViewById(R.id.btnFecha);
        btnHora = (ImageButton) findViewById(R.id.btnHora);
        etFecha = (EditText) findViewById(R.id.etFecha);
        etHora = (EditText) findViewById(R.id.etHora);

        //Para el Fin
        btnFecha2=(ImageButton)findViewById(R.id.btnFecha2);
        btnHora2=(ImageButton)findViewById(R.id.btnHora2);
        etFecha2 = (EditText) findViewById(R.id.etFecha2);
        etHora2 = (EditText) findViewById(R.id.etHora2);

        btnVerClima = (Button)findViewById(R.id.btnInformacionApp);
        btnRegistrarActividad=(Button)findViewById(R.id.btnRegistrarActividad);

        llenarSpinnerActividades();
//        llenarSpinnerVariedades();

        btnFecha.setOnClickListener(this);
        btnHora.setOnClickListener(this);
        btnFecha2.setOnClickListener(this);
        btnHora2.setOnClickListener(this);

        btnVerClima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent verClima = new Intent(getApplicationContext(), InformacionClimatica.class);
               verClima.putExtra("DATOS_LOTE",lote);
               startActivity(verClima);
            }
        });

        btnRegistrarActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                actividad_seleccionada_id= getActividad_seleccionada_id();

                f1= etFecha.getText().toString();
                f2=etFecha2.getText().toString();
                h1=etHora.getText().toString();
                h2=etHora2.getText().toString();

                fecha_hora_inicio = (getFecha_inicio_Seleccionada()+ " " + getHora_inicio_seleccionada());
                fecha_hora_fin = (getFecha_fin_Seleccionada() + " " + getHora_fin_seleccionada());

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

                fecha_hora_inicial=null;
                fecha_hora_final=null;

                try {
                    fecha_hora_inicial = sdf.parse(fecha_hora_inicio);
                    fecha_hora_final= sdf.parse(fecha_hora_fin);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                AlertDialog.Builder alertaReg = new AlertDialog.Builder(NuevaActividad.this);
                alertaReg.setTitle("Registro")
                        .setMessage("¿Desea Registrar esta Actividad?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(f1.isEmpty() || f2.isEmpty() || h1.isEmpty() || h2.isEmpty()){
                                    Toast.makeText(NuevaActividad.this, "Indique Fechas y Horas de Inicio y Fin", Toast.LENGTH_SHORT).show();
                                }else {
                                    if (!(fecha_hora_inicial.before(fecha_hora_final))) {
                                        Toast.makeText(NuevaActividad.this, "Controle las Fechas de Inicio y Fin", Toast.LENGTH_SHORT).show();
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

                                                        final ProgressDialog progressDialog = new ProgressDialog(NuevaActividad.this);
                                                        progressDialog.setIcon(R.mipmap.ic_launcher);

                                                        progressDialog.setMessage("Registrando Actividad...");
                                                        progressDialog.show();
                                                        progressDialog.dismiss();
                                                        Toast.makeText(getApplicationContext(), "Actividad Registrada con éxito", Toast.LENGTH_LONG).show();
                                                        NuevaActividad.this.finish();
                                                    } else {
                                                        Toast.makeText(NuevaActividad.this, "Ya existe una actividad para el momento elegido", Toast.LENGTH_SHORT).show();
//                                                        AlertDialog.Builder alerta = new AlertDialog.Builder(NuevaActividad.this);
//                                                        alerta.setMessage("Ya existe una activida para el momento indicado").setNegativeButton("Entendido", null).setTitle("Información de Registro de Actividad").setIcon(R.drawable.logo).create().show();
                                                    }
                                                } catch (JSONException e) {
                                                    e.getMessage();
                                                    Toast.makeText(getApplicationContext(), "Seleccione un Cultivo de la Lista", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        };//End Response
                                        NuevaActividadRequest r = new NuevaActividadRequest(proyecto.getId(),actividad_seleccionada_id,fecha_hora_inicio,fecha_hora_fin, respuesta);
                                        RequestQueue cola = Volley.newRequestQueue(NuevaActividad.this);
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

    public void setActividad_seleccionada_id(int actividad_seleccionada_id) {
        this.actividad_seleccionada_id = actividad_seleccionada_id;
    }

    //Spinner de Actividades
    private void llenarSpinnerActividades(){
        String url ="http://"+ Constantes.ip+"/miCampoWeb/mobile/getActividad.php";

        cliente.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    cargarSpinnerActividades(new String(responseBody));
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
    }


    private void cargarSpinnerActividades(String respuesta){
        try{
            JSONArray actividades = new JSONArray(respuesta);
            for (int i=0;i < actividades.length();i++){
                Actividad a = new Actividad();
                a.setActividad_id(actividades.getJSONObject(i).getInt("actividad_id"));
                a.setNombre(actividades.getJSONObject(i).getString("nombre"));
                a.setDescripcion(actividades.getJSONObject(i).getString("descripcion"));
                actividadesList.add(a);
            }
            ArrayAdapter<Actividad> adapter = new ArrayAdapter<Actividad>(this,android.R.layout.simple_dropdown_item_1line,actividadesList);
            spActividad.setAdapter(adapter);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void setActividadId(int id){
        this.actividad_seleccionada_id = id;
    }

    public int getActividad_seleccionada_id(){
        return actividad_seleccionada_id;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Actividad actividadSeleccionada = actividadesList.get(position);
        setActividadId(actividadSeleccionada.getActividad_id());
//        Toast.makeText(this, "Id Actividad" + getActividad_seleccionada_id(), Toast.LENGTH_SHORT).show();
//        if(actividadSeleccionada.getActividad_id()== 2){
//            tvVar.setVisibility(View.VISIBLE);
//            spVariedad.setVisibility(View.VISIBLE);
//        }else{
//            tvVar.setVisibility(View.INVISIBLE);
//            spVariedad.setVisibility(View.INVISIBLE);
//        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if(v==btnFecha){
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH) ;
            anio = c.get(Calendar.YEAR);

            //Picker de Fecha
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    etFecha.setText(dayOfMonth+"/"+ (month+1) +"/"+year);
                    setFecha_inicio_Seleccionada(year+"/"+(month+1)+"/"+dayOfMonth);
                    Toast.makeText(NuevaActividad.this, "Fecha Inicio Seleccionada es: " + getFecha_inicio_Seleccionada(), Toast.LENGTH_SHORT).show();
                }
            },anio,mes,dia);

            //Seteamos fecha mínima al día actual
            datePickerDialog.setTitle("Fecha Estimada de Inicio");
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
//            datePickerDialog.getDatePicker().setMaxDate(2020-10-10);
//            datePickerDialog.getDatePicker().setMaxDate();
            datePickerDialog.show();
        }

        if(v==btnFecha2){
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH) ;
            anio = c.get(Calendar.YEAR);

            //Picker de Fecha
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    etFecha2.setText(dayOfMonth+"/"+ (month+1) +"/"+year);
                    setFecha_fin_Seleccionada(year+"/"+(month+1)+"/"+dayOfMonth);
                    Toast.makeText(NuevaActividad.this, "Fecha Fin Seleccionada es: " + getFecha_fin_Seleccionada(), Toast.LENGTH_SHORT).show();

                }
            },anio,mes,dia);

            //Seteamos fecha mínima al día actual
            datePickerDialog.setTitle("Fecha Estimada de Fin");
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
//            datePickerDialog.getDatePicker().setMaxDate();
            datePickerDialog.show();
        }

        if(v==btnHora){
            final Calendar c= Calendar.getInstance();
            hora =c.get(Calendar.HOUR_OF_DAY);
            minutos =c.get(Calendar.MINUTE);

            //Picker de Hora
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    etHora.setText(hourOfDay+":"+minute + " hs");
                    setHora_inicio_seleccionada(hourOfDay+":"+minute+":00");
                    Toast.makeText(NuevaActividad.this, "Hora Inicio Seleccionada es: " + getHora_inicio_seleccionada(), Toast.LENGTH_SHORT).show();

                }
            },hora,minutos,false);


            timePickerDialog.setTitle("Hora Estimada de Inicio");
            timePickerDialog.show();
        }

        if(v==btnHora2){
            final Calendar c= Calendar.getInstance();
            hora =c.get(Calendar.HOUR_OF_DAY);
            minutos =c.get(Calendar.MINUTE);

            //Picker de Hora
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    etHora2.setText(hourOfDay+":"+minute + " hs");
                    setHora_fin_seleccionada(hourOfDay+":"+minute+":00");
                    Toast.makeText(NuevaActividad.this, "Hora fin Seleccionada es: " + getHora_fin_seleccionada(), Toast.LENGTH_SHORT).show();

                }
            },hora,minutos,false);


            timePickerDialog.setTitle("Hora Estimada de Fin");
            timePickerDialog.show();
        }
    }
}




