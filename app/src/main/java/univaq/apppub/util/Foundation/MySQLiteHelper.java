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
import univaq.apppub.model.Schedario;

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

        String CREATE_TABLE_SCHEDARIO = "CREATE TABLE schedario(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "versione_schedario INTEGER)";

        String CREATE_TABLE_MENU = "CREATE TABLE menu(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "versione_menu INTEGER)";


        String CREATE_TABLE_CATEGORIE = "CREATE TABLE categorie(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT," +
                "descrizione TEXT," +
                "img TEXT," +
                "id_menu INTEGER," +
                "order_categorie INTEGER," +
                "FOREIGN KEY(id_menu) REFERENCES menu(id))";

        String CREATE_TABLE_PIATTI =  "CREATE TABLE piatti(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT," +
                "descrizione TEXT," +
                "img TEXT," +
                "id_categoria INTEGER," +
                "prezzo TEXT," +
                "aggiunte INTEGER," +
                "order_piatti INTEGER," +
                "FOREIGN KEY(id_categoria) REFERENCES categorie(id))";

        String CREATE_TABLE_EVENTI =  "CREATE TABLE eventi(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT," +
                "data TEXT," +
                "oraInizio TEXT," +
                "oraFine TEXT," +
                "descrizione TEXT," +
                "img TEXT)";


        // create books table
        db.execSQL(CREATE_TABLE_SCHEDARIO);
        db.execSQL(CREATE_TABLE_MENU);
        db.execSQL(CREATE_TABLE_CATEGORIE);
        db.execSQL(CREATE_TABLE_PIATTI);
        db.execSQL(CREATE_TABLE_EVENTI);


       // addEventi(db,dataBaseGenerator.generaEventi());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS piatti");
        db.execSQL("DROP TABLE IF EXISTS categorie");
        db.execSQL("DROP TABLE IF EXISTS menu");
        db.execSQL("DROP TABLE IF EXISTS eventi");
        db.execSQL("DROP TABLE IF EXISTS schedario");

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
            System.out.println(categoria.getNome());
            valuesCategorie.put("id",categoria.getId());
            valuesCategorie.put("nome",categoria.getNome());
            valuesCategorie.put("descrizione",categoria.getDescrizione());
            valuesCategorie.put("img",categoria.getImg());
            valuesCategorie.put("id_menu",menu.getId());
            valuesCategorie.put("order_categorie",categoria.getOrder());
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
                valuesPiatti.put("order_piatti",piatto.getOrder());
                if(piatto.isAggiunte()){
                    valuesPiatti.put("aggiunte",1);
                }else {
                    valuesPiatti.put("aggiunte",0);
                }
                db.insert("piatti",null,valuesPiatti);
                valuesPiatti.clear();
            }
        }
    }

    public List<Categoria> getCategorie(){
        List<Categoria> categorie = new LinkedList<Categoria>();

        // 1. build the query
        String query = "SELECT * FROM categorie ORDER BY order_categorie ASC" ;
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
                categoria.setOrder(cursor.getInt(5));
                // Add book to books
                categorie.add(categoria);
            } while (cursor.moveToNext());
        }
        return categorie;
    }

    public List<Piatto> getPiatti(int idCategoria){
        List<Piatto> piatti = new ArrayList<>();

        String query = "SELECT * FROM piatti WHERE id_categoria="+idCategoria+" ORDER BY order_piatti ASC" ;
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
                piatto.setPrezzo(cursor.getString(5));
                int aggiunte = cursor.getInt(6);
                if (aggiunte == 1){
                    piatto.setAggiunte(true);
                }else {
                    piatto.setAggiunte(false);
                }
                piatto.setOrder(cursor.getInt(7));
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
                evento.setImg(cursor.getString(6));
                eventi.add(evento);
            } while (cursor.moveToNext());
        }
        return eventi;
    }

    public int getSchedarioVersion() {
        String query = "SELECT versione_schedario FROM schedario";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int versione_schedario = 0;

        if (cursor.moveToFirst()) {
            do {
                versione_schedario = Integer.parseInt(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        return versione_schedario;
    }

    public void addSchedario(Schedario schedario) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valuesSchedario= new ContentValues();
        ContentValues valuesEventi = new ContentValues();

        valuesSchedario.put("versione_schedario",schedario.getVersion());
        valuesSchedario.put("id",schedario.getId());
        db.insert("schedario",null,valuesSchedario);

        List<Evento> eventi = schedario.getEventi();


        for (Evento evento: eventi) {
            valuesEventi.put("id",evento.getId());
            valuesEventi.put("nome",evento.getNome());
            valuesEventi.put("data",evento.getData());
            valuesEventi.put("oraInizio",evento.getOraInizio());
            valuesEventi.put("oraFine",evento.getOraFine());
            valuesEventi.put("descrizione",evento.getDescrizione());
            valuesEventi.put("img",evento.getImg());
            db.insert("eventi",null,valuesEventi);
            valuesEventi.clear();
        }
    }
}
