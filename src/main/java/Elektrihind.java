public class Elektrihind implements Comparable<Elektrihind>{
    private String aeg;
    private double hind;

    public Elektrihind(String aeg, double hind) {
        this.aeg = aeg;
        this.hind = hind;
    }

    public Elektrihind() {
    }

    public String getAeg() {
        return aeg;
    }

    public double getHind() {
        return hind;
    }

    @Override
    public String toString() {
        return aeg + ";" + hind;
    }

    @Override
    public int compareTo(Elektrihind lisatav){
        if(getHind() > lisatav.getHind()){
            return 1;
        }
        else if(getHind() < lisatav.getHind()){
            return -1;
        }
        else{
            return 0;
        }
    }

}
