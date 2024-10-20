public class Node {
    private final Event item;
    public Node next;
    public Node prev;

    // Node constructor
    public Node(Event item) {
        this.item = item;
        next = null;
        prev = null;
    }

    /**
     * Returns the item (in this case, the event) that is stored in the Node.
     *
     * @return the event in the Node
     */
    public Event getItem() {
        return item;
    }
}
