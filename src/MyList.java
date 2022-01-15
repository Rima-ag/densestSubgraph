public interface MyList<T> {
    void add(Node<T> newNode);
    void removeFirst();
    Node<T> getHead();
    Node<T> getTail();
    Boolean isEmpty();
    Integer size();
}
