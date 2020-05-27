package pedrotti.gonzalo.proyecto.Clima;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pedrotti.gonzalo.proyecto.Modelo.Actividad;
import pedrotti.gonzalo.proyecto.Constantes;
import pedrotti.gonzalo.proyecto.Lote.Lote;
import pedrotti.gonzalo.proyecto.ProyectoCultivo.ProyectoCultivo;
import pedrotti.gonzalo.proyecto.R;

public class InformacionClimatica extends AppCompatActivity implements ClimaActualAdapter.OnItemClickListener {

    private Lote lote;
    private List<ClimaActual> climaActualList;
    RecyclerView recyclerView;
    ClimaActualAdapter adapter;
    private TextView tvRecomendacion;
    private Actividad actividad;
    private ProyectoCultivo proyecto;
    public int actividad_id;

    public String ruta1Siembra = "http://"+ Constantes.ip+"/miCampoWeb/mobile/getMomentoSiembra.php";
    public String ruta2Fumigacion = "http://"+ Constantes.ip+"/miCampoWeb/mobile/getMomentoFumigacion.php";
    public String ruta3Arado = "http://"+ Constantes.ip+"/miCampoWeb/mobile/getMomentoArado.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_climatica);

        //Se recibe desde TodosLosLotes o Desde Replanificar Actividad
        Bundle bundle = getIntent().getExtras();
        lote = bundle.getParcelable("DATOS_LOTE");

        //Se recibe desde DetalleProyecto
        Bundle bundle2 = getIntent().getExtras();
        proyecto = bundle2.getParcelable("DATOS_PROYECTO");

        //se recibe desde DetalleProyecto->Replanificar Actividad /// se recibe desde nueva Actividad
        actividad_id = getIntent().getIntExtra("actividad_id",0);

//        Toast.makeText(this, "actividad_id es: " + actividad_id, Toast.LENGTH_SHORT).show();

