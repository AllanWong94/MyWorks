
import edu.princeton.cs.algs4.StdRandom;




public class PercolationStats {
    private int n = 10;          
    private int t = 100000;
    private double[] fractions;
    private double mean, stddev;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.n = n;
        t = trials;
        fractions = new double[t];
        for (int i = 0; i < t; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                randomOpen(p);
            }
            double f = (double) p.numberOfOpenSites() / (n * n);
            fractions[i] = f;
        }
        mean = 0;
        stddev = 0;
        mean = mean();
        stddev = stddev();
    }
    // sample mean of percolation threshold
    public double mean() {
        if (mean != 0)
            return mean;
        double sum = 0;
        for (int i = 0; i < t; i++) {
            sum += fractions[i];
        }
        return sum/t;
    }
    // sample standard deviation of percolation threshold
    public double stddev() {
        if (stddev != 0)
            return stddev;
        double u = mean;
        double res = 0;
        for (int i = 0; i < t; i++) {
            res += (fractions[i]-u)*(fractions[i]-u);
        }
        res /= (t-1);
        return Math.sqrt(res);
    }
    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        double res = 0;
        res = mean-1.96*stddev/Math.sqrt(t);
        return res;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double res = 0;
        res = mean+1.96*stddev/Math.sqrt(t);
        return res;

    }

    public static void main(String[] args) {

    }


    private void randomOpen(Percolation p) {
        int row = StdRandom.uniform(1, n+1);
        int col = StdRandom.uniform(1, n+1);
        while (p.isOpen(row, col)) {
            row = StdRandom.uniform(1, n+1);
            col = StdRandom.uniform(1, n+1);
        }
        p.open(row, col); 
    }
}                       
