package mx.lania.registrogastos.controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import mx.lania.registrogastos.database.DBAdapter;

/**
 * Created by jaguilera on 26/09/2015.
 */

public class GastosController {

    public static boolean insertGasto(Context context, String descripcion, Money gasto){
        DBAdapter db = new DBAdapter(context);
        db.open();
        boolean isInserted = db.insertContact(descripcion, gasto);
        db.close();
        return isInserted;
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
