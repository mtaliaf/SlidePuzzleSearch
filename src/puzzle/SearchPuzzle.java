package puzzle;

import java.util.*;

public class SearchPuzzle
{
    private static ArrayList<SlidePuzzle> generateChildren(SlidePuzzle p)
    {
        ArrayList<String> moves = p.getValidMoves();
        ArrayList<SlidePuzzle> temp = new ArrayList<SlidePuzzle>();
        
        for(int i = 0; i<moves.size(); i++)
        {
            temp.add(new SlidePuzzle(p.getPuzzleState()));
            temp.get(i).move(moves.get(i));
        }
        
        return temp;
    }
    
    private static HashMap buildDictionary(SlidePuzzle currentNode, 
                                           ArrayList<SlidePuzzle> children, 
                                           HashMap parent,
                                           int depth,
                                           int maxFrontierSize,
                                           int goalsTested)
    {
        HashMap temp = new HashMap();
        temp.put("node", currentNode);
        temp.put("children", children);
        temp.put("parent", parent);
        temp.put("depth", depth);
        temp.put("maxFrontierSize", maxFrontierSize);
        temp.put("goalsTested", goalsTested);
        
        return temp;
    }
    
    private static boolean isInFrontier(ArrayList<HashMap> frontier, SlidePuzzle child)
    {
        for(HashMap f: frontier)
            if(((SlidePuzzle)f.get("node")).compare(child.getPuzzleState()))
                        return true;
        return false;
    }
    
    private static boolean isInSearched(SlidePuzzle child, ArrayList<char[][]> searched)
    {
        for(char[][] s: searched)
            if(child.compare(s))
                return true;
        return false;
    }
    
    public static HashMap breathFirstSearch(SlidePuzzle p)
    {
        int maxFrontierSize = 0;
        int goalsTested = 1;
        
        if(p.compare())
            return buildDictionary(p,null,null,0,maxFrontierSize,1);
        
        ArrayList<HashMap> frontier = new ArrayList<HashMap>();
        frontier.add(buildDictionary(p,generateChildren(p),null,0,1,1));
        
        while(true)
        {
            goalsTested++;
            maxFrontierSize = (frontier.size() > maxFrontierSize) ? frontier.size() : maxFrontierSize;
            
            if(frontier.isEmpty())
                return null;
            HashMap node = frontier.remove(0);
            
            for(SlidePuzzle child: (ArrayList<SlidePuzzle>)node.get("children"))
            {
                if(child.compare())
                    return buildDictionary(child,null,node,((Integer)node.get("depth"))+1,maxFrontierSize,goalsTested);
                if(!isInFrontier(frontier,child))
                    frontier.add(buildDictionary(child,generateChildren(child),node,((Integer)node.get("depth"))+1,maxFrontierSize,goalsTested));
                    
            }
            
        }

    }
    
    public static HashMap breathFirstSearchVisited(SlidePuzzle p)
    {
        int maxFrontierSize = 0;
        int goalsTested = 1;
        
        if(p.compare())
            return buildDictionary(p,null,null,0,0,1);
        
        ArrayList<HashMap> frontier = new ArrayList<HashMap>();
        ArrayList<char[][]> searched = new ArrayList<char[][]>();
        
        frontier.add(buildDictionary(p,generateChildren(p),null,0,1,1));
        searched.add(p.getPuzzleState());
        
        while(true)
        {
            goalsTested++;
            maxFrontierSize = (frontier.size() > maxFrontierSize) ? frontier.size() : maxFrontierSize;
            
            if(frontier.isEmpty())
                return null;
            HashMap node = frontier.remove(0);
            
            for(SlidePuzzle child: (ArrayList<SlidePuzzle>)node.get("children"))
            {
                if(child.compare())
                    return buildDictionary(child,null,node,((Integer)node.get("depth"))+1,maxFrontierSize,goalsTested);
                if(!isInFrontier(frontier,child)&&!isInSearched(child,searched))
                {
                    frontier.add(buildDictionary(child,generateChildren(child),node,((Integer)node.get("depth"))+1,maxFrontierSize,goalsTested));
                    searched.add(child.getPuzzleState());
                }
                    
            }
            
        }

    }
    
