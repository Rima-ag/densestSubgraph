public class DoubleLinkedList<T> implements MyList<T>{

    private Node<T> head;
    private Node<T> tail;
    private Integer size;

    public DoubleLinkedList(){
        head = null;
        tail = null;
        size = 0;
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
        ++size;
    }

    public void remove(Node<T> node){
        if(size() > 0) {
            if (head == node) {
                head = head.next;
                if (head == null)
                    tail = null;
            } else if (tail == node)
                tail = node.prev;
            else
                node.prev.next = node.next;

            --size;
        }
    }

    public void removeFirst(){
        if(size() > 0){
            head = head.next;
            if(head == null)
                tail = null;
            --size;
        }
    }


    public Node<T> getHead(){
        return head;
    }

    public Node<T> getTail(){
        return tail;
    }

    public Node<T> popFirst(){
        if(size() > 0) {
            Node<T> node = getHead();
            removeFirst();
            return node;
        }
        return null;
    }

    public Boolean isEmpty(){
        return size() <= 0;
    }

    public Integer size(){return size;}
}


