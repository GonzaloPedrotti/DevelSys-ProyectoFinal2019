package pedrotti.gonzalo.proyecto.Reportes;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


import pedrotti.gonzalo.proyecto.Constantes;
import pedrotti.gonzalo.proyecto.R;


public class ReporteActividad extends AppCompatActivity {

    private ArrayList<Integer> realLista; //dataset1
    private ArrayList<Integer> estimadaLista; //dataset2
    private ArrayList<String>  actividadesList;
    private int longitudVector=0;

    private ArrayList<BarEntry> barEntries1;
    private ArrayList<BarEntry> barEntries2;

    private BarChart mpBarChart;

    private static final String urlFecha = "http://"+ Constantes.ip+"/miCampoWeb/mobile/Reporte/reporteActividad.php?proyecto_cultivo_id=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_actividad);

        setTitle("Grafico Comparativo de Actividades");

        realLista = new ArrayList<>();
        estimadaLista = new ArrayList<>();
        actividadesList = new ArrayList<>();


        barEntries1 = new ArrayList<>();
        barEntries2 = new ArrayList<>();

        cargarDuraciones();

        mpBarChart = findViewById(R.id.barChartActividad);
//        barEntries1 = new ArrayList<>();
//
//        realLista = new ArrayList<>();
//        estimadaLista = new ArrayList<>();
//
//        cargarDuraciones();
//
//        //Traigo la longitud desde el metodo cargarDuraciones
//        estimadaLista1 = new Integer[getLongitudVector()];
//        realLista1 = new Integer[getLongitudVector()];
//
//        BarDataSet barDataSet1 = new BarDataSet(barEntries1(),"DURACIÓN REAL");
//        barDataSet1.setColor(Color.RED);
//
//        BarDataSet barDataSet2 = new BarDataSet(barEntries2(),"DURACIÓN ESTIMADA");
//        barDataSet2.setColor(Color.BLUE);
//
//
//        BarData data = new BarData(barDataSet1,barDataSet2);
//        mpBarChart.setData(data);
//
//        String[] actividades = new String[]{"ARADO", "SIEMBRA","FERTILIZACIÓN", "FUMIGACIÓN"};
//
//        XAxis xAxis = mpBarChart.getXAxis();
//        xAxis.setValueFormatter(new IndexAxisValueFormatter(actividades));
//        xAxis.setCenterAxisLabels(true);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setGranularity(1);
//        xAxis.setGranularityEnabled(true);
//
//
//        xAxis.setGranularity(1f);
//        xAxis.setGranularityEnabled(true);
//        xAxis.setCenterAxisLabels(true);
//        xAxis.setDrawGridLines(false);
//        xAxis.setAxisMaximum(6);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//
//        //Y-axis
//        mpBarChart.getAxisRight().setEnabled(false);
//        YAxis leftAxis = mpBarChart.getAxisLeft();
//        leftAxis.setValueFormatter(new LargeValueFormatter());
//        leftAxis.setDrawGridLines(true);
//        leftAxis.setSpaceTop(35f);
//        leftAxis.setAxisMinimum(0f);
//
//
//        mpBarChart.setDragEnabled(true);
//        mpBarChart.setVisibleXRangeMaximum(3);
//
//        float barWidth  = 0.3f;
//        float barSpace = 0.05f;
//        float groupSpace = 0.16f;
//        data.setBarWidth(0.16f);
//
//        mpBarChart.getXAxis().setAxisMinimum(0);
//        mpBarChart.getXAxis().setAxisMaximum(0+mpBarChart.getBarData().getGroupWidth(groupSpace,barSpace)*5);
//
//        mpBarChart.getAxisLeft().setAxisMinimum(0);
//
//        mpBarChart.groupBars(0,groupSpace,barSpace);
//
//        //Agregado
//        mpBarChart.getData().setHighlightEnabled(false);
//
//        Legend l = mpBarChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(true);
//        l.setYOffset(20f);
//        l.setXOffset(0f);
//        l.setYEntrySpace(0f);
//        l.setTextSize(8f);
//
//        mpBarChart.invalidate();

    }

    void cargarDuraciones(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlFecha,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            int grupos = array.length();

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject duracion = array.getJSONObject(i);

                                Integer estimada = duracion.getInt("difEstimadadias");
                                Integer real = duracion.getInt("difRealDias");
                                String actividad = duracion.getString("actividad");

                                realLista.add(real);
                                estimadaLista.add(estimada);
                                actividadesList.add(actividad);

                                setBarEntries1(i,real);
                                setBarEntries2(i,estimada);

                                setLongitudVector(1);
                            }

                            BarDataSet barDataSet1 = new BarDataSet(getbarEntries1(),"DURACIÓN REAL");
                            barDataSet1.setColor(Color.RED);

                            BarDataSet barDataSet2 = new BarDataSet(getbarEntries2(),"DURACIÓN ESTIMADA");
                            barDataSet2.setColor(Color.BLUE);

                            BarData data = new BarData(barDataSet1,barDataSet2);

                            mpBarChart.setData(data);

                            String[] actividades = new String[]{"ARADO", "SIEMBRA","FERTILIZACIÓN", "FUMIGACIÓN"};

                            XAxis xAxis = mpBarChart.getXAxis();
                            xAxis.setValueFormatter(new IndexAxisValueFormatter(actividadesList));
                            xAxis.setCenterAxisLabels(true);
                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                            xAxis.setGranularity(1);
                            xAxis.setGranularityEnabled(true);
