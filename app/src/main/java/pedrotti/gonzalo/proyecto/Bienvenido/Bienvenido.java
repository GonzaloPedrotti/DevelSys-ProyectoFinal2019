package pedrotti.gonzalo.proyecto.Bienvenido;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import pedrotti.gonzalo.proyecto.Campo.TodosLosCampos;
import pedrotti.gonzalo.proyecto.InformacionDeApp;
import pedrotti.gonzalo.proyecto.Lotes.InformacionDelLote;
import pedrotti.gonzalo.proyecto.R;
import pedrotti.gonzalo.proyecto.TiposMapas;
import pedrotti.gonzalo.proyecto.Usuario.Usuario;

import static pedrotti.gonzalo.proyecto.Constantes.ERROR_DIALOG_REQUEST;
import static pedrotti.gonzalo.proyecto.Constantes.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static pedrotti.gonzalo.proyecto.Constantes.PERMISSIONS_REQUEST_ENABLE_GPS;

public class Bienvenido extends AppCompatActivity {

private Usuario user;
private boolean mLocationPermissionGranted = false;
    private static final String TAG = "Bienvenido";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenido);

        //Titulo de la Activity
        this.setTitle(R.string.micuenta);


        //Declaración de Botones
        Button btnVerCampos = (Button)findViewById(R.id.btnVerCampos);
        Button btnTiposMapa =(Button)findViewById(R.id.btnTipos);
        Button btnSitios =(Button)findViewById(R.id.btnSitios);
        Button btnVerInfo = (Button)findViewById(R.id.btnInformacionApp);


//        Se recibe en el Bienvenido lo enviado desde el Login se comenta para probar el registro de un campo
        Bundle bundle = getIntent().getExtras();
        user = bundle.getParcelable("DATOS_USER");


        //Metodo para abrir el mapa con los campos registrados(Clase Mapa)
        btnVerCampos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Se reenvian a la pantalla de TodosLosCampos
                Intent verCampos = new Intent(getApplicationContext(), TodosLosCampos.class);
                //Se comenta para el registro de un campo
                verCampos.putExtra("DATOS_USER",user);
                verCampos.putExtra("usuario_id",user.getUsuario_id());
                startActivity(verCampos);
            }
        });

        btnSitios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(),Mapa.class);
////                Bienvenido.this.finish();
//                startActivity(intent);
            }
        });


        btnVerInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent verClima = new Intent(getApplicationContext(), InformacionDeApp.class);
                startActivity(verClima);
            }
        });
        checkMapServices();
    }

    //Solicitud de Permisos

    private boolean checkMapServices(){
        if(isServicesOK()){
            if(isMapsEnabled()){
                return true;
            }
        }
        return false;
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean isMapsEnabled(){
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;


        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(Bienvenido.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(Bienvenido.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: called.");
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if(mLocationPermissionGranted){
//                    getChatrooms();
                }
                else{
                    getLocationPermission();
                }
            }
        }

    }













    //Fin Solicitud de Permisos


    public void mapaTipos (View view){
        Intent intent = new Intent(getApplicationContext(), TiposMapas.class);
        startActivity(intent);
    }

}
