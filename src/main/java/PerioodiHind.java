import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PerioodiHind {
    private String riik;
    private int kuu;
    private int aasta;
    private String perioodiAlgus;
    private String perioodiLõpp;
    private ElektriHindPaev elektriHinnad = new ElektriHindPaev();
    private JSONObject data;
    private String restEndPoint = "/api/nps/price";
    DateTimeFormatter dtformatStart = DateTimeFormatter.ofPattern("yyyy-MM-dd 22:00");
    DateTimeFormatter dtformatEnd = DateTimeFormatter.ofPattern("yyyy-MM-dd 21:59");

    public PerioodiHind (String riik, int kuu, int aasta){
        this.riik = riik;
        this.kuu = kuu;
        this.aasta = aasta;
    }

    private void leiaAeg(){
        perioodiAlgus = dtformatStart.format(LocalDateTime.of(aasta,kuu,1,0,0).plusDays(-1));
        perioodiLõpp = dtformatEnd.format(LocalDateTime.of(aasta,kuu,1,0,0).plusMonths(1).plusDays(-1));
    }

    private void teeEttevalmistus() throws IOException, ParseException {
        leiaAeg();
        EleringJsonApi eleringData = new EleringJsonApi(restEndPoint);
        eleringData.setStart(perioodiAlgus);
        eleringData.setEnd(perioodiLõpp);
        data = eleringData.getEleringData();
    }

    public List<ElektriHindPaev> getPerioodiHinnad() throws IOException, ParseException {
        List<ElektriHindPaev> elektriHinnad = new ArrayList<>();
        teeEttevalmistus();
        KuvaElektriHind kuvaElektriHind = new KuvaElektriHind();
        elektriHinnad = kuvaElektriHind.leiaPerioodiHinnad((JSONObject) data.get("data"),riik);
        return elektriHinnad;
    }

    public Elektrihind getPerioodiMaksimaalneHind() throws IOException, ParseException {
        teeEttevalmistus();
        KuvaElektriHind kuvaElektriHind = new KuvaElektriHind();
        return kuvaElektriHind.leiaPerioodiMaxHind((JSONObject) data.get("data"),riik);
    }

    public Elektrihind getPerioodiMiniimaalneHind() throws IOException, ParseException {
        teeEttevalmistus();
        KuvaElektriHind kuvaElektriHind = new KuvaElektriHind();
        return kuvaElektriHind.leiaPerioodiMinHind((JSONObject) data.get("data"),riik);
    }

    public double getPerioodiKeskmineHind() throws IOException, ParseException {
        teeEttevalmistus();
        KuvaElektriHind kuvaElektriHind = new KuvaElektriHind();
        return kuvaElektriHind.leiaPerioodiKeskmine((JSONObject) data.get("data"),riik);
    }
}
