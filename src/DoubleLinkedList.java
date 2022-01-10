public class DoubleLinkedList<T>{

    Node<T> head;
    Node<T> tail;

    public DoubleLinkedList(){
        head = null;
        tail = null;
    }

    public void add(Node<T> newNode){
        if(head == null){
            head = newNode;
            head.prev = null;
            head.next = null;
            tail = head;
        }else{
            tail.next = newNode;
            newNode.prev = tail;
            newNode.next = null;
            tail = tail.next;
        }
    }

    public void remove(Node<T> node){
        if(head == node)
            head = head.next == null ? null : head.next;
        else if(tail == node)
            tail = node.prev;
        else
            node.prev.next = node.next;

    }
}


