public class Arithmetic extends AbstractSeries {
    private double sum = 0;
    private int term = 0;
    public double next() {
        term++;
        sum += term;
        return sum;
    }
}
