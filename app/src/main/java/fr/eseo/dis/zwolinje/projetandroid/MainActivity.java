package fr.eseo.dis.zwolinje.projetandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import fr.eseo.dis.zwolinje.projetandroid.data.InputStreamOperations;
import fr.eseo.dis.zwolinje.projetandroid.model.Logon;

public class MainActivity extends AppCompatActivity {

    public Logon getLogon(EditText username, EditText password){

        Logon logon = new Logon();


        try {
            String myurl= "https://192.168.4.248/www/pfe/webservice.php?q=LOGON&user=" + username +"&pass="
                    + password;
            URL url = new URL(myurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            /*
             * InputStreamOperations est une classe complémentaire:
             * Elle contient une méthode InputStreamToString.
             */
            String result = InputStreamOperations.InputStreamToString(inputStream);

            // On récupère le JSON complet
            JSONObject jsonObject = new JSONObject(result);
            // On récupère le tableau d'objets qui nous concernent
            JSONArray array = new JSONArray(jsonObject);
            // Pour tous les objets on récupère les infos
            for (int i = 0; i < array.length(); i++) {
                // On récupère un objet JSON du tableau
                JSONObject obj = new JSONObject(array.getString(i));
                // On fait le lien Logon - Objet JSON
                logon.setResult(obj.getString("result"));
                logon.setApi(obj.getString("api"));
                logon.setToken(obj.getString("token"));


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // On retourne la liste des personnes
        return logon;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText username = (EditText)findViewById(R.id.username);
        EditText password = (EditText)findViewById(R.id.password);
        Button showSecondActivityButton = findViewById(R.id.show_button);

    }
}
