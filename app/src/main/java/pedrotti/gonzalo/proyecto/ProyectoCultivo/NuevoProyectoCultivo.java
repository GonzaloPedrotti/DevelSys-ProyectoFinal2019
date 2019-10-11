package pedrotti.gonzalo.proyecto.ProyectoCultivo;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;
import pedrotti.gonzalo.proyecto.Constantes;
import pedrotti.gonzalo.proyecto.Cultivo.Cultivo;
import pedrotti.gonzalo.proyecto.R;



public class NuevoProyectoCultivo extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private AsyncHttpClient cliente;
    private Spinner  spCultivo;

    private TextView tvNombre, tvPeriodo, tvPeriodo1;
    ArrayList<Cultivo> cultivoList;

    private Button btnRegistrarProyecto;
    private int cultivo_id;
    private int lote_id;
    private String nombre;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_proyecto_cultivo);

        setTitle(R.string.nuevoProyecto);
        cliente  = new AsyncHttpClient();
        tvNombre = (TextView)findViewById(R.id.tvNombreProyecto);
        spCultivo=(Spinner)findViewById(R.id.spCultivo);
        tvPeriodo1 = (TextView)findViewById(R.id.tvPeriodo1);
        btnRegistrarProyecto = (Button)findViewById(R.id.btnRegistrarProyectoCultivo);
        spCultivo.setOnItemSelectedListener(this);


        Intent intent = getIntent();
        lote_id = intent.getIntExtra("lote_id",0);

        cultivoList = new ArrayList<>();

        llenarSpinner();

        btnRegistrarProyecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nombre=tvNombre.getText().toString();
                int loteSel = lote_id;
                int cultivoSel = getCultivoId();


                if (nombre.isEmpty() ) {
                    Toast.makeText(getApplicationContext(), "Coloque un Nombre a su Proyecto", Toast.LENGTH_LONG).show();
                }else {
                            Response.Listener<String> respuesta = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonRespuesta = new JSONObject(response);
                                        boolean ok = jsonRespuesta.getBoolean("success");
//                                        boolean ok2 = jsonRespuesta.getBoolean("cuenta");
                                        if (ok == true) {

                                            Toast.makeText(getApplicationContext(), "Proyecto Registrado. Ya puedes Comenzar a Trabajar!", Toast.LENGTH_LONG).show();
                                            NuevoProyectoCultivo.this.finish();
                                        } else {

                                                AlertDialog.Builder alerta = new AlertDialog.Builder(NuevoProyectoCultivo.this);
                                                alerta.setMessage("Ya existe un Proyecto para este Lote").setNegativeButton("Entendido", null).setTitle("Información de Registro").setIcon(R.drawable.logo).create().show();
                                        }
                                    } catch (JSONException e) {
                                        e.getMessage();
                                        Toast.makeText(getApplicationContext(), "Seleccione un Cultivo de la Lista", Toast.LENGTH_LONG).show();
                                    }
                                }
                            };
                          ProyectoCultivoRequest r = new ProyectoCultivoRequest(nombre, cultivoSel, lote_id, respuesta);
                            RequestQueue cola = Volley.newRequestQueue(NuevoProyectoCultivo.this);
                            cola.add(r);
                        }//else del Response

            }
        });
    }


    private void llenarSpinner(){
        String url ="http://"+ Constantes.ip+"/miCampoWeb/mobile/getCultivo.php";

        cliente.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    cargarSpinner(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
    }

    private void cargarSpinner(String respuesta){
        try{
            JSONArray cultivos = new JSONArray(respuesta);
            for (int i=0;i < cultivos.length();i++){
                Cultivo c = new Cultivo();
                c.setCultivo_id(cultivos.getJSONObject(i).getInt("id"));
                c.setNombre(cultivos.getJSONObject(i).getString("nombre"));
                c.setPeriodo(cultivos.getJSONObject(i).getString("periodo"));
                c.setMesInicio(cultivos.getJSONObject(i).getString("mesinicio"));
                c.setMesFin(cultivos.getJSONObject(i).getString("mesfin"));



                cultivoList.add(c);
            }

            ArrayAdapter <Cultivo> adapter = new ArrayAdapter<Cultivo>(this,android.R.layout.simple_dropdown_item_1line,cultivoList);
            spCultivo.setAdapter(adapter);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setCultivoId (int id){
        this.cultivo_id = id;
    }

    public int getCultivoId (){
        return this.cultivo_id;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Cultivo itemSeleccionado = cultivoList.get(position);
        String item = parent.getItemAtPosition(position).toString();

//        Toast.makeText(this, "Periodo: " + itemSeleccionado.getNombre() + ", Periodo: " + itemSeleccionado.getPeriodo() + "Inicio: " +  itemSeleccionado.getMesInicio(), Toast.LENGTH_SHORT).show();
        tvPeriodo1.setText(itemSeleccionado.getPeriodo());
        setCultivoId(itemSeleccionado.getCultivo_id());
//        Toast.makeText(this, "Cultivo id " + getCultivoId(), Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
