package pedrotti.gonzalo.proyecto.Lote;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pedrotti.gonzalo.proyecto.Campo.Campo;
import pedrotti.gonzalo.proyecto.Constantes;
import pedrotti.gonzalo.proyecto.NuevoLote.NuevoLote;
import pedrotti.gonzalo.proyecto.R;

public class TodosLosLotes extends AppCompatActivity implements LoteAdapter.OnItemClickListener{

    private static final String url = "http://"+ Constantes.ip+"/miCampoWeb/mobile/obtenerLotesDeCampo.php?campo_id=";


    private int campo_id;
    private double latitud;
    private double longitud;
    RecyclerView recyclerView;
    List<Lote> loteList;
    LoteAdapter adapter;
    private FloatingActionButton btnFlotanteNuevoLote;

    Campo itemSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos_los_lotes);

        setTitle(R.string.listadoDeLotes);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loteList = new ArrayList<>();

        //Se recibe desde  DetalleCampo
        Bundle bundle = getIntent().getExtras();
        itemSeleccionado = bundle.getParcelable("DATOS_CAMPO_SEL");

        loadLotes();

        btnFlotanteNuevoLote = (FloatingActionButton)findViewById(R.id.btnFlotanteNuevoLote);

      btnFlotanteNuevoLote.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent nuevoLote = new Intent(TodosLosLotes.this, NuevoLote.class);

              nuevoLote.putExtra("DATOS_CAMPO_SEL",itemSeleccionado);
              startActivity(nuevoLote);
              //Este finish se puso para que vuelva a DetalleCampo
              finish();
          }
      });
    }

    private void loadLotes() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+ itemSeleccionado.getCampo_id(),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object

                            Campo paramtrosCampo = new Campo();
                            JSONArray array = new JSONArray(response);

                            if(array==null || array.length()==0){
                                AlertDialog.Builder alerta = new AlertDialog.Builder(TodosLosLotes.this);
                                alerta.setMessage("No Tiene Lotes Registrados. Comience registrando uno!").setPositiveButton("Entendido", null).create().show();
                            }

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject lotes = array.getJSONObject(i);

                                int campo_id = lotes.getInt("campo_id");
                                int lote_id = lotes.getInt("lote_id");
                                String nombre = lotes.getString("nombre");
                               double tamano = lotes.getDouble("tamano");
                               double latitud = lotes.getDouble("lat1");
                               double longitud = lotes.getDouble("long1");
                               String estado = lotes.getString("estado");

                                paramtrosCampo.setCampo_id(campo_id);

                                Lote lote = new Lote(campo_id,lote_id,nombre,tamano,latitud,longitud,estado);

                                //adding the product to product list
                                loteList.add(lote);

                            }
                            //creating adapter object and setting it to recyclerview
                            adapter = new LoteAdapter(TodosLosLotes.this, loteList);

                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(TodosLosLotes.this);

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(TodosLosLotes.this,"Error al traer los datos", Toast.LENGTH_SHORT).show();
                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }


    @Override
    public void OnItemClick(int position) {
        Intent detalleLote = new Intent(this, InformacionDelLote.class);
        Lote loteSeleccionado = loteList.get(position);
        detalleLote.putExtra("DATOS_LOTE",loteSeleccionado);
        startActivity(detalleLote);
    }
}
