package puzzle;

import java.util.*;

public class PuzzleMain
{
    public static void main(String[] args)
    {
        SlidePuzzle p = new SlidePuzzle();
        p.generateRandomPuzzle(10);
        //System.out.println(p);
        

        System.out.print("BFSV");
        SearchPuzzle.printBackTrace(SearchPuzzle.iterativeDeepening(p));
    }
}