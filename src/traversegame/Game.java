package traversegame;

public class Game {

    public class Cell{

        private int a_h;
        private int i_viii;

        public Cell() {
            a_h = 0;
            i_viii = 0;
        }

        public int getA(){
            return a_h;
        }

        public int getI(){
            return i_viii;
        }

        public Cell(int a, int i) {
            a_h = a;
            i_viii = i;
        }

        public boolean equal(Cell c){
            if (a_h == c.a_h && i_viii == c.i_viii)
                return true;
            return false;
        }

        public Cell copy(){
            Cell c = new Cell(a_h,i_viii);
            return c;
        }
    }

    public void play(){
        final int[][] map = generateMap();
        CellArrayList pathArray = new CellArrayList();
        CellArrayList stonesArray = new CellArrayList();
        PathArrayList allPaths = new PathArrayList();
        findTheShortestPath(map,pathArray,stonesArray,allPaths,0,0);
        int random = (int) (Math.random()*allPaths.size());
        int min = allPaths.getPath(random).size();
        int minIndex = random;
        for (int i=0; i<allPaths.size(); ++i){
            if (allPaths.getPath(i).size() < min){
                min = allPaths.getPath(i).size();
                minIndex = i;
            }
        }
        String[][] printMap;
        printMap = new String[8][];
        for (int i=0; i<8; ++i)
            printMap[i] = new String[8];
        for (int i=0; i<8; ++i){
            for (int j=0; j<8; ++j){
                printMap[i][j] = ".";
            }
        }
        for (int j=0; j< allPaths.getPath(minIndex).size(); ++j){
            int a = allPaths.getPath(minIndex).get(j).getA();
            int i = allPaths.getPath(minIndex).get(j).getI();
            printMap[a][i] = "o";
        }
        for (int i=0; i<8; ++i){
            for (int j=0; j<8; ++j){
                if (map[i][j] == 1)
                    printMap[i][j] = "X";
            }
        }
        System.out.printf("\nShortest path: \n\n");
        for (int j=7; j>-1; --j){
            System.out.printf("%d ",j+1);
            for(int i=0; i<8; ++i){
                System.out.printf("%s ",printMap[i][j]);
            }
            System.out.printf("\n");
        }
        System.out.printf("  A B C D E F G H \n\n");
        System.out.printf("Bumped stones: ");
        for (int j=0; j< allPaths.getStones(minIndex).size(); ++j){
            int a = allPaths.getStones(minIndex).get(j).getA();
            switch (a){
                case 0:
                    System.out.printf("A");
                    break;
                case 1:
                    System.out.printf("B");
                    break;
                case 2:
                    System.out.printf("C");
                    break;
                case 3:
                    System.out.printf("D");
                    break;
                case 4:
                    System.out.printf("E");
                    break;
                case 5:
                    System.out.printf("F");
                    break;
                case 6:
                    System.out.printf("G");
                    break;
                case 7:
                    System.out.printf("H");
                    break;
            }
            int i = allPaths.getStones(minIndex).get(j).getI();
            System.out.printf("%d ",i+1);
        }
        System.out.printf("\n");
    }

    public int[][] generateMap(){
        boolean hasEnd = false;
        int[][] tempMap = null;
        while(!hasEnd){
            int blackCount = (int) (Math.random()*9 + 1);
            int[][] blackStones;
            blackStones = new int[blackCount][];
            for (int i=0; i<blackCount; ++i){
                blackStones[i] = new int[2];
            }
            int count = 0;
            for (int i=0; i<blackCount; ++i) {
                int a_h = 0;
                int i_viii = 0;
                boolean hasDuplicate = true;
                while (hasDuplicate) {
                    a_h = (int) (Math.random()*8);
                    i_viii = (int) (Math.random()*8);
                    hasDuplicate = false;
                    for (int j = 0; j < count; ++j) {
                        if ((blackStones[j][0] == a_h && blackStones[j][1] == i_viii) || (a_h == 0 && i_viii == 0) || (a_h == 7 && i_viii == 7)) {
                            hasDuplicate = true;
                            break;
                        }
                    }
                }
                blackStones[count][0] = a_h;
                blackStones[count][1] = i_viii;
                ++count;
            }
            tempMap = new int[10][];
            for (int i=0; i<10; ++i)
                tempMap[i] = new int[10];
            for (int i=0; i<8; ++i)
                for (int j=0; j<10; ++j)
                    tempMap[i][j] = 0;
            for (int i=0; i<blackCount; ++i)
                tempMap[blackStones[i][0]][blackStones[i][1]] = 1;
            hasEnd = doesMapHasEnd(tempMap,new CellArrayList(),0,0);
        }
        return tempMap;
    }

