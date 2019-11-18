package pedrotti.gonzalo.proyecto.Actividad;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import pedrotti.gonzalo.proyecto.Constantes;

 public class ResultadoActividadRequest extends StringRequest {

    private static final String ruta = "http://" + Constantes.ip + "//miCampoWeb/mobile/registrarSiembra.php";

    private Map<String, String> parametros;
    public ResultadoActividadRequest(int detalle_actividad_id, String fecha_fin_real,String descripcion,int variedad_id,double kg_semillas, Response.Listener<String> listener) {
        super(Request.Method.POST, ruta, listener, null);
        parametros = new HashMap<>();
        parametros.put("detalle_actividad_id", detalle_actividad_id + "");
        parametros.put("fecha_fin_real", fecha_fin_real + "");
        parametros.put("descripcion", descripcion + "");
        parametros.put("variedad_id",variedad_id+ "");
        parametros.put("kg_semillas",kg_semillas+"");
    }

    protected Map<String, String> getParams() {
        return parametros;
    }
}
