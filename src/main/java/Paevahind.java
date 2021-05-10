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
    private JSONObject data;
    private String restEndPoint = "/api/nps/price";
    DateTimeFormatter dtformat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:00");

    public Paevahind(String riik) {
        this.riik = riik;
    }

    // koostab päringu KuvaElektriHind klassile järgneva 24 tunni hindade listi saamiseks
    public List<Elektrihind> getPäevaHinnad() throws IOException, ParseException {
        List<Elektrihind> paevaHinnad = new ArrayList<>();
        teeEttevalmistus();
        KuvaElektriHind kuvaElektriHind = new KuvaElektriHind();
        paevaHinnad = kuvaElektriHind.leiaHinnad((JSONObject) data.get("data"),riik);
        return paevaHinnad;
    }
    // koostab päringu KuvaElektriHind klassile järgneva 24 tunni hindade keskmise saamiseks
    public double keskmineHind () throws IOException, ParseException {
        teeEttevalmistus();
        KuvaElektriHind kuvaElektriHind = new KuvaElektriHind();
        return kuvaElektriHind.leiaKeskmised((JSONObject) data.get("data"),riik);
    }

    // koostab päringu KuvaElektriHind klassile järgneva 24 tunni kõrgeima hinna saamiseks
    public Elektrihind maksimaalneHind () throws IOException, ParseException {
        teeEttevalmistus();
        KuvaElektriHind kuvaElektriHind = new KuvaElektriHind();
        return kuvaElektriHind.leiaMaxHind((JSONObject) data.get("data"),riik);
    }

    // koostab päringu KuvaElektriHind klassile järgneva 24 tunni madalaima hinna saamiseks
    public Elektrihind minimaalneHind () throws IOException, ParseException {
        teeEttevalmistus();
        KuvaElektriHind kuvaElektriHind = new KuvaElektriHind();
        return kuvaElektriHind.leiaMinHind((JSONObject) data.get("data"),riik);
    }

    // arvestab hetke ajast päringu alguse ja 24 tundi edasi lõpu aja
    // -3 tundi on vajalik Elering API ja LocalDateTime aja vahe kompenseerimiseks + 1h lisaks, et kuvada ka juba
    // alanud tunni elektrihind
    private void leiaAeg (){
        LocalDateTime hetkeAeg = LocalDateTime.now().plusHours(-3);
        perioodiAlgus = dtformat.format(hetkeAeg);
        perioodiLõpp = dtformat.format(hetkeAeg.plusDays(1));
    }

    // teostab päringute korduvad toimingud
    private void teeEttevalmistus () throws IOException, ParseException {
        leiaAeg();
        EleringJsonApi eleringData = new EleringJsonApi(restEndPoint);
        eleringData.setStart(perioodiAlgus);
        eleringData.setEnd(perioodiLõpp);
        data = eleringData.getEleringData();
    }
}

