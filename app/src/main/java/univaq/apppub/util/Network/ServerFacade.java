package univaq.apppub.util.Network;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
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
import java.net.URL;

import univaq.apppub.controller.MainActivity;
import univaq.apppub.model.Categoria;
import univaq.apppub.model.Menu;
import univaq.apppub.model.Piatto;
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
        new GetJson_MenuVersion().execute("https://appub.herokuapp.com/api/getMenu");
    }

    public void getMenu(){
        new GetMenu().execute("https://appub.herokuapp.com/api/getMenu");
    }

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
                File imgFile = new File(sdCardRoot + "/appPub", fileName+".jpeg");
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
            return fileName+".jpeg";
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
            if (jsonStr != null) {
                stringaJson = jsonStr;
            }
            new SaveImage().execute("https://www.codeproject.com/KB/GDI-plus/ImageProcessing2/img.jpg");
            return stringaJson;
        }

        @Override
        protected void onPostExecute(String result) {
            System.out.println(result);
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
                        Categoria categoria = new Categoria(Integer.parseInt(id),nome,descrizione,immagine);

                        JSONArray p = c.getJSONArray("piatti");
                        for (int j = 0; j < p.length(); j++) {
                            String id_piatto = c.getString("id");
                            String nome_piatto = c.getString("nome");
                            String descrizione_piatto = c.getString("descrizione");
                            String immagine_piatto = c.getString("immagine");
                            Piatto piatto = new Piatto(Integer.parseInt(id_piatto),nome_piatto,descrizione_piatto,immagine_piatto,2);
                            categoria.aggiungiPiatto(piatto);
                        }
                        menu.aggiungiCategoria(categoria);
                    }

                    MySQLiteHelper helper = new MySQLiteHelper(context);

                    System.out.println("qaaaa");
                    helper.addMenu(menu);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            new SaveImage().execute("https://www.codeproject.com/KB/GDI-plus/ImageProcessing2/img.jpg");
            return stringaJson;
        }
    }


}



