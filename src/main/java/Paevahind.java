import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Paevahind {
    private String riik;
    private String perioodiAlgus;
    private String perioodiLõpp;
    private int minHind;
    private int maxHind;
    private int keskmineHind;
    private List<Elektrihind> elektriHinnad;
    private JSONObject data;
    private String restEndPoint = "/api/nps/price";
    SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public Paevahind(String riik, String perioodiAlgus, String perioodiLõpp, int minHind, int maxHind, int keskmineHind, List<Elektrihind> elektriHinnad, JSONObject data, String restEndPoint) {
        this.riik = riik;
        this.perioodiAlgus = perioodiAlgus;
        this.perioodiLõpp = perioodiLõpp;
        this.minHind = minHind;
        this.maxHind = maxHind;
        this.keskmineHind = keskmineHind;
        this.elektriHinnad = elektriHinnad;
        this.data = data;
        this.restEndPoint = restEndPoint;
    }

    public Paevahind(String riik) {
        this.riik = riik;
    }

    public void setRiik(String riik) {
        this.riik = riik;
    }

    public List<Elektrihind> getPäevaHinnad() throws IOException, ParseException {
        List<Elektrihind> paevaHinnad = new ArrayList<>();
        leiaAeg();
        EleringJsonApi eleringData = new EleringJsonApi(restEndPoint);
        KuvaElektriHind kuvaElektriHind = new KuvaElektriHind();
        eleringData.setStart(perioodiAlgus);
        eleringData.setEnd(perioodiLõpp);
        data = eleringData.getEleringData();
        System.out.println(kuvaElektriHind.leiaKeskmised(data,riik));



        return paevaHinnad;
    }

    private void leiaAeg (){
        perioodiAlgus = sdformat.format(new Date((System.currentTimeMillis())));
        perioodiLõpp = sdformat.format(new Date((System.currentTimeMillis() + 24*60*60*1000)));
    }
}

