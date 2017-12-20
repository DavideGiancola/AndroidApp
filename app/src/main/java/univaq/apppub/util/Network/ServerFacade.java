package univaq.apppub.util.Network;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import univaq.apppub.R;
import univaq.apppub.controller.MainActivity;
import univaq.apppub.model.Categoria;
import univaq.apppub.model.Evento;
import univaq.apppub.model.Menu;
import univaq.apppub.model.Piatto;
import univaq.apppub.model.Schedario;
import univaq.apppub.util.Foundation.MySQLiteHelper;


/**
 * Created by Gioele on 12/12/2017.
 */

public class ServerFacade  {


    public Context context;

    public static ServerFacade Istance = null;


    public static ServerFacade getInstance(){

        if(Istance == null){
            Istance = new ServerFacade();
        }
        return  Istance;
    }

    public void setContext(Context context){
        this.context = context;
    }

    public void saveImg(String url){
        new SaveImage().execute(url);
    }

    public void getMenuVersion(){
        new GetJson_MenuVersion().execute("https://appub.herokuapp.com/api/getMenuVersion");
    }

    public void getMenu(){
        new GetMenu().execute("https://appub.herokuapp.com/api/getMenu");
    }

    public void getSchedarioVersion() {
        new GetJson_SchedarioVersion().execute("https://appub.herokuapp.com/api/getSchedarioVersion"); // !!!!!!!!
    }

    private void getSchedario() { new GetSchedario().execute("https://appub.herokuapp.com/api/getEventi"); } //{"schedario":..... !!!!!!!!!!!!!!!!!!!!!!!!


    public class SaveImage extends AsyncTask<String, Void, String> {
        private String saveImage(String Url_image) {
            String stringUrl = Url_image;
            HttpURLConnection urlConnection = null;
            String fileName = "";
            try {
                URL url = new URL(stringUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                File sdCardRoot = Environment.getExternalStorageDirectory().getAbsoluteFile() ;
                fileName = stringUrl.substring(stringUrl.lastIndexOf('/') + 1, stringUrl.length());
                String fileNameWithoutExtn = fileName.substring(0, fileName.lastIndexOf('.'));
                File imgFile = new File(sdCardRoot + "/appPub", fileName);
                if (!sdCardRoot.exists()) {
                    imgFile.createNewFile();
                }
                InputStream inputStream = urlConnection.getInputStream();
                int totalSize = urlConnection.getContentLength();
                FileOutputStream outPut = new FileOutputStream(imgFile);
                int downloadedSize = 0;
                byte[] buffer = new byte[8000];
                int bufferLength = 0;
                while ((bufferLength = inputStream.read(buffer)) > 0) {
                    outPut.write(buffer, 0, bufferLength);
                    downloadedSize += bufferLength;
                }
                outPut.close();
                //if (downloadedSize == totalSize);
                //Toast.makeText(context, "Downloaded" + imgFile.getPath(), Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return fileName;
        }
        @Override
        protected String doInBackground(String... strings) {
            String fileName = saveImage(strings[0]);
            return fileName;
        }
        @Override
        protected void onPostExecute(String s) {
            System.out.println(s);
        }
    }

    public class GetJson_MenuVersion extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... arg0) {
            String stringaJson = null;
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(arg0[0]);
            String MenuVersion = null;
            if (jsonStr != null) {
                JSONObject jsonObj = null;
                try {
                    jsonObj = new JSONObject(jsonStr);
                    MenuVersion = jsonObj.getString("menu_version");
                    System.out.println(MenuVersion);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                MySQLiteHelper helper = new MySQLiteHelper(context);
                SQLiteDatabase db = helper.getWritableDatabase();
                int current_version = helper.getMenuVersion();
                if(Integer.parseInt(MenuVersion) > current_version){
                    System.out.println("versione non aggiornata -- Aggiornamento");
                    helper.onUpgrade(db,current_version,Integer.parseInt(MenuVersion));
                    getMenu();
                }else{
                    System.out.println("versione aggiornata!");
                }


            }
            return stringaJson;
        }

        @Override
        protected void onPostExecute(String result) {
        }
    }

