package pedrotti.gonzalo.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import pedrotti.gonzalo.proyecto.ProyectoCultivo.DetalleActividad;
import pedrotti.gonzalo.proyecto.ProyectoCultivo.ProyectoCultivo;

public class MenuActividad extends AppCompatActivity {

    private DetalleActividad detalleSeleccionado;
    private ProyectoCultivo proyectoSeleccionado;

    private TextView tvActividad, tvInicio, tvFin, tvEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_actividad);

        //Se recibe desde DetalleActividad
        Bundle bundle = getIntent().getExtras();
        detalleSeleccionado = bundle.getParcelable("DETALLE_SELECCIONADO");

        //Se recibe desde DetalleActividad
        Bundle bundle2 = getIntent().getExtras();
        proyectoSeleccionado = bundle2.getParcelable("DATOS_PROYECTO");

        setTitle(detalleSeleccionado.getActividad());

        tvActividad = (TextView)findViewById(R.id.tvActividadMenu);
        tvInicio = (TextView)findViewById(R.id.tvInicioMenu);
        tvFin = (TextView)findViewById(R.id.tvFinMenu);
        tvEstado= (TextView)findViewById(R.id.tvEstadoMenu);

        tvActividad.setText(detalleSeleccionado.getActividad());
        tvInicio.setText(detalleSeleccionado.getInicio());
        tvFin.setText(detalleSeleccionado.getFin());
        tvEstado.setText(detalleSeleccionado.getEstado());
    }
}
