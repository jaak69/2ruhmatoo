import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException, ParseException {
        String algus = "2021-05-01";
        String riigiTähis = "ee";

        Paevahind paevahind = new Paevahind(riigiTähis);
        System.out.println("Tundide hinnad " + paevahind.getPäevaHinnad());
        System.out.println("Keskmine hind " + paevahind.keskmineHind());
        System.out.println("Max hind " + paevahind.maksimaalneHind());
        System.out.println("Min hind " + paevahind.minimaalneHind());
    }
}
