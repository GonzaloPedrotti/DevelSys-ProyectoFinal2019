package pedrotti.gonzalo.proyecto.Campo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pedrotti.gonzalo.proyecto.Constantes;
import pedrotti.gonzalo.proyecto.Modelo.Campo;
import pedrotti.gonzalo.proyecto.NuevoCampo.NuevoCampo;
import pedrotti.gonzalo.proyecto.R;
import pedrotti.gonzalo.proyecto.Usuario.Usuario;

public class TodosLosCampos extends AppCompatActivity implements CamposAdapter.OnItemClickListener {

    private static final String url = "http://"+Constantes.ip+"/miCampoWeb/ControladorVista/campoCode.php";

//    private static final String url = "http://"+Constantes.ip+"/miCampoWeb/mobile/obtenerCamposDelUsuario.php?usuario_id=";


    private Usuario user;
    private Campo camponuevo;
    RecyclerView recyclerView;
    List<Campo> campoList;
    CamposAdapter adapter;
    private EditText etBuscar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos_los_campos);

        setTitle(R.string.listaCampos);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        campoList = new ArrayList<>();
        etBuscar = (EditText)findViewById(R.id.etBuscar);


        //Se recibe desde el Bienvenido
        Bundle bundle = getIntent().getExtras();
        user = bundle.getParcelable("DATOS_USER");

        loadCampos();

        FloatingActionButton btnFlotanteNuevoCampo = (FloatingActionButton)findViewById(R.id.btnFlotanteNuevoCampo);

        btnFlotanteNuevoCampo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nuevoCampoMapa = new Intent(TodosLosCampos.this,NuevoCampo.class);
                nuevoCampoMapa.putExtra("DATOS_USER",user);
                nuevoCampoMapa.putExtra("campo_id",user.getUsuario_id());
                startActivity(nuevoCampoMapa);
                finish();
            }
        });

        etBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filtrar(etBuscar.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void loadCampos() {
        final ProgressDialog progressDialog = new ProgressDialog(TodosLosCampos.this);
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setMessage("Cargando Campos...");
        progressDialog.show();

        Response.Listener<String> respuesta = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                camponuevo = new Campo();

                try {
                    JSONArray array = new JSONArray(response);

                    if (array == null || array.length() == 0) {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(TodosLosCampos.this);
                        alerta.setMessage("No Tiene Campos Registrados. Comience registrando uno!").setPositiveButton("Entendido", null).create().show();
                    }

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject campos = array.getJSONObject(i);

                        int campo_id = campos.getInt("campo_id");
                        int usuario_id = campos.getInt("usuario_id");
                        String nombre = campos.getString("nombre");
                        double lat = campos.getDouble("lat1");
                        double lon = campos.getDouble("long1");

                        camponuevo.setCampo_id(campo_id);

                        //Se crea el constructor para crear el objeto Campo
                        Campo campo = new Campo(usuario_id, campo_id, nombre, lat, lon);

                        //añadiendo a la lista campoList el objeto Campo recien creado
                        campoList.add(campo);
                    }
                    adapter = new CamposAdapter(TodosLosCampos.this, campoList);
                            recyclerView.setAdapter(adapter);
                            progressDialog.dismiss();
                            adapter.setOnItemClickListener(TodosLosCampos.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        };
        CamposRequest r = new CamposRequest(user.getUsuario_id(),respuesta);
        RequestQueue cola = Volley.newRequestQueue(TodosLosCampos.this);
        cola.add(r);

    }


    class  CamposRequest extends StringRequest {

//        private static  final String ruta = "http://"+ Constantes.ip+"/miCampoWeb/mobile/getMomentoSiembra.php";

        private Map<String,String> parametros;
        public CamposRequest (int usuario_id, Response.Listener<String> listener){
            super(Request.Method.POST, url, listener, null);
            parametros = new HashMap<>();
            parametros.put("usuario_id",  usuario_id+"");
        }

        @Override
        protected Map<String, String> getParams(){
            return parametros;
        }
    }



    //Este metodo muestra en una nueva actividad el campo seleccionado
    @Override
    public void OnItemClick(int position) {
        Intent detalleCampo = new Intent(this, DetalleCampo.class);
        Campo itemSeleccionado = campoList.get(position);
        detalleCampo.putExtra("DATOS_CAMPO_SEL",itemSeleccionado);
        startActivity(detalleCampo);
    }
}
