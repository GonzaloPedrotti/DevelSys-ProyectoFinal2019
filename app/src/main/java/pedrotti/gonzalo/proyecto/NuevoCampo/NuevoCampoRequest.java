package pedrotti.gonzalo.proyecto.NuevoCampo;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import pedrotti.gonzalo.proyecto.Constantes;

public class NuevoCampoRequest extends StringRequest {

//    private static  final String ruta = "http://192.168.0.35:/v1/registrar.php";
  private static  final String ruta = "http://"+Constantes.ip+"/miCampoWeb/mobile/registrarCampo.php";

    private Map<String,String> parametros2;

    public NuevoCampoRequest(int usuario_id,  String nombre , double lat1,double long1,Response.Listener<String> listener){
        super(Request.Method.POST, ruta, listener, null);
        parametros2 = new HashMap<>();
        parametros2.put("usuario_id",  usuario_id+"");
        parametros2.put("nombre",  nombre+"");
        parametros2.put("lat1",  lat1+"");
        parametros2.put("long1",  long1+"");
    }
    protected Map<String, String> getParams(){
       return parametros2;
    }

}
