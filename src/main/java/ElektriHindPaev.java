public class ElektriHindPaev extends Elektrihind{
    private double maxHind;
    private double minHind;

    public ElektriHindPaev(String aeg, double hind, double maxHind, double minHind) {
        super(aeg, hind);
        this.maxHind = maxHind;
        this.minHind = minHind;
    }

    public ElektriHindPaev(){
    }

    public double getMaxHind() {
        return maxHind;
    }

    public double getMinHind() {
        return minHind;
    }

    @Override
    public String toString() {
        return super.toString() + ";" + maxHind + ";" + minHind;
    }
}
