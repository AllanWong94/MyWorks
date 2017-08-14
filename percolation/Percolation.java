import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private int size;
	private int numOfOpenSites;
	private boolean[][] sites;
	private WeightedQuickUnionUF uf, uf2;


	
	

	// create n-by-n grid, with all sites blocked
	   public Percolation(int n) {
		   if (n <= 0)
				throw new java.lang.IllegalArgumentException();
		   sites = new boolean[n][n];
		   numOfOpenSites = 0;
		   size = n;
		   uf = new WeightedQuickUnionUF(n * n + 2); 
           uf2 = new WeightedQuickUnionUF(n * n + 1); 
		   
		   // sites[i][j] is represented by uf[(i-1)*n+j-1]  
		   //uf[n*n] is the source, uf[n*n+1] is the destination.
	   }
	   // open site (row, col) if it is not open already
	   public    void open(int row, int col) {
		   if (outOfBounds(row-1, col-1))
				throw new java.lang.IndexOutOfBoundsException();
		   if (!sites[row-1][col-1]) {
		       numOfOpenSites += 1;
		       sites[row-1][col-1] = true;
		   }
           int index = (row-1)*size+col-1;
		   if (row == 1) {
		       uf.union(index, size * size);
               uf2.union(index, size * size);
		   }
		   if (row == size) {
		       uf.union(index, size * size + 1);
		   }
		   if (row > 1 && isOpen(row-1, col)) {
			   uf.union(index-size, index);
               uf2.union(index-size, index);
		   }
		   if (row < size && isOpen(row+1, col)) {
			   uf.union(index+size, index);
               uf2.union(index+size, index);
		   }
		   if (col > 1 && isOpen(row, col-1)) {
			   uf.union(index-1, index);
               uf2.union(index-1, index);
		   }
		   if (col < size && isOpen(row, col+1)) {
			   uf.union(index+1, index);
               uf2.union(index+1, index);
		   }
		   
	   }
	   // is site (row, col) open?
	   public boolean isOpen(int row, int col) {
		   if (outOfBounds(row-1, col-1))
               throw new java.lang.IndexOutOfBoundsException();
		   return sites[row-1][col-1];
	   } 
	   // is site (row, col) full?
	   public boolean isFull(int row, int col) {
		   if (outOfBounds(row-1, col-1))
               throw new java.lang.IndexOutOfBoundsException();
		   return uf2.connected(size * size, (row-1)*size+col-1);
	   }
	    // number of open sites
	   public int numberOfOpenSites() {
		   return numOfOpenSites;
	   }
	   // does the system percolate?
	   public boolean percolates() {
	       return uf.connected(size * size, size * size + 1);
	   }

	   // test client (optional)
	   public static void main(String[] args) {
		   
	   }
	

	   private boolean outOfBounds(int x, int y) {
		   return (x < 0 || y < 0 || x >= size || y >= size);
	   }
	
}