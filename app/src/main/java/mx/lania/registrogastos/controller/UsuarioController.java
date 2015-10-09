package mx.lania.registrogastos.controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import mx.lania.registrogastos.database.DBAdapter;

/**
 * Created by miguel on 07/10/2015.
 */
public class UsuarioController {

    public static boolean insertUsuario(Context context, String login,String passworrd){
        DBAdapter db = new DBAdapter(context);
        db.open();
        boolean isInserted = db.insertUsuario(login,passworrd);
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

    public static void displayUsuario(Context context, long id) {
        DBAdapter db = new DBAdapter(context);
        db.open();
        String descripcion = db.getGastoByID(id);
        db.close();
        Log.d("DESCRIPCION_GASTO", descripcion);
        Toast.makeText(context, "La Descripción del gasto es: " + descripcion, Toast.LENGTH_LONG).show();
    }

    public static boolean existeUsuario(Context context,String login)
    {
        DBAdapter db = new DBAdapter(context);
        db.open();
        boolean existe = db.existeUsuario(login);
        db.close();
        return existe;
    }
}
