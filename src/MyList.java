public interface MyList<T> {
    void add(Node<T> newNode);
    Node<T> popFirst();
    Node<T> getHead();
    Node<T> getTail();
    Boolean isEmpty();
    Integer size();
}
