package pedrotti.gonzalo.proyecto.Util;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import pedrotti.gonzalo.proyecto.ActividadesActivity;
import pedrotti.gonzalo.proyecto.Bienvenido.Bienvenido;
import pedrotti.gonzalo.proyecto.CamposActivity;
import pedrotti.gonzalo.proyecto.CuentaActivity;
import pedrotti.gonzalo.proyecto.HomeActivity;
import pedrotti.gonzalo.proyecto.R;
import pedrotti.gonzalo.proyecto.ReporteActivity;
import pedrotti.gonzalo.proyecto.Reportes.ReporteActividad;

public class BottomNavigationViewHelper {

    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
//        bottomNavigationViewEx.enableAnimation(false);
//        bottomNavigationViewEx.enableShiftingMode(false);
//        bottomNavigationViewEx.enableItemShiftingMode(false);

    }

    public static void enableNavigation(final Context context, BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.ic_home:
                        Intent intent1 = new Intent(context, HomeActivity.class); //ACTIVITY_NUM = 0
                        context.startActivity(intent1);
                        break;

                    case R.id.ic_campo:
                        Intent intent2 = new Intent(context, CamposActivity.class); //ACTIVITY_NUM = 1
                        context.startActivity(intent2);
                        break;


                    case R.id.ic_actividades:
                        Intent intent4 = new Intent(context, ActividadesActivity.class); //ACTIVITY_NUM = 2
                        context.startActivity(intent4);
                        break;

                    case R.id.ic_estadistica:
                        Intent intent3 = new Intent(context, ReporteActivity.class); //ACTIVITY_NUM = 3
                        context.startActivity(intent3);
                        break;


                    case R.id.ic_cuenta:
                        Intent intent5 = new Intent(context, CuentaActivity.class); //ACTIVITY_NUM = 4
                        context.startActivity(intent5);
                        break;

                }
                return false;
            }
        });

    }
}