//                            xAxis.setGranularity(1f);
//                            xAxis.setDrawGridLines(false);
                            xAxis.setAxisMaximum(6);

                            //Y-axis
                            mpBarChart.getAxisRight().setEnabled(false);
                            YAxis leftAxis = mpBarChart.getAxisLeft();
//                            leftAxis.setValueFormatter(new LargeValueFormatter());
                            leftAxis.setDrawGridLines(true);
                            leftAxis.setSpaceTop(35f);
                            leftAxis.setAxisMinimum(0f);


                            mpBarChart.setDragEnabled(true);
                            mpBarChart.setVisibleXRangeMaximum(3);

                            float barWidth  = 0.15f;
                            float barSpace = 0.05f;
                            float groupSpace = 0.26f;
                            data.setBarWidth(barWidth);

                            mpBarChart.getXAxis().setAxisMinimum(0);
                            mpBarChart.getXAxis().setAxisMaximum(0+mpBarChart.getBarData().getGroupWidth(groupSpace,barSpace)*grupos);

                            mpBarChart.getAxisLeft().setAxisMinimum(0);

                            mpBarChart.groupBars(0,groupSpace,barSpace);
                            mpBarChart.setDrawBarShadow(true);


                            //Agregado
                            mpBarChart.getData().setHighlightEnabled(false);

                            Legend legend = mpBarChart.getLegend();

                            legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                            legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
                            legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                            legend.setDrawInside(true);
                            legend.setYOffset(20f);
                            legend.setXOffset(0f);
                            legend.setYEntrySpace(0f);
                            legend.setTextSize(8f);


                            mpBarChart.animateY(3000);

                            mpBarChart.invalidate();

                        } catch (JSONException e) {
                e.printStackTrace();
            }
        }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Volley.newRequestQueue(this).add(stringRequest);
        setLongitudVector(getLongitudVector());
    }

    public void getLongitud(){
        Toast.makeText(this, "Longitud del vector" + this.getLongitudVector() , Toast.LENGTH_SHORT).show();
    }


    public int getLongitudVector() {
        return longitudVector;
    }

    public void setLongitudVector(int valor) {
        longitudVector= longitudVector + valor ;
    }


    //Entries1
    private ArrayList<BarEntry> getbarEntries1(){
        return barEntries1;
    }

    private void setBarEntries1(int h1, int h2){
        barEntries1.add(new BarEntry(h1,h2));
    }


    //Entries2
    private ArrayList<BarEntry> getbarEntries2(){
        return barEntries2;
    }
    private void setBarEntries2(int h1, int h2){
        barEntries2.add(new BarEntry(h1,h2));
    }


//

}
