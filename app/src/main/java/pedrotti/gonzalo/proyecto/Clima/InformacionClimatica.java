package pedrotti.gonzalo.proyecto.Clima;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pedrotti.gonzalo.proyecto.Lotes.Lote;
import pedrotti.gonzalo.proyecto.R;

public class InformacionClimatica extends AppCompatActivity implements ClimaActualAdapter.OnItemClickListener {

    private Lote lote;
    private List<ClimaActual> climaActualList;
    RecyclerView recyclerView;
    ClimaActualAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_climatica);



        //Se recibe desde TodosLosLotes
        Bundle bundle = getIntent().getExtras();
        lote = bundle.getParcelable("DATOS_LOTE");

        setTitle("Datos del lote: " + lote.getNombre());
//        Toast.makeText(this, "lote: "  + lote.getLatitud()+ " / " + lote.getLongitud(), Toast.LENGTH_SHORT).show();


        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewClima);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        climaActualList = new ArrayList<>();
        findweather();

    }

    public void findweather(){

        String url="https://api.openweathermap.org/data/2.5/forecast?lat="+lote.getLatitud()+"&lon="+lote.getLongitud()+"&appid=6ce6f121d256afb888c209ead96b5b18&units=metric&lang=es";
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    //El objeto a iterar es el array list. Lo Declaramos a continuación:
                    JSONArray list = response.getJSONArray("list");

                 for (int i=0; i<list.length();i++){
                     //Dentro del array hay una lista de objetos

                     //Declaramos el objeto, uno por iteracion. Este objeto contiene el main y todos los demas.
                     //Tenemos que iterar sobre este
                     JSONObject climaActual = list.getJSONObject(i);


                     //Declaramos el Objeto Main y su contenido
                     JSONObject main = climaActual.getJSONObject("main");
                     double  temperatura = main.getDouble("temp");
                     double  humedad = main.getDouble("humidity");

                     //Declaramos el array weather
                     JSONArray weather = climaActual.getJSONArray("weather");
                     JSONObject object1 = weather.getJSONObject(0);
                     String descripcion = object1.getString("description");
                     String icon = object1.getString("icon");

                     //Declaramos el Objeto wind y su contenido
                     JSONObject wind = climaActual.getJSONObject("wind");
                     double speed = wind.getDouble("speed");
                     double speed_metrico =speed*3.6;

                     String txt_date = climaActual.getString("dt_txt");

                     double mmLluvia =0;
                     //Declaramos el objetvo wind y su contenido
                     try{
                         JSONObject rain = climaActual.getJSONObject("rain");
                         mmLluvia = rain.getDouble("3h");
                         Toast.makeText(InformacionClimatica.this, "Lluvia para el momento "+(i+1)+": " + mmLluvia, Toast.LENGTH_SHORT).show();
                     }catch (Exception e){
                         mmLluvia=0;
                     }

                     ClimaActual ca = new ClimaActual(temperatura,icon,txt_date,descripcion,humedad,speed_metrico,mmLluvia);
                     climaActualList.add(ca);
                 }
                 adapter = new ClimaActualAdapter(InformacionClimatica.this,climaActualList);
                 recyclerView.setAdapter(adapter);
                 adapter.setOnItemClickListener(InformacionClimatica.this);

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

    @Override
    public void OnItemClick(int position) {
        ClimaActual climaActualSeleccionado = climaActualList.get(position);
        Toast.makeText(this, "Seleccioando:" + climaActualSeleccionado.getTemperatura(), Toast.LENGTH_SHORT).show();

    }
}



