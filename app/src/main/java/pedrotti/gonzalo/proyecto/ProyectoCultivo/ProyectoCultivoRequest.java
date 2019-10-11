package pedrotti.gonzalo.proyecto.ProyectoCultivo;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import pedrotti.gonzalo.proyecto.Constantes;

public class ProyectoCultivoRequest extends StringRequest {

    private static  final String ruta = "http://"+ Constantes.ip+"//miCampoWeb/mobile/nuevoProyecto.php";

    private Map<String,String> parametros;
    public ProyectoCultivoRequest(String nombre, int cultivo_id, int lote_id, Response.Listener<String> listener){
        super(Request.Method.POST, ruta, listener, null);
        parametros = new HashMap<>();
        parametros.put("nombre",  nombre+"");
        parametros.put("cultivo_id",  cultivo_id+"");
        parametros.put("lote_id", lote_id+"");
    }

    protected Map<String, String> getParams(){
        return parametros;
    }



}
