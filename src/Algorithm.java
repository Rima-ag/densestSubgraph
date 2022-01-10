import java.util.*;

public class Algorithm {
    public Graph bestGraph;
    public List<List<Integer>> allEdges;
    public Integer currentNumberOfNodes;
    public Integer currentNumberOfEdges;

    public List<DoubleLinkedList<Integer>> nodesSortedByDegree;
    public Integer[] degreeOfEachNode;
    public ArrayList<Node<Integer>> addressOfEachNode;

    Algorithm(String filename){
        try {
            bestGraph = new Graph(filename);
            allEdges = bestGraph.neighbours;
            currentNumberOfEdges = bestGraph.numberOfEdges;
            currentNumberOfNodes = bestGraph.numberOfNodes;
            System.out.println(currentNumberOfNodes);
            nodesSortedByDegree = new ArrayList<>();
            degreeOfEachNode = new Integer[currentNumberOfNodes];
            addressOfEachNode = new ArrayList<>(currentNumberOfNodes);

            for(int i = 0; i < currentNumberOfNodes + 1; ++i)
                nodesSortedByDegree.add(new DoubleLinkedList<>());

            for(int i = 0; i < currentNumberOfNodes + 1; ++i)
                addressOfEachNode.add(new Node<>(null, null, i));

            for(int i = 0; i < currentNumberOfNodes; ++i) {
                degreeOfEachNode[i] = allEdges.get(i).size();
                nodesSortedByDegree.get(degreeOfEachNode[i]).add(addressOfEachNode.get(i));
            }


        }catch(Exception e){
            System.out.println("Exception occurred while creating graph");
        }
    }

    public Graph run(){
        Node<Integer> node;
        Integer degreeIndex = 0;

        List<Integer> neighbours;

        Node<Integer> currentNeighbour;
        Integer currentNeighbourIndex;

        Boolean goBack;
        Double currentDensity;

        while(degreeIndex < nodesSortedByDegree.size() && currentNumberOfNodes > 0){
            // Check if graph is complete, if yes stop else:
            if(bestGraph.getDensity() == ((bestGraph.numberOfNodes - 1.) / 2))
                break;
            // select in node degree list and add it to set
            if(!nodesSortedByDegree.get(degreeIndex).isEmpty()) {
                node = nodesSortedByDegree.get(degreeIndex).getHead();
                nodesSortedByDegree.get(degreeIndex).remove(node);
                --currentNumberOfNodes;

                // Search for neighbours
                neighbours = allEdges.get(node.value);
                degreeOfEachNode[node.value] = 0;
                // -- degree of each neighbour : remove from its position in degree and put it in the one before
                // edge cases : if no more edges left for neighbour : remove it too (i.e. if we're in index 0)
                goBack = false;
                for (int i = 0; i < neighbours.size() && !goBack; ++i) {
                    currentNeighbour = addressOfEachNode.get(neighbours.get(i));
                    currentNeighbourIndex = currentNeighbour.value;
                    if (degreeOfEachNode[currentNeighbourIndex] > 0) {
                        nodesSortedByDegree.get(degreeOfEachNode[currentNeighbourIndex]).remove(currentNeighbour);
                        --degreeOfEachNode[currentNeighbourIndex];
                        --currentNumberOfEdges;
                        if (degreeOfEachNode[currentNeighbourIndex] > 0) {
                            nodesSortedByDegree.get(degreeOfEachNode[currentNeighbourIndex]).add(currentNeighbour);
                            if (degreeOfEachNode[currentNeighbourIndex] < degreeIndex - 1)
                                goBack = true;
                        }
                    }
                }
                if (goBack)
                    --degreeIndex;
                else if (!nodesSortedByDegree.get(degreeIndex).isEmpty())
                    ++degreeIndex;
            }else{
                ++degreeIndex;
            }

            currentDensity = new Double(currentNumberOfEdges) / currentNumberOfNodes;
            // compare densities and update
            if(currentDensity > bestGraph.getDensity()){
                bestGraph = new Graph(currentNumberOfNodes, currentNumberOfEdges,
                        bestGraph.neighbours, degreeOfEachNode);
            }
        }
        return bestGraph;
    }

    public static void main(String[] args) {
        Algorithm algo = new Algorithm("large_twitch_edges.csv");
        Graph bestGraph = algo.run();
        System.out.println(bestGraph.getDensity());
        Integer nodes = 0;
        for(int i = 0; i < bestGraph.degreeOfEachNode.length; ++i){
            if(bestGraph.degreeOfEachNode[i] != 0)
                ++nodes;
        }
        System.out.println(nodes);
    }

}
