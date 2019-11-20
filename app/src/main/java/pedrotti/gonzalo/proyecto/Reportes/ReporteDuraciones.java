package pedrotti.gonzalo.proyecto.Reportes;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;

import pedrotti.gonzalo.proyecto.R;

public class ReporteDuraciones extends AppCompatActivity {

    private PieChart pieChart;
    private BarChart barChart;
    private String[]months= new String[]{"ENERO","FEBRERO","MARZO","ABRIL","MAYO"};
    private int[]sale= new int[]{25,20,38,10,15};
    private int[]colors = new int[]{Color.BLACK,Color.RED,Color.GREEN,Color.BLUE,Color.LTGRAY};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_duraciones);
    }
}
