package pedrotti.gonzalo.proyecto.Variedad;

 public class Variedad {
    private String cultivo;
    private int variedad_id;
    private String variedad;
    private String descripcion;
    private int dias;
    private double profundidad;
    private String niveldezona;
    private String zona;
    private String siembra;
    private double peso;
    private int densidad;





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

     public int getDias() {
         return dias;
     }

     public void setDias(int dias) {
         this.dias = dias;
     }

     public double getProfundidad() {
         return profundidad;
     }

     public void setProfundidad(double profundidad) {
         this.profundidad = profundidad;
     }

     public String getSiembra() {
         return siembra;
     }

     public void setSiembra(String siembra) {
         this.siembra = siembra;
     }

     public double getPeso() {
         return peso;
     }

     public void setPeso(double peso) {
         this.peso = peso;
     }

     public int getDensidad() {
         return densidad;
     }

     public void setDensidad(int densidad) {
         this.densidad = densidad;
     }
 }
