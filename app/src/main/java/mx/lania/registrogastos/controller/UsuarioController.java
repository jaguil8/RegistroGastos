package mx.lania.registrogastos.controller;

import android.content.Context;
import android.database.SQLException;
import android.util.Log;
import android.widget.Toast;

import mx.lania.registrogastos.database.DBAdapter;

/**
 * Created by miguel on 07/10/2015.
 */
public class UsuarioController {

    public static boolean insertUsuario(Context context, String login,String password){
        DBAdapter db = new DBAdapter(context);
        db.open();
        boolean isInserted = db.insertUsuario(login, password);
        db.close();
        return isInserted;
    }

    public static boolean deleteUsuario (Context context, int idUsuario)
    {
        DBAdapter db = new DBAdapter(context);
        db.open();
        boolean isDeleted = true;//db.(descripcion, gasto,fecha,idUsuario,tipoGasto);
        db.close();
        return isDeleted;
    }

    public static void displayUsuario(Context context, String login) {
        DBAdapter db = new DBAdapter(context);
        db.open();
        String nombre = db.getUsuarioByLogin(login).toString();
        db.close();
        Log.d("NOMBRE_CONTACTO", nombre);
        Toast.makeText(context, "El nombre del usuario es: " + nombre, Toast.LENGTH_LONG).show();
    }

    public static String getPassword(Context context, String login) {
        DBAdapter db = new DBAdapter(context);
        db.open();
        String password = db.getPasswordByLogin(login).toString();
        db.close();
        return password;
    }

    public static boolean existeUsuario(Context context,String login) throws SQLException
    {
        DBAdapter db = new DBAdapter(context);
        db.open();
        boolean existe = db.existeUsuario(login);
        db.close();
        return existe;
    }
}
