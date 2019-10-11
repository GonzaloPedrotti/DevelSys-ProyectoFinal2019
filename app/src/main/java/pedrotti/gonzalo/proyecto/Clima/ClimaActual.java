package pedrotti.gonzalo.proyecto.Clima;

import java.util.List;

public class ClimaActual {

//    public String cod;
//    public double message;
//    public int cnt ;
//    public List<Lista> list ;
//    public City city ;

    private double temperatura;
    private String imagen;
    private String fecha;
    private String descripcion;
    private double humedad;
    private double viento;
    private double lluvia;

    public ClimaActual (double temperatura,String imagen, String fecha, String descripcion, double humedad, double viento,double lluvia) {
        this.temperatura = temperatura;
        this.imagen = imagen;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.humedad = humedad;
        this.viento = viento;
        this.lluvia = lluvia;
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
}
