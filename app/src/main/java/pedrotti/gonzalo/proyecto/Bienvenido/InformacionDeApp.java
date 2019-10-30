package pedrotti.gonzalo.proyecto.Bienvenido;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import pedrotti.gonzalo.proyecto.R;
import pedrotti.gonzalo.proyecto.Usuario.Usuario;

public class InformacionDeApp extends AppCompatActivity {

    Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_de_app);


//        Se recibe en el Bienvenido lo enviado desde el Login se comenta para probar el registro de un campo
        Bundle bundle = getIntent().getExtras();
        user = bundle.getParcelable("DATOS_USER");


        Toast.makeText(this, "BIENVENIDO A LA APLICACIÓN MICAMPO MOBILE", Toast.LENGTH_SHORT).show();
    }
}
