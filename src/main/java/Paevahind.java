import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Paevahind {
    private String riik;
    private String perioodiAlgus;
    private String perioodiLõpp;
    private Elektrihind minHind = new Elektrihind();
    private Elektrihind maxHind = new Elektrihind();
    private int keskmineHind;
    private JSONObject data;
    private String restEndPoint = "/api/nps/price";
    DateTimeFormatter dtformat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:00");

    /*public Paevahind(String riik, String perioodiAlgus, String perioodiLõpp, int minHind, int maxHind, int keskmineHind, JSONObject data, String restEndPoint) {
        this.riik = riik;
        this.perioodiAlgus = perioodiAlgus;
        this.perioodiLõpp = perioodiLõpp;
        this.keskmineHind = keskmineHind;
        this.data = data;
        this.restEndPoint = restEndPoint;
    }

     */

    public Paevahind(String riik) {
        this.riik = riik;
    }

    public void setRiik(String riik) {
        this.riik = riik;
    }

    public List<Elektrihind> getPäevaHinnad() throws IOException, ParseException {
        List<Elektrihind> paevaHinnad = new ArrayList<>();
        teeEttevalmistus();
        KuvaElektriHind kuvaElektriHind = new KuvaElektriHind();
        paevaHinnad = kuvaElektriHind.leiaHinnad((JSONObject) data.get("data"),riik);
        return paevaHinnad;
    }

    public double keskmineHind () throws IOException, ParseException {
        teeEttevalmistus();
        KuvaElektriHind kuvaElektriHind = new KuvaElektriHind();
        return kuvaElektriHind.leiaKeskmised((JSONObject) data.get("data"),riik);
    }

    public Elektrihind maksimaalneHind () throws IOException, ParseException {
        teeEttevalmistus();
        KuvaElektriHind kuvaElektriHind = new KuvaElektriHind();
        return kuvaElektriHind.leiaMaxHind((JSONObject) data.get("data"),riik);
    }

    public Elektrihind minimaalneHind () throws IOException, ParseException {
        teeEttevalmistus();
        KuvaElektriHind kuvaElektriHind = new KuvaElektriHind();
        return kuvaElektriHind.leiaMinHind((JSONObject) data.get("data"),riik);
    }

    private void leiaAeg (){
        LocalDateTime hetkeAeg = LocalDateTime.now().plusHours(-3);
        perioodiAlgus = dtformat.format(hetkeAeg);
        perioodiLõpp = dtformat.format(hetkeAeg.plusDays(1));
    }

    private void teeEttevalmistus () throws IOException, ParseException {
        leiaAeg();
        EleringJsonApi eleringData = new EleringJsonApi(restEndPoint);
        eleringData.setStart(perioodiAlgus);
        eleringData.setEnd(perioodiLõpp);
        data = eleringData.getEleringData();
    }
}

