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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import pedrotti.gonzalo.proyecto.Constantes;
import pedrotti.gonzalo.proyecto.Inicio;
import pedrotti.gonzalo.proyecto.R;
import pedrotti.gonzalo.proyecto.Registro.Registro;
import pedrotti.gonzalo.proyecto.Usuario.Usuario;
import pedrotti.gonzalo.proyecto.UsuarioSesion.UsuarioSesion;

public class Sesion extends AppCompatActivity {

    //Atributos de vista
    private EditText etCorreo;
    private EditText etcontrasena;
    private TextView registro;
    private Button btnlogin;

    //Atributos datos
    private String correo;
    private String contrasena;
    private Usuario user;
    private UsuarioSesion userSesion;

    private ProgressDialog progressDialog;

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
        userSesion = new UsuarioSesion();
        progressDialog = new ProgressDialog(Sesion.this);

        //SHAREDPREFERENCE
        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        etCorreo.setText(preferences.getString("correo", ""));
        etcontrasena.setText(preferences.getString("contrasena", ""));
//
////        Si los campos tienen datos, inicia sesion directamente
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

    public boolean validarCredenciales() {
        correo = etCorreo.getText().toString().toLowerCase().trim();
        contrasena = etcontrasena.getText().toString();

        if(!correo.isEmpty() && !contrasena.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public void alertaDatos() {
        Toast.makeText(getApplicationContext(), "Complete su Correo o Contraseña", Toast.LENGTH_SHORT).show();
    }

    public void irAlInicio() {
//        Intent irABienvenido = new Intent(Sesion.this, Bienvenido.class);
//        Intent irABienvenido = new Intent(Sesion.this, HomeActivity.class);
        Intent irABienvenido = new Intent(Sesion.this, Inicio.class);
        irABienvenido.putExtra("DATOS_USER", user);
        startActivity(irABienvenido);
        Sesion.this.finish();
    }


    public void iniciarSesion(){

        if(validarCredenciales()) {

            if(haveNetwork()) {
                Toast.makeText(getApplicationContext(), "Hay Conexión (Conecte Su dispositivo al  Wifi si está depurando)", Toast.LENGTH_SHORT).show();
                btnlogin.setEnabled(false);
                btnlogin.setText("Iniciando...");

                //Mostrar efecto cargando
                progressDialog.setIcon(R.mipmap.ic_launcher);
                progressDialog.setMessage("Cargando...");
                progressDialog.show();

                Response.Listener<String> respuesta = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.d("VALIDANDO DATOS", "VALIDACION");

                            JSONObject jsonRespuesta = new JSONObject(response);

                            boolean estado = jsonRespuesta.getBoolean("estadoSesion");

                            if(estado) { //Los datos son correctos
                                Toast.makeText(Sesion.this, "Accediendo al Perfil...", Toast.LENGTH_SHORT).show();

                                JSONObject usuarioSesion = jsonRespuesta.getJSONObject("usuarioSesion");

                                Log.d(usuarioSesion.toString(), "usuarioSesion");

                                int usu_id = usuarioSesion.getInt("usu_id");
                                String usu_nombre = usuarioSesion.getString("usu_nombre");
                                String usu_apellido = usuarioSesion.getString("usu_apellido");
                                String usu_email = usuarioSesion.getString("usu_email");
                                String ses_token = usuarioSesion.getString("ses_token");
                                String ses_fechaAlta = usuarioSesion.getString("ses_fechaAlta");
                                String ses_expiracion_date = usuarioSesion.getString("ses_expiracion_date");

                                user.setUsuario_id(usu_id);
                                user.setNombre(usu_nombre);
                                user.setApellido(usu_apellido);
                                user.setCorreo(usu_email);

                                userSesion.setSes_token(ses_token);
                                userSesion.setSes_fechaAlta(ses_fechaAlta);
                                userSesion.setSes_expiracion_date(ses_expiracion_date);
                                userSesion.setUsuario(user);

                                Toast.makeText(Sesion.this, "Datos: " + user.getNombre() + ' ' + user.getApellido() + " " + userSesion.getSes_token(), Toast.LENGTH_SHORT).show();

                                //Redirigir Inicio
                                progressDialog.dismiss();
                                guardarCredenciales();
                                irAlInicio();
                            } else { //Los datos son incorrectos
//                                Toast.makeText(Sesion.this, "El correo o la contraseña son incorrectos.", Toast.LENGTH_SHORT).show();

                                AlertDialog.Builder alerta = new AlertDialog.Builder(Sesion.this);
                                alerta.setMessage("Correo o Contraseña incorrectos").setNegativeButton("Reintentar", null).setTitle("Datos Inválidos").setIcon(R.drawable.logo).create().show();
                                etcontrasena.setText("");
                                btnlogin.setEnabled(true);
                                btnlogin.setText("INGRESAR");
                                progressDialog.dismiss();
                            }

                        } catch (Exception e) {

//                            Toast.makeText(Sesion.this, "Ocurrió un error durante la conexión. Intente de nuevo más tarde.", Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder alerta = new AlertDialog.Builder(Sesion.this);
                            alerta.setMessage("Usuario o Contraseña Incorrectos").setNegativeButton("Reintentar", null).setTitle("Datos Inválidos").setIcon(R.drawable.logo).create().show();
                            etcontrasena.setText("");
                            btnlogin.setEnabled(true);
                            btnlogin.setText("INGRESAR");
                            progressDialog.dismiss();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(correo, contrasena, respuesta);
                RequestQueue cola = Volley.newRequestQueue(Sesion.this);
                cola.add(loginRequest);
            } else {
                AlertDialog.Builder alerta = new AlertDialog.Builder(Sesion.this);
                alerta.setTitle("Error")
                        .setMessage("Error al iniciar sesión. Intente de nuevo más tarde")
                        .setPositiveButton("Aceptar", null)
                        .setIcon(R.drawable.logo)
                        .create()
                        .show();
            }

        } else {
            alertaDatos();
        }
        


////                            if (respuesta.equals("true")) {
//
////                                JSONObject usuario = jsonrespuesta.getJSONObject("usuario");
////
////                                int user_id = usuario.getInt("usuario_id");
////                                user.setUsuario_id(user_id);
////
////                                String nombre = usuario.getString("nombre");
////                                user.setNombre(nombre);
////
////                                String apellido = usuario.getString("apellido");
////                                user.setApellido(apellido);
////
////                                String correo = usuario.getString("correo");
////                                user.setCorreo(correo);
//
//                                /*
////                                int usuario_id = jsonrespuesta.getInt("usuario_id");
////                                user.setUsuario_id(usuario_id);
////
////                                String nombre = jsonrespuesta.getString("nombre");
////                                user.setNombre(nombre);
////
////                                String apellido = jsonrespuesta.getString("apellido");
////                                user.setApellido(apellido);
////
////                                String correo = jsonrespuesta.getString("correo");
////                                user.setCorreo(correo);
//
////                                String telefonoString = jsonrespuesta.getString("telefono");
////                                int telefono = Integer.parseInt(telefonoString);
////                                user.setTelefono(telefono);
//
//                                progressDialog.dismiss();
//
//                                //GUARDA LAS CREDENCIALES
//                                guardar();
//
////                                Intent irABienvenido = new Intent(Sesion.this, Bienvenido.class);
//                                Intent irABienvenido = new Intent(Sesion.this, Inicio.class);
////                                Intent irABienvenido = new Intent(Sesion.this, HomeActivity.class);
//
//                                irABienvenido.putExtra("DATOS_USER",user);
//                                startActivity(irABienvenido);
//                                Sesion.this.finish();
//
//
//                                */
//
//                                //sino arroja un mensaje de error.
////                            } else {
////                                //El success es false, por lo que no existe el usuario
////                                AlertDialog.Builder alerta = new AlertDialog.Builder(Sesion.this);
////                                alerta.setMessage("Usuario o Contraseña Incorrectos").setNegativeButton("Reintentar", null).setTitle("Datos Inválidos").setIcon(R.drawable.logo).create().show();
////                                etcontrasena.setText("");
////                                btnlogin.setEnabled(true);
////                                btnlogin.setText("INGRESAR");
////                                progressDialog.dismiss();
////
////                            }
//                        } catch (JSONException e) {
//                            e.getMessage();
//                            AlertDialog.Builder alerta = new AlertDialog.Builder(Sesion.this);
//                            alerta.setMessage("Ups! Algo ha salido mal").setNegativeButton("Reintentar", null).setTitle("Error en la Conexión").setIcon(R.drawable.logo).create().show();
//                            btnlogin.setText("INGRESAR");
//                            progressDialog.dismiss();
//                        }
//                    }
//                };
////                Se añade a la variable RequestQueue el resultado de la consulta
//                SesionRequest r = new SesionRequest(correo,contrasena, respuesta);
//                RequestQueue cola = Volley.newRequestQueue(Sesion.this);
//                cola.add(r);
//
//
//            }
//            else{
////                Intent irABienvenido = new Intent(Sesion.this,Bienvenido.class);
//////                                Intent irABienvenido = new Intent(Sesion.this, Inicio.class);
////
////                irABienvenido.putExtra("DATOS_USER",user);
////                startActivity(irABienvenido);
////                Sesion.this.finish();
////                Toast.makeText(getApplicationContext(), "Ingresando, Sin conexión a Internet", Toast.LENGTH_SHORT).show();
////                btnlogin.setEnabled(true);
////                btnlogin.setText("INGRESAR");
//            }
//        }

    }

    //METODO SHAREDPREFERENCES
    public void guardarCredenciales(){
        SharedPreferences preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor obj_editor = preferencias.edit();

        obj_editor.putInt("usu_id",user.getUsuario_id() );
        obj_editor.putString("usu_nombre", user.getNombre());
        obj_editor.putString("usu_apellido", user.getApellido());
        obj_editor.putString("usu_email", etCorreo.getText().toString());
        obj_editor.putString("usu_telefono", String.valueOf(user.getTelefono()));
        obj_editor.putString("usu_pass", etcontrasena.getText().toString());
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

      private static final String url_api = "http://"+Constantes.ip+"miCampo/miCampoWeb/ControladorVista/Sesion.php";

      private Map<String,String> parametros;

      public SesionRequest (String usu_email, String usu_pass, Response.Listener<String> listener){
          super(Request.Method.POST, url_api, listener, null);
          parametros = new HashMap<>();
          parametros.put("usu_email",  usu_email+"");
          parametros.put("usu_pass",  usu_pass+"");
      }

      @Override
      protected Map<String, String> getParams(){
          return parametros;
      }
  }

}

