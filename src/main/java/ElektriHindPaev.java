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

    public void setMaxHind(double maxHind) {
        this.maxHind = maxHind;
    }

    public double getMinHind() {
        return minHind;
    }

    public void setMinHind(double minHind) {
        this.minHind = minHind;
    }

    @Override
    public String toString() {
        return "ElektriHindPaev{" +
                "maxHind=" + maxHind +
                ", minHind=" + minHind +
                "} " + super.toString();
    }
}
