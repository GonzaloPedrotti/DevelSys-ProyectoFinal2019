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
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import pedrotti.gonzalo.proyecto.Bienvenido.Bienvenido;
import pedrotti.gonzalo.proyecto.Constantes;
import pedrotti.gonzalo.proyecto.HomeActivity;
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

    private String correo, contrasena;

    //Atributos datos
    private Usuario usuario;
    private UsuarioSesion userSesion;

    private ProgressDialog progressDialog;

    private static final String url_api = Constantes.url + "Sesion.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setTitle(R.string.inicio);

        registro = (TextView)findViewById(R.id.registroLogin);
        btnlogin = (Button)findViewById(R.id.btnLogin);
        etCorreo = (EditText)findViewById(R.id.usuarioLogin);
        etcontrasena= (EditText)findViewById(R.id.claveLogin);

        usuario = new Usuario();
        userSesion = new UsuarioSesion();
        progressDialog = new ProgressDialog(Sesion.this);

        //SHAREDPREFERENCE
        SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        etCorreo.setText(preferences.getString("usu_email", ""));
        etcontrasena.setText(preferences.getString("usu_pass", ""));

//        Si los campos tienen datos, inicia sesion directamente
        if(!(etCorreo.getText().toString().isEmpty()) && !(etcontrasena.getText().toString().isEmpty())){
            iniciarSesion();
        }

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

    public void setEstadoBotones(boolean estado, String texto) {
        btnlogin.setEnabled(estado);
        btnlogin.setText(texto);
    }

    public void iniciarSesion() {

        if(validarCredenciales()) {
            if(haveNetwork()) {

                Toast.makeText(getApplicationContext(), "Hay Conexión (Conecte Su dispositivo al  Wifi si está depurando)", Toast.LENGTH_SHORT).show();

                setEstadoBotones(false, "Iniciando...");

                //Mostrar efecto cargando
                progressDialog.setIcon(R.mipmap.ic_launcher);
                progressDialog.setMessage("Cargando...");
                progressDialog.show();

                correo = etCorreo.getText().toString().trim();
                contrasena = etcontrasena.getText().toString();

                RequestQueue cola = Volley.newRequestQueue(this);

                usuario = new Usuario();
                usuario.setUsu_email(correo);
                usuario.setUsu_pass(contrasena);

//                Gson permite generar un JSON a partir de un objeto
                Gson gson = new Gson();
                final String rep_json = gson.toJson(usuario);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url_api,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //Respuesta
                                try {

                                    JSONObject jsonRespuesta = new JSONObject(response);

                                    boolean estadoSesion = jsonRespuesta.getBoolean("estadoSesion");

                                    //Los datos son correctos
                                    if (estadoSesion) {

                                        JSONObject usuarioSesion = jsonRespuesta.getJSONObject("usuarioSesion");

                                        int usu_id = usuarioSesion.getInt("usu_id");
                                        String usu_nombre = usuarioSesion.getString("usu_nombre");
                                        String usu_apellido = usuarioSesion.getString("usu_apellido");
                                        String usu_email = usuarioSesion.getString("usu_email");
                                        String ses_token = usuarioSesion.getString("ses_token");
                                        String ses_fechaAlta = usuarioSesion.getString("ses_fechaAlta");
                                        String ses_expiracion_date = usuarioSesion.getString("ses_expiracion_date");

                                        usuario.setUsu_id(usu_id);
                                        usuario.setUsu_nombre(usu_nombre);
                                        usuario.setUsu_apellido(usu_apellido);
                                        usuario.setUsu_email(usu_email);

                                        userSesion.setSes_token(ses_token);
                                        userSesion.setSes_fechaAlta(ses_fechaAlta);
                                        userSesion.setSes_expiracion_date(ses_expiracion_date);
                                        userSesion.setUsuario(usuario);

                                        guardarCredenciales();

                                        //Redirigir Inicio
                                        progressDialog.dismiss();

                                        guardarCredenciales();

                                        irAlInicio();

                                    } else { //Los datos son incorrectos

                                        progressDialog.dismiss();

                                        AlertDialog.Builder alerta = new AlertDialog.Builder(Sesion.this);
                                        alerta.setMessage("Correo o Contraseña incorrectos").setNegativeButton("Reintentar", null).setTitle("Datos Inválidos").setIcon(R.drawable.logo).create().show();
                                        etcontrasena.setText("");

                                        setEstadoBotones(true, "Ingresar");

                                    }

                                } catch (Exception e) {

                                    AlertDialog.Builder alerta = new AlertDialog.Builder(Sesion.this);
                                    alerta.setMessage("Ups, Algo ha salido mal. Intente de nuevo más tarde.").setNegativeButton("Reintentar", null).setTitle("Datos Inválidos").setIcon(R.drawable.logo).create().show();
                                    etcontrasena.setText("");
//                                    btnlogin.setEnabled(true);
//                                    btnlogin.setText("INGRESAR");
                                    setEstadoBotones(true, "Ingresar");
                                    progressDialog.dismiss();
                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("ERROR DE CONEXIÓN", "SE PRODUJO UN ERROR AL CONECTAR CON EL SERVIDOR");

                    }
                }){
                    @Override
                    public byte[] getBody() {
                        String jsonString = rep_json ;
                        return jsonString.getBytes();
                    }
                };
                cola.add(stringRequest);
            }
        }
    }

    public void irAlInicio() {
        Intent irABienvenido = new Intent(Sesion.this, Bienvenido.class); //Inicio original con el menu de opciones centrado
//        Intent irABienvenido = new Intent(Sesion.this, HomeActivity.class); //Contiene BottomNavigationView con Activities vacias
//        Intent irABienvenido = new Intent(Sesion.this, Inicio.class); //Contiene BottomNavigationView con Fragments
        irABienvenido.putExtra("DATOS_USER", usuario);
        startActivity(irABienvenido);
        Sesion.this.finish();
    }

    public void guardarCredenciales(){
        SharedPreferences preferencias = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor obj_editor = preferencias.edit();

        obj_editor.putInt("usu_id",usuario.getUsu_id() );
        obj_editor.putString("usu_nombre", usuario.getUsu_nombre());
        obj_editor.putString("usu_apellido", usuario.getUsu_apellido());
        obj_editor.putString("usu_email", etCorreo.getText().toString());
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

}