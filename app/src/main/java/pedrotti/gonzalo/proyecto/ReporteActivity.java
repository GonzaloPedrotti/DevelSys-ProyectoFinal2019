package pedrotti.gonzalo.proyecto;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import pedrotti.gonzalo.proyecto.Reportes.ReporteActividad;
import pedrotti.gonzalo.proyecto.Util.BottomNavigationViewHelper;

public class ReporteActivity extends AppCompatActivity {

    private static final String TAG= "ReporteActivity";
    private static final int ACTIVITY_NUM = 3 ;

    private Context mContext = ReporteActivity.this;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupBottomNavigationView();
        Log.d(TAG, "onCreate: starting.");

    }

    //Configuraciones del BottomNavigationView (Menú Inferior)

    public void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx)findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);

        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }
}
