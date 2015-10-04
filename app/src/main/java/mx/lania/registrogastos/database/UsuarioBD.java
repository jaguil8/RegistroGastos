package mx.lania.registrogastos.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by miguel on 04/10/2015.
 */
public class UsuarioBD {

    /*
    CREATE TABLE `usuario` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`login`	TEXT NOT NULL UNIQUE,
	`password`	TEXT NOT NULL
    );
     */


    public static final String TABLENAME_GASTOS = "gastos";

    //---Campos---
    public static final String KEY_ID = "id";
    public static final String KEY_DESC = "descripcion";
    public static final String KEY_CANTIDAD = "cantidad";

    //---Scripting---
    static final String DATABASE_CREATE =
            "CREATE TABLE " + TABLENAME_GASTOS + " ("
                    + KEY_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + KEY_DESC + " TEXT NOT NULL, "
                    + KEY_CANTIDAD + " NUMERIC NOT NULL);";

    /**
     * CREATE TABLE contacts ( _id  integer primary key autoincrement, name  text not null,
     * email  text not null);
     */
    SQLiteDatabase db;

    public UsuarioBD(SQLiteDatabase db) {
        this.db = db;
    }

    //---Inserta una constante a la base de datos---
    public long insertGasto(String descripcion, Double gasto) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_DESC, descripcion);
        contentValues.put(KEY_CANTIDAD, gasto);
        long rowID = db.insert(TABLENAME_GASTOS, null, contentValues);
        return rowID;
    }

    //---Obtiene un contacto en particular---
    public Cursor getGastoByID(long rowId) throws SQLException {
        Cursor mCursor = db.query(
                true,
                TABLENAME_GASTOS,
                new String[]{KEY_ID, KEY_DESC, KEY_CANTIDAD},
                KEY_ID + "=" + rowId,
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
        return db.query(TABLENAME_GASTOS, new String[]{KEY_ID, KEY_DESC,
                KEY_CANTIDAD}, null, null, null, null, null);
    }

    //---Actualiza un gasto---
    public boolean updateGasto(long rowId, String descripcion, Double gasto) {
        ContentValues args = new ContentValues();
        args.put(KEY_DESC, descripcion);
        args.put(KEY_CANTIDAD, gasto);
        return db.update(TABLENAME_GASTOS, args, KEY_ID + "=" + rowId, null) > 0;
    }
}
