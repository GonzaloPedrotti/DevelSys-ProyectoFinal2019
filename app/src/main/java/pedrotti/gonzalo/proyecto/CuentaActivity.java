package pedrotti.gonzalo.proyecto;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import org.w3c.dom.Text;

import pedrotti.gonzalo.proyecto.Util.BottomNavigationViewHelper;

public class CuentaActivity extends AppCompatActivity {

    private static final String TAG= "CuentaActivity";
    private static final int ACTIVITY_NUM = 4 ;
    private Context mContext = CuentaActivity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        setupBottomNavigationView();
        setupComponents();
        Log.d(TAG, "onCreate: starting.");
    }

    private void setupComponents(){

        Toolbar toolbar =findViewById(R.id.profileToolBar);
        TextView tvNombreUsuario = findViewById(R.id.profile_tvNombre);
        final TextView tvCorreoUsuario = findViewById(R.id.profile_tvCorreo);
        TextView tvSalir         = findViewById(R.id.tvSalir);
        final TextView tvProfileNombre = findViewById(R.id.profile_Bar_Nombre);

        tvNombreUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Nombre Seleccionado", Toast.LENGTH_SHORT).show();
                tvProfileNombre.setText(tvCorreoUsuario.getText().toString());
            }
        });

        tvCorreoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Correo Seleccionado", Toast.LENGTH_SHORT).show();
            }
        });

        tvSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Salir Seleccionado", Toast.LENGTH_SHORT).show();
            }
        });

        setSupportActionBar(toolbar);
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
