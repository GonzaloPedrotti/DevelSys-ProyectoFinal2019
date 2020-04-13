package pedrotti.gonzalo.proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import pedrotti.gonzalo.proyecto.Fragments.CampoFragment;
import pedrotti.gonzalo.proyecto.Fragments.CuentaFragment;
import pedrotti.gonzalo.proyecto.Fragments.EstadisticaFragment;
import pedrotti.gonzalo.proyecto.Fragments.HomeFragment;
import pedrotti.gonzalo.proyecto.NuevoCampo.NuevoCampo;

public class Inicio extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.btnNavigationView);

        showSelectedFragment(new HomeFragment());

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if(menuItem.getItemId() == R.id.home){
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

                return true;
            }
        });
    }

    private void showSelectedFragment (Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
