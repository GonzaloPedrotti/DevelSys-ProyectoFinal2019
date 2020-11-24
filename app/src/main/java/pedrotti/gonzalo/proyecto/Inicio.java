package pedrotti.gonzalo.proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayDeque;
import java.util.Deque;

import pedrotti.gonzalo.proyecto.Fragments.ActividadesFragment;
import pedrotti.gonzalo.proyecto.Fragments.CampoFragment;
import pedrotti.gonzalo.proyecto.Fragments.CuentaFragment;
import pedrotti.gonzalo.proyecto.Fragments.EstadisticaFragment;
import pedrotti.gonzalo.proyecto.Fragments.HomeFragment;
import pedrotti.gonzalo.proyecto.Usuario.Usuario;

public class Inicio extends AppCompatActivity  {

    FragmentTransaction transaction;

    private Usuario usuario;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getParcelable("DATOS_USER");

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.btnNavigationView);

        showSelectedFragment(new HomeFragment());

        setBottomNavigationView(bottomNavigationView);
    }


    public void setBottomNavigationView(BottomNavigationView bottomNavigationView) {

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if(menuItem.getItemId() == R.id.ic_home){
                    showSelectedFragment(new HomeFragment());
                }

                if(menuItem.getItemId() == R.id.ic_campo){
                    showSelectedFragment(new CampoFragment());
                }

                if(menuItem.getItemId() == R.id.ic_estadistica){
                    showSelectedFragment(new EstadisticaFragment());

                }

                if(menuItem.getItemId() == R.id.ic_cuenta){
                    showSelectedFragment(new CuentaFragment());
                }

                if(menuItem.getItemId() == R.id.ic_actividades){
                    showSelectedFragment(new ActividadesFragment());
                }

                return true;
            }
        });

    }

    private void showSelectedFragment (Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments,fragment)
//                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if(bottomNavigationView.getSelectedItemId() != R.id.ic_home)
        {
            bottomNavigationView.setSelectedItemId(R.id.ic_home);
        }
        else
        {
            super.onBackPressed();
        }
    }

//
//    @Override
//    public void onBackPressed() {
//        if ( getSupportFragmentManager().getBackStackEntryCount() > 0) {
//            getSupportFragmentManager().popBackStack();
//        } else {
//            super.onBackPressed();
//        }
//    }
}
