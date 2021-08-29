import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

public class Cell {

	public int size;
	public int x;
	public int y;
	Random r;

	boolean deadEnd;
	boolean path;
	public Boolean[] walls = new Boolean[4]; // a cell has 4 walls; top, right, bottom, left
	public boolean visited;

	/* constructor */
	public Cell(int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
		r = new Random();

		this.visited = false;
		this.path = false;
		this.deadEnd = false;

		for (int i = 0; i < walls.length; i++)
			walls[i] = true;
	}

	public static void removeWalls(Cell curr, Cell next) { // open path from curr cell to next
		int i = curr.x - next.x;
		int j = curr.y - next.y;

		if (i == 1) {
			curr.walls[3] = false;
			next.walls[1] = false;
		} else if (i == -1) {
			curr.walls[1] = false;
			next.walls[3] = false;
		}

		if (j == 1) {
			curr.walls[0] = false;
			next.walls[2] = false;
		} else if (j == -1) {
			curr.walls[2] = false;
			next.walls[0] = false;
		}
	}

	public void mark(Graphics g) { // mark the current cell
		int x1 = x * size;
		int y1 = y * size;

		if (path) {
			g.setColor(Color.GRAY);
			g.fillRect(x1 + 1, y1 + 1, size - 2, size - 2);

		} else {
			g.setColor(Color.GREEN);
			g.fillRect(x1 + 3, y1 + 3, size - 6, size - 6);
		}
	}

	public void markPath(Graphics g) { // highlight the solution path
		int x1 = x * size;
		int y1 = y * size;

		if (path) {
			g.setColor(Color.YELLOW);
			g.fillRect(x1 + 1, y1 + 1, size - 2, size - 2);
		}
	}

	public void draw(Graphics g) { // draw the cell
		g.setColor(Color.RED);
		Graphics2D G2D = (Graphics2D) g;

		G2D.setStroke(new BasicStroke(2.0F));
		int x1 = x * size;
		int y1 = y * size;

		if (walls[0]) // - (top wall)
			G2D.drawLine(x1, y1, x1 + size, y1);

		if (walls[1]) // o| (right wall)
			G2D.drawLine(x1 + size, y1, x1 + size, y1 + size);

		if (walls[2]) // _ (bottom wall)
			G2D.drawLine(x1 + size, y1 + size, x1, y1 + size);

		if (walls[3]) // |o (left wall)
			G2D.drawLine(x1, y1 + size, x1, y1);
		if (visited) {
			G2D.setColor(Color.LIGHT_GRAY);
			G2D.fillRect(x1, y1, size, size);
		}

	}

	public Cell getNeighbor(Cell[][] grid) { // check all the available (unvisited) neighbors and return a random one
												// among them, null if empty
		ArrayList<Cell> neighbors = new ArrayList<>();

		if (x > 0 && !grid[x - 1][y].visited)
			neighbors.add(grid[x - 1][y]);
		if (x < grid[0].length - 1 && !grid[x + 1][y].visited)
			neighbors.add(grid[x + 1][y]);
		if (y < grid[0].length - 1 && !grid[x][y + 1].visited)
			neighbors.add(grid[x][y + 1]);
		if (y > 0 && !grid[x][y - 1].visited)
			neighbors.add(grid[x][y - 1]);

		if (neighbors.size() > 0)
			return neighbors.get(r.nextInt(neighbors.size()));
		else
			return null;

	}

	public Cell getAnyNeighbor(Cell[][] grid) { // check all the available (unvisited) neighbors and return a random one
		// among them
		ArrayList<Cell> neighbors = new ArrayList<>();

		if (x > 0)
			neighbors.add(grid[x - 1][y]);
		if (x < grid[0].length - 1)
			neighbors.add(grid[x + 1][y]);
		if (y < grid[0].length - 1)
			neighbors.add(grid[x][y + 1]);
		if (y > 0)
			neighbors.add(grid[x][y - 1]);

		return neighbors.get(r.nextInt(neighbors.size()));

	}

	public boolean isVisited() {
		return visited;
	}

	public Cell getPathNeighbor(Cell[][] grid) {

		ArrayList<Cell> neighbors = new ArrayList<>();

		if (x > 0 && !grid[x - 1][y].deadEnd && !grid[x - 1][y].walls[1])
			neighbors.add(grid[x - 1][y]);
		if (x < grid[0].length - 1 && !grid[x + 1][y].deadEnd && !grid[x + 1][y].walls[3])
			neighbors.add(grid[x + 1][y]);
		if (y < grid[0].length - 1 && !grid[x][y + 1].deadEnd && !grid[x][y + 1].walls[0])
			neighbors.add(grid[x][y + 1]);
		if (y > 0 && !grid[x][y - 1].deadEnd && !grid[x][y - 1].walls[2])
			neighbors.add(grid[x][y - 1]);

		if (neighbors.size() > 0)
			return neighbors.get(r.nextInt(neighbors.size()));
		else
			return null;

	}

}
