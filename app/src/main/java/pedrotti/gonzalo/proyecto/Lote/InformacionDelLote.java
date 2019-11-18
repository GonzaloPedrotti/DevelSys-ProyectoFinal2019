package pedrotti.gonzalo.proyecto.Lote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import pedrotti.gonzalo.proyecto.ProyectoCultivo.NuevoProyectoCultivo;
import pedrotti.gonzalo.proyecto.ProyectoCultivo.TodosLosProyectos;
import pedrotti.gonzalo.proyecto.R;


public class InformacionDelLote extends AppCompatActivity {

    TextView tvTemp,tvNombre,tvDescripcion,tvDate,tvHora, tvHumedad, tvViento , tvLoteId, tvLatitud, tvLongitud;
    Button btnNuevoProyectoCultivo, btnVerHistorial;


    private int campo_id;
    private int lote_id;
    private String nombrelote;
    double latitudlote;
    double longitudlote;

    private Lote loteSeleccionado;

    ImageView ivIcono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_del_lote);

        //Se recibe desde TodosLosLotes
        Bundle bundle = getIntent().getExtras();
        loteSeleccionado = bundle.getParcelable("DATOS_LOTE");

        setTitle(nombrelote);

//        tvLoteId=(TextView)findViewById(R.id.tvLoteId);
        tvLatitud =(TextView)findViewById(R.id.tvLatitud);
        tvLongitud = (TextView)findViewById(R.id.tvLongitud);
        tvTemp = (TextView)findViewById(R.id.tvTemperatura);
        tvNombre = (TextView)findViewById((R.id.tvNombreLoteInf));
        tvDescripcion = (TextView)findViewById(R.id.tvDescripcion);
        tvDate = (TextView)findViewById(R.id.tvDate);
        tvHora = (TextView)findViewById(R.id.tvHora);
        tvViento= (TextView)findViewById(R.id.tvViento);
        tvHumedad = (TextView)findViewById(R.id.tvHumedad);

        btnNuevoProyectoCultivo=(Button)findViewById(R.id.btnNuevoProyectoCultivo);
        btnVerHistorial = (Button)findViewById(R.id.btnVerHistorial);

        tvNombre.setText(loteSeleccionado.getNombre());
//        tvLoteId.setText(""+loteSeleccionado.getLote_id());
        tvLatitud.setText(""+loteSeleccionado.getLatitud());
        tvLongitud.setText(""+loteSeleccionado.getLongitud());

        DateFormat fecha = new SimpleDateFormat("dd/MM/YYYY");
        Date date = new Date();
        tvDate.setText(fecha.format(date));

        Calendar horaActual = new GregorianCalendar();
        int hora = horaActual.get(Calendar.HOUR_OF_DAY);
        int minutos = horaActual.get(Calendar.MINUTE);

        tvHora.setText(hora +":"+ minutos);

        find_weather();

        btnNuevoProyectoCultivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nuevoProyectoCultivo();
            }
        });

        btnVerHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verHistorialProyectos();
            }
        });
    }

    public void find_weather(){
        double latitud = loteSeleccionado.getLatitud();
        double longitud = loteSeleccionado.getLongitud();

        String url="https://api.openweathermap.org/data/2.5/weather?lat="+latitud+"&lon="+longitud+"&appid=16fd3d6923010b36f5a362fc5695fd9f&units=metric&lang=es";
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //Dentro de la Respuesta se devuelven varios objetos

                    //un objeto es el main, por eso lo instanciamos
                    JSONObject main_object = response.getJSONObject("main");

                    //Otro objeto es una array, lo instanciamos como un array
                    JSONArray array = response.getJSONArray("weather");

                    //Dentro del array hay un solo objeto (con indice 0) con varios items
                    JSONObject object = array.getJSONObject(0);

                    //Declaramos un String llamado temperatura. La temperatura esta dentro del main
                    String  temperatura = String.valueOf(main_object.getDouble("temp"));


                    //Declaramos un String llamado temperatura. La temperatura esta dentro del main
                    String  humedad = String.valueOf(main_object.getDouble("humidity"));

                    //Otro objeto es el wind, por eso lo instanciamos
                    JSONObject wind_object = response.getJSONObject("wind");

                    //Declaramos un String llamado viento. la Velocidad está dentro del wind
                    String  viento = String.valueOf(wind_object.getDouble("speed"));

                    //Declaramos un String llamado descripcion. La descripcion está dentro del unico objeto que hay dentro del array weather
                    String descripcion = object.getString("description");


                    //Otro objeto es el nombre de la ciudad llamado name
//                    String ciudad = response.getString("name");


//                    tvCiudad.setText(ciudad);
                    tvDescripcion.setText(descripcion);
                    tvTemp.setText(temperatura+"");
                    tvViento.setText(viento+"");
                    tvHumedad.setText(humedad+"");

//                    double temp_int = Double.parseDouble(temperatura);
//                    String temprString = String.valueOf(temp_int);
////                    tvTemp.setText(temprString);
//                    tvTemp.setText(temperatura+"");
//
//                    double viento_int = Double.parseDouble(viento);
//                    double velViento = (viento_int*3.6);
//
//                    String vientoString = String.valueOf(velViento);
//                    tvViento.setText(vientoString);
//
//                    double humedad_int = Double.parseDouble(humedad);
//                    String humedadString = String.valueOf(humedad_int);
//                    tvHumedad.setText(humedadString);

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jor);
    }

    public void nuevoProyectoCultivo(){
        Intent nuevoProyecto = new Intent(getApplicationContext(), NuevoProyectoCultivo.class);
        nuevoProyecto.putExtra("DATOS_LOTE",loteSeleccionado);
        startActivity(nuevoProyecto);
    }

    public void verHistorialProyectos(){
        Intent verProyectos = new Intent(getApplicationContext(), TodosLosProyectos.class);
        verProyectos.putExtra("DATOS_LOTE",loteSeleccionado);
        startActivity(verProyectos);
    }
}
