
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private WeightedQuickUnionUF grid;
	private boolean[] state;
	private final int n;

	public Percolation(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		} else {
			this.n = n;
			int size = this.n * this.n + 1;
			grid = new WeightedQuickUnionUF(size);
			state = new boolean[size];
			for (int i = 1; i < size; i++) {
				state[i] = false;
			}
		}
	}

	private boolean isInGrid(int i, int j) {
		if ((i < 1 || i > n) || (j < 1 || j > n))
			return false;
		else
			return true;
	}

	public void open(int row, int col) {
		if (!isInGrid(row, col)) {
			throw new IllegalArgumentException();
		}
		if (isOpen(row, col))
			return;
		int p = (row - 1) * this.n + col;
		state[p] = true;
		int up = p - this.n;
		if (isInGrid(row - 1, col) && state[up]) {
			grid.union(p, up);
		}
		int left = p - 1;
		if (isInGrid(row, col - 1) && state[left]) {
			grid.union(p, left);
		}
		int right = p + 1;
		if (isInGrid(row, col + 1) && state[right]) {
			grid.union(p, right);
		}
		int bottom = p + this.n;
		if (isInGrid(row + 1, col) && state[bottom]) {
			grid.union(p, bottom);
		}
	}

	public boolean isOpen(int row, int col) {
		if (row < 1 || row > this.n || col < 1 || col > this.n) {
			throw new IllegalArgumentException();
		}
		int index = (row - 1) * this.n + col;
		return state[index];
	}

	public boolean isFull(int row, int col) {
		if (row < 1 || row > this.n || col < 1 || col > this.n) {
			throw new IllegalArgumentException();
		}
		int p = (row - 1) * this.n + col;
		for (int i = 1; i < this.n + 1; i++) {
			// first must consider the row,col is open
			if (isOpen(1, i) && isOpen(row, col) && grid.connected(p, i))
				return true;
		}
		return false;
	}

	public int numberOfOpenSites() {
		int num = 0;
		int size = this.n * this.n + 1;
		for (int i = 0; i < size; i++) {
			if (state[i])
				num++;
		}
		return num;
	}

	public boolean percolates() {
		int row = this.n;
		for (int col = 1; col < this.n + 1; col++) {
			if (isFull(row, col))
				return true;
		}
		return false;
	}

	public static void main(String[] args) {
		int[] test = new In(args[0]).readAllInts();
		Percolation percolation = new Percolation(test[0]);
		for (int i = 1; i < test.length - 2; i += 2) {
			percolation.open(test[i], test[i + 1]);
			System.out.println(
					test[i] + "," + test[i + 1] + "		isopen:" + percolation.isOpen(test[i], test[i + 1]));
			System.out.println(
					test[i] + "," + test[i + 1] + "		isfull:" + percolation.isFull(test[i], test[i + 1]));
			System.out.println(test[i] + "," + test[i + 1] + "		percolation:" + percolation.percolates());
		}
	}
}

