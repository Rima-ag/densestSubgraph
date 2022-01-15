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
        newNode.next = null;
        if(head == null){
            newNode.prev = null;
            head = newNode;
            tail = head;
        }else{
            tail.next = newNode;
            newNode.prev = tail;
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
                else
                    head.prev = null;
            } else if (tail == node) {
                tail = node.prev;
                tail.next = null;
            }
            else {
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }

            --size;
        }
    }

    private void removeFirst(){
        if(size() > 0){
            head = head.next;
            if(head == null)
                tail = null;
            else
                head.prev = null;
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


