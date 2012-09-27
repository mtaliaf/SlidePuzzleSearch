package puzzle;

import java.util.*; 

public class SlidePuzzle
{
    private char[][] puzzleState;
    private final char[][] GOAL = {{'1','2','3'},{'4','N','5'},{'6','7','8'}};
    
    final String UP = "up";
    final String DOWN = "down";
    final String LEFT = "left";
    final String RIGHT = "right";
    
    public SlidePuzzle()
    {
        puzzleState = new char[3][3];
        
        for (int y = 0; y < GOAL.length; y++)
            for (int x = 0; x < GOAL[y].length; x++)
                puzzleState[y][x] = GOAL[y][x];
    }
    
    public SlidePuzzle(char[][] state)
    {
        puzzleState = new char[3][3];
        
        for (int y = 0; y < state.length; y++)
            for (int x = 0; x < state[y].length; x++)
                puzzleState[y][x] = state[y][x];
    }
    
    public char[][] getPuzzleState()
    {
        return puzzleState;
    }
          
    
    public String toString()
    {
        String temp = "";
        for(char[] row: getPuzzleState())
            temp+=new String(row) + "\n";
        
        return temp;
    }
    
    public int[] getBlank()
    {
        int[] temp = new int[2];
        for(int y=0; y < getPuzzleState().length; y++)
            for(int x = 0; x<getPuzzleState()[y].length;x++)
                if(getPuzzleState()[y][x] == 'N')
                {
                    temp[0] = x;
                    temp[1] = y;
                }

        return temp;
    }
    
    public boolean isValidMove(String move)
    {
        int[] cord = getBlank();
        
        if(move.equals(UP))
            return cord[1] != 0;
        else if (move.equals(DOWN))
            return cord[1] != 2;
        else if (move.equals(LEFT))
            return cord[0] != 0;
        else if (move.equals(RIGHT))
            return cord[0] != 2;
        else
        {
            System.out.println("Invalid Move!");
            return false;
        }
    }
    
    public void move(String move)
    {
        int[] cord = getBlank();
        
        if(move.equals(UP))
            swap(puzzleState,cord[0],cord[1],cord[0],cord[1]-1);
        else if (move.equals(DOWN))
            swap(puzzleState,cord[0],cord[1],cord[0],cord[1]+1);
        else if (move.equals(LEFT))
            swap(puzzleState,cord[0],cord[1],cord[0]-1,cord[1]);
        else if (move.equals(RIGHT))
            swap(puzzleState,cord[0],cord[1],cord[0]+1,cord[1]);
    }
    
    public String getRandomMove()
    {
        ArrayList<String> validMoves = new ArrayList<String>();
        validMoves.add("up");
        validMoves.add("down");
        validMoves.add("left");
        validMoves.add("right");
        
        if(!isValidMove(UP))
            validMoves.remove("up");
        if(!isValidMove(DOWN))
            validMoves.remove("down");
        if(!isValidMove(LEFT))
            validMoves.remove("left");
        if(!isValidMove(RIGHT))
            validMoves.remove("right");
        
        Collections.shuffle(validMoves);
        return validMoves.get(0);
        
    }
    
    public ArrayList<String> getValidMoves()
    {
        ArrayList<String> validMoves = new ArrayList<String>();
        validMoves.add("up");
        validMoves.add("down");
        validMoves.add("left");
        validMoves.add("right");
        
        if(!isValidMove(UP))
            validMoves.remove("up");
        if(!isValidMove(DOWN))
            validMoves.remove("down");
        if(!isValidMove(LEFT))
            validMoves.remove("left");
        if(!isValidMove(RIGHT))
            validMoves.remove("right");
        
        return validMoves;
    }
    
    public void generateRandomPuzzle(int numMoves)
    {
        for(int i = 0; i<numMoves; i++)
            move(getRandomMove());
    }
    
    public boolean compare()
    {
        
        return Arrays.deepEquals(puzzleState,GOAL);
    }
    
    public boolean compare(char[][] c)
    {
        
        return Arrays.deepEquals(puzzleState,c);
    }
    
    public ArrayList<SlidePuzzle> generateChildren()
    {
        List<String> moves = getValidMoves();
        ArrayList<SlidePuzzle> temp = new ArrayList<SlidePuzzle>(); 
        
        for(int i = 0; i < moves.size(); i++)
            temp.add(new SlidePuzzle(getPuzzleState()));
        
        for(int i = 0; i < moves.size(); i++)
            temp.get(i).move(moves.get(i));
        
        return temp;    
    }
    
    private static void swap(char[][] a, int i0, int j0, int i1, int j1) 
    {
        char temp = a[j0][i0];
        a[j0][i0] = a[j1][i1];
        a[j1][i1] = temp;
    }
}