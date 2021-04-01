package pedrotti.gonzalo.proyecto.Fragments.MainFragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pedrotti.gonzalo.proyecto.Campo.CamposAdapter;
import pedrotti.gonzalo.proyecto.Campo.DetalleCampo;
import pedrotti.gonzalo.proyecto.Constantes;
import pedrotti.gonzalo.proyecto.Modelo.Campo;
import pedrotti.gonzalo.proyecto.NuevoCampo.NuevoCampo;
import pedrotti.gonzalo.proyecto.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CampoFragment extends Fragment implements CamposAdapter.OnItemClickListener{

//    implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, GoogleMap.OnMapClickListener

    GoogleMap mMap;
    SupportMapFragment mapFragment;

    EditText etNombre;
    EditText etBuscar;
    Button btnRegistrarCampo;
    Button btnAyudaRegistro;
    FloatingActionButton btnFlotanteNuevoCampo;
    CheckBox btnCheck;

    View view;
    RecyclerView recyclerCampos;
    ArrayList<Campo> listaCampos;
    CamposAdapter adapter;
    Campo campo;


    public CampoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_campo, container, false);

        listaCampos = new ArrayList<>();

        recyclerCampos= (RecyclerView)view.findViewById(R.id.recyclerId);

        recyclerCampos.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);

        recyclerCampos.setLayoutManager(layoutManager);

//        recyclerCampos.setLayoutManager(new LinearLayoutManager(getContext()));

        etBuscar = (EditText)view.findViewById(R.id.etBuscar);

        btnFlotanteNuevoCampo = (FloatingActionButton)view.findViewById(R.id.btnFlotante);

        btnFlotanteNuevoCampo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent nuevoCampo = new Intent(getContext(), NuevoCampo.class);

                nuevoCampo.putExtra("adapter", adapter);

                startActivity(nuevoCampo);
            }
        });

        llenarLista();

        etBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filtrar(etBuscar.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        adapter = new CamposAdapter(getContext(),listaCampos);
//        recyclerCampos.setAdapter(adapter);
//        adapter.setOnItemClickListener(this);
//
//        adapter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "Hola", Toast.LENGTH_SHORT).show();
//
////                Toast.makeText(getContext(), "Seleccion: "+ listaCampos.get(recyclerCampos.getChildAdapterPosition(view)).getNombre(), Toast.LENGTH_SHORT).show();
//            }
//        });


//        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapCampoNuevo);
//        if (mapFragment == null) {
//            FragmentManager fm = getFragmentManager();
//            FragmentTransaction ft = fm.beginTransaction();
//            mapFragment = SupportMapFragment.newInstance();
//            ft.replace(R.id.mapCampoNuevo, mapFragment).commit();
//        }
//        mapFragment.getMapAsync(this);


        return view;
    }

    private void llenarLista(){
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setMessage("Cargando Campos...");
        progressDialog.show();

        Response.Listener<String> respuesta = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                campo = new Campo();

                try {
                    JSONArray array = new JSONArray(response);
                    if (array == null || array.length() == 0) {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
                        alerta.setMessage("No Tiene Campos Registrados. Comience registrando uno!").setPositiveButton("Entendido", null).create().show();
                    }

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject campos = array.getJSONObject(i);

                        int campo_id = campos.getInt("campo_id");
                        int usuario_id = campos.getInt("usuario_id");
                        String nombre = campos.getString("nombre");
                        double lat = campos.getDouble("lat1");
                        double lon = campos.getDouble("long1");

                        campo.setCampo_id(campo_id);

                        //Se crea el constructor para crear el objeto Campo
                        Campo campo = new Campo(usuario_id, campo_id, nombre, lat, lon);

                        //añadiendo a la lista campoList el objeto Campo recien creado
                        listaCampos.add(campo);
                    }

                    adapter = new CamposAdapter(getContext(), listaCampos);
                    recyclerCampos.setAdapter(adapter);
                    progressDialog.dismiss();
                    adapter.setOnItemClickListener(CampoFragment.this);

                } catch (JSONException e) {

                    e.printStackTrace();

                    progressDialog.dismiss();
                }
            }
        };
        CamposRequest r = new CamposRequest(1,respuesta);
        RequestQueue cola = Volley.newRequestQueue(getContext());
        cola.add(r);
    }

    class  CamposRequest extends StringRequest {
        private static final String url = "http://"+ Constantes.ip+"/miCampoWeb/ControladorVista/campoCode.php";

        private Map<String,String> parametros;
        public CamposRequest (int usuario_id, Response.Listener<String> listener){
            super(Request.Method.POST, url, listener, null);
            parametros = new HashMap<>();
            parametros.put("usuario_id",  usuario_id+"");
        }

        @Override
        protected Map<String, String> getParams(){
            return parametros;
        }
    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
////
////        btnAyudaRegistro = getView().findViewById(R.id.btnAyudaRegistro);
////
////
////        btnAyudaRegistro.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Toast.makeText(getContext(), "Boton Seleccionado", Toast.LENGTH_SHORT).show();
////            }
////        });
//    }

    @Override
    public void OnItemClick(int position) {
//        Toast.makeText(getContext(), "Mensaje", Toast.LENGTH_SHORT).show();
        Intent detalleCampo = new Intent(getContext(), DetalleCampo.class);
//                adapter.notifyItemInserted(position);  //
//        adapter.notifyItemRemoved(position); //quitar item
        Campo itemSeleccionado = listaCampos.get(position);
//        listaCampos.remove(position);
//        adapter.notifyDataSetChanged();
//        Toast.makeText(getContext(), "Campo Eliminado", Toast.LENGTH_SHORT).show();
//        Toast.makeText(getContext(), itemSeleccionado.getNombre()+ "/" + itemSeleccionado.getLat()+ "/" + itemSeleccionado.getLon(), Toast.LENGTH_SHORT).show();
        detalleCampo.putExtra("DATOS_CAMPO_SEL",itemSeleccionado);
        startActivity(detalleCampo);
    }


//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//
//        mMap = googleMap;
//
//        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//        mMap.setOnMapLongClickListener(this);
//        mMap.setOnMapClickListener(this);
//
//        LatLng latLng = new LatLng(22.5, 88.7);
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//        markerOptions.title("POSICION ACTUAL");
//        markerOptions.snippet("MI POSICION");
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//        mMap.addMarker(markerOptions);
//
//
//        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
//                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return ;
//        }
//        mMap.setMyLocationEnabled(true);
//
//    }
//
//    @Override
//    public void onMapClick(LatLng latLng) {
//        Toast.makeText(getContext(),
//                "Coordenadas Aquí: \n" + latLng.latitude + " : " + latLng.longitude,
//                Toast.LENGTH_SHORT).show();
//
//    }
//
//    @Override
//    public void onMapLongClick(LatLng latLng) {
//
//        Toast.makeText(getContext(), "Ya seleccionó una Ubicación para su Campo.", Toast.LENGTH_SHORT).show();
//
//    }
}
