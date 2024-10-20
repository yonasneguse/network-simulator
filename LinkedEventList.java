public class LinkedEventList implements FutureEventList {
    private Node head;
    private int size;
    private int simulationTime;

    public LinkedEventList() {
        head = null;
        size = 0;
        simulationTime = 0;
    }

    /**
     * Remove and return the Event at the front of the list.
     * <br>
     * The LinkedEventList is sorted by arrival time, so the Event at the front of the list will be the one with the
     * smallest arrival time.
     *
     * @return the Event at the front of the list
     */

    @Override
    public Event removeFirst() {
        if (size == 0) {
            return null;
        }
        Event removedEvent = head.getItem();
        simulationTime = removedEvent.getArrivalTime(); // set the simulation time to the last removed event's arrival
        head = head.next;

        if (head != null) {
            head.prev = null;
        }
        size--;
        return removedEvent;

    }


    /**
     * Remove the Event e from the list, if it exists.
     *
     * @param e an Event to remove from the list
     * @return true if Event present in the list, false otherwise
     */


    @Override
    public boolean remove(Event e) {
        Node current = head;

        while (current != null) {
            if (current.getItem() == e) {
                if (current.prev != null) {
                    current.prev.next = current.next;
                }
                else {
                    head = current.next;
                }

                if (current.next != null) {
                    current.next.prev = current.prev;
                }

                size--;

                e.cancel(); // call cancel method on the event
                return true;


            }

            current = current.next; // increment
        }

        return false;
    }


    /**
     * Insert an Event into the list.
     * <br>
     * The LinkedEventList maintains an ordering of Events based on arrival time.
     *
     * @param e an Event to insert into the list
     */

    @Override
    public void insert(Event e) {
        Node newNode = new Node(e);
        e.setInsertionTime(simulationTime); // set the insertion time based on the current time

        if (head == null) { // first Node in the list
            head = newNode;
        }
        else if (e.getArrivalTime() < head.getItem().getArrivalTime()) { // if the new Event has the smallest arrival
            newNode.next = head;
            head.prev = newNode;
            head = newNode;

        }
        else {
            Node current = head;
            while (current.next != null && current.next.getItem().getArrivalTime() < e.getArrivalTime()) {
                current = current.next;
            }

            newNode.next = current.next;
            if (current.next != null) {
                current.next.prev = newNode;
            }
            current.next = newNode;
            newNode.prev = current;
        }

        size++;
    }

    /**
     * Return the list size (number of Events in the list).
     *
     * @return the number of Events in the list
     */

    @Override
    public int size() {
        return size;
    }

    /**
     * Return the list capacity, in this case it is equal to the size.
     *
     * @return the size of the list (the number of Events in the list)
     */

    @Override
    public int capacity() {
        return size;
    }

    /**
     * Return the current simulation time (arrival time of last Event)
     *
     * @return the current simulation time
     */

    @Override
    public int getSimulationTime() {
        return simulationTime;
    }
}
