public abstract class AbstractSeries implements Series {
    public double[] take(int k) {
        double[] result = new double[k];
        for (int index = 0; index < k; index++) {
            result[index] = next();
        }
        return result;
    }
    public abstract double next();
}