    public boolean doesMapHasEnd(int[][] testmap, CellArrayList path, int a, int i){
        if(a == 7 && i == 7)
            return true;
        else{
            path.add(new Cell(a,i));
            boolean right = false;
            if(a<7)
                if(testmap[a + 1][i] == 0 && !(path.contains(new Cell(a + 1, i))))
                    right = doesMapHasEnd(testmap, path, a + 1, i);
            boolean up = false;
            if(i<7)
                if(testmap[a][i + 1] == 0 && !(path.contains(new Cell(a, i + 1))))
                    up = doesMapHasEnd(testmap, path, a, i+1);
            /*
            boolean left = false;
            if(a>0)
                if(testmap[a - 1][i] == 0 && !(path.contains(new Cell(a - 1, i))))
                    left = doesMapHasEnd(testmap, path, a - 1, i);
            boolean down = false;
            if(i>0)
                if (testmap[a][i - 1] == 0 && !(path.contains(new Cell(a, i - 1))))
                    down = doesMapHasEnd(testmap, path, a, i-1);
            */
            path.remove();
            return (right || up /*|| left || down*/);
        }
    }

    public void findTheShortestPath(int[][] testmap, CellArrayList path, CellArrayList stones, PathArrayList theShortest, int a, int i){
        if(a == 7 && i == 7){
            path.add(new Cell(7,7));
            int count = 0;
            if (testmap[6][7] == 1 && !stones.contains(new Cell(6,7))) {
                stones.add(new Cell(6, 7));
                ++count;
            }
            if (testmap[7][6] == 1 && !stones.contains(new Cell(7,6))) {
                stones.add(new Cell(7, 6));
                ++count;
            }
            if (stones.size() > 0)
                theShortest.add(path.copy(),stones.copy());
            for (int in=0; in<count; ++in)
                stones.remove();
            path.remove();
        }
        else{
            path.add(new Cell(a,i));
            int count = 0;
            if(a<7)
                if(testmap[a + 1][i] == 1 && !(stones.contains(new Cell(a + 1, i)))){
                    stones.add(new Cell(a+1,i));
                    ++count;
                }
            if(i<7)
                if(testmap[a][i + 1] == 1 && !(stones.contains(new Cell(a, i + 1)))){
                    stones.add(new Cell(a,i+1));
                    ++count;
                }
            if(a>0)
                if(testmap[a - 1][i] == 1 && !(stones.contains(new Cell(a - 1, i)))){
                    stones.add(new Cell(a-1,i));
                    ++count;
                }
            if(i>0)
                if (testmap[a][i - 1] == 1 && !(stones.contains(new Cell(a, i - 1)))){
                    stones.add(new Cell(a,i-1));
                    ++count;
                }
            if(a<7)
                if(testmap[a + 1][i] == 0 && !(path.contains(new Cell(a + 1, i))))
                    findTheShortestPath(testmap, path, stones,theShortest,a + 1, i);
            if(i<7)
                if(testmap[a][i + 1] == 0 && !(path.contains(new Cell(a, i + 1))))
                    findTheShortestPath(testmap, path, stones,theShortest,a, i+1);
            /*
            if(a>0)
                if(testmap[a - 1][i] == 0 && !(path.contains(new Cell(a - 1, i))))
                    findTheShortestPath(testmap, path, stones,theShortest,a - 1, i);
            if(i>0)
                if (testmap[a][i - 1] == 0 && !(path.contains(new Cell(a, i - 1))))
                    findTheShortestPath(testmap, path, stones,theShortest,a, i-1);
            */
            for (int j=0; j<count; ++j)
                stones.remove();
            path.remove();
        }
    }

}
