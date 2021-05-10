import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Test {
    public static void main(String[] args) throws IOException, ParseException {
        String algus = "2021-05-01";
        String riigiTähis = "fi";

        Paevahind paevahind = new Paevahind(riigiTähis);
        System.out.println("Tundide hinnad " + paevahind.getPäevaHinnad());
        System.out.println("Keskmine hind " + paevahind.keskmineHind());
        System.out.println("Max hind " + paevahind.maksimaalneHind());
        System.out.println("Min hind " + paevahind.minimaalneHind());


        System.out.println(Month.from(LocalDate.now()).getDisplayName(TextStyle.FULL_STANDALONE,new Locale("et","EE")));
    }
}
