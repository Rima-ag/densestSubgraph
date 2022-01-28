import javafx.util.Pair;

import java.util.*;

public class Algorithm {
    /**
     * Current best subgraph found
     */
    public Graph bestGraph;

    /**
     * All edges present in the initial graph
     */
    public List<DoubleLinkedList<Integer>> allEdges;

    /**
     * Edges left after removing vertices from graph
     */
    public List<DoubleLinkedList<Integer>> currentNeighbourNodes;

    /**
     * Current number of nodes and edges left
     * Stored to ensure stopping condition for the algorithm and fast calculation of the current graph's density
     */
    public Integer currentNumberOfNodes;
    public Integer currentNumberOfEdges;

    /**
     * Sorted list of vertices based on their degree
     */
    public List<DoubleLinkedList<Integer>> nodesSortedByDegree;

    /**
     * Address of each node in the sorted list of vertices
     * Ensures O(1) lookup to the list if we know exactly what node we'd like to access
     */
    public ArrayList<Node<Integer>> addressOfEachNode;

    Algorithm(String filename, String delimiter){
        try {
            bestGraph = new Graph(filename, delimiter);

            allEdges = bestGraph.allNeighbourNodes;
            currentNeighbourNodes = bestGraph.neighbours;

            currentNumberOfEdges = bestGraph.numberOfEdges;
            currentNumberOfNodes = bestGraph.numberOfNodes;

            nodesSortedByDegree = new ArrayList<>();
            addressOfEachNode = new ArrayList<>(currentNumberOfNodes);

            for(int i = 0; i < currentNumberOfNodes; ++i)
                nodesSortedByDegree.add(new DoubleLinkedList<>());

            for(int i = 0; i < currentNumberOfNodes; ++i)
                addressOfEachNode.add(new Node<>(i, null, null, null));

            //Fills up sorted list
            for(int i = 0; i < currentNumberOfNodes; ++i)
                nodesSortedByDegree.get(allEdges.get(i).size()).add(addressOfEachNode.get(i));


        }catch(Exception e){
            System.out.println("Exception occurred while creating graph");
        }
    }

    public Graph run(){
        //Keeps track of all permanently removed vertices to obtain a denser subgraph
        LinkedList<Integer> permanentlyRemoved = new LinkedList<>();
        //keeps track of all vertices removed since we last found a denser subgraph
        LinkedList<Integer> tmpRemoved = new LinkedList<>();

        //Vertex to be removed from graph
        Node<Integer> minDegreeNode;
        Integer degreeIndex = 0;

        //Neighbours of min degree node
        DoubleLinkedList<Integer> neighbours;

        Node<Integer> neighbour;
        Integer neighbourIndex;
        //Address of a neighbour in the sorted list of vertices
        Node<Integer> neighbourInSortedDegreeList;


        boolean goBack = false;
        double currentDensity;

        while(degreeIndex < nodesSortedByDegree.size() && currentNumberOfNodes > 0){
            // Check if graph is complete, if yes stop
            if(bestGraph.getDensity() == ((bestGraph.numberOfNodes - 1.) / 2))
                break;

            // select in node degree list and add it to set
            if(!nodesSortedByDegree.get(degreeIndex).isEmpty()) {
                minDegreeNode = nodesSortedByDegree.get(degreeIndex).popFirst();
                tmpRemoved.add(minDegreeNode.value);

                // Search for neighbours
                neighbours = currentNeighbourNodes.get(minDegreeNode.value);

                --currentNumberOfNodes;
                // -- degree of each neighbour : remove from its position in degree and put it in the one before
                // edge cases : if no more edges left for neighbour : remove it too (i.e. if we're in index 0)
                goBack = false;
                while (!neighbours.isEmpty()) {
                    //Selecting neighbour and removing it from the list of neighbours
                    neighbour = neighbours.popFirst();

                    --currentNumberOfEdges;

                    neighbourIndex = neighbour.value;
                    neighbourInSortedDegreeList = addressOfEachNode.get(neighbourIndex);

                    //Remove neighbour from sorted list of vertices
                    nodesSortedByDegree.get(currentNeighbourNodes.get(neighbourIndex).size())
                            .remove(neighbourInSortedDegreeList);
                    //Remove current node from its list of neighbours
                    currentNeighbourNodes.get(neighbourIndex).remove(neighbour.relative);

                    //if other edges are still dependent from the neighbour we add it again to the sorted list in its
                    //rightful place
                    if (currentNeighbourNodes.get(neighbourIndex).size() > 0) {
                        nodesSortedByDegree.get(currentNeighbourNodes.get(neighbourIndex).size())
                                .add(neighbourInSortedDegreeList);
                        //identify if pointer to next vertex with minimum degree's behaviour should be modified
                        if (currentNeighbourNodes.get(neighbourIndex).size() < degreeIndex)
                            goBack = true;
                    }

                }
            }
            //Move pointer to the next vertex with minimum degree
            if (goBack)
                --degreeIndex;
            else if (nodesSortedByDegree.get(degreeIndex).isEmpty())
                ++degreeIndex;

            //compute density of the current graph
            currentDensity = new Double(currentNumberOfEdges) / currentNumberOfNodes;
            // compare densities and update if the graph is denser
            if (currentDensity > bestGraph.getDensity()) {
                bestGraph = new Graph(currentNumberOfNodes, currentNumberOfEdges, allEdges, currentNeighbourNodes);
                //Adds additional vertices to the permanent list that should be removed too
                //to retrieve a denser subgraph
                permanentlyRemoved.append(tmpRemoved);
                tmpRemoved.clear();
            }

        }

        //Update the edges left in the graph by removing permanently every single vertex
        //to get the densest subgraph
        //This updated list can be used to output in a file the resulting graph
        while(!permanentlyRemoved.isEmpty()){
            minDegreeNode = permanentlyRemoved.popFirst();
            neighbours = allEdges.get(minDegreeNode.value);
            while(!neighbours.isEmpty()) {
                neighbour = neighbours.popFirst();
                allEdges.get(neighbour.value).remove(neighbour.relative);
            }
        }


        return bestGraph;
    }

    public static void main(String[] args) {
        List<Pair<String, String>> input = new ArrayList<>();
        input.add(new Pair<>("HU_edges.csv", ","));
        input.add(new Pair<>("HR_edges.csv", ","));
        input.add(new Pair<>("com-amazon.ungraph.txt", "\t"));
        input.add(new Pair<>("com-youtube.ungraph.txt", "\t"));
        input.add(new Pair<>("large_twitch_edges.csv", ","));

        for (Pair<String, String> stringStringPair : input) {
            long startTime = System.nanoTime();
            Algorithm algo = new Algorithm(stringStringPair.getKey(), stringStringPair.getValue());
            Graph bestGraph = algo.run();
            long endTime = System.nanoTime();
            System.out.print(stringStringPair.getKey() + " density: " + bestGraph.getDensity() +
                    " number of nodes : " + bestGraph.numberOfNodes + " ");

            long duration = (endTime - startTime);
            System.out.println("duration: " + duration / 1000000);
        }
    }

}
