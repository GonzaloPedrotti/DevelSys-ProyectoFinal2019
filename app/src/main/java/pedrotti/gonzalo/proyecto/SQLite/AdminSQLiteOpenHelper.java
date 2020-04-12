package pedrotti.gonzalo.proyecto.SQLite;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper(Context context,String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase miCampo) {

        String query1= "CREATE TABLE USUARIO(usuario_id int primary key, nombre text, apellido text, correo text, telefono text, contrasena text)";
        String query2= "CREATE TABLE LOGIN (usuario_id int, correo text, contrasena text, fecha text)";

        miCampo.execSQL(query2);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
