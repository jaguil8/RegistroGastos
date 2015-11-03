package mx.lania.registrogastos.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
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

    public static boolean insertGasto(Context context, String descripcion, Double gasto, String fecha,int idUsuario , String tipoGasto,String latitude,String longitude){
        DBAdapter db = new DBAdapter(context);
        db.open();
        boolean isInserted = db.insertGasto(descripcion, gasto,fecha,idUsuario,tipoGasto,latitude,longitude);
        db.close();
        return isInserted;
    }

    public static boolean deleteGasto (Context context, int idGasto)
    {
        DBAdapter db = new DBAdapter(context);
        db.open();
        boolean isDeleted = db.deleteGasto(idGasto);
        db.close();
        return isDeleted;
    }

  /*  public static void displayGasto(Context context, long id) {
        DBAdapter db = new DBAdapter(context);
        db.open();
        String descripcion = db.getGastoByID(id);
        db.close();
        Log.d("DESCRIPCION_GASTO", descripcion);
        Toast.makeText(context, "La Descripción del gasto es: " + descripcion, Toast.LENGTH_LONG).show();
    }
*/
    public static int getGastoByDescripcionFecha(Context context,String descripcion,String fecha) throws SQLException {
        DBAdapter db = new DBAdapter(context);
        int id=0;
        db.open();
        Cursor c = db.getGastoByDescripcionFecha(descripcion,fecha);
        if(c.moveToFirst()){
            id = Integer.parseInt(c.getString(0));
        }
        db.close();
        return id;
    }

    public static int getGastoByDescripcion(Context context,String descripcion) throws SQLException {
        DBAdapter db = new DBAdapter(context);
        int id=0;
        db.open();
        Cursor c = db.getGastoByDescripcion(descripcion);
        if(c.moveToFirst()){
            id = Integer.parseInt(c.getString(0));
        }
        db.close();
        return id;
    }

    public static String getGastoById(Context context, int id)
    {
        DBAdapter db = new DBAdapter(context);
        String gastoString="";
        db.open();
        Cursor c = db.getGastoByIDCursor(id);
        if(c.moveToFirst()){
            String tipo = c.getString(4);
            if(tipo.equals("1"))
            {
                tipo="Primario";
            }
            else
            {
                tipo="Secundaria";
            }
            gastoString += "Gasto en: "+c.getString(1)+ ". Por: " + c.getString(2) + "Pesos." + " El día: "+ c.getString(3)+ " " +  tipo;
        }
        db.close();
        return gastoString;
    }



    public static List<String> getAllGastosByIdUsuario(Context context,int idUsuario) {
        DBAdapter db = new DBAdapter(context);
        List<String> list = new ArrayList<String>();
        db.open();
        Cursor c = db.getAllGastosByIdUsuario(idUsuario);
        while(c.moveToNext()){
            String descipcion = c.getString(1);
            String gasto = c.getString(2);
            //String fecha = c.getString(3).toString();
            list.add(descipcion + " " + gasto );
        }
        db.close();
        return list;
        //Log.d("DESCRIPCION_GASTO", descripcion);
        //Toast.makeText(context, "La Descripción del gasto es: " + descripcion, Toast.LENGTH_LONG).show();
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
