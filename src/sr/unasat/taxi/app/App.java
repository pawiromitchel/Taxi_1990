package sr.unasat.taxi.app;

import sr.unasat.taxi.libs.Graph;

import java.util.List;
import java.util.Scanner;

public class App {
    public static Graph initGraph() {
        Graph theGraph = new Graph();
        theGraph.addVertex("Centrum"); // 0 (start)
        theGraph.addVertex("Maretraite Mall"); // 1
        theGraph.addVertex("Leonsberg"); // 2
        theGraph.addVertex("Ondrobong"); // 3
        theGraph.addVertex("Lyco 1"); // 4
        theGraph.addVertex("NATIN"); // 5
        theGraph.addVertex("Kasabaolo"); // 6
        theGraph.addVertex("Dijkveld"); // 7
        theGraph.addVertex("Ornanibo"); // 8
        theGraph.addVertex("Boxel"); // 9
        theGraph.addVertex("Domburg"); // 10
        theGraph.addVertex("White Beach"); // 11
        theGraph.addVertex("Highway"); // 12
        theGraph.addVertex("Paranam"); // 13
        theGraph.addEdge(0, 1, 120); // Centrum -> Maretraite
        theGraph.addEdge(1, 2, 40); // Maretraite -> Leonsberg
        theGraph.addEdge(0, 3, 20); // Centrum -> Ondrobong
        theGraph.addEdge(3, 4, 10); // Ondrobong -> LYCO1
        theGraph.addEdge(4, 5, 10); // LYCO1 -> NATIN
        theGraph.addEdge(5, 6, 15); // NATIN -> Kasabaolo
        theGraph.addEdge(0, 7, 80); // Centrum -> Dijkveld
        theGraph.addEdge(7, 8, 20); // Dijkveld -> Ornanibo
        theGraph.addEdge(8, 9, 30); // Ornanibo -> Boxel
        theGraph.addEdge(9, 10, 10); // Boxel -> Domburg
        theGraph.addEdge(10, 11, 10); // Domburg -> White beach
        theGraph.addEdge(0, 12, 95); // Centrum -> Highway
        theGraph.addEdge(12, 10, 50); // Highway -> Domburg
        theGraph.addEdge(12, 13, 80); // Highway -> Paranam
        theGraph.addEdge(13, 11, 40); // Paranam -> White beach

        return theGraph;
    }

    public static void printAreasGoingThrough(List areas) {
        System.out.println("---> Areas going through <---");
        areas.forEach(area -> System.out.println(area));
    }

    public static void zelfToevoegenVanEdges(Graph theGraph) {
        Scanner scan = new Scanner(System.in);
        System.out.println("The last index is: " + theGraph.countVertex());
        System.out.println("Insert your new label: ");
        String label = scan.nextLine();
        theGraph.addVertex(label);

        System.out.println("Insert your startIndex: ");
        int startIndex = scan.nextInt();

        int endIndex = theGraph.countVertex() - 1;

        System.out.println("Insert the weight: ");
        int weight = scan.nextInt();

        theGraph.addEdge(startIndex, endIndex, weight);
        System.out.println("Inserted new area");
        System.out.println("Start: " + startIndex + " End: " + endIndex + " Weight: " + weight);
    }

    public static void zonesTussenCentraleEnDesiredZone(Graph theGraph) {
        printAreasGoingThrough(theGraph.dfs("Domburg", false));
    }

    public static void zoneDichtbijGewensteZone(Graph theGraph) {
        printAreasGoingThrough(theGraph.dfs("Domburg", true));
    }

    public static void langsteRoute(Graph theGraph, String area) {
        theGraph.longestPathToArea(area);
    }

    public static void korsteRoute(Graph theGraph, String area, int multiplier) {
        theGraph.shortestPathToArea(area, multiplier);
    }

    public static void routeMetTarief(Graph theGraph, double amount) {
        theGraph.areasBasedOnAmount(amount, 1);
    }

    public static void editArea(Graph theGraph, String areaToChange, String newAreaLabel, int oldStartEdge, int oldEndEdge, int newAmount){
        theGraph.editArea(areaToChange, newAreaLabel, oldStartEdge, oldEndEdge, newAmount);
    }

    public static void main(String[] args) {
        Graph theGraph = initGraph();

        routeMetTarief(theGraph, 120);
    }
}
