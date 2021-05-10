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
    private ArrayList<Elektrihind> perioodiTipud = new ArrayList<>();
    private ArrayList<Elektrihind> põhjad = new ArrayList<>();
    private ArrayList<Elektrihind> perioodiPõhjad = new ArrayList<>();
    private ArrayList<ElektriHindPaev> paevadeHinnad = new ArrayList<>();
    private ArrayList<Elektrihind> minHindadeList = new ArrayList<>();
    private ArrayList<Elektrihind> maxHinddadeList = new ArrayList<>();
    private ArrayList<Elektrihind> perioodiHinnad = new ArrayList<>();

    private String tunnidTimestampist (Long timestamp){
        return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date((timestamp)*1000L));
    }

    private String kuupäevTimestampist (Long timestamp){
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date((timestamp)*1000L));
    }

    // nullib elektrihinna abilistid
    private void nulliListid(){
        elektrihind.clear();
        tipud.clear();
        põhjad.clear();
    }

    // nullib perioodiandmete abilistid
    private void nulliPerioodiListid(){
        paevadeHinnad.clear();
        minHindadeList.clear();
        maxHinddadeList.clear();
        perioodiTipud.clear();
        perioodiPõhjad.clear();
        perioodiHinnad.clear();
    }

    // leiab kõrgeima hinna
    private ArrayList<Elektrihind> topUp (int n, ArrayList<Elektrihind> hindadeList, ArrayList<Elektrihind> tiputabel){
        Collections.sort(hindadeList, Collections.reverseOrder());
        if (n > hindadeList.size()){
            n = hindadeList.size();
        }
        for (int i = 0; i < n; i++){
            tiputabel.add(hindadeList.get(i));
        }
        return tiputabel;
    }

    // leiab madalaima hinna
    private ArrayList<Elektrihind> topDown (int n, ArrayList<Elektrihind> hindadeList, ArrayList<Elektrihind> põhjatabel){
        Collections.sort(hindadeList);
        if (n > hindadeList.size()){
            n = hindadeList.size();
        }
        for (int i = 0; i < n; i++){
            põhjatabel.add(hindadeList.get(i));
        }
        return põhjatabel;
    }

    // arvutab keskmise hinna
    private double leiaKeskmine (ArrayList<Elektrihind> hindadeList){
        double summa = 0.0;
        for (int i = 0; i <hindadeList.size();i++){
            summa += hindadeList.get(i).getHind();
        }
        return Math.round(summa/ hindadeList.size()*100)/100.0;
    }

    // loeb json andmetest elektrihinnad ning tekitab Elektrihind tüüpi objektid ning objektide listi
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

    // loeb json andmetest elektrihinnad ning teeb kalkulatsioonid ElektriHindPäev objekti tekitamiseks (arvestab
    // päeva keskmise, leiab päeva minimaalse ja maksimaalse hinna, tekitab objekti ning objektide listi
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

            // kuupäeva kontroll, et arvestada ühe päeva andmed
            if (!testPäev.equals(algusPäev) || (i == dataRiik.size() - 2)){
                päevaKeskmine = leiaKeskmine(elektrihind);
                päevaMaksimum = topUp(1,elektrihind,tipud).get(0).getHind();
                maxHinddadeList.add(topUp(1,elektrihind,tipud).get(0));
                päevaMiinimum = topDown(1,elektrihind,põhjad).get(0).getHind();
                minHindadeList.add(topDown(1,elektrihind,põhjad).get(0));
                ElektriHindPaev uksPaev = new ElektriHindPaev(algusPäev,päevaKeskmine,päevaMaksimum,päevaMiinimum);
                paevadeHinnad.add(uksPaev);
                nulliListid();
                algusPäev = testPäev;
            }
            String aeg = tunnidTimestampist((Long) tunniInfo.get("timestamp"));
            double hind = Math.round(((double) tunniInfo.get("price"))/10.0*100)/100.0;
            Elektrihind tunnihind = new Elektrihind(aeg,hind);
            elektrihind.add(tunnihind);
            perioodiHinnad.add(tunnihind);
        }
    }

    // teostab korduvad toimingud
    private void teeEttevalmistus (JSONObject statesJson, String riik){
        nulliListid();
        loeJson(statesJson, riik);
    }

    // teostab korduvad toimingud periooditsüklitega
    private void teeEttevalmistusPeriood (JSONObject statesJson, String riik){
        nulliListid();
        nulliPerioodiListid();
        loeJsonKuu(statesJson, riik);
    }

    // suunab töötlusele ning annab vastusena elektrihindade listi Elektrihind objektina
    public List<Elektrihind> leiaHinnad(JSONObject statesJson, String riik){
        teeEttevalmistus(statesJson,riik);
        return elektrihind;
    }

    // suunab töötlusele ning annab vastusena esitatud listi maksimaalse hinna Elektrihind objektina
    public Elektrihind leiaMaxHind (JSONObject statesJson, String riik){
        teeEttevalmistus(statesJson,riik);
        topUp(1,elektrihind,tipud);
        return tipud.get(0);
    }

    // suunab töötlusele ning annab vastusena esitatud listi minimaalse hinna Elektrihind objektina
    public Elektrihind leiaMinHind (JSONObject statesJson, String riik){
        teeEttevalmistus(statesJson,riik);
        topDown(1,elektrihind,põhjad);
        return põhjad.get(0);
    }

    // suunab töötlusele ning annab vastusena esitatud listi keskmise hinna
    public double leiaKeskmised (JSONObject statesJson, String riik){
        teeEttevalmistus(statesJson,riik);
        return leiaKeskmine(elektrihind);
    }

    // suunab töötlusele ning annab vastusena elektrihindade listi ElektriHindPäev objektina
    public List<ElektriHindPaev> leiaPerioodiHinnad (JSONObject statesJson, String riik){
        teeEttevalmistusPeriood(statesJson,riik);
        return paevadeHinnad;
    }

    // suunab töötlusele ning annab vastusena esitatud listi maksimaalse hinna Elektrihind objektina
    public Elektrihind leiaPerioodiMaxHind (JSONObject statesJson, String riik){
        teeEttevalmistusPeriood(statesJson,riik);
        topUp(1,maxHinddadeList,perioodiTipud);
        return perioodiTipud.get(0);
    }

    // suunab töötlusele ning annab vastusena esitatud listi minimaalse hinna Elektrihind objektina
    public Elektrihind leiaPerioodiMinHind (JSONObject statesJson, String riik){
        teeEttevalmistusPeriood(statesJson,riik);
        topDown(1,minHindadeList,perioodiPõhjad);
        return perioodiPõhjad.get(0);
    }

    // suunab töötlusele ning annab vastusena esitatud listi keskmise hinna
    public double leiaPerioodiKeskmine (JSONObject statesJson, String riik) {
        teeEttevalmistusPeriood(statesJson, riik);
        return leiaKeskmine(perioodiHinnad);
    }
}
