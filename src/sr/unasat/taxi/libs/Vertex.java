package sr.unasat.taxi.libs;

public class Vertex
{
    public String label;
    public double weight;
    public boolean isInTree;
    public boolean wasVisited;
    // -------------------------------------------------------------
    public Vertex(String lab) // constructor
    {
        label = lab;
        isInTree = false;
        wasVisited = false;
    }
// -------------------------------------------------------------
} // end class Vertex
