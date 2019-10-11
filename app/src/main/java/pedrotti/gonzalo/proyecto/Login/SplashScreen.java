package pedrotti.gonzalo.proyecto.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import pedrotti.gonzalo.proyecto.Login.Login;
import pedrotti.gonzalo.proyecto.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent  = new Intent (getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        },1200);
    }
}
