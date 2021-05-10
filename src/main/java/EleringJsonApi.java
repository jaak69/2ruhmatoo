import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class EleringJsonApi {
    private String start;
    // private String startTime;
    private String end;
    //private String endTime;
    private String restEndPoint;
    private String baseUrl="https://dashboard.elering.ee";
    private URL url;

    public EleringJsonApi(String restEndPoint) {
        this.restEndPoint = restEndPoint;
    }
    //setter Elering API end point muutmiseks
    public void setRestEndPoint(String restEndPoint) {
        this.restEndPoint = restEndPoint;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    private void setEleringApiUrl(){

        String urlString;
        urlString =
                this.baseUrl + this.restEndPoint + "?" + "start=" + URLEncoder.encode(this.start) + "&end=" + URLEncoder.encode(this.end);
        //System.out.println(urlString);
        try{
            this.url = new URL(urlString);
        }catch (MalformedURLException e) {
            System.err.println("URL oli vigane");
            System.err.println("Väljastatud viga: " + e.getMessage());
        }
    }

    public JSONObject getEleringData() throws IOException, ParseException {

        setEleringApiUrl();

        HttpURLConnection conn = (HttpURLConnection) this.url.openConnection();
        //Elering API nõuab GET meetodit
        conn.setRequestMethod("GET");
        conn.connect();

        //Testi kas ühendus on korras HTTP kood 200
        int responsecode = conn.getResponseCode();

        if (responsecode != 200) {
            throw new RuntimeException("Http vastuskood vea korral: " + responsecode);
        } else {
            String inline = "";
            Scanner scanner = new Scanner(url.openStream());

            //Kirjuta kõik andmed JSON stringi
            while (scanner.hasNext()) {
                inline += scanner.nextLine();
            }

            //Sulge scanner
            scanner.close();

//            Using the JSON simple library parse the string into a json object
            JSONParser parse = new JSONParser();
            JSONObject data_obj = (JSONObject) parse.parse(inline);
            return data_obj;
        }
    }
}
