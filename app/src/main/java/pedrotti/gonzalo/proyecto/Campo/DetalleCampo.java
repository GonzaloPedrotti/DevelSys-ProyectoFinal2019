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


public class DetalleCampo extends AppCompatActivity {


    private Usuario user;
    private Campo itemSeleccionado;
    private int campo_id;
    private double lat;
    private double lon;
    private Button btnVerLotes;
    private Button btnVerUbicacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_campo);

        setTitle(R.string.detallecampo);

        //Declaración de Botones
        btnVerLotes =(Button)findViewById(R.id.btnVerLotes);
        btnVerUbicacion = (Button)findViewById(R.id.btnVerUbicacion);

        //DetalleCampo recibe lo enviado desde TodosLosCampos
        Bundle bundle = getIntent().getExtras();
        itemSeleccionado = bundle.getParcelable("DATOS_CAMPO_SEL");

        TextView nombrecampo = findViewById(R.id.tvNombreCampo);
        TextView latcampo = findViewById(R.id.txtLatitud);
        TextView longcampo = findViewById(R.id.tvLongitud);

        nombrecampo.setText("Nombre del Campo: " + itemSeleccionado.getNombre());
        latcampo.setText("Latitud del Campo: " + itemSeleccionado.getLat());
        longcampo.setText("Longitud del Campo: " + itemSeleccionado.getLon());
        
        btnVerLotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verLotesRegistrados();
            }
        });

        btnVerUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent verUbicacion = new Intent(getApplicationContext(), UbicacionCampo.class);
                verUbicacion.putExtra("DATOS_CAMPO_SEL",itemSeleccionado);
                startActivity(verUbicacion);
            }
        });
    }

    public void verLotesRegistrados(){
        Intent verLotes = new Intent(getApplicationContext(), TodosLosLotes.class);

        verLotes.putExtra("DATOS_CAMPO_SEL",itemSeleccionado);
        startActivity(verLotes);
    }

}
