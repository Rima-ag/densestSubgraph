import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Graph {
    public List<List<Integer>> neighbours;
    public Integer numberOfEdges;
    public Integer numberOfNodes;
    private Double density;
    public Integer[] degreeOfEachNode;

    Graph(Integer numberOfNodes, Integer numberOfEdges, List<List<Integer>> neighbours, Integer[] degreeOfEachNode){
        this.numberOfNodes = numberOfNodes;
        this.numberOfEdges = numberOfEdges;
        this.neighbours = neighbours;
        this.density = computeDensity(numberOfEdges, numberOfNodes);
        copyDegreeOfEachNode(degreeOfEachNode);
    }

    Graph(String filePath) throws IOException{
        Integer numberOfEdges = 0;
        Integer numberOfNodes = 0;
        BufferedReader br =
                    new BufferedReader(new FileReader(filePath));

        String line = br.readLine();
        if(line != null){
            Integer[] metaData = convertLineToInteger(line.split(","));
            numberOfNodes = metaData[0];
            numberOfEdges = metaData[1];
        }

        List<List<Integer>> neighbours = new ArrayList<>();
        for(int i = 0; i < numberOfNodes; ++i)
            neighbours.add(new ArrayList<>());

        while ((line = br.readLine()) != null){
            Integer[] pair = convertLineToInteger(line.split(","));
            neighbours.get(pair[0]).add(pair[1]);
            neighbours.get(pair[1]).add(pair[0]);
        }

        this.numberOfEdges = numberOfEdges;
        this.numberOfNodes = numberOfNodes;
        this.density = computeDensity(numberOfEdges, numberOfNodes);
        this.neighbours = neighbours;
        initDegreeOfEachNode();
    }

    private Integer[] convertLineToInteger(String[] line){
        Integer[] pair = new Integer[line.length];
        for(int i = 0; i < line.length; ++i)
            pair[i] = Integer.parseInt(line[i]);
        return pair;
    }

    private void initDegreeOfEachNode(){
        degreeOfEachNode = new Integer[numberOfNodes];
        for(int i = 0; i < neighbours.size(); ++i)
            degreeOfEachNode[i] = neighbours.get(i).size();

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

}
