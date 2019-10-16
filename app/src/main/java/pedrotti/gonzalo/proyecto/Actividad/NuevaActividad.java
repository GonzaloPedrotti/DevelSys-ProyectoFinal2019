package pedrotti.gonzalo.proyecto.Actividad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;
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

    private Spinner  spActividad, spVariedad;

    ArrayList<Actividad> actividadesList;
    ArrayList<Variedad> variedadesList;

    private Button btnRegistrarActividad, btnVerClima;
    private TextView tvVar;
    ImageButton  btnHora, btnFecha;
    private EditText etFecha, etHora;

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

        spVariedad = (Spinner) findViewById(R.id.spVariedad);
        spVariedad.setVisibility(View.INVISIBLE);

        tvVar = (TextView) findViewById(R.id.tvVar);
        tvVar.setVisibility(View.INVISIBLE);

        actividadesList = new ArrayList<>();
        variedadesList = new ArrayList<>();

        spActividad.setOnItemSelectedListener(this);

        btnFecha = (ImageButton) findViewById(R.id.btnFecha);
        btnHora = (ImageButton) findViewById(R.id.btnHora);
        etFecha = (EditText) findViewById(R.id.etFecha);
        etHora = (EditText) findViewById(R.id.etHora);
        btnVerClima = (Button)findViewById(R.id.btnInformacionApp);

        llenarSpinnerActividades();
        llenarSpinnerVariedades();

        btnFecha.setOnClickListener(this);
        btnHora.setOnClickListener(this);

        btnVerClima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent verClima = new Intent(getApplicationContext(), InformacionClimatica.class);
               verClima.putExtra("DATOS_LOTE",lote);
               startActivity(verClima);
            }
        });

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
                v.setNiveldezona(variedades.getJSONObject(i).getString("niveldezona"));
                v.setZona(variedades.getJSONObject(i).getString("zona"));
                v.setDescripcion(variedades.getJSONObject(i).getString("descripcion"));

                variedadesList.add(v);
            }
            ArrayAdapter<Variedad> adapter = new ArrayAdapter<Variedad>(this,android.R.layout.simple_dropdown_item_1line,variedadesList);
            spVariedad.setAdapter(adapter);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        Actividad actividadSeleccionada = actividadesList.get(position);
//        Toast.makeText(this, "Proyecto: "+ proyecto.getNombre()+ " Actividad: " + actividadSeleccionada.getActividad_id() + "nombre: " + actividadSeleccionada.getNombre(), Toast.LENGTH_SHORT).show();
        if(actividadSeleccionada.getActividad_id()== 2){
            tvVar.setVisibility(View.VISIBLE);
            spVariedad.setVisibility(View.VISIBLE);

        }else{
            tvVar.setVisibility(View.INVISIBLE);
            spVariedad.setVisibility(View.INVISIBLE);
        }

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
                }
            },anio,mes,dia);

            //Seteamos fecha mínima al día actual
            datePickerDialog.setTitle("Fecha Estimada de Inicio");
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
                }
            },hora,minutos,false);


            timePickerDialog.setTitle("Hora Estimada de Inicio");
            timePickerDialog.show();
        }
    }
}




