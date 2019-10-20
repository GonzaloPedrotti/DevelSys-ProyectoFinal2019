package pedrotti.gonzalo.proyecto.Campo;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import pedrotti.gonzalo.proyecto.R;

public class UbicacionCampo extends FragmentActivity implements OnMapReadyCallback {



    private GoogleMap mMap;
    private Campo itemSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion_campo);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //DetalleCampo recibe lo enviado desde TodosLosCampos
        Bundle bundle = getIntent().getExtras();
        itemSeleccionado = bundle.getParcelable("DATOS_CAMPO_SEL");
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
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        double latitud = itemSeleccionado.getLat();
        double longitud =itemSeleccionado.getLon();

        LatLng campo = new LatLng(latitud,longitud);
        mMap.addMarker(new MarkerOptions().position(campo).title(itemSeleccionado.getNombre()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(campo));
        float zoomlevel = 15;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(campo, zoomlevel));
    }
}
