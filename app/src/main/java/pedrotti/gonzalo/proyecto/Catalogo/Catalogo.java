package pedrotti.gonzalo.proyecto.Catalogo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import pedrotti.gonzalo.proyecto.R;

public class Catalogo extends AppCompatActivity {

    String url = "https://www.donmario.com/catalogo-2/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        setTitle("Catalogo de Semillas");

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIcon(R.mipmap.ic_launcher);
//        progressDialog.setProgressStyle();
        progressDialog.setMessage("Cargando Catálogo...");

        progressDialog.show();


        WebView webView = (WebView)findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true); //permite que algunas paginas funciones bien
        webView.getSettings().setBuiltInZoomControls(true); //permite el zoom si no es responsive
        webView.loadUrl(url);
        //permite navegar dentro del WebView sin abrir el navegador
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView webView , String url){
                super.onPageFinished(webView,url);
                progressDialog.dismiss();
            }
            public boolean shouldOverriceUrlLoading(WebView webView1,String url){
                return  false;
            }
        });

    }
}
