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
                "img TEXT," +
                "id_menu INTEGER," +
                "FOREIGN KEY(id_menu) REFERENCES menu(id))";

        String CREATE_TABLE_PIATTI =  "CREATE TABLE piatti(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT," +
                "descrizione TEXT," +
                "img TEXT," +
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


        addEventi(db,dataBaseGenerator.generaEventi());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS piatti");
        db.execSQL("DROP TABLE IF EXISTS categorie");
        db.execSQL("DROP TABLE IF EXISTS menu");
        db.execSQL("DROP TABLE IF EXISTS eventi");

        // create fresh books table
        this.onCreate(db);
    }


    public int getMenuVersion(){
        String query = "SELECT versione_menu FROM menu";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int versione_menu = 0;
        if (cursor.moveToFirst()) {
            do {
                versione_menu = Integer.parseInt(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return versione_menu;
    }


    public void addMenu(Menu menu){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valuesMenu = new ContentValues();
        ContentValues valuesCategorie = new ContentValues();
        ContentValues valuesPiatti = new ContentValues();

        valuesMenu.put("versione_menu",menu.getVersion());
        valuesMenu.put("id",menu.getId());
        db.insert("menu",null,valuesMenu);

        List<Categoria> categorie = menu.getCategorie();

        for (Categoria categoria: categorie) {
            valuesCategorie.put("id",categoria.getId());
            valuesCategorie.put("nome",categoria.getNome());
            valuesCategorie.put("descrizione",categoria.getDescrizione());
            valuesCategorie.put("img",categoria.getImg());
            valuesCategorie.put("id_menu",menu.getId());
            db.insert("categorie",null,valuesCategorie);
            valuesCategorie.clear();
            List<Piatto> piatti = categoria.getPiatti();
            for (Piatto piatto:piatti) {
                valuesPiatti.put("id",piatto.getId());
                valuesPiatti.put("nome",piatto.getNome());
                valuesPiatti.put("descrizione",piatto.getDescrizione());
                valuesPiatti.put("img",piatto.getImg());
                valuesPiatti.put("id_categoria",categoria.getId());
                valuesPiatti.put("prezzo",piatto.getPrezzo());
                db.insert("piatti",null,valuesPiatti);
                valuesPiatti.clear();
            }
        }
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
                categoria.setImg(cursor.getString(3));
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
                piatto.setImg(cursor.getString(3));
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
