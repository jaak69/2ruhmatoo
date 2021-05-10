public class ElektriHindPäev extends Elektrihind{
    private double maxHind;
    private double minHind;

    public ElektriHindPäev(String aeg, double hind, double maxHind, double minHind) {
        super(aeg, hind);
        this.maxHind = maxHind;
        this.minHind = minHind;
    }

    public ElektriHindPäev(){
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
        return "ElektriHindPäev{" +
                "maxHind=" + maxHind +
                ", minHind=" + minHind +
                "} " + super.toString();
    }
}
