package pedrotti.gonzalo.proyecto.Actividad;

public class Actividad {

    private int actividad_id;
    private String nombre;
    private String descripcion;

    public Actividad(int actividad_id, String nombre, String descripcion) {
        this.actividad_id = actividad_id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Actividad() {
    }

    public int getActividad_id() {
        return actividad_id;
    }

    public void setActividad_id(int actividad_id) {
        this.actividad_id = actividad_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