    public static HashMap depthFirstSearch(SlidePuzzle p)
    {
        int maxFrontierSize = 0;
        int goalsTested = 1;
        
        if(p.compare())
            return buildDictionary(p,null,null,0,0,1);
        
        ArrayList<HashMap> frontier = new ArrayList<HashMap>();
        ArrayList<char[][]> searched = new ArrayList<char[][]>();
        
        frontier.add(buildDictionary(p,generateChildren(p),null,0,1,1));
        searched.add(p.getPuzzleState());
        
        while(true)
        {
            goalsTested++;
            maxFrontierSize = (frontier.size() > maxFrontierSize) ? frontier.size() : maxFrontierSize;
            
            if(frontier.isEmpty())
                return null;
            HashMap node = frontier.remove(frontier.size()-1);
            
            if((Integer)node.get("depth") > 5000)
                return null;
            
            for(SlidePuzzle child: (ArrayList<SlidePuzzle>)node.get("children"))
            {
                if(child.compare())
                    return buildDictionary(child,null,node,((Integer)node.get("depth"))+1,maxFrontierSize,goalsTested);
                if(!isInFrontier(frontier,child)&&!isInSearched(child,searched))
                {
                    frontier.add(buildDictionary(child,generateChildren(child),node,((Integer)node.get("depth"))+1,maxFrontierSize,goalsTested));
                    searched.add(child.getPuzzleState());
                }
                    
            }
            
        }

    }
    
    public static HashMap depthLimitedSearch(SlidePuzzle p, int depth)
    {
        int maxFrontierSize = 0;
        int goalsTested = 1;
        
        if(p.compare())
            return buildDictionary(p,null,null,0,0,1);
        
        ArrayList<HashMap> frontier = new ArrayList<HashMap>();
        ArrayList<char[][]> searched = new ArrayList<char[][]>();
        
        frontier.add(buildDictionary(p,generateChildren(p),null,0,1,1));
        searched.add(p.getPuzzleState());
        
        while(true)
        {
            goalsTested++;
            maxFrontierSize = (frontier.size() > maxFrontierSize) ? frontier.size() : maxFrontierSize;
            
            if(frontier.isEmpty())
                return null;
            
            HashMap node = frontier.remove(frontier.size()-1);
            
            if((Integer)node.get("depth")<depth)
                for(SlidePuzzle child: (ArrayList<SlidePuzzle>)node.get("children"))
                {
                    if(child.compare())
                        return buildDictionary(child,null,node,((Integer)node.get("depth"))+1,maxFrontierSize,goalsTested);
                    if(!isInFrontier(frontier,child)&&!isInSearched(child,searched))
                    {
                        frontier.add(buildDictionary(child,generateChildren(child),node,((Integer)node.get("depth"))+1,maxFrontierSize,goalsTested));
                        searched.add(child.getPuzzleState());
                    }

                }
            
        }

    }
    
    public static HashMap iterativeDeepening(SlidePuzzle p)
    {
           int currentDepth = 1;
           HashMap temp = depthLimitedSearch(p,currentDepth);
           
           while(temp == null)
               temp = depthLimitedSearch(p,currentDepth++);
           
           return temp;
    }
    
    public static void printBackTrace(HashMap h)
    {
        if(h == null)
        {
            System.out.println(" FAIL!!");
            return;
        }
        
        SlidePuzzle p = (SlidePuzzle)h.get("node");        
        Stack<SlidePuzzle> s = new Stack<SlidePuzzle>();
        s.push(p);
        
        System.out.print(" Depth: "+(Integer)h.get("depth"));
        System.out.print(" maxFrontierSize: "+(Integer)h.get("maxFrontierSize"));
        System.out.print(" Goals Tested: "+(Integer)h.get("goalsTested") + "\n");
        
        while(h.get("parent")!=null)
        {
            h = (HashMap)h.get("parent");
            p = (SlidePuzzle)h.get("node"); 
            s.push(p);
        }
        
        while(!s.isEmpty())
            System.out.println(s.pop());
    }
}