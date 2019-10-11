package pedrotti.gonzalo.proyecto.NuevoLote;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import pedrotti.gonzalo.proyecto.Constantes;

class NuevoLoteRequest extends StringRequest {

    private static  final String ruta = "http://"+ Constantes.ip+"/miCampoWeb/mobile/registrarLote.php";

    private Map<String,String> parametros2;


    public NuevoLoteRequest(int campo_id,String nombre,double tamano,double lat1,double lat2,double lat3,double lat4,double long1,double long2,double long3,double long4,Response.Listener<String> listener){
        super(Request.Method.POST, ruta, listener, null);
        parametros2 = new HashMap<>();

        parametros2.put("campo_id",campo_id+"");
        parametros2.put("nombre",  nombre+"");
        parametros2.put("tamano",  tamano+"");
        parametros2.put("lat1",  lat1+"");
        parametros2.put("lat2",  lat2+"");
        parametros2.put("lat3",  lat3+"");
        parametros2.put("lat4",  lat4+"");
        parametros2.put("long1",  long1+"");
        parametros2.put("long2",  long2+"");
        parametros2.put("long3",  long3+"");
        parametros2.put("long4",  long4+"");

    }

    protected Map<String, String> getParams(){
        return parametros2;

    }
}
