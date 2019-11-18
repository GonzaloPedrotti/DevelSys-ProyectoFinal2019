package pedrotti.gonzalo.proyecto.Clima;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
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
import java.util.ArrayList;
import java.util.List;

import pedrotti.gonzalo.proyecto.Actividad.Actividad;
import pedrotti.gonzalo.proyecto.Campo.TodosLosCampos;
import pedrotti.gonzalo.proyecto.Lote.Lote;
import pedrotti.gonzalo.proyecto.R;

public class InformacionClimatica extends AppCompatActivity implements ClimaActualAdapter.OnItemClickListener {

    private Lote lote;
    private List<ClimaActual> climaActualList;
    RecyclerView recyclerView;
    ClimaActualAdapter adapter;
    private TextView tvRecomendacion;
    private Actividad actividad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_climatica);

        //Se recibe desde TodosLosLotes o Desde Replanificar Actividad
        Bundle bundle = getIntent().getExtras();
        lote = bundle.getParcelable("DATOS_LOTE");

        setTitle("Datos del lote: " + lote.getNombre());

        tvRecomendacion=(TextView)findViewById(R.id.tvRecomendacion);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewClima);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        climaActualList = new ArrayList<>();
        findweather();
    }
    public void mostrarReferencia(){
        AlertDialog.Builder alerta = new AlertDialog.Builder(InformacionClimatica.this);
        alerta.setMessage(R.drawable.verde+": Permitido Realizar \n" +  R.drawable.verde+ ": Permitido Realizar \n").setPositiveButton("Entendido", null).setTitle("Recomendación").setIcon(R.drawable.logo).create().show();
    }

    public void findweather(){

        String url="https://api.openweathermap.org/data/2.5/forecast?lat="+lote.getLatitud()+"&lon="+lote.getLongitud()+"&appid=6ce6f121d256afb888c209ead96b5b18&units=metric&lang=es";

        final ProgressDialog progressDialog = new ProgressDialog(InformacionClimatica.this);
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setMessage("Cargando Recomendaciones...");
        progressDialog.show();
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
                     }catch (Exception e){
                         mmLluvia=0;
                     }

                     ClimaActual ca = new ClimaActual(temperatura,icon,txt_date,descripcion,humedad,speed_metrico,mmLluvia);
                     climaActualList.add(ca);

                 }

                 adapter = new ClimaActualAdapter(InformacionClimatica.this,climaActualList,2);
                 recyclerView.setAdapter(adapter);

                 progressDialog.dismiss();
                 adapter.setOnItemClickListener(InformacionClimatica.this);

                } catch (JSONException e) {
                    progressDialog.dismiss();
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
        final ClimaActual climaActualSeleccionado = climaActualList.get(position);
        final AlertDialog.Builder alerta = new AlertDialog.Builder(InformacionClimatica.this);
        alerta.setMessage("Verde: Aplicación Segura \n" + "Amarillo: Precaución al Aplicar \n"+ "Rojo: No es Recomendable Aplicar \n" ).setTitle("Recomendación").setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(InformacionClimatica.this, "Salir", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }).setIcon(R.drawable.logo).create().show();
    }

}



