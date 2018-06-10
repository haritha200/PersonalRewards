package saulmm.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import java.util.UUID;

import database.AppDbHelper;
import database.AppDbSchema.ProductTable;

public class ProductLab {
    private static ProductLab sProductLab;
    private ArrayList<ProductObject> mProducts;
    private Context mContext;
    private SQLiteDatabase mSQLiteDatabase;

    public static ProductLab get(Context context) {
        if(sProductLab==null){
            sProductLab=new ProductLab(context);
        }
        return sProductLab;
    }

    private ProductLab(Context context) {     //private constructor => only this class can create the object.
        mSQLiteDatabase = new AppDbHelper(context).getWritableDatabase();
        mContext= context;
        mProducts = new ArrayList<ProductObject>();
        Log.i("crimelab: ", "inserted uid");
    }

    public void updateProduct(ProductObject c){
        ContentValues values = getContentValues(c); //ie in onPause of CrimeFragment
        mSQLiteDatabase.update(ProductTable.NAME, values,
                ProductTable.Cols.PRODUCTID+ "= ?",
                new String[]
                        {c.getId().toString()}
        );
    }

    public void addProduct(ProductObject c){
        if(c!=null){
            ContentValues values= getContentValues(c);
            mSQLiteDatabase.insert(ProductTable.NAME,null,values);
        }
    }

    private ContentValues getContentValues(ProductObject c) {
        ContentValues values =  new ContentValues();
        values.put(ProductTable.Cols.PRODUCTID, String.valueOf(c.getId()));
        values.put(ProductTable.Cols.PRODUCTIMAGE, c.getImagePath());
        values.put(ProductTable.Cols.PRODUCTNAME, c.getName());
        values.put(ProductTable.Cols.PRODUCTPOINT, String.valueOf(c.getPoint()));
        values.put(ProductTable.Cols.PRODUCTPOINTSUM, String.valueOf(c.getPointSum()));
        return values;
    }

    public ArrayList<ProductObject> getProducts() {  //return as a List, so we can change or datastructure to Linkedlist or something if we need to in the future
        String query= "SELECT * FROM " + ProductTable.NAME + " ORDER BY  "+ProductTable.Cols.PRODUCTPOINT+" DESC";
        Cursor cursor= mSQLiteDatabase.rawQuery(query,null);
        ArrayList<ProductObject> products= new ArrayList<ProductObject>();

        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                products.add(packProductObject(cursor));
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return products;
    }

    public ProductObject getProduct(UUID id){
        if(id==null || id.toString().isEmpty()) return null;
        Cursor cursor= mSQLiteDatabase.query(ProductTable.NAME,     //get cursor
                null,       //null here means select all columns
                ProductTable.Cols.PRODUCTID+"=?",
                new String[] {id.toString()} ,
                null,
                null,
                null
        );
        //use try block mostly because ummm <not sure> but so that 'finally' has cursor.close() ?
        try{
            if(cursor.getCount() !=0) {
                cursor.moveToFirst();       //IMPORTANT. MOVE TO FIRST ROW OF ALL RETRIEVED ROWS, (here 1 row only)
                //unpack from table and into Crime Object
                ProductObject c= packProductObject(cursor);
                return c;
            }
        } finally {
            cursor.close();     //IMPORTANT TO CLOSE YOUR CURSOR
        }
        return null;
    }

    public ProductObject packProductObject(Cursor cursor){
        int productpoint = cursor.getInt(cursor.getColumnIndex(ProductTable.Cols.PRODUCTPOINT));
        String productname=cursor.getString(cursor.getColumnIndex(ProductTable.Cols.PRODUCTNAME));
        String productImage= cursor.getString(cursor.getColumnIndex(ProductTable.Cols.PRODUCTIMAGE));
        int productPointSum = cursor.getInt(cursor.getColumnIndex(ProductTable.Cols.PRODUCTPOINTSUM));
        String productId=cursor.getString(cursor.getColumnIndex(ProductTable.Cols.PRODUCTID));

        //pack into Crime object
        ProductObject c= new ProductObject(productname,productImage,productpoint,productPointSum);  //create crime obj with details from the table's cursor
        c.setPointSum(productPointSum);
        c.setPoint(productpoint);
        c.setImagePath(productImage);
        c.setName(productname);
        c.setId(UUID.fromString(productId));
        return c;
    }
}
