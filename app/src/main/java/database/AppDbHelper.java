package database;

/**
 * Created by haritha on 9/6/18.
 */


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import database.AppDbSchema.SummaryTable;
import database.AppDbSchema.ProductTable;

public class AppDbHelper extends SQLiteOpenHelper {

    public static final int VERSION= 1;
    public static final String DATABASENAME= "appdb.db";  //for the constructor

    // private static final String DATABASE_ALTER_APP_1 = "ALTER TABLE "
//            + AppTable.NAME + " ADD COLUMN " + AppTable.Cols.TIMEUSEDLIST + " string;";

    public AppDbHelper(Context context) {
        super(context, DATABASENAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //CAREFUL WITH THE COMMAS AND SPACING !!
        db.execSQL("create table "+SummaryTable.NAME+" ("+
                " _id integer primary key autoincrement, "+
                SummaryTable.Cols.TOTALPOINTS+","+
                SummaryTable.Cols.DATE+")"
        );

        db.execSQL("create table "+ ProductTable.NAME+" ("+
                " _id integer primary key autoincrement, "+
                ProductTable.Cols.PRODUCTID+","+
                ProductTable.Cols.PRODUCTIMAGE+","+
                ProductTable.Cols.PRODUCTNAME+","+
                ProductTable.Cols.PRODUCTPOINT+","+
                ProductTable.Cols.PRODUCTPOINTSUM+")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*    if (oldVersion < 2) {
                db.execSQL(DATABASE_ALTER_APP_1);
            } */

        //   db.execSQL("DROP TABLE IF EXISTS " + AppTable.NAME);
        //  onCreate(db);
    }
}

