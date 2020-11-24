package pedrotti.gonzalo.proyecto;

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

import pedrotti.gonzalo.proyecto.ProyectoCultivo.ProyectoCultivo;
import pedrotti.gonzalo.proyecto.ProyectoCultivo.ProyectoCultivoAdapter;
import pedrotti.gonzalo.proyecto.Reportes.ReporteActividad;
import pedrotti.gonzalo.proyecto.Usuario.Usuario;

public class MenuReporte extends AppCompatActivity implements ProyectoCultivoAdapter.OnItemClickListener{



    private  String url = "http://"+ Constantes.ip+"/miCampoWeb/mobile/obtenerProyectosDeUsuario.php?usuario_id=1";

    private int lote_id;
    RecyclerView recyclerView;
    List<ProyectoCultivo> proyectoCultivoList;
    ProyectoCultivoAdapter adapter;
    private ProyectoCultivo proyectonuevo;
    private Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_reporte);

        setTitle("Lista de Proyectos");

        //Se recibe desde el Bienvenido
        Bundle bundle = getIntent().getExtras();
        user = bundle.getParcelable("DATOS_USER");

        Toast.makeText(this, "id: " + user.getUsu_id(), Toast.LENGTH_SHORT).show();

        recyclerView = (RecyclerView)findViewById(R.id.recyclerProyectos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        proyectoCultivoList = new ArrayList<>();

        loadProyectos();
    }

    public void loadProyectos(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+user.getUsu_id(),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        proyectonuevo = new ProyectoCultivo();
                        try {
                            //converting the string to json array object

                            JSONArray array = new JSONArray(response);

                            if(array==null || array.length()==0){
                                AlertDialog.Builder alerta = new AlertDialog.Builder(MenuReporte.this);
                                alerta.setMessage("No Tiene Proyectos Registrados. Comience registrando uno!").setPositiveButton("Entendido", null).create().show();
//                                Toast.makeText(TodosLosCampos.this, "No tiene Campos Registrados.Comience Registrando uno!", Toast.LENGTH_SHORT).show();
                            }

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject campos = array.getJSONObject(i);

                                int usuario_id = campos.getInt("idusuario");
                                int idcampo = campos.getInt("idcampo");
                                int idlote = campos.getInt("idlote");
                                int proyecto_id = campos.getInt("idproyecto");
                                String nombre = campos.getString("nombreProyecto");
                                String fechareg = campos.getString("fechareg");
                                int cultivo_id = campos.getInt("idcultivo");
                                String cultivo = campos.getString("cultivo");
                                String periodo = campos.getString("periodo");
                                String estadoproyecto = campos.getString("estado");


                                proyectonuevo.setId(proyecto_id);

                                //Se crea el constructor para crear el objeto Campo
                                ProyectoCultivo proyectoCultivo = new ProyectoCultivo(proyecto_id,nombre,fechareg,cultivo,periodo,estadoproyecto,cultivo_id);

                                //añadiendo a la lista campoList el objeto Campo recien creado
                                proyectoCultivoList.add(proyectoCultivo);
                            }

                            adapter = new ProyectoCultivoAdapter(MenuReporte.this, proyectoCultivoList);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(MenuReporte.this);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MenuReporte.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }


    //Este metodo muestra en una nueva actividad el campo seleccionado
    @Override
    public void OnItemClick(int position) {
        Intent detalleProyecto = new Intent(this, ReporteActividad.class);
        ProyectoCultivo itemSeleccionado = proyectoCultivoList.get(position);

        detalleProyecto.putExtra("DATOS_PROYECTO", itemSeleccionado);
        startActivity(detalleProyecto);
    }
}
