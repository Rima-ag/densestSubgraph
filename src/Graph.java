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

    Graph(Integer numberOfNodes, Integer numberOfEdges, List<List<Integer>> neighbours){
        this.numberOfEdges = numberOfEdges;
        this.numberOfNodes = numberOfNodes;
        this.neighbours = neighbours;
    }

    Graph(String filePath) throws IOException{
        Integer numberOfEdges = 0;
        Integer numberOfNodes = 0;
        BufferedReader br =
                    new BufferedReader(new FileReader(filePath));

        String line = br.readLine();
        if(line != null){
            Integer[] metaData = convertLineToInteger(line.split("\\s+"));
            numberOfNodes = metaData[0];
            numberOfEdges = metaData[1];
        }

//      if(numberOfEdges == 0 || numberOfNodes == 0)
//         throw Exception;

        List<List<Integer>> neighbours = new ArrayList<>();
        for(int i = 0; i < numberOfNodes; ++i)
            neighbours.add(new ArrayList<>());

        while ((line = br.readLine()) != null){
            Integer[] pair = convertLineToInteger(line.split("\\s+"));
            neighbours.get(pair[0]).add(pair[1]);
            neighbours.get(pair[1]).add(pair[0]);
        }

        this.numberOfEdges = numberOfEdges;
        this.numberOfNodes = numberOfNodes;
        this.density = new Double(numberOfEdges) / numberOfNodes;
        this.neighbours = neighbours;
    }

    private Integer[] convertLineToInteger(String[] line){
        Integer[] pair = new Integer[line.length];
        for(int i = 0; i < line.length; ++i)
            pair[i] = Integer.parseInt(line[i]);
        return pair;
    }

    public Double getDensity(){return density;}

}
