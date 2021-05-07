import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException, ParseException {
        String algus = "2021-05-01";
        String lõpp = "2021-05-02";
        String riigiTähis = "ee";

        System.out.println("Alustan");

        Päevahind päevahind = new Päevahind();
        päevahind.setPerioodiAlgus(algus);
        päevahind.setPerioodiLõpp(lõpp);
        päevahind.setRiik(riigiTähis);

        päevahind.arvutaElektriHinnad();

        //System.out.println(päevahind.getMaxHind());
    }
}
