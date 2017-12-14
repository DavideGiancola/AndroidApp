package univaq.apppub.util.Network;

import android.app.ProgressDialog;
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


/**
 * Created by Gioele on 12/12/2017.
 */

public class ServerFacade  {

    public static ServerFacade Istance = null;


    public static ServerFacade getInstance(){

        if(Istance == null){
            Istance = new ServerFacade();
        }
        return  Istance;
    }

    public void saveImg(String url){
        new SaveImage().execute(url);
    }

    public void getMenuVersion(){
        new GetJson_MenuVersion().execute("https://appub.herokuapp.com/api/getCategorie");
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
}



