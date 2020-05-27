package pedrotti.gonzalo.proyecto.Sesion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import pedrotti.gonzalo.proyecto.Bienvenido.Bienvenido;
import pedrotti.gonzalo.proyecto.Constantes;
import pedrotti.gonzalo.proyecto.HomeActivity;
import pedrotti.gonzalo.proyecto.Inicio;
import pedrotti.gonzalo.proyecto.R;
import pedrotti.gonzalo.proyecto.NuevoUsuario.Registro;
import pedrotti.gonzalo.proyecto.Usuario.Usuario;

public class Sesion extends AppCompatActivity {

    private EditText etCorreo, etcontrasena;
    private TextView registro;
    private Button btnlogin;
    private String correo, contrasena;
    private Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setTitle(R.string.inicio);

        registro = (TextView)findViewById(R.id.registroLogin);
        btnlogin = (Button)findViewById(R.id.btnLogin);
        etCorreo = (EditText)findViewById(R.id.usuarioLogin);
        etcontrasena= (EditText)findViewById(R.id.claveLogin);

        user = new Usuario();

        //SHAREDPREFERENCE
        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        etCorreo.setText(preferences.getString("correo", ""));
        etcontrasena.setText(preferences.getString("contrasena", ""));

//        if(!(etCorreo.getText().toString().isEmpty()) && !(etcontrasena.getText().toString().isEmpty())){
//            iniciarSesion();
//        }


        //Codigo del evento click del boton registrar
        registro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent nuevousuario = new Intent(Sesion.this, Registro.class);
                Sesion.this.startActivity(nuevousuario);
            }
        });

        //Código del evento click del boton para iniciar sesión
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarSesion();
            }
        });
    }


    public void iniciarSesion(){

        correo = etCorreo.getText().toString().toLowerCase().trim();
        contrasena = etcontrasena.getText().toString();

//        final Usuario user = new Usuario();


        if (correo.isEmpty()||contrasena.isEmpty()){
            Toast.makeText(getApplicationContext(), "Complete su Correo o Contraseña", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "HOLA", Toast.LENGTH_LONG).show();
        }else{
            //Dentro del método se define la respuesta del LoginRequest

            if(haveNetwork()){
//                Toast.makeText(getApplicationContext(), "Hay Conexión (Conecte Su dispositivo al  Wifi si está depurando)", Toast.LENGTH_SHORT).show();
                btnlogin.setEnabled(false);
                btnlogin.setText("INICIANDO");

                final ProgressDialog progressDialog = new ProgressDialog(Sesion.this);
                progressDialog.setIcon(R.mipmap.ic_launcher);
                progressDialog.setMessage("Cargando...");
                progressDialog.show();

                Response.Listener<String> respuesta = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //se define una respuesta del tipo JSONObject
                            JSONObject jsonrespuesta = new JSONObject(response);

                            //se define una variable booleada llamada ok, la cual toma el valor del resultado de success
                            String respuesta = jsonrespuesta.getString("estado");

                            Log.d(jsonrespuesta.toString(), "RESPUESTA");

                            //si success es verdadero, ejecuta el siguiente codigo
                            if (respuesta.equals("true")) {

//                                JSONObject usuario = jsonrespuesta.getJSONObject("usuario");
//
//                                int user_id = usuario.getInt("usuario_id");
//                                user.setUsuario_id(user_id);
//
//                                String nombre = usuario.getString("nombre");
//                                user.setNombre(nombre);
//
//                                String apellido = usuario.getString("apellido");
//                                user.setApellido(apellido);
//
//                                String correo = usuario.getString("correo");
//                                user.setCorreo(correo);

                                int usuario_id = jsonrespuesta.getInt("usuario_id");
                                user.setUsuario_id(usuario_id);

                                String nombre = jsonrespuesta.getString("nombre");
                                user.setNombre(nombre);

                                String apellido = jsonrespuesta.getString("apellido");
                                user.setApellido(apellido);

                                String correo = jsonrespuesta.getString("correo");
                                user.setCorreo(correo);

//                                String telefonoString = jsonrespuesta.getString("telefono");
//                                int telefono = Integer.parseInt(telefonoString);
//                                user.setTelefono(telefono);

                                progressDialog.dismiss();

                                //GUARDA LAS CREDENCIALES
                                guardar();

//                                Intent irABienvenido = new Intent(Sesion.this, Bienvenido.class);
                                Intent irABienvenido = new Intent(Sesion.this, Inicio.class);
//                                Intent irABienvenido = new Intent(Sesion.this, HomeActivity.class);

                                irABienvenido.putExtra("DATOS_USER",user);
                                startActivity(irABienvenido);
                                Sesion.this.finish();

                                //sino arroja un mensaje de error.
                            } else {
                                //El success es false, por lo que no existe el usuario
                                AlertDialog.Builder alerta = new AlertDialog.Builder(Sesion.this);
                                alerta.setMessage("Usuario o Contraseña Incorrectos").setNegativeButton("Reintentar", null).setTitle("Datos Inválidos").setIcon(R.drawable.logo).create().show();
                                etcontrasena.setText("");
                                btnlogin.setEnabled(true);
                                btnlogin.setText("INGRESAR");
                                progressDialog.dismiss();

                            }
                        } catch (JSONException e) {
                            e.getMessage();
                            AlertDialog.Builder alerta = new AlertDialog.Builder(Sesion.this);
                            alerta.setMessage("Ups! Algo ha salido mal").setNegativeButton("Reintentar", null).setTitle("Error en la Conexión").setIcon(R.drawable.logo).create().show();
                            btnlogin.setText("INGRESAR");
                            progressDialog.dismiss();
                        }
                    }
                };
                //Se añade a la variable RequestQueue el resultado de la consulta
                SesionRequest r = new SesionRequest(correo,contrasena, respuesta);
                RequestQueue cola = Volley.newRequestQueue(Sesion.this);
                cola.add(r);
            }
            else{
                Intent irABienvenido = new Intent(Sesion.this,Bienvenido.class);
//                                Intent irABienvenido = new Intent(Sesion.this, Inicio.class);

                irABienvenido.putExtra("DATOS_USER",user);
                startActivity(irABienvenido);
                Sesion.this.finish();
                Toast.makeText(getApplicationContext(), "Ingresando, Sin conexión a Internet", Toast.LENGTH_SHORT).show();
//                btnlogin.setEnabled(true);
//                btnlogin.setText("INGRESAR");
            }
        }

    }

    //METODO SHAREDPREFERENCES
    public void guardar(){
        SharedPreferences preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor obj_editor = preferencias.edit();

        obj_editor.putInt("usuario_id",user.getUsuario_id() );
        obj_editor.putString("nombre", user.getNombre());
        obj_editor.putString("apellido", user.getApellido());
        obj_editor.putString("correo", etCorreo.getText().toString());
        obj_editor.putString("telefono", String.valueOf(user.getTelefono()));
        obj_editor.putString("contrasena", etcontrasena.getText().toString());
        obj_editor.commit();
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


  class SesionRequest extends StringRequest{

      private static  final String ruta = "http://"+ Constantes.ip+"/miCampoWeb/vista/validarCode.php";

      private Map<String,String> parametros;
      public SesionRequest (String correo, String contrasena, Response.Listener<String> listener){
          super(Request.Method.POST, ruta, listener, null);
          parametros = new HashMap<>();
          parametros.put("correo",  correo+"");
          parametros.put("contrasena",  contrasena+"");
      }

      @Override
      protected Map<String, String> getParams(){
          return parametros;
      }
  }

}

