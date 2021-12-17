import java.util.*;

public class Algorithm {
    public Graph bestGraph;
    public List<List<Integer>> nodesSortedByDegree;
    public Integer[] degreeOfEachNode;
    public List<List<Integer>> allEdges;
    public Integer currentNumberOfNodes;
    public Integer currentNumberOfEdges;


    Algorithm(String filename){
        try {
            this.bestGraph = new Graph(filename);
            this.nodesSortedByDegree = new ArrayList<>();
            this.degreeOfEachNode = new Integer[bestGraph.numberOfNodes];

            for(int i = 0; i < bestGraph.numberOfNodes + 2; ++i)
                nodesSortedByDegree.add(new LinkedList<>());

            for(int i = 0; i < bestGraph.numberOfNodes; ++i) {
                degreeOfEachNode[i] = bestGraph.neighbours.get(i).size();
                nodesSortedByDegree.get(degreeOfEachNode[i]).add(i);
            }

            allEdges = bestGraph.neighbours;
            currentNumberOfEdges = bestGraph.numberOfEdges;
            currentNumberOfNodes = bestGraph.numberOfNodes;
        }catch(Exception e){
            System.out.println("Exception occurred while creating graph");
        }
    }

    public void run(){
        Integer degreeIndex = 0;
        Integer node;
        List<Integer> neighbours;
        Integer currentNeighbour;
        Boolean goBack;
        Double currentDensity;
        while(degreeIndex < nodesSortedByDegree.size()){
            // Check if graph is complete, if yes stop else:
            if(bestGraph.getDensity() == 2)
                break;
            // select in node degree list and add it to set
            if(nodesSortedByDegree.get(degreeIndex).size() > 0) {
                node = nodesSortedByDegree.get(degreeIndex).get(0);
                nodesSortedByDegree.get(degreeIndex).remove(0);
                --currentNumberOfNodes;

                // Search for neighbours
                neighbours = allEdges.get(node);
                degreeOfEachNode[node] = 0;
                // -- degree of each neighbour : remove from its position in degree and put it in the one before
                // edge cases : if no more edges left for neighbour : remove it too (i.e. if we're in index 0)
                goBack = false;
                for (int i = 0; i < neighbours.size() && !goBack; ++i) {
                    currentNeighbour = neighbours.get(i);
                    if (degreeOfEachNode[currentNeighbour] > 0) {
                        nodesSortedByDegree.get(degreeOfEachNode[currentNeighbour]).remove(currentNeighbour);
                        --degreeOfEachNode[currentNeighbour];
                        if (degreeOfEachNode[currentNeighbour] > 0) {
                            nodesSortedByDegree.get(degreeOfEachNode[currentNeighbour]).add(currentNeighbour);
                            if (degreeOfEachNode[currentNeighbour] < degreeIndex - 1)
                                goBack = true;
                        }

                        --currentNumberOfEdges;
                    }
                }
                if (goBack)
                    --degreeIndex;
                else if (! (nodesSortedByDegree.get(degreeIndex).size() > 0))
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
    }


}
