import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Graph {
    public List<DoubleLinkedList<Integer>> allNeighbourNodes;
    public List<DoubleLinkedList<Integer>> neighbours;
    public Integer numberOfEdges;
    public Integer numberOfNodes;
    final private Double density;
    public Integer[] degreeOfEachNode;

    Graph(Integer numberOfNodes, Integer numberOfEdges, List<DoubleLinkedList<Integer>> allNeighbourNodes,
          List<DoubleLinkedList<Integer>> neighbours, Integer[] degreeOfEachNode){
        this.numberOfNodes = numberOfNodes;
        this.numberOfEdges = numberOfEdges;
        this.allNeighbourNodes = allNeighbourNodes;
        this.neighbours = neighbours;
        this.density = computeDensity(numberOfEdges, numberOfNodes);
        copyDegreeOfEachNode(degreeOfEachNode);
    }

    Graph(String filePath, String delimiter) throws IOException{
        Integer numberOfEdges = 0;
        Integer numberOfNodes = 0;
        BufferedReader br =
                    new BufferedReader(new FileReader(filePath));

        String line = br.readLine();
        if(line != null){
            Integer[] metaData = convertLineToInteger(line.split(delimiter));
            numberOfNodes = metaData[0];
            numberOfEdges = metaData[1];
        }

        List<DoubleLinkedList<Integer>> neighbours = new ArrayList<>();
        List<DoubleLinkedList<Integer>> copyNeighbours = new ArrayList<>();
        for(int i = 0; i < numberOfNodes; ++i) {
            neighbours.add(new DoubleLinkedList<>());
            copyNeighbours.add(new DoubleLinkedList<>());
        }


        while ((line = br.readLine()) != null){
            Integer[] pair = convertLineToInteger(line.split(delimiter));
            if(pair.length == 2) {
                fillNeighbours(pair, neighbours);
                fillNeighbours(pair, copyNeighbours);
            }else{
                System.out.println("Wrong delimiter");
                break;
            }
        }

        this.numberOfEdges = numberOfEdges;
        this.numberOfNodes = numberOfNodes;
        this.density = computeDensity(numberOfEdges, numberOfNodes);
        this.allNeighbourNodes = neighbours;
        this.neighbours = copyNeighbours;
        initDegreeOfEachNode();
    }

    private Integer[] convertLineToInteger(String[] line){
        Integer[] pair = new Integer[line.length];
        for(int i = 0; i < line.length; ++i)
            pair[i] = Integer.parseInt(line[i]);
        return pair;
    }

    private void initDegreeOfEachNode(){
        degreeOfEachNode = new Integer[allNeighbourNodes.size()];
        for(int i = 0; i < allNeighbourNodes.size(); ++i)
            degreeOfEachNode[i] = allNeighbourNodes.get(i).size();

    }

    private void copyDegreeOfEachNode(Integer[] previous){
        degreeOfEachNode = new Integer[previous.length];
        for(int i = 0; i < previous.length; ++i)
            degreeOfEachNode[i] = previous[i];
    }

    private Double computeDensity(Integer numberOfEdges, Integer numberOfNodes){
        return new Double(numberOfEdges) / numberOfNodes;
    }

    public Double getDensity(){return density;}

    private void fillNeighbours(Integer[] pair, List<DoubleLinkedList<Integer>> list){
        Node<Integer> first;
        Node<Integer> second;

        first = new Node<>(pair[1], null, null, null);
        second = new Node<>(pair[0], null, null, null);
        first.relative = second;
        second.relative = first;

        list.get(pair[0]).add(first);
        list.get(pair[1]).add(second);
    }

}
