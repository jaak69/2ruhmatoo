import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException, ParseException {
        String algus = "2021-05-01";
        String riigiTähis = "ee";
        int kuu = 2;
        int aasta = 2021;

        Paevahind paevahind = new Paevahind(riigiTähis);
        System.out.println("Tundide hinnad " + paevahind.getPäevaHinnad());
        System.out.println("Keskmine hind " + paevahind.keskmineHind());
        System.out.println("Max hind " + paevahind.maksimaalneHind());
        System.out.println("Min hind " + paevahind.minimaalneHind());

        PerioodiHind perioodiHind = new PerioodiHind(riigiTähis,kuu,aasta);
        System.out.println("------------");
        List<ElektriHindPaev> tabel = perioodiHind.getPerioodiHinnad();
        System.out.println("Tabel " + tabel);
        System.out.println("1. kuupäev " + tabel.get(0).getAeg());
        System.out.println("1. kuupäeva max " + tabel.get(0).getMaxHind());
        System.out.println("1. kuupäeva min " + tabel.get(0).getMinHind());
        System.out.println("1. kuupäeva keskmine " + tabel.get(0).getHind());
        System.out.println("Viimane kuupäev " + tabel.get(tabel.size()-1).getAeg());

    }
}
