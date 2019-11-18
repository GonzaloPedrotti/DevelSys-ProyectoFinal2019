package pedrotti.gonzalo.proyecto.Siembra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.JSONArray;
import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;
import pedrotti.gonzalo.proyecto.Catalogo.Catalogo;
import pedrotti.gonzalo.proyecto.Constantes;
import pedrotti.gonzalo.proyecto.ProyectoCultivo.DetalleActividad;
import pedrotti.gonzalo.proyecto.ProyectoCultivo.ProyectoCultivo;
import pedrotti.gonzalo.proyecto.R;
import pedrotti.gonzalo.proyecto.Variedad.Variedad;

public class Siembra extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {

    private AsyncHttpClient cliente;
    private Spinner spVariedadCultivo;
    ArrayList<Variedad> variedadesList;
    private ProyectoCultivo proyecto;
    private DetalleActividad detalleSeleccionado;


    private EditText etCicloDias, etProfundidad, etDensidad, etKgHa;
    private Button btnLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siembra);

        setTitle("Variedades de Cultivo");

        cliente = new AsyncHttpClient();
        spVariedadCultivo = (Spinner)findViewById(R.id.spVariedadesCultivo);
        spVariedadCultivo.setOnItemSelectedListener(this);
        variedadesList = new ArrayList<>();
        btnLink = (Button)findViewById(R.id.btnLink);


        //Se recibe desde DetalleProyecto
        Bundle bundle = getIntent().getExtras();
        proyecto = bundle.getParcelable("DATOS_PROYECTO");


        //Se recibe desde DetalleProyecto
        Bundle bundle2 = getIntent().getExtras();
        detalleSeleccionado = bundle2.getParcelable("DETALLE_SELECCIONADO");

        llenarSpinnerVariedades();

        etCicloDias = (EditText)findViewById(R.id.etCicloDias);
        etProfundidad = (EditText)findViewById(R.id.etProfundidadSiembra);
        etDensidad = (EditText)findViewById(R.id.etDensidadSiembra);
        etKgHa =(EditText)findViewById(R.id.etDosisUtilizada);

        btnLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irAURL();
            }
        });

    }

    private void llenarSpinnerVariedades(){
        String url = "http://"+ Constantes.ip+"/miCampoWeb/mobile/getVariedades.php?cultivo_id="+proyecto.getCultivo_id();

        cliente.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    cargarSpinnerVariedades(new String(responseBody));
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
    private void cargarSpinnerVariedades(String respuesta){
        try{
            JSONArray variedades = new JSONArray(respuesta);
            for (int i=0;i < variedades.length();i++){
                Variedad v = new Variedad();
                v.setCultivo(variedades.getJSONObject(i).getString("Cultivo"));
                v.setVariedad_id(variedades.getJSONObject(i).getInt("idvariedad"));
                v.setVariedad(variedades.getJSONObject(i).getString("variedad"));
                v.setDescripcion(variedades.getJSONObject(i).getString("descripcion"));
                v.setDias(variedades.getJSONObject(i).getInt("dias"));
                v.setProfundidad(variedades.getJSONObject(i).getDouble("profundidad"));
                v.setPeso(variedades.getJSONObject(i).getDouble("peso"));
                v.setDensidad(variedades.getJSONObject(i).getInt("densidad"));
                v.setNiveldezona(variedades.getJSONObject(i).getString("niveldezona"));
                v.setZona(variedades.getJSONObject(i).getString("zona"));
                v.setSiembra(variedades.getJSONObject(i).getString("siembra"));

                variedadesList.add(v);
            }
            ArrayAdapter<Variedad> adapter = new ArrayAdapter<Variedad>(this,android.R.layout.simple_dropdown_item_1line,variedadesList);
            spVariedadCultivo.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public void irAURL(){
        //Lo comentado funciona si lo quisieramos abrir en la web, en este caso, lo abrimos en una
        //nueva actividad.
//        Uri uri = Uri.parse("https://www.donmario.com/catalogo-2/");
//        Intent intent  = new Intent(Intent.ACTION_VIEW,uri);
//        startActivity(intent);

        //Creamos un intent para ir al Catalogo
        Intent catalogo = new Intent(getApplicationContext(), Catalogo.class);
        startActivity(catalogo);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Variedad variedadSeleccionada = variedadesList.get(position);
        etCicloDias.setText(""+variedadSeleccionada.getDias());
        etProfundidad.setText(""+variedadSeleccionada.getProfundidad());
        etDensidad.setText(""+variedadSeleccionada.getDensidad());
        double kg = (variedadSeleccionada.getDensidad() * variedadSeleccionada.getPeso())/1000;
        etKgHa.setText(""+kg);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
