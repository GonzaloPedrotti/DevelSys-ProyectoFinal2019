package pedrotti.gonzalo.proyecto.ProyectoCultivo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pedrotti.gonzalo.proyecto.Constantes;
import pedrotti.gonzalo.proyecto.Lote.Lote;
import pedrotti.gonzalo.proyecto.R;

public class TodosLosProyectos extends AppCompatActivity implements  ProyectoCultivoAdapter.OnItemClickListener{

    private static final String url = "http://"+ Constantes.ip+"/miCampoWeb/mobile/obtenerProyectosDelLote.php?lote_id=";

    private int lote_id;
    RecyclerView recyclerView;
    List<ProyectoCultivo> proyectoCultivoList;
    ProyectoCultivoAdapter adapter;
    private ProyectoCultivo proyectonuevo;
    Lote lote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos_los_proyectos);

        //Se recibe desde Información desde InformacionDelLote
        Bundle bundle = getIntent().getExtras();
        lote = bundle.getParcelable("DATOS_LOTE");

        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewProyectos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        proyectoCultivoList = new ArrayList<>();

        loadProyectos();

    }

    public void loadProyectos(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+lote.getLote_id(),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        proyectonuevo = new ProyectoCultivo();
                        try {
                            //converting the string to json array object

                            JSONArray array = new JSONArray(response);

                            if(array==null || array.length()==0){
                                AlertDialog.Builder alerta = new AlertDialog.Builder(TodosLosProyectos.this);
                                alerta.setMessage("No Tiene Proyectos Registrados. Comience registrando uno!").setPositiveButton("Entendido", null).create().show();
//                                Toast.makeText(TodosLosCampos.this, "No tiene Campos Registrados.Comience Registrando uno!", Toast.LENGTH_SHORT).show();
                            }

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject campos = array.getJSONObject(i);

                                int proyecto_id = campos.getInt("id");
                                String nombre = campos.getString("nombre");
                                String fechareg = campos.getString("fechareg");
                                int cultivo_id = campos.getInt("idcultivo");
                                String cultivo = campos.getString("cultivo");
                                String periodo = campos.getString("periodo");
                                String estadoproyecto = campos.getString("estadoproyecto");



                                proyectonuevo.setId(proyecto_id);

                                //Se crea el constructor para crear el objeto Campo
                                ProyectoCultivo proyectoCultivo = new ProyectoCultivo(proyecto_id,nombre,fechareg,cultivo,periodo,estadoproyecto,cultivo_id);

                                //añadiendo a la lista campoList el objeto Campo recien creado
                                proyectoCultivoList.add(proyectoCultivo);
                            }

                            adapter = new ProyectoCultivoAdapter(TodosLosProyectos.this, proyectoCultivoList);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(TodosLosProyectos.this);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TodosLosProyectos.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }


    //Este metodo muestra en una nueva actividad el campo seleccionado
    @Override
    public void OnItemClick(int position) {
        Intent detalleProyecto = new Intent(this, DetalleProyecto.class);
        ProyectoCultivo itemSeleccionado = proyectoCultivoList.get(position);

        detalleProyecto.putExtra("DATOS_PROYECTO", itemSeleccionado);
        detalleProyecto.putExtra("DATOS_LOTE",lote);
        startActivity(detalleProyecto);
    }
}
