package pedrotti.gonzalo.proyecto.Clima;

import android.graphics.Color;
import android.widget.Switch;

import java.util.List;

public class ClimaActual {

    private double temperatura;
    private String imagen;
    private String fecha;
    private String descripcion;
    private double humedad;
    private double viento;
    private double lluvia;
    private int recomendacion;

    public ClimaActual (double temperatura,String imagen, String fecha, String descripcion, double humedad, double viento,double lluvia,int recomendacion) {
        this.temperatura = temperatura;
        this.imagen = imagen;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.humedad = humedad;
        this.viento = viento;
        this.lluvia = lluvia;
        this.recomendacion = recomendacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public ClimaActual() {
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }


    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public double getHumedad() {
        return humedad;
    }

    public void setHumedad(double humedad) {
        this.humedad = humedad;
    }

    public double getViento() {
        return viento;
    }

    public void setViento(double viento) {
        this.viento = viento;
    }

    public double getLluvia() {
        return lluvia;
    }

    public void setLluvia(double lluvia) {
        this.lluvia = lluvia;
    }

    public int getRecomendacion() {
        return recomendacion;
    }

    public void setRecomendacion(int recomendacion) {
        this.recomendacion = recomendacion;
    }

    public int estaPermitidaFumigacion(double temperatura, double viento){

        int valor = 2;

        if(viento>0 && viento <8 ){
            if(temperatura>12 && temperatura<25){
                valor= 0;
            }else{
                valor=1;
            }
        }

        if(viento>=8 && viento <20){
            valor=1;
        }

        if(viento>=20){
            valor=2;

        }
        return valor;

    }

    public int estaPermitidoArar(int temperatura){

        int valor = 0;

        if(temperatura<25){
            valor=0;
        }else{
            valor=1;
        }
        return valor;
    }

    public int estaPermitidoSembrar(){
        return 1;
    }


}
