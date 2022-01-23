public class Node<T> {
    Node<T> prev;
    Node<T> next;
    Node<T> relative;
    T value;

    public Node( T value, Node<T> prev, Node<T> next, Node<T> relative){
        this.next = next;
        this.prev = prev;
        this.value = value;
    }
}
