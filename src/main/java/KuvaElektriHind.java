import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class KuvaElektriHind {
    private ArrayList<Elektrihind> elektrihind = new ArrayList<>();
    private ArrayList<Elektrihind> tipud = new ArrayList<>();
    private ArrayList<Elektrihind> põhjad = new ArrayList<>();
    private ArrayList<ElektriHindPaev> paevadeHinnad = new ArrayList<>();
    private String minHind;
    private String maksHind;
    private String keskmineHind;

    private String tunnidTimestampist (Long timestamp){
        return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date((timestamp)*1000L));
    }

    private String kuupäevTimestampist (Long timestamp){
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date((timestamp)*1000L));
    }

    private void nulliListid(){
        elektrihind.clear();
        paevadeHinnad.clear();
        tipud.clear();
        põhjad.clear();
    }

    private ArrayList<Elektrihind> topUp (int n){
        Collections.sort(elektrihind, Collections.reverseOrder());
        if (n > elektrihind.size()){
            n = elektrihind.size();
        }
        for (int i = 0; i < n; i++){
            tipud.add(elektrihind.get(i));
        }
        return tipud;
    }

    private ArrayList<Elektrihind> topDown (int n){
        Collections.sort(elektrihind);
        if (n > elektrihind.size()){
            n = elektrihind.size();
        }
        for (int i = 0; i < n; i++){
            põhjad.add(elektrihind.get(i));
        }
        return põhjad;
    }

    private double leiaKeskmine (){
        double summa = 0.0;
        int pikkus;
        for (int i = 0; i <elektrihind.size();i++){
            summa += elektrihind.get(i).getHind();
        }
        return Math.round(summa/ elektrihind.size()*100)/100.0;
    }

    private void loeJson(JSONObject statesJson, String riik){
        JSONArray dataRiik = (JSONArray) statesJson.get(riik);
        for (int i = 0; i < dataRiik.size();i++){
            JSONObject tunniInfo = (JSONObject) dataRiik.get(i);
            String aeg = tunnidTimestampist((Long) tunniInfo.get("timestamp"));
            double hind = Math.round(((double) tunniInfo.get("price"))/10.0*100)/100.0;
            Elektrihind tunnihind = new Elektrihind(aeg,hind);
            elektrihind.add(tunnihind);
        }
    }

    private void loeJsonKuu (JSONObject statesJson, String riik){
        JSONArray dataRiik = (JSONArray) statesJson.get(riik);
        JSONObject tunniInfo = (JSONObject) dataRiik.get(0);
        String algusPäev = kuupäevTimestampist((Long) tunniInfo.get("timestamp"));
        String testPäev;
        double päevaKeskmine = 0.0;
        double päevaMaksimum = 0.0;
        double päevaMiinimum = 0.0;
        for (int i = 0; i < dataRiik.size(); i++){
            tunniInfo = (JSONObject) dataRiik.get(i);
            testPäev = kuupäevTimestampist((Long) tunniInfo.get("timestamp"));
            if (!testPäev.equals(algusPäev)){
                algusPäev = testPäev;
                päevaKeskmine = leiaKeskmine();
                //päevaMaksimum = topUp(1).;
                //päevaMiinimum = topDown(1);

            }
            String aeg = tunnidTimestampist((Long) tunniInfo.get("timestamp"));
            double hind = Math.round(((double) tunniInfo.get("price"))/10.0*100)/100.0;
            Elektrihind tunnihind = new Elektrihind(aeg,hind);
            elektrihind.add(tunnihind);
        }


    }

    private void teeEttevalmistus (JSONObject statesJson, String riik){
        nulliListid();
        loeJson(statesJson, riik);
    }

    public double leiaKeskmised (JSONObject statesJson, String riik){
        teeEttevalmistus(statesJson,riik);
        return leiaKeskmine();
    }

    public Elektrihind leiaMaxHind (JSONObject statesJson, String riik){
        teeEttevalmistus(statesJson,riik);
        topUp(1);
        return tipud.get(0);
    }

    public Elektrihind leiaMinHind (JSONObject statesJson, String riik){
        teeEttevalmistus(statesJson,riik);
        topDown(1);
        return põhjad.get(0);
    }

    public List<Elektrihind> leiaHinnad(JSONObject statesJson, String riik){
        teeEttevalmistus(statesJson,riik);
        return elektrihind;
    }

    public List<ElektriHindPaev> leiaPaevadeHinnad (JSONObject statesJson, String riik){
        List<ElektriHindPaev> päevadeHinnad = new ArrayList<>();
        nulliListid();

        return päevadeHinnad;
    }

}
