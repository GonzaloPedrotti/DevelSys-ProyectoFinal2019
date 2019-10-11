package pedrotti.gonzalo.proyecto;

import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

public class TiposMapas extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    private Button btnHibrido,btnSatelital, btnTereno, btnNormal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipos_mapas);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnHibrido = (Button)findViewById(R.id.btnAyuda);
        btnSatelital = (Button)findViewById(R.id.btnRegistrarLote);
//        btnTereno = (Button)findViewById(R.id.btnTerreno);
        btnNormal = (Button)findViewById(R.id.btnNormal);



    }

    public void cambiarHibrido(View view){
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }

    public void cambiarSatelital(View view){
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

//    public void cambiarTerreno(View view){
//        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
//    }
    public void cambiarNormal(View view){
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);

    }



    public void onMapLongClick(LatLng latLng) {

        //Añadir marker en la posición
        Marker marker = mMap.addMarker(new MarkerOptions().position(latLng));

        // Obtener pixeles reales
        Point point = mMap.getProjection().toScreenLocation(latLng);

        // Determinar el ancho total de la pantalla
        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);
        int width = display.widthPixels;

        float hue;

        String format = String.format(Locale.getDefault(),"Lat/Lng = (%f,%f)", latLng.latitude, latLng.longitude);
        Toast.makeText(this, format, Toast.LENGTH_LONG).show();

        hue = BitmapDescriptorFactory.HUE_BLUE;

        marker.setIcon(BitmapDescriptorFactory.defaultMarker(hue));
    }
}
