package pedrotti.gonzalo.proyecto.Login;

import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;

import pedrotti.gonzalo.proyecto.Bienvenido.Bienvenido;
import pedrotti.gonzalo.proyecto.R;
import pedrotti.gonzalo.proyecto.NuevoUsuario.Registro;
import pedrotti.gonzalo.proyecto.Usuario.Usuario;

public class Login extends AppCompatActivity {

    private EditText etCorreo, etcontrasena;
    private Usuario usuarioRegistrado;
    private TextView registro;
    private Button btnlogin;
    private String correo, contrasena;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setTitle(R.string.inicio);

        registro = (TextView)findViewById(R.id.registroLogin);
        btnlogin = (Button)findViewById(R.id.btnLogin);
        etCorreo = (EditText)findViewById(R.id.usuarioLogin);
        etcontrasena= (EditText)findViewById(R.id.claveLogin);

        //Codigo del evento click del boton registrar
        registro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent nuevousuario = new Intent(Login.this, Registro.class);
                Login.this.startActivity(nuevousuario);
            }
        });


        //Código del evento click del boton para iniciar sesión
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correo = etCorreo.getText().toString().toLowerCase().trim();
                contrasena = etcontrasena.getText().toString();

                if (correo.isEmpty()||contrasena.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Complete su Correo o Contraseña", Toast.LENGTH_SHORT).show();
                }else{
                    //Dentro del método se define la respuesta del LoginRequest

                    if(haveNetwork()){
                        Toast.makeText(getApplicationContext(), "Hay Conexión (Conecte Su dispositivo al  Wifi si está depurando)", Toast.LENGTH_SHORT).show();
                        btnlogin.setEnabled(false);
                        btnlogin.setText("INICIANDO");

                        Response.Listener<String> respuesta = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Usuario user = new Usuario();
                                try {
                                    //se define una respuesta del tipo JSONObject
                                    JSONObject jsonrespuesta = new JSONObject(response);

                                    //se define una variable booleada llamada ok, la cual toma el valor del resultado de success
                                    boolean ok = jsonrespuesta.getBoolean("success");
                                    //si success es verdadero, ejecuta el siguiente codigo
                                    if (ok == true) {

                                        int usuario_id = jsonrespuesta.getInt("usuario_id");
                                        user.setUsuario_id(usuario_id);

                                        String nombre = jsonrespuesta.getString("nombre");
                                        user.setNombre(nombre);

                                        String apellido = jsonrespuesta.getString("apellido");
                                        user.setApellido(apellido);

                                        String correo = jsonrespuesta.getString("correo");
                                        user.setCorreo(correo);

                                        String telefonoString = jsonrespuesta.getString("telefono");
                                        int telefono = Integer.parseInt(telefonoString);
                                        user.setTelefono(telefono);

                                        Intent irABienvenido = new Intent(Login.this,Bienvenido.class);
                                        irABienvenido.putExtra("DATOS_USER",user);
                                        startActivity(irABienvenido);
                                        Login.this.finish();

                                        //sino arroja un mensaje de error.
                                    } else {
                                        AlertDialog.Builder alerta = new AlertDialog.Builder(Login.this);
                                        alerta.setMessage("Usuario o Contraseña Incorrectos").setNegativeButton("Reintentar", null).setTitle("Datos Inválidos").setIcon(R.drawable.logo).create().show();
                                        etcontrasena.setText("");
                                        btnlogin.setEnabled(true);
                                        btnlogin.setText("INGRESAR");
                                    }
                                } catch (JSONException e) {
                                    e.getMessage();
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(Login.this);
                                    alerta.setMessage("Ups! Algo ha salido mal").setNegativeButton("Reintentar", null).setTitle("Error en la Conexión").setIcon(R.drawable.logo).create().show();
                                }
                            }
                        };
                        //Se añade a la variable RequestQueue el resultado de la consulta
                        LoginRequest r = new LoginRequest(correo,contrasena, respuesta);
                        RequestQueue cola = Volley.newRequestQueue(Login.this);
                        cola.add(r);
                    }
                   else{
                        Toast.makeText(getApplicationContext(), "Por favor, verifique su conexion a Internet", Toast.LENGTH_SHORT).show();
                        btnlogin.setEnabled(true);
                        btnlogin.setText("INGRESAR");
                    }
                }
            }
        });
    }


    public  boolean haveNetwork(){
        boolean have_WIFI = false;
        boolean have_MobileData = false;

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[]networkInfos=connectivityManager.getAllNetworkInfo();

        for(NetworkInfo info:networkInfos){

            if(info.getTypeName().equalsIgnoreCase("WIFI"))
                if(info.isConnected())
                    have_WIFI=true;
            if(info.getTypeName().equalsIgnoreCase("MOBILE"))
                if(info.isConnected())
                    have_MobileData=true;
        }
        return have_MobileData || have_WIFI;

    }

}

