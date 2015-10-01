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

    //---Nombres de la Tabla---
    public static final String TABLENAME_GASTOS = "gastos";

    //---Campos---
    public static final String KEY_ID = "_id";
    public static final String KEY_DESC = "descripcion";
    public static final String KEY_CANTIDAD = "cantidad";

    //---Scripting---
    static final String DATABASE_CREATE =
            "CREATE TABLE " + TABLENAME_GASTOS + " ("
                    + KEY_ID + " integer primary key autoincrement, "
                    + KEY_DESC + " text not null, "
                    + KEY_CANTIDAD + " money not null);";

    /**
     * CREATE TABLE contacts ( _id  integer primary key autoincrement, name  text not null,
     * email  text not null);
     */
    SQLiteDatabase db;
    public GastosDB(SQLiteDatabase db){
        this.db = db;
    }
    //---Inserta una constante a la base de datos---
    public long insertGasto(String descripcion, Money gasto)
    {
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

        if (mCursor != null){
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
    public boolean updateGasto(long rowId, String descripcion, Money gasto) {
        ContentValues args = new ContentValues();
        args.put(KEY_DESC, descripcion);
        args.put(KEY_CANTIDAD, gasto);
        return db.update(TABLENAME_GASTOS, args, KEY_ID + "=" + rowId, null) > 0;
    }
}
