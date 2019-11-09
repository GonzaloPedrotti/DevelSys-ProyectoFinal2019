package pedrotti.gonzalo.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import pedrotti.gonzalo.proyecto.ProyectoCultivo.DetalleActividad;
import pedrotti.gonzalo.proyecto.ProyectoCultivo.ProyectoCultivo;

public class ResultadoSiembra extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnf1,btnf2,btnh1,btnh2;
    private EditText etFecha1Real, etFecha2Real, etHora1Real,etHora2Real,etDescripcion, etDosisUtilizada;
    private String f1,f2,h1,h2;
    private Button btnGuardarResultado;

    private ProyectoCultivo proyecto;
    private DetalleActividad detalleSeleccionado;

    private int dia,mes,anio, hora, minutos;

    //Valores seteados
    private String hora_inicio_seleccionada;
    private String fecha_inicio_Seleccionada;
    private String hora_fin_seleccionada;
    private String fecha_fin_Seleccionada;


    Date fecha_hora_inicial=null;
    Date fecha_hora_final=null;

    Date fecha1_modificar= null;
    Date fecha2_modificar=null;

    //Concatenacion de las 4 anteriores
    private String fecha_hora_inicio;
    private String fecha_hora_fin;

    public Date getFecha1_modificar() {
        return fecha1_modificar;
    }

    public void setFecha1_modificar(Date fecha1_modificar) {
        this.fecha1_modificar = fecha1_modificar;
    }

    public Date getFecha2_modificar() {
        return fecha2_modificar;
    }

    public void setFecha2_modificar(Date fecha2_modificar) {
        this.fecha2_modificar = fecha2_modificar;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_siembra);

        btnf1= (ImageButton)findViewById(R.id.btnf1);
        btnf2= (ImageButton)findViewById(R.id.btnf2);
        btnh1= (ImageButton)findViewById(R.id.btnh1);
        btnh2= (ImageButton)findViewById(R.id.btnh2);

        etFecha1Real = (EditText)findViewById(R.id.etFecha1Real);
        etFecha2Real = (EditText)findViewById(R.id.etFecha2Real);
        etHora1Real = (EditText)findViewById(R.id.etHora1Real);
        etHora2Real = (EditText)findViewById(R.id.etHora2Real);
        etDescripcion = (EditText)findViewById(R.id.etDescripcionActividad);
        etDosisUtilizada = (EditText)findViewById(R.id.etKgHa);

        btnGuardarResultado = (Button)findViewById(R.id.btnGuardarResultado);

        //Se recibe desde DetalleProyecto
        Bundle bundle = getIntent().getExtras();
        proyecto = bundle.getParcelable("DATOS_PROYECTO");


        //Se recibe desde DetalleProyecto
        Bundle bundle2 = getIntent().getExtras();
        detalleSeleccionado = bundle2.getParcelable("DETALLE_SELECCIONADO");

    }

    void guardarResultado(){
        Toast.makeText(this, "Resultado Guardado con éxito", Toast.LENGTH_SHORT).show();
        //Se debe cambiar estado de la actividad segun lo que el usuario seleccione
    }

    public String getHora_inicio_seleccionada() {
        return hora_inicio_seleccionada;
    }

    public void setHora_inicio_seleccionada(String hora_inicio_seleccionada) {
        this.hora_inicio_seleccionada = hora_inicio_seleccionada;
    }

    public String getFecha_inicio_Seleccionada() {
        return fecha_inicio_Seleccionada;
    }

    public void setFecha_inicio_Seleccionada(String fecha_inicio_Seleccionada) {
        this.fecha_inicio_Seleccionada = fecha_inicio_Seleccionada;
    }

    public String getHora_fin_seleccionada() {
        return hora_fin_seleccionada;
    }

    public void setHora_fin_seleccionada(String hora_fin_seleccionada) {
        this.hora_fin_seleccionada = hora_fin_seleccionada;
    }

    public String getFecha_fin_Seleccionada() {
        return fecha_fin_Seleccionada;
    }

    public void setFecha_fin_Seleccionada(String fecha_fin_Seleccionada) {
        this.fecha_fin_Seleccionada = fecha_fin_Seleccionada;
    }


    @Override
    public void onClick(View v) {
        if(v==btnf1){
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH) ;
            anio = c.get(Calendar.YEAR);

            //Picker de Fecha
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    etFecha1Real.setText(dayOfMonth+"-"+ (month+1) +"-"+year);
                    setFecha_inicio_Seleccionada(year+"-"+(month+1)+"-"+dayOfMonth);
                    Toast.makeText(ResultadoSiembra.this, "Fecha Inicio Seleccionada es: " + getFecha_inicio_Seleccionada(), Toast.LENGTH_SHORT).show();
                }
            },anio,mes,dia);

            //Seteamos fecha mínima al día actual
            datePickerDialog.setTitle("Fecha de Inicio");
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
//            datePickerDialog.getDatePicker().setMaxDate();
            datePickerDialog.show();
        }

        if(v==btnf2){
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH) ;
            anio = c.get(Calendar.YEAR);

            //Picker de Fecha
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    etFecha2Real.setText(dayOfMonth+"-"+ (month+1) +"-"+year);
                    setFecha_fin_Seleccionada(year+"-"+(month+1)+"-"+dayOfMonth);
                    Toast.makeText(ResultadoSiembra.this, "Fecha Fin Seleccionada es: " + getFecha_fin_Seleccionada(), Toast.LENGTH_SHORT).show();

                }
            },anio,mes,dia);

            //Seteamos fecha mínima al día actual
            datePickerDialog.setTitle("Fecha de Fin");
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
//            datePickerDialog.getDatePicker().setMaxDate();
            datePickerDialog.show();
        }

        if(v==btnh1){
            final Calendar c= Calendar.getInstance();
            hora =c.get(Calendar.HOUR_OF_DAY);
            minutos =c.get(Calendar.MINUTE);

            //Picker de Hora
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    etHora1Real.setText(hourOfDay+":"+minute + " hs");
                    setHora_inicio_seleccionada(hourOfDay+":"+minute+":00");
                    Toast.makeText(ResultadoSiembra.this, "Hora Inicio Seleccionada es: " + getHora_inicio_seleccionada(), Toast.LENGTH_SHORT).show();

                }
            },hora,minutos,false);


            timePickerDialog.setTitle("Hora de Inicio");
            timePickerDialog.show();
        }

        if(v==btnh2){
            final Calendar c= Calendar.getInstance();
            hora =c.get(Calendar.HOUR_OF_DAY);
            minutos =c.get(Calendar.MINUTE);

            //Picker de Hora
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    etHora2Real.setText(hourOfDay+":"+minute + " hs");
                    setHora_fin_seleccionada(hourOfDay+":"+minute+":00");
                    Toast.makeText(ResultadoSiembra.this, "Hora fin Seleccionada es: " + getHora_fin_seleccionada(), Toast.LENGTH_SHORT).show();

                }
            },hora,minutos,false);


            timePickerDialog.setTitle("Hora de Fin");
            timePickerDialog.show();
        }
    }
}
