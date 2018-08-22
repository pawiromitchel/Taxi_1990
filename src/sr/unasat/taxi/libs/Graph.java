package sr.unasat.taxi.libs;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private final int MAX_VERTS = 20;
    private final int INFINITY = 1000000;
    private Vertex vertexList[]; // list of vertices
    private int adjMat[][]; // adjacency matrix
    private int nVerts; // current number of vertices
    private int nTree; // number of verts in tree
    private DistPar sPath[]; // array for shortest-path data
    private int currentVert; // current vertex
    private int startToCurrent; // distance to currentVert

    // Dept first search
    private StackX theStack;

    // Breath first search
    private Queue theQueue;

    public Graph() // constructor
    {
        vertexList = new Vertex[MAX_VERTS];
        // adjacency matrix
        adjMat = new int[MAX_VERTS][MAX_VERTS];
        nVerts = 0;
        nTree = 0;
        for (int j = 0; j < MAX_VERTS; j++) // set adjacency
            for (int k = 0; k < MAX_VERTS; k++) // matrix
                adjMat[j][k] = INFINITY; // to infinity
        sPath = new DistPar[MAX_VERTS]; // shortest paths
        theStack = new StackX();
        theQueue = new Queue();
    } // end constructor

    public int countVertex() {
        return nVerts - 1;
    }

    public void addVertex(String lab) {
        vertexList[nVerts++] = new Vertex(lab);
    }

    public void addEdge(int start, int end, int weight) {
        adjMat[start][end] = weight; // (directed)
    }

    public void shortestPathToArea(String area, int multiplier) // find the shortest path to area
    {
        int startTree = 0; // start at vertex 0
        vertexList[startTree].isInTree = true;
        nTree = 1; // put it in tree
        // transfer row of distances from adjMat to sPath
        for (int j = 0; j < nVerts; j++) {
            int tempDist = adjMat[startTree][j];
            sPath[j] = new DistPar(startTree, tempDist);
        }
        // until all vertices are in the tree
        while (nTree < nVerts) {
            int indexMin = getMin(); // get minimum from sPath
            int minDist = sPath[indexMin].distance;
            if (minDist == INFINITY) // if all infinite
            { // or in tree,
                System.out.println("There are unreachable vertices");
                break; // sPath is complete
            } else { // reset currentVert
                currentVert = indexMin; // to closest vert
                startToCurrent = sPath[indexMin].distance;
                // minimum distance from startTree is
                // to currentVert, and is startToCurrent
            }
            // put current vertex in tree
            vertexList[currentVert].isInTree = true;
            nTree++;
            adjust_sPath(multiplier); // update sPath[] array
        } // end while(nTree<nVerts)
        displayPaths(area); // display sPath[] contents
        nTree = 0; // clear tree
        for (int j = 0; j < nVerts; j++)
            vertexList[j].isInTree = false;
    } // end path()

    public int getMin() // get entry from sPath
    { // with minimum distance
        int minDist = INFINITY; // assume minimum
        int indexMin = 0;
        for (int j = 1; j < nVerts; j++) // for each vertex,
        { // if it's in tree and
            if (!vertexList[j].isInTree && // smaller than old one
                    sPath[j].distance < minDist) {
                minDist = sPath[j].distance;
                indexMin = j; // update minimum
            }
        } // end for
        return indexMin; // return index of minimum
    } // end getMin()

    public void longestPathToArea(String area) // find the shortest path to area
    {
        int startTree = 0; // start at vertex 0
        vertexList[startTree].isInTree = true;
        nTree = 1; // put it in tree
        // transfer row of distances from adjMat to sPath
        for (int j = 0; j < nVerts; j++) {
            int tempDist = adjMat[startTree][j];

            if (tempDist == INFINITY) {
                tempDist = 0;
                sPath[j] = new DistPar(startTree, tempDist);
            } else {
                sPath[j] = new DistPar(startTree, tempDist);
            }
        }
        // until all vertices are in the tree
        while (nTree < nVerts) {
            int indexMin = getMax(); // get minimum from sPath
            int minDist = sPath[indexMin].distance;
            if (minDist == INFINITY) // if all infinite
            { // or in tree,
                System.out.println("There are unreachable vertices");
                break; // sPath is complete
            } else { // reset currentVert
                currentVert = indexMin; // to closest vert
                startToCurrent = sPath[indexMin].distance;
                // minimum distance from startTree is
                // to currentVert, and is startToCurrent
            }
            // put current vertex in tree
            vertexList[currentVert].isInTree = true;
            nTree++;
            adjust_lPath(); // update sPath[] array
        } // end while(nTree<nVerts)
        displayPaths(area); // display sPath[] contents
        nTree = 0; // clear tree
        for (int j = 0; j < nVerts; j++)
            vertexList[j].isInTree = false;
    } // end path()

    public int getMax() // get entry from sPath
    { // with maximum distance
        int maxDist = 0; // assume minimum
        int indexMax = 0;
        for (int j = 1; j < nVerts; j++) // for each vertex,
        { // if it's in tree and
            if (!vertexList[j].isInTree && // smaller than old one
                    sPath[j].distance > maxDist) {
                maxDist = sPath[j].distance;
                indexMax = j; // update maximum
            }
        } // end for
        return indexMax; // return index of minimum
    } // end getMax()

    public void adjust_sPath(int multiplier) {
        // adjust values in shortest-path array sPath
        int column = 1; // skip starting vertex
        while (column < nVerts) // go across columns
        {
            // if this column's vertex already in tree, skip it
            if (vertexList[column].isInTree) {
                column++;
                continue;
            }
            // calculate distance for one sPath entry
            // get edge from currentVert to column
            int currentToFringe = adjMat[currentVert][column];
            // add distance from start
            int startToFringe = (startToCurrent + currentToFringe) * multiplier;
            // get distance of current sPath entry
            int sPathDist = sPath[column].distance;
            // compare distance from start with sPath entry
            if (startToFringe < sPathDist) // if shorter,
            { // update sPath
                sPath[column].parentVert = currentVert;
                sPath[column].distance = startToFringe;
            }
            column++;
        } // end while(column < nVerts)
    } // end adjust_sPath()

    public void adjust_lPath() {
        // adjust values in shortest-path array sPath
        int column = 1; // skip starting vertex
        while (column < nVerts) // go across columns
        {
            // if this column's vertex already in tree, skip it
            if (vertexList[column].isInTree) {
                column++;
                continue;
            }
            // calculate distance for one sPath entry
            // get edge from currentVert to column
            int currentToFringe = adjMat[currentVert][column];

            if (currentToFringe == INFINITY) {
                currentToFringe = 0;
            }

            // add distance from start
            int startToFringe = startToCurrent + currentToFringe;
            // get distance of current sPath entry
            int sPathDist = sPath[column].distance;
            // compare distance from start with sPath entry
            if (startToFringe > sPathDist && startToFringe != startToCurrent) // if shorter,
            { // update sPath
                sPath[column].parentVert = currentVert;
                sPath[column].distance = startToFringe;
            }
            column++;
        } // end while(column < nVerts)
    } // end adjust_sPath()

    public void displayPaths(String desiredAreaToGo) {

        // array to hold the vertices
        List vertexLevel = new ArrayList();

        for (int j = 0; j < nVerts; j++) // display contents of sPath[]
        {
            // parent -> child = SRD ...
            String buildString = "";

            String areaComingFrom = vertexList[sPath[j].parentVert].label;
            String areaGoingTo = vertexList[j].label;
            int areaAmount = sPath[j].distance;

            buildString = areaComingFrom + " -> " + areaGoingTo;

            if (areaAmount == INFINITY) {
                buildString = buildString + " = Start";
            } else {
                buildString = buildString + " = SRD " + areaAmount;
            }

            // detect if areaComingFrom is Centrum, this means that the search is on another level
            if(CompareTo.execute(areaComingFrom, "Centrum") == 0){
                // clear the arrayList
                vertexLevel.clear();
            }

            // add to level
            vertexLevel.add(buildString);

            // detect if the area is found
            if(CompareTo.execute(areaGoingTo, desiredAreaToGo) == 0) {
                vertexLevel.add("You must pay SRD " + areaAmount);
                // jump out of the for loop
                break;
            }
        }

        // print
        vertexLevel.forEach(element -> System.out.println(element));
    }

    // search for a area
    public List dfs(String area, boolean searchForSurroundingAreas) // depth-first search
    { // begin at vertex 0
        vertexList[0].wasVisited = true; // mark it
        // displayVertex(0); // display it
        theStack.push(0); // push it

        List areas = new ArrayList();

        while (!theStack.isEmpty()) // until stack empty,
        {
            // get an unvisited vertex adjacent to stack top
            int v = getAdjUnvisitedVertex(theStack.peek());
            if (v == -1) {
                // if no such vertex,
                theStack.pop();
                // clear the arraylist
                areas.clear();
            } else // if it exists,
            {
                vertexList[v].wasVisited = true; // mark it
                // displayVertex(v); // display it
                theStack.push(v); // push it

                // push into the array
                areas.add(vertexList[v].label);

                // if the area is found exit out of the loop
                if (CompareTo.execute(vertexList[v].label, area) == 0) {
                    if(searchForSurroundingAreas){
                        // search for surrounding areas
                        bfs(v);
                        break;
                    } else {
                        // stop the loop when the area is found
                        break;
                    }
                }
            }
        } // end while
        // stack is empty, so we’re done
        for (int j = 0; j < nVerts; j++) // reset flags
            vertexList[j].wasVisited = false;

        return areas;
    } // end dfs

    public void displayVertex(int v) {
        System.out.println(vertexList[v].label);
    }

    public int getAdjUnvisitedVertex(int v) {
        for (int j = 0; j < nVerts; j++)
            if (adjMat[v][j] > 0 && vertexList[j].wasVisited == false && adjMat[v][j] < INFINITY)
                return j;
        return -1;
    } // end getAdjUnvisitedVertex()

    public void bfs(int startingPoint) // breadth-first search
    { // begin at vertex 0
        System.out.println("---> Surrounding Areas <---");
        vertexList[startingPoint].wasVisited = true; // mark it
        // displayVertex(startingPoint); // display it
        theQueue.insert(startingPoint); // insert at tail
        int v2;
        while (!theQueue.isEmpty()) // until queue empty,
        {
            int v1 = theQueue.remove(); // remove vertex at head
            // until it has no unvisited neighbors
            while ((v2 = getAdjUnvisitedVertex(v1)) != -1) { // get one,
                vertexList[v2].wasVisited = true; // mark it
                displayVertex(v2); // display it
                theQueue.insert(v2); // insert it
            } // end while
            break;
        } // end while(queue not empty)
        // queue is empty, so we’re done
        for (int j = 0; j < nVerts; j++) // reset flags
            vertexList[j].wasVisited = false;
    } // end bfs()

    public void editArea(String area, String newAreaLabel, int startEdge, int endEdge, int amount){
        vertexList[0].wasVisited = true; // mark it
        theStack.push(0); // push it

        boolean found = false;

        while (!theStack.isEmpty()) // until stack empty,
        {
            // get an unvisited vertex adjacent to stack top
            int v = getAdjUnvisitedVertex(theStack.peek());
            if (v == -1) {
                // if no such vertex,
                theStack.pop();
            } else // if it exists,
            {
                vertexList[v].wasVisited = true; // mark it
                theStack.push(v); // push it

                // if the area is found exit out of the loop
                if (CompareTo.execute(vertexList[v].label, area) == 0) {
                    found = true;
                    // stop the loop when the area is found
                    System.out.println("Found area, changing name now");
                    vertexList[v].label = newAreaLabel;
                    System.out.println("Changing its edges");
                    // adjMat[startEdge][endEdge] = amount;
                    System.out.println("Here's the new name:");
                    System.out.println(vertexList[v].label);
                    break;
                }
            }
        } // end while
        // stack is empty, so we’re done
        for (int j = 0; j < nVerts; j++) // reset flags
            vertexList[j].wasVisited = false;

        if(!found) {
            System.out.println("Area not found");
        }
    }
} // end class Graph