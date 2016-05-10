package cap7.com.br.petcare.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cap7.com.br.petcare.Util.ScriptDB;

/**
 * Created by Virginia on 02/03/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Current version of database
    private static final int DATABASE_VERSION = 5;

    private static DatabaseHelper instance;


    public static synchronized DatabaseHelper getHelper(Context context) {
        if (instance == null)
            instance = new DatabaseHelper(context);
        return instance;
    }

    private DatabaseHelper(Context context) {
        super(context, ScriptDB.DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ScriptDB.CREATE_TABLE_ANIMAL);
        db.execSQL(ScriptDB.CREATE_TABLE_PROPRIETARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ScriptDB.TAB_PROPRIETARIO_NOVO);
        db.execSQL("DROP TABLE IF EXISTS " + ScriptDB.TAB_ANIMAL);
        onCreate(db);
    }
}
