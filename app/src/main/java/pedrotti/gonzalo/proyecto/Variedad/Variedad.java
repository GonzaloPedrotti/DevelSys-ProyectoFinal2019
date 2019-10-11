package pedrotti.gonzalo.proyecto.Variedad;

 public class Variedad {
    private String cultivo;
    private int variedad_id;
    private String variedad;
    private String niveldezona;
    private String zona;
    private String descripcion;


    public Variedad(String cultivo, int variedad_id, String variedad, String niveldezona, String zona, String descripcion) {
        this.cultivo = cultivo;
        this.variedad_id = variedad_id;
        this.variedad = variedad;
        this.niveldezona = niveldezona;
        this.zona = zona;
        this.descripcion = descripcion;
    }

    public Variedad(){

    }

     @Override
     public String toString() {
         return variedad;
     }

     public String getCultivo() {
        return cultivo;
    }

    public void setCultivo(String cultivo) {
        this.cultivo = cultivo;
    }

    public int getVariedad_id() {
        return variedad_id;
    }

    public void setVariedad_id(int variedad_id) {
        this.variedad_id = variedad_id;
    }

    public String getVariedad() {
        return variedad;
    }

    public void setVariedad(String variedad) {
        this.variedad = variedad;
    }

    public String getNiveldezona() {
        return niveldezona;
    }

    public void setNiveldezona(String niveldezona) {
        this.niveldezona = niveldezona;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
