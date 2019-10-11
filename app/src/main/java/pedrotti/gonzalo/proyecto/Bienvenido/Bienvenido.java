package pedrotti.gonzalo.proyecto.Bienvenido;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import pedrotti.gonzalo.proyecto.Campo.TodosLosCampos;
import pedrotti.gonzalo.proyecto.InformacionDeApp;
import pedrotti.gonzalo.proyecto.Lotes.InformacionDelLote;
import pedrotti.gonzalo.proyecto.R;
import pedrotti.gonzalo.proyecto.TiposMapas;
import pedrotti.gonzalo.proyecto.Usuario.Usuario;

public class Bienvenido extends AppCompatActivity {

private Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenido);

        //Titulo de la Activity
        this.setTitle(R.string.micuenta);


        //Declaración de Botones
        Button btnVerCampos = (Button)findViewById(R.id.btnVerCampos);
        Button btnTiposMapa =(Button)findViewById(R.id.btnTipos);
        Button btnSitios =(Button)findViewById(R.id.btnSitios);
        Button btnVerInfo = (Button)findViewById(R.id.btnInformacionApp);


//        Se recibe en el Bienvenido lo enviado desde el Login se comenta para probar el registro de un campo
        Bundle bundle = getIntent().getExtras();
        user = bundle.getParcelable("DATOS_USER");


        //Metodo para abrir el mapa con los campos registrados(Clase Mapa)
        btnVerCampos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Se reenvian a la pantalla de TodosLosCampos
                Intent verCampos = new Intent(getApplicationContext(), TodosLosCampos.class);
                //Se comenta para el registro de un campo
                verCampos.putExtra("DATOS_USER",user);
                verCampos.putExtra("usuario_id",user.getUsuario_id());
                startActivity(verCampos);
            }
        });

        btnSitios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(),Mapa.class);
////                Bienvenido.this.finish();
//                startActivity(intent);
            }
        });


        btnVerInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent verClima = new Intent(getApplicationContext(), InformacionDeApp.class);
                startActivity(verClima);
            }
        });
    }

    public void mapaTipos (View view){
        Intent intent = new Intent(getApplicationContext(), TiposMapas.class);
        startActivity(intent);
    }

}
