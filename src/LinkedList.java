public class LinkedList<T> implements MyList<T> {
    Node<T> head;
    Node<T> tail;
    private Integer size;

    public LinkedList(){
        init();
    }

    public void add(Node<T> newNode) {
        if(head == null){
            head = newNode;
            tail = head;
        }else{
            tail.next = newNode;
            tail = tail.next;
        }
        ++size;
    }

    public void add(T value) {
        Node<T> newNode = new Node<>(value, null, null, null);
        if(head == null){
            head = newNode;
            tail = head;
        }else{
            tail.next = newNode;
            tail = tail.next;
        }
        ++size;
    }

    public void removeFirst() {
        if(size() > 0) {
            if (head == null)
                return;

            if (head == tail)
                tail = null;

            head = head.next;

            --size;
        }
    }

    public Node<T> popFirst(){
        if(size() > 0) {
            Node<T> node = getHead();
            removeFirst();
            return node;
        }
        return null;
    }

    public void append(LinkedList<T> list2){
        if(size() == 0) {
            head = list2.getHead();
            tail = list2.getTail();
            size = list2.size();
        }
        else if(list2.size() > 0) {
            if (head == null) {
                head = list2.getHead();
            } else {
                tail.next = list2.getHead();
            }
            tail = list2.getTail();
            size += list2.size();
        }
    }

    public Node<T> getHead(){return head;}

    public Node<T> getTail(){return tail;}

    public Boolean isEmpty() {
        return size() <= 0;
    }

    public void clear(){
        init();
    }

    public Integer size() {
        return size;
    }

    private void init(){
        head = null;
        tail = head;
        size = 0;
    }
}
