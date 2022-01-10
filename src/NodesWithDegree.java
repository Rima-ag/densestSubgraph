public class NodesWithDegree {
    Boolean[] isNodeHere;

    public NodesWithDegree(int numberOfNodes){
        isNodeHere = new Boolean[numberOfNodes];
        for(int i = 0; i < isNodeHere.length; ++i) {
            isNodeHere[i] = false;
        }
    }

    public void setNodeHere(int nodeIndex){
        isNodeHere[nodeIndex] = true;
    }

    public Boolean getIsNodeHere(int nodeIndex){
        return isNodeHere[nodeIndex];
    }
}
