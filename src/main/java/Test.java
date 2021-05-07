public class Test {
    public static void main(String[] args) {
        String algus = "2021-05-01";
        String lõpp = "2021-05-02";
        String riigiTähis = "ee";

        Päevahind päevahind = new Päevahind();
        päevahind.setPerioodiAlgus(algus);
        päevahind.setPerioodiLõpp(lõpp);
        päevahind.setRiik(riigiTähis);

        
    }
}
