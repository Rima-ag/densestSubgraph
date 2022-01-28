public class DoubleLinkedList<T> implements MyList<T>{

    private Node<T> head;
    private Node<T> tail;
    private Integer size;

    public DoubleLinkedList(){
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Appends a Node at the end of the list
     * @param newNode
     */
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

    /**
     * Removes a specific node from anywhere in the list
     * @param node
     */
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

    /**
     * Removes first node in the list
     */
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

    /**
     *
     * @return the first node of the list
     */
    public Node<T> getHead(){
        return head;
    }

    /**
     *
     * @return the last node of the list
     */
    public Node<T> getTail(){
        return tail;
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
     * Checks if the list doesn't contain any nodes
     * @return
     */
    public Boolean isEmpty(){
        return size() <= 0;
    }

    /**
     * returns the number of nodes in the list
     * @return
     */
    public Integer size(){return size;}
}


