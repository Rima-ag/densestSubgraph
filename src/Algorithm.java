import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Algorithm {
    public Graph bestGraph;
    public List<List<Integer>> degree;
    public Set<Integer> removedNodes = new HashSet<>();


    Algorithm(String filename){
        try {
            this.bestGraph = new Graph(filename);
            this.degree = new ArrayList<>();

            for(int i = 0; i < bestGraph.numberOfNodes + 2; ++i)
                degree.add(new ArrayList<>());

            for(int i = 0; i < bestGraph.neighbours.size(); ++i)
                degree.get(bestGraph.neighbours.get(i).size()).add(i);

        }catch(Exception e){
            System.out.println("Exception occurred while creating graph");
        }
    }

    public void run(){
        Integer degreeIndex = 0;
        while(degreeIndex < degree.size()){
            // Check if graph is complete, if yes stop else:
            // select in node degree list and add it to set
            // Search for neighbours
            // -- degree of each neighbour : remove from its position in degree and put it in the one before
            // edge cases : if no more edges left for neighbour : remove it too (i.e. if we're in index 0)
            // compare densities and update
        }
    }


}
