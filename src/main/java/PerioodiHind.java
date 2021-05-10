import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.YearMonth;
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
    DateTimeFormatter dtformatStart = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00");
    DateTimeFormatter dtformatEnd = DateTimeFormatter.ofPattern("yyyy-MM-dd 23:59");

    public PerioodiHind (String riik, int kuu, int aasta){
        this.riik = riik;
        this.kuu = kuu;
        this.aasta = aasta;
    }

    public void setRiik(String riik) {
        this.riik = riik;
    }

    public void setKuu(int kuu) {
        this.kuu = kuu;
    }

    public void setAasta(int aasta) {
        this.aasta = aasta;
    }

    public List<ElektriHindPaev> getPerioodiHinnad() throws IOException, ParseException {
        List<ElektriHindPaev> elektriHinnad = new ArrayList<>();
        leiaAeg();
        EleringJsonApi eleringData = new EleringJsonApi(restEndPoint);
        eleringData.setStart(perioodiAlgus);
        eleringData.setEnd(perioodiLõpp);
        data = eleringData.getEleringData();
        KuvaElektriHind kuvaElektriHind = new KuvaElektriHind();
        elektriHinnad = kuvaElektriHind.leiaPaevadeHinnad((JSONObject) data.get("data"),riik);
        return elektriHinnad;
    }

    private void leiaAeg(){
        perioodiAlgus = dtformatStart.format(LocalDateTime.of(aasta,kuu,1,0,0));
        perioodiLõpp = dtformatEnd.format(LocalDateTime.of(aasta,kuu,1,0,0).plusMonths(1).plusDays(-1));
    }
}
