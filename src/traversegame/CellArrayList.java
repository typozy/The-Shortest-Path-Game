package traversegame;

public class CellArrayList {

    private Game.Cell[] list;
    private int size;

    public CellArrayList(){
        list = new Game.Cell[10];
        size = 0;
    }

    public int size(){
        return size;
    }

    public Game.Cell get(int index){
        return list[index];
    }

    public CellArrayList(CellArrayList me){
        list = new Game.Cell[me.list.length];
        size = me.size;
        for (int i=0; i<size;++i){
            list[i] = me.list[i].copy();
        }
    }

    public void add(Game.Cell c){
        if(size < list.length){
            list[size] = c;
            ++size;
        }
        else{
            Game.Cell[] temp = new Game.Cell[list.length+10];
            for (int i=0; i<list.length; ++i){
                temp[i] = list[i];
            }
            temp[size] = c;
            list = temp;
            ++size;
        }
    }

    public boolean contains(Game.Cell c){
        for (int i=0; i<size; ++i)
            if (list[i].equal(c))
                return true;
        return false;
    }

    public CellArrayList copy(){
        CellArrayList temp = new CellArrayList(this);
        return temp;
    }

    public void remove(){--size;}

}
