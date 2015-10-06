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


    public static final String TABLENAME_USUARIOS = "gastos";

    //---Campos---
    public static final String KEY_ID = "id";
    public static final String KEY_LOGIN = "login";
    public static final String KEY_PASSWORD = "password";

    //---Scripting---
    static final String TABLE_CREATE =
            "CREATE TABLE " + TABLENAME_USUARIOS + " ("
                    + KEY_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + KEY_LOGIN + " TEXT NOT NULL UNIQUE, "
                    + KEY_PASSWORD + " TEXT NOT NULL);";

    /**
     * CREATE TABLE contacts ( _id  integer primary key autoincrement, name  text not null,
     * email  text not null);
     */
    SQLiteDatabase db;

    public UsuarioBD(SQLiteDatabase db) {
        this.db = db;
    }

    //---Inserta un usuario a la base de datos---
    public long insertUsuario(String login, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_LOGIN, login);
        contentValues.put(KEY_PASSWORD, password);
        long rowID = db.insert(TABLENAME_USUARIOS, null, contentValues);
        return rowID;
    }

    //---Obtiene un usuario en particular---
    public Cursor getUsuarioByID(long rowId) throws SQLException {
        Cursor mCursor = db.query(
                true,
                TABLENAME_USUARIOS,
                new String[]{KEY_ID, KEY_LOGIN, KEY_PASSWORD},
                KEY_ID + "=" + rowId,
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;
    }

    //---Borra un usuario en particular---
    public boolean deleteUsuario(long rowId) {
        return db.delete(TABLENAME_USUARIOS, KEY_ID + "=" + rowId, null) > 0;
    }

    //---Regresa todos los usuarios---
    public Cursor getAllUsuarios() {
        return db.query(TABLENAME_USUARIOS, new String[]{KEY_ID, KEY_LOGIN,
                KEY_PASSWORD}, null, null, null, null, null);
    }

    //---Actualiza un usuario---
    public boolean updateUsuario(long rowId, String login, String password) {
        ContentValues args = new ContentValues();
        args.put(KEY_LOGIN, login);
        args.put(KEY_PASSWORD, password);
        return db.update(TABLENAME_USUARIOS, args, KEY_ID + "=" + rowId, null) > 0;
    }
}
