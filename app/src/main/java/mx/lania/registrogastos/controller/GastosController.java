package mx.lania.registrogastos.controller;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.lania.registrogastos.database.DBAdapter;

/**
 * Created by jaguilera on 26/09/2015.
 */

public class GastosController {

    public static boolean insertGasto(Context context, String descripcion, Double gasto, String fecha,int idUsuario , String tipoGasto){
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

    public static List<String> getAllGastos(Context context) {
        DBAdapter db = new DBAdapter(context);
        List<String> list = new ArrayList<String>();
        db.open();
        Cursor c = db.getAllGastos();
        while(c.moveToNext()){
            String descipcion = c.getString(1);
            String gasto = c.getString(2);
            String fecha = c.getString(3);
            list.add(descipcion + " " + gasto);
        }
        db.close();
        return list;
        //Log.d("DESCRIPCION_GASTO", descripcion);
        //Toast.makeText(context, "La Descripción del gasto es: " + descripcion, Toast.LENGTH_LONG).show();
    }
}
