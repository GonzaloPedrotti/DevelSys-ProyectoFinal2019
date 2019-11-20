package pedrotti.gonzalo.proyecto.Reportes;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;

import android.os.Bundle;
import android.view.Gravity;
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
import com.github.mikephil.charting.formatter.LargeValueFormatter;

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


    private ArrayList<BarEntry> barEntries1;
    private ArrayList<BarEntry> barEntries2;

    private BarChart chart;
    private float barWidth;
    float barSpace;
    float groupSpace;

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

        chart = findViewById(R.id.barChartActividad);

        cargarDuraciones();



//        mpBarChart = findViewById(R.id.barChartActividad);
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



   public void cargarDuraciones(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlFecha,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            //0.3f
                            barWidth = 0.3f;
                            //0f
                            barSpace = 0.0f;
                            //0.4f
                            groupSpace = 0.4f;

                            chart.setDescription(null);
                            chart.setPinchZoom(false);
                            chart.setScaleEnabled(false);
                            chart.setDrawBarShadow(false);
                            chart.setDrawGridBackground(false);

                            ArrayList xVals = new ArrayList();
                            ArrayList yVals1 = new ArrayList();
                            ArrayList yVals2 = new ArrayList();

                            int groupCount = array.length();

                            if(groupCount==0){
                                Toast toast1 = Toast.makeText(getApplicationContext(),"No hay Actividades Finalizadas. \n " + "Vuelva cuando termine alguna actividad",Toast.LENGTH_SHORT);
                                toast1.setGravity(Gravity.CENTER,0,0);
                                toast1.show();
                            ReporteActividad.this.finish();

                            }else{
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject duracion = array.getJSONObject(i);

                                    Integer estimada = duracion.getInt("difEstimadadias");
                                    Integer real = duracion.getInt("difRealDias");
                                    String actividad = duracion.getString("actividad");

                                    realLista.add(real);
                                    estimadaLista.add(estimada);
                                    actividadesList.add(actividad);

                                    xVals.add(actividad);
                                    yVals1.add(new BarEntry(i+1,real));
                                    yVals2.add(new BarEntry(i+1,estimada));

                                }

                                BarDataSet set1, set2;

                                set1 = new BarDataSet(yVals1,"Duración Real en días");
                                set1.setColor(Color.RED);

                                set2 = new BarDataSet(yVals2,"Duración Estimada en días");
                                set2.setColor(Color.BLUE);

                                BarData data = new BarData(set1,set2);
                                data.setValueFormatter(new LargeValueFormatter());
                                chart.setData(data);
                                chart.getBarData().setBarWidth(barWidth);
                                chart.getXAxis().setAxisMinimum(0);
                                chart.getXAxis().setAxisMaximum(0 + chart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
                                chart.groupBars(0, groupSpace, barSpace);
                                chart.getData().setHighlightEnabled(false);
                                chart.invalidate();


                                Legend l = chart.getLegend();
                                l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                                l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
                                l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                                l.setDrawInside(true);
                                l.setYOffset(20f);
                                l.setXOffset(0f);
                                l.setYEntrySpace(0f);
                                l.setTextSize(8f);

                                //X-axis
                                XAxis xAxis = chart.getXAxis();
                                //1f se ve bien
                                xAxis.setGranularity(1f);
                                xAxis.setGranularityEnabled(true);
                                xAxis.setCenterAxisLabels(true);
                                xAxis.setDrawGridLines(false);
                                //comentado se ve mejor. sino, era 6
//                                xAxis.setAxisMaximum(groupCount+1);
                                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                                xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));

                                //Y-axis
                                chart.getAxisRight().setEnabled(false);
                                YAxis leftAxis = chart.getAxisLeft();
                                leftAxis.setValueFormatter(new LargeValueFormatter());
                                leftAxis.setDrawGridLines(true);
                                leftAxis.setSpaceTop(35f);
                                leftAxis.setAxisMinimum(0f);

                                chart.setDragEnabled(true);
                                //Cantidad maxima que se ve. Despues se tiene que desplazar la ventana
                                chart.setVisibleXRangeMaximum(3);
                            }

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

    }



//    public int getLongitudVector() {
//        return longitudVector;
//    }
//
//    public void setLongitudVector(int valor) {
//        longitudVector= longitudVector + valor ;
//    }


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
