package pedrotti.gonzalo.proyecto.NuevoUsuario;

import android.content.Intent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import pedrotti.gonzalo.proyecto.Sesion.Sesion;
import pedrotti.gonzalo.proyecto.R;

public class Registro extends AppCompatActivity {

    private String nombreUsuario;
    private String apellidoUsuario;
    private String correoUsuario;
    private int telefonoUsuario;
    private String telString;
    private String telefono;
    private String contrasenaUsuario;
    private String confirmarContrasenaUsuario;

    private EditText tvNombreUsuario, tvApellidoUsuario,tvCorreoUsuario, tvTelefonoUsuario, tvContrasenaUsuario,tvConfirmarContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);

        this.setTitle(R.string.crearcuenta);

        tvNombreUsuario= (EditText)findViewById(R.id.tvNombreUsuario);
        tvApellidoUsuario= (EditText)findViewById(R.id.tvApellidoUsuario);
        tvCorreoUsuario= (EditText)findViewById(R.id.tvCorreoUsuario);
        tvTelefonoUsuario= (EditText)findViewById(R.id.tvTelefono);
        tvContrasenaUsuario= (EditText)findViewById(R.id.tvContrasenaUsuario);
        tvConfirmarContrasena = (EditText)findViewById(R.id.tvConfirmarContrasena);

        Button btnRegistro = (Button)findViewById(R.id.btnRegistrar);
        Button btnVolver = (findViewById(R.id.btnVolver));

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volver();
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                nombreUsuario = tvNombreUsuario.getText().toString().trim();
                apellidoUsuario = tvApellidoUsuario.getText().toString().trim();
                correoUsuario = tvCorreoUsuario.getText().toString().trim();
//              telefonoUsuario = tvTelefonoUsuario.getText().toString().trim();
                telString =(tvTelefonoUsuario.getText().toString()).trim();
                contrasenaUsuario = tvContrasenaUsuario.getText().toString().trim();
                confirmarContrasenaUsuario = tvConfirmarContrasena.getText().toString().trim();

//                if ((telString==null) || (telString.equals(""))) {
//                    telefonoUsuario=0;
//                }else{
//                    telefonoUsuario = Integer.parseInt(telString);
//                }

                if (nombreUsuario.isEmpty() || apellidoUsuario.isEmpty() || correoUsuario.isEmpty()  || telString.isEmpty() ||contrasenaUsuario.isEmpty() || confirmarContrasenaUsuario.isEmpty() ) {
                    Toast.makeText(getApplicationContext(), "Complete Todos los Campos.", Toast.LENGTH_LONG).show();
                }
                //inicio else
                else {
                    if(contrasenaUsuario.length()<8 || confirmarContrasenaUsuario.length()<8){
                        Toast.makeText(Registro.this, "La contraseña debe contener 8 o más caracteres", Toast.LENGTH_SHORT).show();

                    }else {


                        //inicio if verificación contraseñas (La expresión puede ser igualada a ==false. Es lo mismo.
                        if (!(contrasenaUsuario.equals(confirmarContrasenaUsuario))) {
                            Toast.makeText(Registro.this, "Las Contraseñas no Coinciden", Toast.LENGTH_SHORT).show();
                        } else {
                            Response.Listener<String> respuesta = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonRespuesta = new JSONObject(response);
                                        boolean ok = jsonRespuesta.getBoolean("success");
                                        if (ok == true) {

                                            Intent i = new Intent(Registro.this, Sesion.class);

                                            Toast.makeText(getApplicationContext(), "Registrado con Éxito. Ahora inicia Sesión", Toast.LENGTH_LONG).show();
                                            Registro.this.startActivity(i);
                                            Registro.this.finish();
                                        } else {
                                            AlertDialog.Builder alerta = new AlertDialog.Builder(Registro.this);
                                            alerta.setMessage("El correo " + correoUsuario + " ya se encuentra en Uso. Pruebe con otro").setNegativeButton("Entendido", null).setTitle("Información de Registro").setIcon(R.drawable.logo).create().show();
                                            tvCorreoUsuario.setText("");
                                        }
                                    } catch (JSONException e) {
                                        e.getMessage();
                                        Toast.makeText(getApplicationContext(), "Error al intentar guardar", Toast.LENGTH_LONG).show();
                                    }
                                }
                            };
                            RegistroRequest r = new RegistroRequest(nombreUsuario, apellidoUsuario, correoUsuario, telString, contrasenaUsuario, respuesta);
                            RequestQueue cola = Volley.newRequestQueue(Registro.this);
                            cola.add(r);
                        }//else del Response
                    }

                    //fin del else
                }


            }
        });
    }


    public void volver(){
            Intent volver = new Intent(getApplicationContext(), Sesion.class);
            startActivity(volver);
    }

}
