package pedrotti.gonzalo.proyecto.Actividad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pedrotti.gonzalo.proyecto.ProyectoCultivo.DetalleActividad;
import pedrotti.gonzalo.proyecto.R;

public class ItemActividad extends AppCompatActivity {

    private DetalleActividad detalleActividad;
    private EditText etActividad;
    private EditText etNombre;
    private EditText etFechaInicio;
    private EditText etFechaFin;
    private EditText etEstadoActividad;
    private Button btnActualizarActividad;

    private String actividad ;
    private String inicio;
    private String fin ;
    private String estado ;

    private Date fecha_hora_inicial;
    private Date fecha_hora_final;

    public Date getFecha_hora_inicial() {
        return fecha_hora_inicial;
    }

    public void setFecha_hora_inicial(Date fecha_hora_inicial) {
        this.fecha_hora_inicial = fecha_hora_inicial;
    }

    public Date getFecha_hora_final() {
        return fecha_hora_final;
    }

    public void setFecha_hora_final(Date fecha_hora_final) {
        this.fecha_hora_final = fecha_hora_final;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_actividad);

        //Se recibe desde TodosLosProyectos
        Bundle bundle = getIntent().getExtras();
        detalleActividad = bundle.getParcelable("DETALLE_SELECCIONADO");

        actividad = detalleActividad.getActividad();
        inicio= detalleActividad.getInicio();
        fin = detalleActividad.getFin();
        estado = detalleActividad.getEstado();

        etActividad = (EditText)findViewById(R.id.etActividad);
        etFechaInicio = (EditText)findViewById(R.id.etInicioEstimado);
        etFechaFin =(EditText)findViewById(R.id.etFinEstimado);
        etEstadoActividad = (EditText)findViewById(R.id.etEstado);
        btnActualizarActividad = (Button)findViewById(R.id.btnActualizarActividad);

        etActividad.setText(detalleActividad.getActividad());
        etFechaFin.setText(detalleActividad.getFin());
        etFechaInicio.setText(detalleActividad.getInicio());
        etEstadoActividad.setText(detalleActividad.getEstado());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            setFecha_hora_inicial(sdf.parse(inicio));
            setFecha_hora_final(sdf.parse(fin));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        btnActualizarActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarActividad();
            }
        });
    }

    public void actualizarActividad(){

        Toast.makeText(this, "Fecha: " + getFecha_hora_inicial() , Toast.LENGTH_SHORT).show();
//        if(etActividad.getText().toString()==actividad){
//            Toast.makeText(this, "No se Realizaron modificaciones", Toast.LENGTH_SHORT).show();
//
//        }else{
//            Toast.makeText(this, "Se realizacon Modificaciones", Toast.LENGTH_SHORT).show();
//        }

    }
}
