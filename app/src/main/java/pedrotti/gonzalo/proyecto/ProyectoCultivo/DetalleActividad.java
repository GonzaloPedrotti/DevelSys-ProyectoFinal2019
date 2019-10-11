package pedrotti.gonzalo.proyecto.ProyectoCultivo;

public class DetalleActividad {

    private String actividad;
    private String inicio;
    private String fin;
    private String estado;


    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public DetalleActividad(String actividad, String inicio, String fin, String estado) {
        this.actividad = actividad;
        this.inicio = inicio;
        this.fin = fin;
        this.estado = estado;
    }

    public DetalleActividad() {
    }


}