        tvRecomendacion=(TextView)findViewById(R.id.tvRecomendacion);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewClima);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        climaActualList = new ArrayList<>();


        //Si es arado
        if(actividad_id==1){
            buscarClimaArado(ruta3Arado);
        }

        //Si es siembra
        if(actividad_id==2){
            buscarClimaSiembra(ruta1Siembra);
        }

        //Si es Fummigacion
        if(actividad_id==3){
            buscarClimaFumigacion(ruta2Fumigacion);
        }

        //Si es Fertilizacion
        if(actividad_id==4){
            buscarClimaFertilizacion();
        }

        //Si es Cosecha
        if(actividad_id==5){
            buscarClimaCosecha();
        }

    }

    public void buscarClimaFertilizacion() {
        Toast.makeText(this, "Fertilización aún no está implementado", Toast.LENGTH_SHORT).show();
    }

    public void buscarClimaCosecha() {
        Toast.makeText(this, "Cosecha Aún no está implementado", Toast.LENGTH_SHORT).show();
    }


    public void buscarClimaArado(String url){
//        Toast.makeText(this, "Se recomienda para Arado ", Toast.LENGTH_SHORT).show();
        final ProgressDialog progressDialog = new ProgressDialog(InformacionClimatica.this);
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setMessage("Cargando Recomendaciones...");
        progressDialog.show();

        Response.Listener<String> respuesta = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject momento = array.getJSONObject(i);

                        double temperatura = momento.getDouble("temperatura");
                        double humedad = momento.getDouble("humedad");
                        double viento = momento.getDouble("viento");
                        String cielo = momento.getString("cielo");
                        String icono = momento.getString("icono");
                        String fecha = momento.getString("momento");
                        double mm = momento.getDouble("mm");
                        int recomendacion = momento.getInt("recomendacion");

                        ClimaActual ca = new ClimaActual(temperatura,icono,fecha,cielo,humedad,viento,mm,recomendacion);
                        climaActualList.add(ca);

                    }

                    adapter = new ClimaActualAdapter(InformacionClimatica.this,climaActualList);
                    recyclerView.setAdapter(adapter);

                    progressDialog.dismiss();

                    adapter.setOnItemClickListener(InformacionClimatica.this);
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }
        };
        MomentoRequest r = new MomentoRequest(proyecto.getCultivo_id(),lote.getLatitud(),lote.getLongitud(),url,respuesta);
        RequestQueue cola = Volley.newRequestQueue(InformacionClimatica.this);
        cola.add(r);
    }


    public void buscarClimaSiembra(String url){

        final ProgressDialog progressDialog = new ProgressDialog(InformacionClimatica.this);
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setMessage("Cargando Recomendaciones...");
        progressDialog.show();

        Response.Listener<String> respuesta = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject momento = array.getJSONObject(i);

                        double temperatura = momento.getDouble("temperatura");
                        double humedad = momento.getDouble("humedad");
                        double viento = momento.getDouble("viento");
                        String cielo = momento.getString("cielo");
                        String icono = momento.getString("icono");
                        String fecha = momento.getString("momento");
                        double mm = momento.getDouble("mm");
                        int recomendacion = momento.getInt("recomendacion");

                        ClimaActual ca = new ClimaActual(temperatura,icono,fecha,cielo,humedad,viento,mm,recomendacion);
                        climaActualList.add(ca);

                    }

                    adapter = new ClimaActualAdapter(InformacionClimatica.this,climaActualList);
                    recyclerView.setAdapter(adapter);

                    progressDialog.dismiss();

                    adapter.setOnItemClickListener(InformacionClimatica.this);
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }
        };
        MomentoRequest r = new MomentoRequest(proyecto.getCultivo_id(),lote.getLatitud(),lote.getLongitud(),url,respuesta);
        RequestQueue cola = Volley.newRequestQueue(InformacionClimatica.this);
        cola.add(r);

    }

    public void mostrarReferencia(){
        AlertDialog.Builder alerta = new AlertDialog.Builder(InformacionClimatica.this);
        alerta.setMessage(R.drawable.verde+": Permitido Realizar \n" +  R.drawable.verde+ ": Permitido Realizar \n").setPositiveButton("Entendido", null).setTitle("Recomendación").setIcon(R.drawable.logo).create().show();
    }

    public void buscarClimaFumigacion(String url){

//        String url="http://"+ Constantes.ip+"/miCampoWeb/mobile/getMomentoSiembra.php";


        final ProgressDialog progressDialog = new ProgressDialog(InformacionClimatica.this);
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setMessage("Cargando Recomendaciones...");
        progressDialog.show();

        Response.Listener<String> respuesta = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject momento = array.getJSONObject(i);

                        double temperatura = momento.getDouble("temperatura");
                        double humedad = momento.getDouble("humedad");
                        double viento = momento.getDouble("viento");
                        String cielo = momento.getString("cielo");
                        String icono = momento.getString("icono");
                        String fecha = momento.getString("momento");
                        double mm = momento.getDouble("mm");
                        int recomendacion = momento.getInt("recomendacion");

                        ClimaActual ca = new ClimaActual(temperatura,icono,fecha,cielo,humedad,viento,mm,recomendacion);
                        climaActualList.add(ca);
                    }

                    adapter = new ClimaActualAdapter(InformacionClimatica.this,climaActualList);
                    recyclerView.setAdapter(adapter);

                    progressDialog.dismiss();

                    adapter.setOnItemClickListener(InformacionClimatica.this);
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }
        };
        MomentoRequest r = new MomentoRequest(proyecto.getCultivo_id(),lote.getLatitud(),lote.getLongitud(),url,respuesta);
        RequestQueue cola = Volley.newRequestQueue(InformacionClimatica.this);
        cola.add(r);
    }

    @Override
    public void OnItemClick(int position) {
        final ClimaActual climaActualSeleccionado = climaActualList.get(position);
        final AlertDialog.Builder alerta = new AlertDialog.Builder(InformacionClimatica.this);
        alerta.setMessage("Verde: Aplicación Segura \n" + "Amarillo: Precaución al Aplicar \n"+ "Rojo: No es Recomendable Aplicar \n" ).setTitle("Recomendación").setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setIcon(R.drawable.logo).create().show();
    }


    class MomentoRequest extends StringRequest {

//        private static  final String ruta = "http://"+ Constantes.ip+"/miCampoWeb/mobile/getMomentoSiembra.php";

        private Map<String,String> parametros;
        public MomentoRequest (int cultivo_id, double latitud,double longitud,String ruta, Response.Listener<String> listener){
            super(Request.Method.POST, ruta, listener, null);
            parametros = new HashMap<>();
            parametros.put("cultivo_id",  cultivo_id+"");
            parametros.put("latitud",  latitud+"");
            parametros.put("longitud",  longitud+"");
        }


        @Override
        protected Map<String, String> getParams(){
            return parametros;
        }
    }


