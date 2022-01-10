public class Node<T> {
    Node<T> prev;
    Node<T> next;
    T value;

    public Node(Node<T> prev, Node<T> next, T value){
        this.next = next;
        this.prev = prev;
        this.value = value;
    }
}
