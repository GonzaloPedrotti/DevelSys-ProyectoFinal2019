package pedrotti.gonzalo.proyecto.Fragments.MainFragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import pedrotti.gonzalo.proyecto.NuevoCampo.NuevoCampo;
import pedrotti.gonzalo.proyecto.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    View vista;
    Button btnPrueba;
    RecyclerView recyclerView;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_home, container, false);

        btnPrueba = vista.findViewById(R.id.btnPrueba);

        btnPrueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBoton();
            }
        });

        return vista;
    }

    private int usuario_id = 0;
    private String correo = "";
    private String contrasena = "";

    void clickBoton() {

        SharedPreferences preferences = this.getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);

        usuario_id = preferences.getInt("usuario_id", 0);
        correo = preferences.getString("correo", "");
        contrasena = preferences.getString("contrasena", "");

        Toast.makeText(getContext(),"ID: "+ usuario_id +  " Correo: " + correo +  " Contraseña: "+ contrasena, Toast.LENGTH_SHORT).show();


        //SHAREDPREFERENCE
//        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
//        etCorreo.setText(preferences.getString("correo", ""));
//        etcontrasena.setText(preferences.getString("contrasena", ""));

//        if(!(etCorreo.getText().toString().isEmpty()) && !(etcontrasena.getText().toString().isEmpty())){
//            iniciarSesion();
//        }

//        Toast.makeText(getContext(), "Hola", Toast.LENGTH_SHORT).show();

//        ABRIR UN FRAGMENT DESDE OTRO FRAGMENT

//        Fragment fragment = new ActividadesFragment();
//
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//        fragmentTransaction.replace(R.id.container, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();

    }




}
