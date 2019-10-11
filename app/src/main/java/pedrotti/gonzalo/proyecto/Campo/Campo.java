package pedrotti.gonzalo.proyecto.Campo;

import android.os.Parcel;
import android.os.Parcelable;

import pedrotti.gonzalo.proyecto.Usuario.Usuario;

public class Campo implements Parcelable {

    private int  usuario_id;
    private int campo_id;
    private String nombre;
    private double  lat;
    private double  lon;

    protected Campo(Parcel in) {
        usuario_id = in.readInt();
        campo_id = in.readInt();
        nombre = in.readString();
        lat = in.readDouble();
        lon = in.readDouble();
        usuario = in.readParcelable(Usuario.class.getClassLoader());
    }

    public static final Creator<Campo> CREATOR = new Creator<Campo>() {
        @Override
        public Campo createFromParcel(Parcel in) {
            return new Campo(in);
        }

        @Override
        public Campo[] newArray(int size) {
            return new Campo[size];
        }
    };



    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public void setCampo_id(int campo_id) {
        this.campo_id = campo_id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    private Usuario usuario;

    public Campo( int usuario_id,int campo_id, String nombre, double lat, double lon) {

        this.usuario_id = usuario_id;
        this.campo_id = campo_id;
        this.nombre = nombre;
        this.lat = lat;
        this.lon = lon;
    }

    public Campo(){
    }


    public int getCampo_id() {
        return campo_id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public int getUsuario_id(){
        return usuario_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(usuario_id);
        dest.writeInt(campo_id);
        dest.writeString(nombre);
        dest.writeDouble(lat);
        dest.writeDouble(lon);
        dest.writeParcelable(usuario, flags);
    }
}
