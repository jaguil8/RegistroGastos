package mx.lania.registrogastos.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by jaguilera on 26/09/2015.
 * Esta clase contendrá el Schema relacionado a la Tabla: Gastos
 */

public class GastosDB {


    /*
    CREATE TABLE `gastos` (
        `id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
        `descripcion`	TEXT NOT NULL,
        `cantidad`	NUMERIC NOT NULL,
        `idusuario`	INTEGER NOT NULL,
        `tipo_gasto`	INTEGER NOT NULL DEFAULT 0,
        `fecha`	TEXT NOT NULL,
        FOREIGN KEY(`idusuario`) REFERENCES usuario
    );
     */
    //---Nombres de la Tabla---
    public static final String TABLENAME_GASTOS = "gastos";

    //---Campos---
    public static final String KEY_ID = "id";
    public static final String KEY_DESC = "descripcion";
    public static final String KEY_CANTIDAD = "cantidad";
    public static final String KEY_ID_USUARIO = "idusuario";
    public static final String KEY_TIPO_GASTO = "tipogasto";
    public static final String KEY_FECHA = "fecha";

    //---Scripting---
    static final String TABLE_CREATE =
            "CREATE TABLE " + TABLENAME_GASTOS + " ("
                    + KEY_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + KEY_DESC + " TEXT NOT NULL, "
                    + KEY_CANTIDAD + " NUMERIC NOT NULL, "
                    + KEY_ID_USUARIO + " INTEGER NOT NULL, "
                    + KEY_TIPO_GASTO + " Text NOT NULL, "
                    + KEY_FECHA + " TEXT NOT NULL, "
                    + " FOREIGN KEY(`idusuario`) REFERENCES usuario "
                    +");";

    /**
     * CREATE TABLE contacts ( _id  integer primary key autoincrement, name  text not null,
     * email  text not null);
     */
    SQLiteDatabase db;

    public GastosDB(SQLiteDatabase db) {
        this.db = db;
    }

    //---Inserta un gasto a la base de datos---
    public long insertGasto(String descripcion, Double gasto, String fecha, int idUsuario, String tipoGasto) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_DESC, descripcion);
        contentValues.put(KEY_CANTIDAD, gasto);
        contentValues.put(KEY_FECHA, fecha);
        contentValues.put(KEY_ID_USUARIO, idUsuario);
        contentValues.put(KEY_TIPO_GASTO, tipoGasto);
        long rowID = db.insert(TABLENAME_GASTOS, null, contentValues);
        return rowID;
    }

    //---Obtiene un contacto en particular---
    public Cursor getGastoByID(long rowId) throws SQLException {
        Cursor mCursor = db.query(
                true,
                TABLENAME_GASTOS,
                new String[]{KEY_ID, KEY_DESC, KEY_CANTIDAD, KEY_FECHA, KEY_ID_USUARIO, KEY_TIPO_GASTO},
                KEY_ID + "=" + rowId,
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;
    }


    public Cursor getGastoByDescripcionFecha(String descripcion,String fecha) throws SQLException {
        Cursor mCursor = db.query(
                true,
                TABLENAME_GASTOS,
                new String[]{KEY_ID, KEY_DESC, KEY_CANTIDAD,KEY_FECHA,KEY_ID_USUARIO,KEY_TIPO_GASTO},
                KEY_DESC + "='" + descripcion + "'" + " and " + KEY_FECHA + " ='"+ fecha +"'",
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;
    }

    public Cursor getGastoByDescripcion(String descripcion) throws SQLException {
        Cursor mCursor = db.query(
                true,
                TABLENAME_GASTOS,
                new String[]{KEY_ID, KEY_DESC, KEY_CANTIDAD,KEY_FECHA,KEY_ID_USUARIO,KEY_TIPO_GASTO},
                KEY_DESC + "='" + descripcion + "'" ,
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;
    }

    //---Borra un gasto en particular---
    public boolean deleteGasto(long rowId) {
        return db.delete(TABLENAME_GASTOS, KEY_ID + "=" + rowId, null) > 0;
    }

    //---Regresa todos los gastos---
    public Cursor getAllGastos() {
        return db.query(TABLENAME_GASTOS, new String[]{KEY_ID, KEY_DESC, KEY_CANTIDAD,KEY_FECHA,KEY_ID_USUARIO,KEY_TIPO_GASTO},
                null, null, null, null, null);
    }

    //---Regresa todos los gastos---
    public Cursor getAllGastosByIdUsuario(int idUsuario) {
        return db.query(TABLENAME_GASTOS, new String[]{KEY_ID, KEY_DESC, KEY_CANTIDAD,KEY_FECHA,KEY_ID_USUARIO,KEY_TIPO_GASTO},
                KEY_ID_USUARIO + "=" + idUsuario, null, null, null, null);
    }


    //---Actualiza un gasto---
    public boolean updateGasto(long rowId, String descripcion, Double gasto, String fecha, int idUsuario, int tipoGasto) {
        ContentValues args = new ContentValues();
        args.put(KEY_DESC, descripcion);
        args.put(KEY_CANTIDAD, gasto);
        args.put(KEY_FECHA, fecha);
        args.put(KEY_ID_USUARIO, idUsuario);
        args.put(KEY_TIPO_GASTO, tipoGasto);
        return db.update(TABLENAME_GASTOS, args, KEY_ID + "=" + rowId, null) > 0;
    }
}
