package mx.lania.registrogastos.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jaguilera on 26/09/2015.
 */

public class DBAdapter {

    //---Variables de Base de Datos---
    private static final String DATABASE_NAME = "Gastos";
    private static final int DATABASE_VERSION = 1;
    final Context context;

    //static final String TAG = "DBAdapter";
    DatabaseHelper DBHelper;

    SQLiteDatabase db;

    public DBAdapter(Context context)
    {
        this.context = context;
        DBHelper = new DatabaseHelper(context);
    }

    //---Abre la base de datos---
    public DBAdapter open() throws SQLException{
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---Cierra la base de datos---
    public void close(){
        DBHelper.close();
    }

    /*
    *  SE INICIA PROCEDIMIENTOS DE DTD
    *
    * */
    public boolean insertGasto(String descripcion, Double gasto, String fecha, int idUsuario, int tipoGasto){
        GastosDB gastosDB = new GastosDB(db);
        long id = gastosDB.insertGasto(descripcion, gasto, fecha, idUsuario, tipoGasto);
        return id > 0;
    }

    public boolean insertUsuario(String login,String password){
        UsuarioBD usuarioDB = new UsuarioBD(db);
        long id = usuarioDB.insertUsuario(login, password);
        return id > 0;
    }

    public Boolean existeUsuario(String login){
        UsuarioBD usuarioDB = new UsuarioBD(db);
        Cursor usuario = usuarioDB.getUsuarioByLogin(login);
        return usuario.getString(1).equals("0")?true:false ;
    }

    public boolean deleteGasto(int idGasto){
        GastosDB gastosDB = new GastosDB(db);
        boolean isDeleted = gastosDB.deleteGasto(idGasto);
        return isDeleted;
    }

    public String getGastoByID(long id) {
        GastosDB gastosDB = new GastosDB(db);
        Cursor cursor = gastosDB.getGastoByID(id);
        return cursor.getString(1);
    }

    public String getUsuarioByLogin(String nombre){
        UsuarioBD usuarioBD = new UsuarioBD(db);
        Cursor cursor = usuarioBD.getUsuarioByLogin(nombre);
        return cursor.getString(1);
    }

    public String getPasswordByLogin(String nombre){
        UsuarioBD usuarioBD = new UsuarioBD(db);
        Cursor cursor = usuarioBD.getUsuarioByLogin(nombre);
        return cursor.getString(2);
    }


    /*-------------------------------*/


    private static class DatabaseHelper extends SQLiteOpenHelper
    {

        public DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            //---Se llama cuando se crea por primera vez---
            try {
                db.execSQL(GastosDB.TABLE_CREATE);
                db.execSQL(UsuarioBD.TABLE_CREATE);
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            //---Se llama cuando la versión de la Base de Datos cambia---
            Log.w("DBAdapter", "Upgrading database from version [" + oldVersion +
                    "]" + " to " + "["
                    + newVersion + "], which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + GastosDB.TABLENAME_GASTOS);
            db.execSQL("DROP TABLE IF EXISTS " + UsuarioBD.TABLENAME_USUARIOS);
            /*---Aquí se pueden insertar mecanismos para recuperar datos de las tablas de la versión
            anterior.*/
            onCreate(db);
        }

    }
}


