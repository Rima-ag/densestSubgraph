public class LinkedList<T> implements MyList<T> {
    Node<T> head;
    Node<T> tail;
    private Integer size;

    public LinkedList(){
        init();
    }

    /**
     * Appends a Node at the end of the list
     * @param newNode
     */
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

    /**
     * Creates a node containing the value provided and appends it at the end of the list
     * @param value
     */
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

    /**
     * Removes first node in the list
     */
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

    /**
     * Selects the first node of the list, removes it from the list and returns it
     * @return
     */
    public Node<T> popFirst(){
        if(size() > 0) {
            Node<T> node = getHead();
            removeFirst();
            return node;
        }
        return null;
    }

    /**
     * Appends a list of nodes to the current list
     * @param list2
     */
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

    /**
     *
     * @return the first node of the list
     */
    public Node<T> getHead(){return head;}

    /**
     *
     * @return the last node of the list
     */
    public Node<T> getTail(){return tail;}

    /**
     * Checks if the list doesn't contain any nodes
     * @return
     */
    public Boolean isEmpty() {
        return size() <= 0;
    }

    /**
     * Empties list from all nodes
     */
    public void clear(){
        init();
    }

    /**
     * returns the number of nodes in the list
     * @return
     */
    public Integer size() {
        return size;
    }

    private void init(){
        head = null;
        tail = head;
        size = 0;
    }
}
