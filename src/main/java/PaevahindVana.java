import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class PaevahindVana {
    private String riik;
    private String perioodiAlgus;
    private String perioodiLõpp;
    private int minHind;
    private int maxHind;
    private int keskmineHind;
    private List<Elektrihind> elektriHinnad;
    private JSONObject data;
    private String restEndPoint = "/api/nps/price";

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

    public List<Elektrihind> getElektriHinnad() throws IOException, ParseException {
        EleringJsonApi eleringJsonData = new EleringJsonApi(restEndPoint);
        KuvaElektriHind elektriHind = new KuvaElektriHind();
        eleringJsonData.setStart(perioodiAlgus);
        eleringJsonData.setEnd(perioodiLõpp);
        data = eleringJsonData.getEleringData();
        //elektriHind.leiaMinMaxKeskm((JSONObject) data.get("data"), riik);
        return elektriHinnad;
    }

    public void arvutaElektriHinnad() throws IOException, ParseException {
        EleringJsonApi eleringJsonData = new EleringJsonApi(restEndPoint);
        KuvaElektriHind elektriHind = new KuvaElektriHind();
        eleringJsonData.setStart(perioodiAlgus);
        eleringJsonData.setEnd(perioodiLõpp);
        data = eleringJsonData.getEleringData();
        //elektriHind.leiaMinMaxKeskm((JSONObject) data.get("data"), riik);
    }


}
