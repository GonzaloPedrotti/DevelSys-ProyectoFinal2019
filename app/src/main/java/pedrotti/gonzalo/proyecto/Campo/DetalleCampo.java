package pedrotti.gonzalo.proyecto.Campo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import pedrotti.gonzalo.proyecto.Lote.TodosLosLotes;
import pedrotti.gonzalo.proyecto.R;
import pedrotti.gonzalo.proyecto.Usuario.Usuario;

import static pedrotti.gonzalo.proyecto.Campo.TodosLosCampos.EXTRA_ID;
import static pedrotti.gonzalo.proyecto.Campo.TodosLosCampos.EXTRA_LAT;
import static pedrotti.gonzalo.proyecto.Campo.TodosLosCampos.EXTRA_LONG;
import static pedrotti.gonzalo.proyecto.Campo.TodosLosCampos.EXTRA_NOMBRE;
import static pedrotti.gonzalo.proyecto.Campo.TodosLosCampos.EXTRA_USUARIO_ID;

public class DetalleCampo extends AppCompatActivity {


    private Usuario user;
    private Campo itemSeleccionado;
    private int campo_id;
    private double lat;
    private double lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_campo);

        setTitle(R.string.detallecampo);

        //Declaración de Botones
        Button btnverLotes =(Button)findViewById(R.id.btnVerLotes);
        Button verUbicacion = (Button)findViewById(R.id.btnVerUbicacion);

        //DetalleCampo recibe lo enviado desde TodosLosCampos
        Bundle bundle = getIntent().getExtras();
        itemSeleccionado = bundle.getParcelable("DATOS_CAMPO_SEL");

        Intent intent = getIntent();
        int usuario_id = intent.getIntExtra(EXTRA_USUARIO_ID,0);
        campo_id = intent.getIntExtra(EXTRA_ID,0);
        String nombre = intent.getStringExtra(EXTRA_NOMBRE);
         lat = intent.getDoubleExtra(EXTRA_LAT,0);
         lon = intent.getDoubleExtra(EXTRA_LONG,0);

        TextView nombrecampo = findViewById(R.id.tvNombreCampo);
        TextView latcampo = findViewById(R.id.txtLatitud);
        TextView longcampo = findViewById(R.id.tvLongitud);

        nombrecampo.setText("Nombre del Campo: " + nombre);
        latcampo.setText("Latitud del Campo: " + lat);
        longcampo.setText("Longitud del Campo: " + lon);
        
        btnverLotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verLotesRegistrados();
            }
        });
    }

    public void verLotesRegistrados(){
        Intent verLotes = new Intent(getApplicationContext(), TodosLosLotes.class);


        //Agregado 29/09
        verLotes.putExtra("DATOS_CAMPO_SEL",itemSeleccionado);
        verLotes.putExtra("campo_id_sel",itemSeleccionado.getCampo_id());

        verLotes.putExtra("campo_id",campo_id);
        verLotes.putExtra("campo_latitud", lat);
        verLotes.putExtra("campo_longitud",lon);
        startActivity(verLotes);
    }

//    public void verUbicacionDelLote(){
//        Intent verUbicacion = new Intent(getApplicationContext(), UbicacionLote.class);
//        startActivity(verUbicacion);
//    }

}
