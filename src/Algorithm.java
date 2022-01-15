import java.util.*;

public class Algorithm {
    public Graph bestGraph;

    public List<DoubleLinkedList<Integer>> allEdges;
    public List<DoubleLinkedList<Integer>> currentNeighbourNodes;

    public Integer currentNumberOfNodes;
    public Integer currentNumberOfEdges;

    public List<DoubleLinkedList<Integer>> nodesSortedByDegree;
    public Integer[] degreeOfEachNode;
    public ArrayList<Node<Integer>> addressOfEachNode;

    Algorithm(String filename, String delimiter){
        try {
            bestGraph = new Graph(filename, delimiter);

            allEdges = bestGraph.allNeighbourNodes;
            currentNeighbourNodes = bestGraph.neighbours;

            currentNumberOfEdges = bestGraph.numberOfEdges;
            currentNumberOfNodes = bestGraph.numberOfNodes;

            nodesSortedByDegree = new ArrayList<>();
            degreeOfEachNode = new Integer[currentNumberOfNodes];
            addressOfEachNode = new ArrayList<>(currentNumberOfNodes);

            for(int i = 0; i < currentNumberOfNodes + 1; ++i)
                nodesSortedByDegree.add(new DoubleLinkedList<>());

            for(int i = 0; i < currentNumberOfNodes + 1; ++i)
                addressOfEachNode.add(new Node<>(i, null, null, null));

            for(int i = 0; i < currentNumberOfNodes; ++i) {
                degreeOfEachNode[i] = allEdges.get(i).size();
                nodesSortedByDegree.get(degreeOfEachNode[i]).add(addressOfEachNode.get(i));
            }


        }catch(Exception e){
            System.out.println("Exception occurred while creating graph");
        }
    }

    public Graph run(){
        LinkedList<Integer> permanentlyRemoved = new LinkedList<>();
        LinkedList<Integer> tmpRemoved = new LinkedList<>();

        Node<Integer> minDegreeNode;
        Integer degreeIndex = 0;

        //Neighbours of min degree node
        DoubleLinkedList<Integer> neighbours;

        Node<Integer> neighbour;
        Integer neighbourIndex;
        Node<Integer> neighbourInSortedDegreeList;


        Boolean goBack = false;
        Double currentDensity;

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
                    //Removing edge from edge list
                    neighbour = neighbours.popFirst();

                    --currentNumberOfEdges;

                    neighbourIndex = neighbour.value;
                    neighbourInSortedDegreeList = addressOfEachNode.get(neighbourIndex);


                    nodesSortedByDegree.get(currentNeighbourNodes.get(neighbourIndex).size())
                            .remove(neighbourInSortedDegreeList);
                    currentNeighbourNodes.get(neighbourIndex).remove(neighbour.relative);

                    if (currentNeighbourNodes.get(neighbourIndex).size() > 0) {
                        nodesSortedByDegree.get(currentNeighbourNodes.get(neighbourIndex).size())
                                .add(neighbourInSortedDegreeList);
                        if (currentNeighbourNodes.get(neighbourIndex).size() < degreeIndex)
                            goBack = true;
                    }

                }
            }
            if (goBack)
                --degreeIndex;
            else if (nodesSortedByDegree.get(degreeIndex).isEmpty())
                ++degreeIndex;


            currentDensity = new Double(currentNumberOfEdges) / currentNumberOfNodes;
            // compare densities and update
            if (currentDensity > bestGraph.getDensity()) {
                bestGraph = new Graph(currentNumberOfNodes, currentNumberOfEdges,
                        allEdges, currentNeighbourNodes, degreeOfEachNode);
                permanentlyRemoved.append(tmpRemoved);
                tmpRemoved.clear();
            }

        }

        while(!permanentlyRemoved.isEmpty()){
            minDegreeNode = permanentlyRemoved.popFirst();
            neighbours = allEdges.get(minDegreeNode.value);
            while(!neighbours.isEmpty()) {
                neighbour = neighbours.popFirst();
                allEdges.get(neighbour.relative.value).remove(neighbour.relative);
            }
        }


        return bestGraph;
    }

    public static void main(String[] args) {
        Algorithm algo = new Algorithm("large_twitch_edges.csv", ",");
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
