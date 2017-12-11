package univaq.apppub.util.Foundation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import univaq.apppub.model.Categoria;
import univaq.apppub.model.Piatto;

/**
 * Created by Gioele on 10/12/2017.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "PubDB";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DataBaseGenerator dataBaseGenerator = new DataBaseGenerator();
        List<Categoria> categorie ;
        String CREATE_TABLE_CATEGORIE = "CREATE TABLE categorie(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT," +
                "descrizione TEXT," +
                "img INTEGER)";

        String CREATE_TABLE_PIATTI =  "CREATE TABLE piatti(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT," +
                "descrizione TEXT," +
                "img INTEGER," +
                "id_categoria INTEGER," +
                " FOREIGN KEY(id_categoria) REFERENCES categorie(id))";


        // create books table
        db.execSQL(CREATE_TABLE_CATEGORIE);
        db.execSQL(CREATE_TABLE_PIATTI);

        addCategorie(db,dataBaseGenerator.generateData());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS categorie");

        // create fresh books table
        this.onCreate(db);
    }

    public void addCategorie(SQLiteDatabase db,List<Categoria> categorie){

        // 2. create ContentValues to add key "column"/value
        ContentValues valuesPiatto = new ContentValues();
        ContentValues valuesCategorie = new ContentValues();

        for (Categoria categoria:categorie) {
            valuesCategorie.clear();
            valuesCategorie.put("nome",categoria.getNome());
            valuesCategorie.put("descrizione",categoria.getDescrizione());
            valuesCategorie.put("img",categoria.getImg());
            db.insert("categorie",null,valuesCategorie);
            for (Piatto piatto: categoria.getPiatti()) {
                valuesPiatto.put("nome",piatto.getNome());
                valuesPiatto.put("descrizione",piatto.getDescrizione());
                valuesPiatto.put("img",piatto.getImg());
                valuesPiatto.put("id_categoria",categoria.getId());
                db.insert("piatti",null,valuesPiatto);
                valuesPiatto.clear();
            }
        }
    }

    public void addCategoria(Categoria categoria){
        //for logging
        Log.d("addBook", categoria.getNome());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        ContentValues valuesPiatto = new ContentValues();

        values.put("nome", categoria.getNome());
        values.put("descrizione",categoria.getDescrizione());
        values.put("img",categoria.getImg());
        for (Piatto piatto: categoria.getPiatti()) {
            valuesPiatto.put("nome",piatto.getNome());
            valuesPiatto.put("descrizione",piatto.getDescrizione());
            valuesPiatto.put("img",piatto.getImg());
            db.insert("piatti",null,valuesPiatto);
            valuesPiatto.clear();
        }

        db.insert("categorie", null, values);

        // 4. close
        db.close();
    }

    public List<Categoria> getCategorie(){
        List<Categoria> categorie = new LinkedList<Categoria>();

        // 1. build the query
        String query = "SELECT  * FROM categorie" ;
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        // 3. go over each row, build book and add it to list
        Categoria categoria = null;
        if (cursor.moveToFirst()) {
            do {
                categoria = new Categoria();
                categoria.setId(Integer.parseInt(cursor.getString(0)));
                categoria.setNome(cursor.getString(1));
                categoria.setDescrizione(cursor.getString(2));
                categoria.setImg(Integer.parseInt(cursor.getString(3)));
                // Add book to books
                categorie.add(categoria);
            } while (cursor.moveToNext());
        }
        return categorie;
    }

    public List<Piatto> getPiatti(int idCategoria){
        List<Piatto> piatti = new ArrayList<>();

        String query = "SELECT * FROM piatti WHERE id_categoria="+idCategoria ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Piatto piatto = null;

        if (cursor.moveToFirst()) {
            do {
                piatto = new Piatto();
                piatto.setId(Integer.parseInt(cursor.getString(0)));
                piatto.setNome(cursor.getString(1));
                piatto.setDescrizione(cursor.getString(2));
                piatto.setImg(Integer.parseInt(cursor.getString(3)));

                piatti.add(piatto);
            } while (cursor.moveToNext());
        }

        return piatti;
    }

}
