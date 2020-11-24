package pedrotti.gonzalo.proyecto.Sesion;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import pedrotti.gonzalo.proyecto.Constantes;


public class LoginRequest  extends StringRequest {

    private static final String url_api = Constantes.url + "Sesion.php";

    private Map<String,String> parametros;

    public LoginRequest (String usu_email, String usu_pass, Response.Listener<String> listener){
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
