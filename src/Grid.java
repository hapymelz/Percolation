public class Grid {
    int SIZE;

    UnionFind top;
    UnionFind bottom;

    int TOP;
    int BOTTOM;
    int [][] grid;
    String BLUE = "\u001B[44m   \u001B[0m";
    String WHITE = "\033[0;107m   \u001B[0m";
    String BLACK = "\u001B[40m   \u001B[0m";
    String RED = "\u001B[41m   \u001B[0m";
    String[] sym = {BLACK, WHITE, BLUE, RED};

    public Grid(int size) {
        this.SIZE = size;
        this.grid = new int[size][size];
        this.top = new UnionFind(size * size);
        this.bottom = new UnionFind(size * size);
        this.TOP = size * size;
        this.BOTTOM = size * size;

        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                this.grid[x][y] = 0;
            }
        }
        this.top.add(this.TOP);
        this.bottom.add(this.BOTTOM);

    }

    public boolean percolate(int x, int y) {
        int id = getID(x, y);

        if (grid[x][y] == 0) {
            grid[x][y] = 1;

            //Checking top of the node
            if (y - 1 < 0) {
                top.union(id, TOP);
            } else if (grid[x][y - 1] != 0) {
                top.union(id, getID(x, y - 1));
                bottom.union(id, getID(x, y - 1));
                top.find(getID(x, y - 1));
                bottom.find(getID(x, y - 1));
            }

            //Checking bottom of the node
            if (y + 1 == SIZE) {
                bottom.union(id, BOTTOM);
            } else if (grid[x][y + 1] != 0) {
                top.union(id, getID(x, (y + 1)));
                bottom.union(getID(x, (y + 1)), id);
                top.find(getID(x, y + 1));
                bottom.find(getID(x, y + 1));
            }

            //Checking left of the node
            if (x - 1 >= 0) {
                if (grid[x - 1][y] != 0) {
                    top.union(id, getID(x - 1, y));
                    bottom.union(id, getID(x - 1, y));
                    top.find(getID(x - 1, y));
                    bottom.find(getID(x - 1, y));
                }
            }

            //Checking right of the node
            if (x + 1 < SIZE) {
                if (grid[x + 1][y] != 0) {
                    top.union(id, getID(x + 1, y));
                    bottom.union(getID(x + 1, y), id);
                    top.find(getID(x + 1, y));
                    bottom.find(getID(x + 1, y));
                }
            }
        }

        return top.find(id) == TOP && bottom.find(id) == BOTTOM;
    }

    public void printRandomGrid() {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                this.grid[x][y] = (int) (Math.random()*sym.length);
                System.out.print(sym[this.grid[x][y]]);
            }
            System.out.println();
        }
    }

    public void updateColors() {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                int id = getID(x, y);
                if (top.find(id) == TOP || bottom.find(id) == BOTTOM) {
                    grid[x][y] = 2;
                }
                if (top.find(id) == top.find(TOP) && bottom.find(id) == bottom.find(BOTTOM)) {
                    grid[x][y] = 3;
                }
            }
        }
    }

    public void printGrid() {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                System.out.print(sym[this.grid[y][x]]);
            }
            System.out.println();
        }
    }

    private int getID(int x, int y) {
        return SIZE * y + x;
    }

    private int getX (int id) {
        return id % SIZE;
    }

    private int getY (int id) {
        return id / SIZE;
    }




    public static void main(String [] args) {
        // Instantiate a new grid with the size parameter
        Grid g = new Grid(20);
        int count = 1;

        while (true) {

            int randX = (int) (Math.random()*g.SIZE);
            int randY = (int) (Math.random()*g.SIZE);

            //Verifying the cell has not been opened before
            while (g.grid[randX][randY] != 0) {
                randX = (int) (Math.random()*g.SIZE);
                randY = (int) (Math.random()*g.SIZE);
            }

            // Percolating the data, if percolate() returns true, it has successfully percolated
            if (g.percolate(randX, randY)) {
                // updating the grid colors after percolate()
                g.updateColors();
                g.printGrid();
                System.out.println("After " + count + " open sites out of " + (g.SIZE * g.SIZE) + " sites");
                break;
            }



            /* Uncomment this code if you want to see the progress*/

            // updating the grid colors after percolate()
//            g.updateColors();
//
//            if (count % g.SIZE == 0) {
//                g.printGrid();
//                System.out.println("After " + count + " open sites");
//                System.out.println();
//            }

            //updating how many cells have been opened
            count++;
        }
    }

}
