import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private final double[] threshold;
    private double x;
    private double s;
	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0) {
			throw new IllegalArgumentException();
		}
		threshold = new double[trials];
		for (int i = 0; i < trials; i++) {
			Percolation p = new Percolation(n);
			while (!p.percolates()) {
				int row = StdRandom.uniform(1, n + 1);
				int col = StdRandom.uniform(1, n + 1);
				if (!p.isOpen(row, col)) {
					p.open(row, col);
				}
			}
			threshold[i] = (double) p.numberOfOpenSites() / n / n;
		}
	}

	public double mean() {
		x=StdStats.mean(threshold);
		return x;
	}

	public double stddev() {
		s=StdStats.stddev(threshold);
		return s;
	}

	public double confidenceLo() {
		double low = x - 1.96 * s / (Math.sqrt((double) threshold.length));
		return low;
	}

	public double confidenceHi() {
		double hi = x + 1.96 * s / (Math.sqrt((double) threshold.length));
		return hi;
	}

	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		int trials = Integer.parseInt(args[1]);
		PercolationStats stats = new PercolationStats(n, trials);
		double x = stats.mean();
		double s = stats.stddev();
		double low = stats.confidenceLo();
		double hi = stats.confidenceHi();
		System.out.printf("mean=%f\n", x);
		System.out.printf("stddev=%f\n", s);
		System.out.printf("%f %f\n", low, hi);
	}

}
