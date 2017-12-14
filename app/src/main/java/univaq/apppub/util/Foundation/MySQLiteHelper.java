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
import univaq.apppub.model.Evento;
import univaq.apppub.model.Menu;
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

        String CREATE_TABLE_MENU = "CREATE TABLE menu(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "versione_menu INTEGER)";


        String CREATE_TABLE_CATEGORIE = "CREATE TABLE categorie(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT," +
                "descrizione TEXT," +
                "img INTEGER," +
                "id_menu INTEGER," +
                "FOREIGN KEY(id_menu) REFERENCES menu(id))";

        String CREATE_TABLE_PIATTI =  "CREATE TABLE piatti(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT," +
                "descrizione TEXT," +
                "img INTEGER," +
                "id_categoria INTEGER," +
                "prezzo REAL," +
                " FOREIGN KEY(id_categoria) REFERENCES categorie(id))";

        String CREATE_TABLE_EVENTI =  "CREATE TABLE eventi(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT," +
                "data TEXT," +
                "oraInizio TEXT," +
                "oraFine TEXT," +
                "descrizione TEXT," +
                "img INTEGER)";


        // create books table
        db.execSQL(CREATE_TABLE_MENU);
        db.execSQL(CREATE_TABLE_CATEGORIE);
        db.execSQL(CREATE_TABLE_PIATTI);
        db.execSQL(CREATE_TABLE_EVENTI);

        addMenu(db,dataBaseGenerator.generateMenu());
        addCategorie(db,dataBaseGenerator.generateData());
        addEventi(db,dataBaseGenerator.generaEventi());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS categorie");

        // create fresh books table
        this.onCreate(db);
    }

    public void addMenu(SQLiteDatabase db,Menu menu){
        ContentValues valuesMenu = new ContentValues();
        valuesMenu.put("versione_menu",menu.getVersion());

        db.insert("menu",null,valuesMenu);

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
                valuesPiatto.put("prezzo",piatto.getPrezzo());
                db.insert("piatti",null,valuesPiatto);
                valuesPiatto.clear();
            }
        }
    }

    public void addEventi(SQLiteDatabase db,List<Evento> eventi){

        // 2. create ContentValues to add key "column"/value
        ContentValues valuesEvento = new ContentValues();

        for (Evento evento:eventi) {
            valuesEvento.clear();
            valuesEvento.put("nome",evento.getNome());
            valuesEvento.put("data",evento.getData());
            valuesEvento.put("oraInizio", evento.getOraInizio());
            valuesEvento.put("oraFine",evento.getOraFine());
            valuesEvento.put("descrizione",evento.getDescrizione());
            valuesEvento.put("img",evento.getImg());
            db.insert("eventi",null,valuesEvento);

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
        String query = "SELECT * FROM categorie" ;
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
                piatto.setId(cursor.getInt(0));
                piatto.setNome(cursor.getString(1));
                piatto.setDescrizione(cursor.getString(2));
                piatto.setImg(cursor.getInt(3));
                piatto.setPrezzo(cursor.getDouble(5));

                piatti.add(piatto);
            } while (cursor.moveToNext());
        }

        return piatti;
    }


    public List<Evento> getEventi() {
        List<Evento> eventi = new LinkedList<Evento>();

        // 1. build the query
        String query = "SELECT * FROM eventi" ;
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        // 3. go over each row, build book and add it to list
        Evento evento = null;
        if (cursor.moveToFirst()) {
            do {
                evento = new Evento();
                evento.setId(Integer.parseInt(cursor.getString(0)));
                evento.setNome(cursor.getString(1));
                evento.setData(cursor.getString(2));
                evento.setOraInizio(cursor.getString(3));
                evento.setOraFine(cursor.getString(4));
                evento.setDescrizione(cursor.getString(5));
                evento.setImg(Integer.parseInt(cursor.getString(6)));
                eventi.add(evento);
            } while (cursor.moveToNext());
        }
        return eventi;
    }
}
