package pedrotti.gonzalo.proyecto.Cultivo;

import android.os.Parcel;
import android.os.Parcelable;



public class Cultivo implements Parcelable{
    private int cultivo_id;
    private String nombre;
    private String periodo;
    private String mesInicio;
    private String mesFin;

    protected Cultivo(Parcel in) {
        cultivo_id = in.readInt();
        nombre = in.readString();
        periodo = in.readString();
        mesInicio = in.readString();
        mesFin = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cultivo_id);
        dest.writeString(nombre);
        dest.writeString(periodo);
        dest.writeString(mesInicio);
        dest.writeString(mesFin);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Cultivo> CREATOR = new Creator<Cultivo>() {
        @Override
        public Cultivo createFromParcel(Parcel in) {
            return new Cultivo(in);
        }

        @Override
        public Cultivo[] newArray(int size) {
            return new Cultivo[size];
        }
    };

    public int getCultivo_id() {
        return cultivo_id;
    }

    public void setCultivo_id(int cultivo_id) {
        this.cultivo_id = cultivo_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getMesInicio() {
        return mesInicio;
    }

    public void setMesInicio(String mesInicio) {
        this.mesInicio = mesInicio;
    }

    public String getMesFin() {
        return mesFin;
    }

    public void setMesFin(String mesFin) {
        this.mesFin = mesFin;
    }

    public Cultivo(int cultivo_id, String nombre, String periodo, String mesInicio, String mesFin) {
        this.cultivo_id = cultivo_id;
        this.nombre = nombre;
        this.periodo = periodo;
        this.mesInicio = mesInicio;
        this.mesFin = mesFin;
    }

    public Cultivo(){

    }

    //El metodo toString se sobreescribe para ser utilizado por el Spinner
    @Override
    public String toString() {
        return nombre;
    }


}
