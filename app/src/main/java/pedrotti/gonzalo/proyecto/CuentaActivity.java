package pedrotti.gonzalo.proyecto;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import pedrotti.gonzalo.proyecto.Util.BottomNavigationViewHelper;

public class CuentaActivity extends AppCompatActivity {

    private static final String TAG= "CuentaActivity";
    private static final int ACTIVITY_NUM = 4 ;
    private Context mContext = CuentaActivity.this;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
//        setTitle("Gonzalo Pedrotti");
        setupBottomNavigationView();
//        setupToolbar();
        Log.d(TAG, "onCreate: starting.");
        Toolbar toolbar = findViewById(R.id.toolbarCuenta);
        setSupportActionBar(toolbar);
    }

    private void setupToolbar(){
        Toolbar toolbar =findViewById(R.id.profileToolbar);
//        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){

                    case R.id.profileMenu:
                        Toast.makeText(mContext, "Opción Seleccionada", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(mContext, "Seleccionado", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu,menu);
        return true;
    }
}
