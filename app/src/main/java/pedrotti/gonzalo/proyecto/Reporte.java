package pedrotti.gonzalo.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Xfermode;
import android.os.Bundle;
import android.service.autofill.Dataset;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

public class Reporte extends AppCompatActivity {


    private PieChart pieChart;
    private BarChart barChart;
    private String[]months= new String[]{"ENERO","FEBRERO","MARZO","ABRIL","MAYO"};
    private int[]sale= new int[]{25,20,38,10,15};
    private int[]colors = new int[]{Color.BLACK,Color.RED,Color.GREEN,Color.BLUE,Color.LTGRAY};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);
        setTitle("Reporte de Actividades");

        pieChart = (PieChart)findViewById(R.id.pieChart);
        barChart = (BarChart)findViewById(R.id.barChart);
        createCharts();
    }

    private Chart getSameChart(Chart chart, String description, int textColor, int background, int animateY){

        chart.getDescription(). setText(description);
        chart.getDescription().setTextColor(textColor);
        chart.getDescription().setTextSize(15);
        chart.setBackgroundColor(background);

        chart.animateY(animateY);
        legend(chart);
        return chart;
    }

    private void legend(Chart chart){
        Legend legend = chart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        ArrayList<LegendEntry> entries = new ArrayList<>();
        for(int i=0;i<months.length;i++){
            LegendEntry entry = new LegendEntry();
            entry.formColor=colors[i];
            entry.label=months[i];
            entries.add(entry);
        }
        legend.setCustom(entries);
    }

    private ArrayList<BarEntry>getBarEntries(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        for(int i=0;i<sale.length;i++)
            entries.add(new BarEntry(i,sale[i]));
        return entries;
    }

    private ArrayList<PieEntry>getPieEntries(){
        ArrayList<PieEntry> entries = new ArrayList<>();
        for(int i=0;i<sale.length;i++)
            entries.add(new PieEntry(sale[i]));
        return entries;
    }

    //Eje x

    private void axisX(XAxis axis){
        //De 1 en 1
        axis.setGranularityEnabled(true);
        //posicion, se muestra por debajo
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //Texto a mostrar
        axis.setValueFormatter(new IndexAxisValueFormatter(months));
        //Desactivamos los titulos de abajo
//        xAxis.setEnabled(false);
    }

    //Eje Y desde el lado izquierdo

    private void axisLeft(YAxis axis){
        //Espacio máximo superior
        axis.setSpaceTop(30);
        //inicio del eje y
        axis.setAxisMinimum(0);
        axis.setGranularity(20);
    }

    private void axisRight(YAxis yAxis){
        yAxis.setEnabled(false);
    }

    public void createCharts(){
        barChart = (BarChart)getSameChart(barChart,"SERIES",Color.RED,Color.CYAN,3000);
        barChart.setDrawGridBackground(true);
        barChart.setDrawBarShadow(true);
        barChart.setData(getBarData());

        axisX(barChart.getXAxis());
        axisLeft(barChart.getAxisLeft());
        axisRight(barChart.getAxisRight());
        barChart.getLegend().setEnabled(false);

        pieChart=(PieChart)getSameChart(pieChart,"Ventas", Color.GRAY,Color.MAGENTA,3000);
        //circulo del centro
        pieChart.setHoleRadius(10);
        pieChart.setTransparentCircleRadius(12);
//        pieChart.setDrawHoleEnabled(false);
        pieChart.setData(getPieData());
        pieChart.invalidate();

    }

    private DataSet getData( DataSet dataSet){
        dataSet.setColors(colors);
        //Color de los valores en el grafico
        dataSet.setValueTextColor(Color.WHITE);
        //Tamaño del texto
        dataSet.setValueTextSize(10);
        return dataSet;
    }

    //Personalizar contenido de los graficos
    private BarData getBarData(){
        BarDataSet barDataSet = (BarDataSet)getData(new BarDataSet(getBarEntries(),""));

        barDataSet.setBarShadowColor(Color.GRAY);
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.45f);
        return barData;
    }

    //Personalizar contenido de los graficos
    private PieData getPieData(){
        PieDataSet pieDataSet = (PieDataSet)getData(new PieDataSet(getPieEntries(),""));
        pieDataSet.setSliceSpace(2);

        pieDataSet.setValueFormatter(new PercentFormatter());
        return new PieData(pieDataSet);


    }

}
