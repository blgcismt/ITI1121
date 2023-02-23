public class Geometric extends AbstractSeries {
    private int term = 0;
    private double sum = 0;
    public double next() {
        sum += (1 / Math.pow(2, term));
        term++;
        return sum;
    }
}
