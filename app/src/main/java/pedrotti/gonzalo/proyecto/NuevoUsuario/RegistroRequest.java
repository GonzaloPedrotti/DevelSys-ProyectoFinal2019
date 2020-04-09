package pedrotti.gonzalo.proyecto.NuevoUsuario;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import pedrotti.gonzalo.proyecto.Constantes;

public class RegistroRequest extends StringRequest {

//    private static  final String ruta = "http://"+ Constantes.ip+"//miCampoWeb/vista/registroCode.php";
    private static  final String ruta = "http://"+ Constantes.ip+"//miCampoWeb/mobile/registrar.php";

//  private static  final String ruta = "https://192.168.0.13:/v1/registrar.php";


    private Map<String,String> parametros;
    public RegistroRequest(String nombreUsuario, String apellidoUsuario, String correoUsuario, String telefonoUsuario, String contrasenaUsuario, Response.Listener<String> listener){
        super(Request.Method.POST, ruta, listener, null);
        parametros = new HashMap<>();
        parametros.put("nombre",  nombreUsuario+"");
        parametros.put("apellido",  apellidoUsuario+"");
        parametros.put("telefono", telefonoUsuario+"");
        parametros.put("correo",  correoUsuario+"");
        parametros.put("contrasena",  contrasenaUsuario+"");
    }

    protected Map<String, String> getParams(){
        return parametros;
    }

}
