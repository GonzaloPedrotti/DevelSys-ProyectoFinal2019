package pedrotti.gonzalo.proyecto.ProyectoCultivo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

import pedrotti.gonzalo.proyecto.Actividad.ItemActividad;
import pedrotti.gonzalo.proyecto.Actividad.NuevaActividad;
import pedrotti.gonzalo.proyecto.Constantes;
import pedrotti.gonzalo.proyecto.Lote.Lote;
import pedrotti.gonzalo.proyecto.R;

public class DetalleProyecto extends AppCompatActivity implements DetalleActividadAdapter.OnItemClickListener {

    private ProyectoCultivo proyecto;
    private TextView tvEstado, tvNombreProyecto, tvFechaRegistro, tvCultivo, tvPeriodo;
    private Button btnNuevaActividad;
    private DetalleActividad detalleActividad;
    private DetalleActividad detalleSeleccionado;


    //Para la obtencion de las actividades realizadas:
    RecyclerView recyclerView;
    DetalleActividadAdapter adapter;
    List<DetalleActividad> detalleActividadList;
    Lote lote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_proyecto);
    }


    protected void onResume() {
        super.onResume();


        setTitle(R.string.detalleProyecto);

        //Se recibe desde TodosLosProyectos
        Bundle bundle = getIntent().getExtras();
        proyecto = bundle.getParcelable("DATOS_PROYECTO");

        //Se recibe desde Información del Lote
        Bundle bundle2 = getIntent().getExtras();
        lote = bundle2.getParcelable("DATOS_LOTE");

        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewActividades);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        detalleActividadList = new ArrayList<>();


        tvEstado = (TextView)findViewById(R.id.tvEstadoActual);
        tvNombreProyecto = (TextView)findViewById(R.id.tvNombreProyectoDet);
        tvFechaRegistro = (TextView)findViewById(R.id.tvFechaRegistroDet);
        tvCultivo = (TextView)findViewById(R.id.tvCultivoDet);
        tvPeriodo = (TextView)findViewById(R.id.tvPeriodoDet);
        btnNuevaActividad = (Button)findViewById(R.id.btnRealizarActividad);

        tvEstado.setText(proyecto.getEstado());
        tvNombreProyecto.setText(proyecto.getNombre());
        tvFechaRegistro.setText(proyecto.getFechaRegistro());
        tvCultivo.setText(proyecto.getCultivo());
        tvPeriodo.setText(proyecto.getPeriodo());

        loadActividades();

        btnNuevaActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirNuevaActividad();
            }
        });

    }


    public void loadActividades(){
        String url = "http://"+ Constantes.ip+"/miCampoWeb/mobile/getDetalleActividad.php?proyecto_cultivo_id="+proyecto.getId();


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                detalleActividad = new DetalleActividad();
                try {

                    JSONArray array = new JSONArray(response);

                    if (array == null || array.length() == 0) {

                        AlertDialog.Builder alerta = new AlertDialog.Builder(DetalleProyecto.this);
                        alerta.setMessage("No Hay Registro de Actividades en este Proyecto").setPositiveButton("Entendido", null).setTitle("Información").setIcon(R.drawable.logo).create().show();

                    }
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject detalle = array.getJSONObject(i);

                        String proyecto = detalle.getString("proyecto");
                        int detalle_actividad_id = detalle.getInt("iddetalle");
                        String actividad = detalle.getString("actividad");
                        int actividad_id = detalle.getInt("idactividad");
                        String fecha_inicio = detalle.getString("inicio");
                        String fecha_fin = detalle.getString("fin");
                        String estado = detalle.getString("estado");

                        DetalleActividad detalleActividad = new DetalleActividad(actividad,detalle_actividad_id,actividad_id, fecha_inicio, fecha_fin, estado);

                        detalleActividadList.add(detalleActividad);
                    }
                    adapter = new DetalleActividadAdapter(DetalleProyecto.this, detalleActividadList);
                    recyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListener(DetalleProyecto.this);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetalleProyecto.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }

    public void abrirNuevaActividad(){
        Intent nuevaactividad = new Intent (getApplicationContext(), NuevaActividad.class);
        nuevaactividad.putExtra("DATOS_PROYECTO",proyecto);
        nuevaactividad.putExtra("DATOS_LOTE", lote);
        startActivity(nuevaactividad);
    }

    public void eliminar(int id){
        Toast.makeText(this, "Se Borró  con éxito el detalle: " + id , Toast.LENGTH_SHORT).show();
        onResume();
    }

    @Override
    public void OnItemClick(int position) {
        detalleSeleccionado  = detalleActividadList.get(position);
        Toast.makeText(this, "Actividad: " + detalleSeleccionado.getActividad(), Toast.LENGTH_SHORT).show();

        AlertDialog.Builder alerta = new AlertDialog.Builder(DetalleProyecto.this);
        alerta.setMessage("La Actividad " + detalleSeleccionado.getActividad() + " comienza el día " + detalleSeleccionado.getInicio() + " y finaliza el día " + detalleSeleccionado.getFin())
                .setNegativeButton("Modificar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent detalleActividad = new Intent(DetalleProyecto.this, ItemActividad.class);
                        detalleActividad.putExtra("DETALLE_SELECCIONADO",detalleSeleccionado);
                        startActivity(detalleActividad);
                    }
                })
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminar(detalleSeleccionado.getDetalle_actividad_id());
                    }
                })
                .setTitle("Detalle de Actividad")
                .setIcon(R.drawable.logo).create().show();
    }

}
