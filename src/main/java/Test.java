import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException, ParseException {
        String algus = "2021-05-01";
        String riigiTähis = "ee";

        System.out.println("Alustan");

        //List<Elektrihind> paevaHinnad = new ArrayList<>();
        Paevahind paevahind = new Paevahind(riigiTähis);
        System.out.println(paevahind.getPäevaHinnad());


        //System.out.println(päevahind.getMaxHind());
    }
}
