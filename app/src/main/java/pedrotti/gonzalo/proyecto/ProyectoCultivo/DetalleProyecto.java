package pedrotti.gonzalo.proyecto.ProyectoCultivo;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
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

import pedrotti.gonzalo.proyecto.Actividad.ReplanificarActividad;
import pedrotti.gonzalo.proyecto.Actividad.NuevaActividad;
import pedrotti.gonzalo.proyecto.Actividad.ResultadoActividad;
import pedrotti.gonzalo.proyecto.Constantes;
import pedrotti.gonzalo.proyecto.Fertilizacion.Fertilizacion;
import pedrotti.gonzalo.proyecto.Actividad.InicioActividad;
import pedrotti.gonzalo.proyecto.Lote.Lote;
import pedrotti.gonzalo.proyecto.R;
import pedrotti.gonzalo.proyecto.Siembra.ResultadoSiembra;
import pedrotti.gonzalo.proyecto.Siembra.Siembra;


public class DetalleProyecto extends AppCompatActivity implements  DetalleActividadAdapter.OnItemClickListener {

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
        //se agrega de prueba:
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        detalleActividadList = new ArrayList<>();
        recyclerView.setItemViewCacheSize(detalleActividadList.size());


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

        cargarActividades();

        btnNuevaActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirNuevaActividad();
            }
        });
    }


    public void cargarActividades(){
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
                        int proyecto_cultivo_id = detalle.getInt("idproyecto");

                        DetalleActividad detalleActividad = new DetalleActividad(actividad,actividad_id,fecha_inicio,fecha_fin,estado,detalle_actividad_id,proyecto_cultivo_id);

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


    @Override
    public void OnItemClick(int position) {

//        Toast.makeText(this, "Nada", Toast.LENGTH_SHORT).show();
//
        try{
            detalleSeleccionado  = detalleActividadList.get(position);
        }catch(Exception e){

        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Lista de Acciones");
        builder.setIcon(R.drawable.logo);
        builder.setItems(new CharSequence[]{
                "Replanificar Actividad", "Ver Recomendación", "Iniciar Actividad","Finalizar Actividad","Eliminar Actividad","Anular Actividad", "Volver"
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(DetalleProyecto.this, "id actividad:" + detalleSeleccionado.getActividad_id(), Toast.LENGTH_SHORT).show();
                switch (which) {

                    //Replanificar
                    case 0:
                        //Hacer if de si está planificada se puede. sino no
                        String estadoActividad = detalleActividad.getEstado().trim();

                        if(((estadoActividad.equals("PLANIFICADA")))) {
                            Intent replanificar = new Intent(DetalleProyecto.this, ReplanificarActividad.class);
                            replanificar.putExtra("DETALLE_SELECCIONADO",detalleSeleccionado);
                            replanificar.putExtra("DATOS_PROYECTO",proyecto);
                            replanificar.putExtra("DATOS_LOTE", lote);
                            startActivity( replanificar);
                        }else{
                            Toast.makeText(DetalleProyecto.this, "Solo se pueden replanificar actividades que no hayan iniciado aún", Toast.LENGTH_SHORT).show();
                        }

                        break;

                        //Ver Recomendacion
                    case 1:
                        if(detalleSeleccionado.getActividad_id()==2){
                            Intent recomendacion = new Intent(DetalleProyecto.this, Siembra.class);
                            recomendacion.putExtra("DETALLE_SELECCIONADO",detalleSeleccionado);
                            recomendacion.putExtra("DATOS_PROYECTO",proyecto);
                            startActivity(recomendacion);
                        }
                        else{
                            if(detalleSeleccionado.getActividad_id()==4){
                                Intent recomendacion2 = new Intent(DetalleProyecto.this, Fertilizacion.class);
                                recomendacion2.putExtra("DETALLE_SELECCIONADO",detalleSeleccionado);
                                recomendacion2.putExtra("DATOS_PROYECTO",proyecto);
                                startActivity(recomendacion2);
                            }else{
                                Toast.makeText(DetalleProyecto.this, "No hay Recomendaciones para esta Actividad", Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;

                    //Iniciar Actividad

                    case 2:
                        String estado1 = detalleSeleccionado.getEstado().trim();

                        if(((estado1.equals("PLANIFICADA")))) {
//                            Toast.makeText(DetalleProyecto.this, "Iniciar Actividad", Toast.LENGTH_SHORT).show();
                            Intent iniciarActividad = new Intent(getApplicationContext(), InicioActividad.class);
                            iniciarActividad.putExtra("DETALLE_SELECCIONADO", detalleSeleccionado);
                            iniciarActividad.putExtra("DATOS_PROYECTO", proyecto);
                            startActivity(iniciarActividad);
                        }

                        if(((estado1.equals("EN EJECUCIÓN")))){
                            Toast.makeText(DetalleProyecto.this, "La Actividad se Encuentra Iniciada", Toast.LENGTH_SHORT).show();
                        }

                        if(((estado1.equals("FINALIZADA")))){
                            Toast.makeText(DetalleProyecto.this, "La Actividad ya Finalizó", Toast.LENGTH_SHORT).show();
                        }

                        break;

                    //Finalizar Actividad

                    case 3:
                        String estado = detalleSeleccionado.getEstado().trim();

                        if((estado.equals("FINALIZADA"))){
                            Toast.makeText(DetalleProyecto.this, "La actividad ya se Finalizó", Toast.LENGTH_SHORT).show();

                        }else{
                            if(estado.equals("EN EJECUCIÓN")){
                                if((detalleSeleccionado.getActividad_id()==2)){
                                    //Entonces se abre el resultado de la siembra
                                    Intent resultado = new Intent(DetalleProyecto.this, ResultadoSiembra.class);
                                    resultado.putExtra("DETALLE_SELECCIONADO",detalleSeleccionado);
                                    resultado.putExtra("DATOS_PROYECTO",proyecto);
                                    startActivity(resultado);
                                }else{
                                    Intent resultadoActividad = new Intent(DetalleProyecto.this, ResultadoActividad.class);
                                    resultadoActividad.putExtra("DETALLE_SELECCIONADO",detalleSeleccionado);
                                    resultadoActividad.putExtra("DATOS_PROYECTO",proyecto);
                                    startActivity(resultadoActividad);
                                }
                            }else{
                                Toast.makeText(DetalleProyecto.this, "Primero debe Iniciar la Actividad", Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;

                        //Eliminar Actividad
                    case 4:
                        eliminarDetalle(detalleSeleccionado.getDetalle_actividad_id(),detalleSeleccionado.getActividad());
//                        dialog.dismiss();
                        break;

                        //Anular Actividad
                    case 5:
                        if((detalleSeleccionado.getEstado())=="FINALIZADA"){
                            Toast.makeText(DetalleProyecto.this, "No es posible anular una actividad Finalizada", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(DetalleProyecto.this, "Actividad Anulada con éxito", Toast.LENGTH_SHORT).show();
                        }
//                        dialog.dismiss();
                        break;

                        //Volver
                    case 6:
                        dialog.dismiss();
                        break;
                }
            }
        });
        builder.create().show();
    }

    void eliminarDetalle(int detalle_actividad,String actividad){
        Toast.makeText(this, "Se Eliminó con éxito la actividad:" + actividad, Toast.LENGTH_SHORT).show();
    }

}