//    public void buscarClimaFumigacion(){
//
//        String url="https://api.openweathermap.org/data/2.5/forecast?lat="+lote.getLatitud()+"&lon="+lote.getLongitud()+"&appid=6ce6f121d256afb888c209ead96b5b18&units=metric&lang=es";
//
//        final ProgressDialog progressDialog = new ProgressDialog(InformacionClimatica.this);
//        progressDialog.setIcon(R.mipmap.ic_launcher);
//        progressDialog.setMessage("Cargando Recomendaciones...");
//        progressDialog.show();
//        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//
//                    //El objeto a iterar es el array list. Lo Declaramos a continuación:
//                    JSONArray list = response.getJSONArray("list");
//
//                    for (int i=0; i<list.length();i++){
//                        //Dentro del array hay una lista de objetos
//
//                        //Declaramos el objeto, uno por iteracion. Este objeto contiene el main y todos los demas.
//                        //Tenemos que iterar sobre este
//                        JSONObject climaActual = list.getJSONObject(i);
//
//                        //Declaramos el Objeto Main y su contenido
//                        JSONObject main = climaActual.getJSONObject("main");
//                        double  temperatura = main.getDouble("temp");
//                        double  humedad = main.getDouble("humidity");
//
//                        //Declaramos el array weather
//                        JSONArray weather = climaActual.getJSONArray("weather");
//                        JSONObject object1 = weather.getJSONObject(0);
//                        String descripcion = object1.getString("description");
//                        String icon = object1.getString("icon");
//
//                        //Declaramos el Objeto wind y su contenido
//                        JSONObject wind = climaActual.getJSONObject("wind");
//                        double speed = wind.getDouble("speed");
//                        double speed_metrico =speed*3.6;
//
//                        String txt_date = climaActual.getString("dt_txt");
//
//                        double mmLluvia =0;
//                        //Declaramos el objetvo wind y su contenido
//                        try{
//                            JSONObject rain = climaActual.getJSONObject("rain");
//                            mmLluvia = rain.getDouble("3h");
//                        }catch (Exception e){
//                            mmLluvia=0;
//                        }
//
//                        ClimaActual ca = new ClimaActual(temperatura,icon,txt_date,descripcion,humedad,speed_metrico,mmLluvia,1);
//                        climaActualList.add(ca);
//
//                    }
//
//                    adapter = new ClimaActualAdapter(InformacionClimatica.this,climaActualList,2);
//                    recyclerView.setAdapter(adapter);
//
//                    progressDialog.dismiss();
//                    adapter.setOnItemClickListener(InformacionClimatica.this);
//
//                } catch (JSONException e) {
//                    progressDialog.dismiss();
//                    e.printStackTrace();
//                }
//            }
//        }, new ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        }
//        );
//        RequestQueue queue = Volley.newRequestQueue(this);
//        queue.add(jor);
//    }


}



