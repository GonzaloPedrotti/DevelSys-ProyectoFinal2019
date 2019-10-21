package pedrotti.gonzalo.proyecto.NuevoLote;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import pedrotti.gonzalo.proyecto.Campo.Campo;
import pedrotti.gonzalo.proyecto.Lote.Lote;
import pedrotti.gonzalo.proyecto.R;

public class NuevoLote extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;

    private double lat1=0;
    private double lat2=0;
    private double lat3=0;
    private double lat4=0;
    private double long1=0;
    private double long2=0;
    private double long3=0;
    private double long4=0;
    private String nombre;
    private double tamano=0;
    private int campo_id2;
    private int j=0;
    private int toque = 0;
    private int param1=0;
    private int param2=0;
    private int param3=0;
    private int param4=0;
    private Marker marker;
    EditText etNombre;
    EditText etTamano;
    private Campo campoSeleccionado;



    public double getLat1() {
        return lat1;
    }

    public void setLat1(double lat1) {
        this.lat1 = lat1;
    }

    public double getLat2() {
        return lat2;
    }

    public void setLat2(double lat2) {
        this.lat2 = lat2;
    }

    public double getLat3() {
        return lat3;
    }

    public void setLat3(double lat3) {
        this.lat3 = lat3;
    }

    public double getLat4() {
        return lat4;
    }

    public void setLat4(double lat4) {
        this.lat4 = lat4;
    }

    public double getLong1() {
        return long1;
    }

    public void setLong1(double long1) {
        this.long1 = long1;
    }

    public double getLong2() {
        return long2;
    }

    public void setLong2(double long2) {
        this.long2 = long2;
    }

    public double getLong3() {
        return long3;
    }

    public void setLong3(double long3) {
        this.long3 = long3;
    }

    public double getLong4() {
        return long4;
    }

    public void setLong4(double long4) {
        this.long4 = long4;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_lote);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        this.setTitle(R.string.crearLote);

        Bundle bundle = getIntent().getExtras();
        campoSeleccionado = bundle.getParcelable("DATOS_CAMPO_SEL");

        Button btnAyuda = (Button)findViewById(R.id.btnAyuda);
        Button btnRegistrarLote = (Button)findViewById(R.id.btnProyectosActuales);

        etNombre = (EditText)findViewById(R.id.txtNombreLote);
        etTamano = ((EditText)findViewById(R.id.txtTamano));

        //Funciones del Boton Registrar al hacer Click
        btnRegistrarLote.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if (param1==1 && param2==1 && param3==1 && param4==1  ){
                     j=1;
                }else{
                    j=0;
                }

                String tamanoString = etTamano.getText().toString();
               nombre = etNombre.getText().toString();

                if ((tamanoString==null) || (tamanoString.equals(""))) {
                    tamano=0;
                }else{
                   tamano = Double.parseDouble(tamanoString);
                }


                //Controles
                if (j==0) {
                    Toast.makeText(getApplicationContext(), "Asegúrese de Que Seleccionó los 4 puntos y Completó los Campos", Toast.LENGTH_LONG).show();
                }

                //inicio else
                else{
                    if(nombre.isEmpty() || tamano==0){

                        Toast.makeText(NuevoLote.this, "Asegúrese de haber ingresado un Nombre y un Tamaño", Toast.LENGTH_SHORT).show();
                    }else{

                        Response.Listener<String> respuesta = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Lote lote = new Lote();

                                try{
                                    JSONObject jsonRespuesta = new JSONObject(response);
                                    boolean ok = jsonRespuesta.getBoolean("success");
                                    if(ok==true){
                                    Toast.makeText(getApplicationContext(),  nombre + " Registrado con Éxito", Toast.LENGTH_LONG).show();
//                                    mMap.clear();
                                    finish();
                                    }else{

                                        AlertDialog.Builder alerta = new AlertDialog.Builder(NuevoLote.this);
                                        alerta.setMessage("Fallo en el Registro")
                                                .setNegativeButton("Reintentar",null)
                                                .create().show();
                                    }
                                }
                                catch(JSONException e){
                                    e.getMessage();
                                    Toast.makeText(getApplicationContext(), "Error al intentar guardar", Toast.LENGTH_LONG).show();
                                }
                            }
                        };
                        NuevoLoteRequest req = new NuevoLoteRequest(campoSeleccionado.getCampo_id(),nombre,tamano,lat1,lat2,lat3,lat4,long1,long2,long3,long4, respuesta);
                        RequestQueue cola = Volley.newRequestQueue(NuevoLote.this);
                        cola.add(req);
                    }
                }
                //fin del else
            }
        });

        btnAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ayuda = new Intent(getApplicationContext(), AyudaRegistrarLote.class);
                startActivity(ayuda);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMapClickListener(this);

        double latitud = campoSeleccionado.getLat();
        double longitud =campoSeleccionado.getLon();

        //Se agrega para ubicar el campo en el mapa
        LatLng campo = new LatLng(latitud,longitud);
        mMap.addMarker(new MarkerOptions().position(campo).title("Campo").snippet("Aqui tiene un campo").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        float zoomlevel = 15;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(campo, zoomlevel));
        //hasta aca

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Toast.makeText(NuevoLote.this,
                "Coordenadas Aquí: \n" + latLng.latitude + " : " + latLng.longitude,
                Toast.LENGTH_SHORT).show();
    }
    public void setToque(int valor){
        toque = toque + valor;
    }

    public int getToque(){
        return toque;
    }


    @Override
    public void onMapLongClick(LatLng latLng) {

        //Add marker on LongClick position
        if(getToque()<4){

            marker = mMap.addMarker(new MarkerOptions().position(latLng).draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).title("Vertice " + (getToque() + 1)));
            setToque(1);
            if(getToque()==1){
                setLat1(marker.getPosition().latitude);
                setLong1(marker.getPosition().longitude);
                Toast.makeText(this, "Punto 1: " + getLat1()+"," + getLong1(), Toast.LENGTH_SHORT).show();
                lat1 = marker.getPosition().latitude;
                long1 = marker.getPosition().longitude;
                param1=1;
            }

            if(getToque()==2){
                setLat2(marker.getPosition().latitude);
                setLong2(marker.getPosition().longitude);
                Toast.makeText(this, "Punto 2: " + getLat2() + "," + getLong2(), Toast.LENGTH_SHORT).show();
                lat2 = marker.getPosition().latitude;
                long2 = marker.getPosition().longitude;
                param2=1;

            }

            if(getToque()==3){
                setLat3(marker.getPosition().latitude);
                setLong3(marker.getPosition().longitude);
                Toast.makeText(this, "Punto 3: " + getLat3() + ","+ getLong3(), Toast.LENGTH_SHORT).show();
                lat3 = marker.getPosition().latitude;
                long3 = marker.getPosition().longitude;
                param3=1;
            }

            if(getToque()==4){
                setLat4(marker.getPosition().latitude);
                setLong4(marker.getPosition().longitude);
                Toast.makeText(this, "Punto 4: " + getLat4()+"," + getLong4(), Toast.LENGTH_SHORT).show();
                lat4 = marker.getPosition().latitude;
                long4 = marker.getPosition().longitude;
                param4=1;

            }
        }else{
            Toast.makeText(this, "Ya seleccionó los 4 Puntos disponibles", Toast.LENGTH_SHORT).show();
        }
    }
}
