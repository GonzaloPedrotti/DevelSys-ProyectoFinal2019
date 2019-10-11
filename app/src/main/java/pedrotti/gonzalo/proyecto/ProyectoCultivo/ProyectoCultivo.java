package pedrotti.gonzalo.proyecto.ProyectoCultivo;

import android.os.Parcel;
import android.os.Parcelable;

import pedrotti.gonzalo.proyecto.Cultivo.Cultivo;

public class ProyectoCultivo implements Parcelable{

    private int id;
    private String nombre;
    private String fechaRegistro;
    private String cultivo;
    private String periodo;
    private String estado;
    private int cultivo_id;

    public ProyectoCultivo(int id, String nombre, String fechaRegistro, String cultivo, String periodo, String estado, int cultivo_id) {
        this.id = id;
        this.nombre = nombre;
        this.fechaRegistro = fechaRegistro;
        this.cultivo = cultivo;
        this.periodo = periodo;
        this.estado = estado;
        this.cultivo_id= cultivo_id;
    }

    public ProyectoCultivo(){

    }


    protected ProyectoCultivo(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
        fechaRegistro = in.readString();
        cultivo = in.readString();
        periodo = in.readString();
        estado = in.readString();
        cultivo_id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nombre);
        dest.writeString(fechaRegistro);
        dest.writeString(cultivo);
        dest.writeString(periodo);
        dest.writeString(estado);
        dest.writeInt(cultivo_id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProyectoCultivo> CREATOR = new Creator<ProyectoCultivo>() {
        @Override
        public ProyectoCultivo createFromParcel(Parcel in) {
            return new ProyectoCultivo(in);
        }

        @Override
        public ProyectoCultivo[] newArray(int size) {
            return new ProyectoCultivo[size];
        }
    };


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public int getCultivo_id() {
        return cultivo_id;
    }

    public void setCultivo_id(int cultivo_id) {
        this.cultivo_id = cultivo_id;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getCultivo() {
        return cultivo;
    }

    public void setCultivo(String cultivo) {
        this.cultivo = cultivo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
