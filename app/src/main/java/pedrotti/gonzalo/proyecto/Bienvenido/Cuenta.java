package pedrotti.gonzalo.proyecto.Bienvenido;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import pedrotti.gonzalo.proyecto.Sesion.Sesion;
import pedrotti.gonzalo.proyecto.R;
import pedrotti.gonzalo.proyecto.Usuario.Usuario;

public class Cuenta extends AppCompatActivity {

    Usuario user;
    private EditText etNombre, etApellido, etCorreo, etTelefono;
    private Button btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta);

        setTitle(R.string.misdatos);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        Se recibe en el Bienvenido lo enviado desde el Sesion se comenta para probar el registro de un campo
        Bundle bundle = getIntent().getExtras();
        user = bundle.getParcelable("DATOS_USER");

        etNombre= (EditText)findViewById(R.id.etNombre);
        etApellido= (EditText)findViewById(R.id.etApellido);
        etCorreo= (EditText)findViewById(R.id.etCorreo);
        etTelefono= (EditText)findViewById(R.id.etTelefono);
        btnCerrarSesion= (Button)findViewById(R.id.btnCerrarSesion);

        etNombre.setText(user.getNombre());
        etApellido.setText(user.getApellido());
        etCorreo.setText(user.getCorreo());
        etTelefono.setText(""+user.getTelefono());

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesion();
//                AlertDialog.Builder cerrarSesion = new AlertDialog.Builder(getApplicationContext());
//                cerrarSesion.setTitle("Cerrar Sesión").setMessage("¿Desea Cerrar Sesión?").setPositiveButton("Sí", null).create().show();

            }
        });
    }

    public void cerrarSesion(){
        Intent cerrar = new Intent(getApplicationContext(), Sesion.class);
        startActivity(cerrar);
        finish();
    }
}
