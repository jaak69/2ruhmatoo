import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class Päevahind {
    private String riik;
    private String perioodiAlgus;
    private String perioodiLõpp;
    private int minHind;
    private int maxHind;
    private int keskmineHind;
    private List<Elektrihind> elektriHinnad;
    private JSONObject data;
    private String restEndPoint = "/api/nps/price";

    /*public Päevahind(String riik, String perioodiAlgus, String perioodiLõpp, int minHind, int maxHind, int keskmineHind, List<Elektrihind> elektriHinnad, String restEndPoint) {
        this.riik = riik;
        this.perioodiAlgus = perioodiAlgus + " 00:00";
        this.perioodiLõpp = perioodiLõpp + " 23:59";
        this.minHind = minHind;
        this.maxHind = maxHind;
        this.keskmineHind = keskmineHind;
        this.elektriHinnad = elektriHinnad;
        this.data = data;
        this.restEndPoint = restEndPoint;
    }

     */

    public void setRiik(String riik) {
        this.riik = riik;
    }

    public void setPerioodiAlgus(String perioodiAlgus) {
        this.perioodiAlgus = perioodiAlgus;
    }

    public void setPerioodiLõpp(String perioodiLõpp) {
        this.perioodiLõpp = perioodiLõpp;
    }

    public int getMinHind() {
        return minHind;
    }

    public int getMaxHind() {
        return maxHind;
    }

    public int getKeskmineHind() {
        return keskmineHind;
    }

    public List<Elektrihind> getElektriHinnad(){
        /*EleringJsonApi eleringJsonData = new EleringJsonApi(restEndPoint);
        KuvaElektriHind elektriHind = new KuvaElektriHind();
        eleringJsonData.setStart(perioodiAlgus);
        eleringJsonData.setEnd(perioodiLõpp);
        data = eleringJsonData.getEleringData();
        elektriHind.leiaMinMaxKeskm((JSONObject) data.get("data"), riik);
         */
        return elektriHinnad;
    }

    public void arvutaElektriHinnad() throws IOException, ParseException {
        EleringJsonApi eleringJsonData = new EleringJsonApi(restEndPoint);
        KuvaElektriHind elektriHind = new KuvaElektriHind();
        eleringJsonData.setStart(perioodiAlgus);
        eleringJsonData.setEnd(perioodiLõpp);
        data = eleringJsonData.getEleringData();
        elektriHind.leiaMinMaxKeskm((JSONObject) data.get("data"), riik);
    }


}
