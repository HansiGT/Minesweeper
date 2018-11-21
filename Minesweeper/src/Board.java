
public class Board {
	private int[][] field;
	private int height;
	private int width;
	private int bombs;

	public Board(int width, int height, int bombs) {
		this.height = height;
		this.width = width;
		this.bombs = bombs;
		this.field = new int[width][height];
	}
	
	public int[][] getField() {
		return this.field;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getBombs() {
		return this.bombs;
	}
	
	public void setField(int x, int y, int input) {
		this.field[x][y] = input;
	}
	
	public void initialize(int sx, int sy){
		for(int i = 0; i < this.getBombs(); i++) {
			int x = (int) (Math.random() * this.getWidth());
			int y = (int) (Math.random() * this.getHeight());
			while(this.getField()[x][y] == -1 || (sx == x && sy == y) || (sx-1 == x && sy-1 == y)
					|| (sx == x && sy-1 == y) || (sx+1 == x && sy-1 == y) || (sx+1 == x && sy == y)
					|| (sx+1 == x && sy+1 == y) || (sx == x && sy+1 == y) || (sx-1 == x && sy+1 == y) || (sx-1 == x && sy == y)) {
				x = (int) (Math.random() * this.getWidth());
				y = (int) (Math.random() * this.getHeight());
			}
			this.setField(x, y, -1);
		}
		for(int a = 0; a < this.getWidth(); a++) {
			for(int b = 0; b < this.getHeight(); b++) {
				int input = 0;
				if(this.getField()[a][b] != -1) {
					if(a > 0 && b > 0 && this.getField()[a-1][b-1] == -1) {
						input++;
					}
					if(b > 0 && this.getField()[a][b-1] == -1) {
						input++;
					}
					if(a < (this.getWidth() - 1) && b > 0 && this.getField()[a+1][b-1] == -1) {
						input++;
					}
					if(a < (this.getWidth() - 1) && this.getField()[a+1][b] == -1) {
						input++;
					}
					if(a < (this.getWidth() - 1) && b < (this.getHeight() - 1) && this.getField()[a+1][b+1] == -1) {
						input++;
					}
					if(b < (this.getHeight() - 1) && this.getField()[a][b+1] == -1) {
						input++;
					}
					if(a > 0 && b < (this.getHeight() - 1) && this.getField()[a-1][b+1] == -1) {
						input++;
					}
					if(a > 0 && this.getField()[a-1][b] == -1) {
						input++;
					}
					this.setField(a, b, input);
				}
			}
		}
	}
	
	public void print() {
	for(int b = 0; b < this.getHeight(); b++) {
		for(int a = 0; a < this.getWidth(); a++) {
			System.out.print(this.getField()[a][b] + "\t");
		}
		System.out.print("\n");
	}
	}
}
