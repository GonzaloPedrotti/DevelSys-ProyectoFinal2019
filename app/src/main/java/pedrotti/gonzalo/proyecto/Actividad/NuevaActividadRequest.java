package pedrotti.gonzalo.proyecto.Actividad;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import pedrotti.gonzalo.proyecto.Constantes;

public class NuevaActividadRequest extends StringRequest {
    private static final String ruta = "http://" + Constantes.ip + "//miCampoWeb/mobile/nuevoDetalleActividad.php";


    private Map<String, String> parametros;

    public NuevaActividadRequest(int proyecto_cultivo_id, int actividad_id, String fecha_hora_inicio,String fecha_hora_fin, Response.Listener<String> listener) {
        super(Request.Method.POST, ruta, listener, null);
        parametros = new HashMap<>();
        parametros.put("proyecto_cultivo_id", proyecto_cultivo_id + "");
        parametros.put("actividad_id", actividad_id + "");
        parametros.put("fecha_inicio", fecha_hora_inicio + "");
        parametros.put("fecha_fin", fecha_hora_fin + "");
     }

    protected Map<String, String> getParams() {
        return parametros;
    }
}



