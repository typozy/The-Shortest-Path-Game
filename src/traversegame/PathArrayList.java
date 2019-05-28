package traversegame;

public class PathArrayList {

    private CellArrayList[] pathList;
    private CellArrayList[] stonesList;
    private int size;

    public int size(){
        return size;
    }

    public CellArrayList getPath(int i){
        return pathList[i];
    }

    public CellArrayList getStones(int i){
        return stonesList[i];
    }

    public PathArrayList(){
        pathList = new CellArrayList[10];
        stonesList = new CellArrayList[10];
        size = 0;
    }

    public void add(CellArrayList pathArr, CellArrayList stonesArr){
        if (size < pathList.length){
            pathList[size] = pathArr;
            stonesList[size] = stonesArr;
            ++size;
        }
        else{
            CellArrayList[] pathTemp = new CellArrayList[size+10];
            CellArrayList[] stonesTemp = new CellArrayList[size+10];
            for (int i=0; i<size; ++i){
                pathTemp[i] = pathList[i];
                stonesTemp[i] = stonesList[i];
            }
            pathTemp[size] = pathArr;
            stonesTemp[size] = stonesArr;
            pathList = pathTemp;
            stonesList = stonesTemp;
            ++size;
        }
    }

}
