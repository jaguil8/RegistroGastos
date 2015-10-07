package mx.lania.registrogastos.controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

import mx.lania.registrogastos.database.DBAdapter;

/**
 * Created by jaguilera on 26/09/2015.
 */

public class GastosController {

    public static boolean insertGasto(Context context, String descripcion, Double gasto, String fecha,int idUsuario , int tipoGasto){
        DBAdapter db = new DBAdapter(context);
        db.open();
        boolean isInserted = db.insertGasto(descripcion, gasto,fecha,idUsuario,tipoGasto);
        db.close();
        return isInserted;
    }

    public static boolean deleteGasto (Context context, int idGasto)
    {
        DBAdapter db = new DBAdapter(context);
        db.open();
        boolean isDeleted = true;//db.(descripcion, gasto,fecha,idUsuario,tipoGasto);
        db.close();
        return isDeleted;
    }

    public static void displayGasto(Context context, long id) {
        DBAdapter db = new DBAdapter(context);
        db.open();
        String descripcion = db.getGastoByID(id);
        db.close();
        Log.d("DESCRIPCION_GASTO", descripcion);
        Toast.makeText(context, "La Descripción del gasto es: " + descripcion, Toast.LENGTH_LONG).show();
    }
}
