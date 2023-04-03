public class UnionFind {
    private int [] arr;
    private int SIZE;

    public UnionFind (int size) {
        SIZE = size;
        arr = new int [size];
        for (int i = 0; i < size; i++) {
            arr[i] = -1;
        }
    }

    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX != rootY) {
            if (arr[rootY] < arr[rootX]) {
                arr[rootX] = rootY;
            }
            else{
                if (arr[rootY] == arr[rootX]) {
                    arr[rootX]--;
                }
                arr[rootY] = rootX;
            }
        }
    }


    public int find(int x) {
        if (arr[x] < 0) {
            return x;
        }
        return arr[x] = find(arr[x]);
    }

    public void printArray() {
        for (int i = 0; i < arr.length; i++) {
            System.out.print("  " + i + "  ");
        }
        System.out.println();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < 0) {
                System.out.print(" " + arr[i] + "  ");
            }
            else System.out.print("  " + arr[i] + "  ");
        }
        System.out.println("\n");
    }

    //

    /**
     * This add() function is really only useful for the grid to point to TOP or BOTTOM,
     * but I suppose it could be helpful outside this assignment
     * @param x, the number you want the new index to point at
     */
    public void add(int x) {
        int [] newArr = new int[arr.length + 1];
        for (int i = 0; i < arr.length; i++) {
            newArr[i] = arr[i];
        }
        SIZE++;
        newArr[arr.length] = (-1) * x;
        arr = newArr;
    }


    public static void main(String [] args) {
        UnionFind uf = new UnionFind(10);
        uf.printArray();

        uf.union(0,6);
        uf.union(3,8);
        uf.union(4,9);
        uf.union(5,4);
        uf.union(6,2);
        uf.union(8,6);
        uf.union(2,7);

        System.out.println("After Union");
        uf.printArray();

        System.out.println(uf.find(1));
        System.out.println(uf.find(3));
        System.out.println(uf.find(6));
        System.out.println(uf.find(8));

        System.out.println("After Path Compression and find ");
        uf.printArray();

        uf.add(99999);

        System.out.println("New Array");
        uf.printArray();
        System.out.println(uf.find(uf.SIZE - 1));

    }
}