    public class GetMenu extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            String stringaJson = null;
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(strings[0]);
            if (jsonStr != null) {
                JSONObject jsonObj = null;
                try {
                    jsonObj = new JSONObject(jsonStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONArray menu_Json = jsonObj.getJSONArray("menu");
                    Menu menu = new Menu(0,1);

                    for (int i = 0; i < menu_Json.length(); i++) {
                        JSONObject c = menu_Json.getJSONObject(i);

                        String id = c.getString("id");
                        String nome = c.getString("nome");
                        String descrizione = c.getString("descrizione");
                        String immagine = c.getString("immagine");
                        String fileNameIMG = immagine.substring(immagine.lastIndexOf('/') + 1, immagine.length());
                        fileNameIMG = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/appPub/" + fileNameIMG;
                        Categoria categoria = new Categoria(Integer.parseInt(id),nome,descrizione,fileNameIMG);


                        new SaveImage().execute(immagine); // link immagine categoria

                        JSONArray p = c.getJSONArray("piatti");
                        JSONObject a = null;
                        for (int j = 0; j < p.length(); j++) {
                            a = p.getJSONObject(j);
                            String id_piatto = a.getString("id");
                            String nome_piatto = a.getString("nome");
                            String descrizione_piatto = a.getString("descrizione");
                            String immagine_piatto = a.getString("immagine");
                            double prezzo_piatto = Double.parseDouble(a.getString("prezzo"));
                            String fileNameIMGPiatto = immagine_piatto.substring(immagine_piatto.lastIndexOf('/') + 1, immagine_piatto.length());
                            fileNameIMGPiatto = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/appPub/" + fileNameIMGPiatto;

                            Piatto piatto = new Piatto(Integer.parseInt(id_piatto),nome_piatto,descrizione_piatto,fileNameIMGPiatto,prezzo_piatto);
                            new SaveImage().execute(immagine_piatto); // link dell'immagine da aggiungere
                            categoria.aggiungiPiatto(piatto);
                        }
                        menu.aggiungiCategoria(categoria);
                    }

                    MySQLiteHelper helper = new MySQLiteHelper(context);
                    helper.addMenu(menu);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return stringaJson;
        }
    }


    private class GetJson_SchedarioVersion extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... arg0) {
            String stringaJson = null;
            HttpHandler sh = new HttpHandler();

            String jsonStr = sh.makeServiceCall(arg0[0]);


            String SchedarioVersion = null;
            if (jsonStr != null) {


                JSONObject jsonObj = null;
                try {
                    jsonObj = new JSONObject(jsonStr);
                    SchedarioVersion = jsonObj.getString("schedario_version");  //{"schedario_version":2}!!!!!!!!!!!!!!!!!!!!!!
                    System.out.println(SchedarioVersion);

                } catch (JSONException e) {
                    e.printStackTrace();
                }



                MySQLiteHelper helper = new MySQLiteHelper(context);
                SQLiteDatabase db = helper.getWritableDatabase();
                int current_version = helper.getSchedarioVersion();
                if(Integer.parseInt(SchedarioVersion) > current_version){
                    System.out.println("versione non aggiornata -- Aggiornamento");
                    helper.onUpgrade(db,current_version,Integer.parseInt(SchedarioVersion));
                    getSchedario();
                }else{
                    System.out.println("versione aggiornata!");
                }


            }
            return stringaJson;
        }
    }

    private class GetSchedario extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            String stringaJson = null;
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(strings[0]);
            if (jsonStr != null) {
                JSONObject jsonObj = null;
                try {
                    jsonObj = new JSONObject(jsonStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONArray schedario_Json = jsonObj.getJSONArray("evento"/*"schedario"*/); // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    Schedario schedario = new Schedario(0,1);                //{"schedario":..... !!!!!!!!!!!!!!!!!!!!!!!!

                    for (int i = 0; i < schedario_Json.length(); i++) {
                        JSONObject c = schedario_Json.getJSONObject(i);

                        String id = c.getString("id");
                        String nome = c.getString("titolo");
                        String immagine = c.getString("immagine");  // !!!!!!!!!!!!!!!    https:\/\/appub.herokuapp.com\/storage\/mozzarella.jpg  !!!!!!!!!!!
                        String fileNameIMG = immagine.substring(immagine.lastIndexOf('/') + 1, immagine.length());
                        fileNameIMG = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/appPub/" + fileNameIMG;
                        String descrizione = c.getString("descrizione");
                        String data = c.getString("data");
                        String ora_inizio = c.getString("ora_inizio");
                        String ora_fine = c.getString("ora_fine");


                        Evento evento = new Evento(Integer.parseInt(id), nome, data, ora_inizio, ora_fine, descrizione, fileNameIMG);


                        new SaveImage().execute(immagine); // link immagine evento

                        schedario.aggiungiEvento(evento);
                    }

                    MySQLiteHelper helper = new MySQLiteHelper(context);
                    helper.addSchedario(schedario);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return stringaJson;
        }
    }
    
}



