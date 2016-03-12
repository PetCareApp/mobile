package cap7.com.br.petcare.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Admin on 02/03/2016.
 */
public class DBDao {

    protected SQLiteDatabase database;
    protected DatabaseHelper dbHelper;
    private Context mContext;


    public DBDao(Context context) {
        this.mContext = context;
        dbHelper = DatabaseHelper.getHelper(mContext);
        open();
    }

    public void open() throws SQLException {
        if(dbHelper == null)
            dbHelper = DatabaseHelper.getHelper(mContext);
        database = dbHelper.getWritableDatabase();
    }

    public SQLiteDatabase readableDB() throws SQLException {
        if(dbHelper == null)
            dbHelper = DatabaseHelper.getHelper(mContext);
        database = dbHelper.getReadableDatabase();
        return database;
    }


}
