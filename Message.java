public class Message extends Event {
    private final String message;
    private final int srcAddress;
    private final int destAddress;
    private Host nextHop;
    private int distance;

    public Message(String message, int srcAddress, int destAddress) {
        super();
        this.message = message;
        this.srcAddress = srcAddress;
        this.destAddress = destAddress;
    }

    /**
     * Returns a string representing the network message
     *
     * @return a String representing the Message, in this case, a network message.
     */

    public String getMessage() {
        return message;
    }

    /**
     * Returns the source address, or the host address of the message.
     *
     *
     * @return an int representing the host address
     */

    public int getSrcAddress() {
        return srcAddress;
    }

    /**
     * Returns the destination address, or the receiver address. In this network simulator, the source address
     * and the destination address will be neighbors.
     *
     * @return an int representing the receiver address
     */

    public int getDestAddress() {
        return destAddress;
    }

    /**
     * Setter method for the nextHop, i.e. the next destination. In this case, one distance is one simulation time.
     * This method is used in the Host class by sendToNeighbor.
     *
     * @param nextHop   the next destination
     * @param distance  the distance between the two Hosts
     */
    public void setNextHop(Host nextHop, int distance) {
        this.nextHop = nextHop;
        this.distance = distance;
    }

    /**
     * Sets the insertion time and arrival time for this Message Event.
     *
     * @param currentTime the current simulation time
     */
    @Override
    public void setInsertionTime(int currentTime) {
        this.insertionTime = currentTime;
        this.arrivalTime = currentTime + distance;
    }

    @Override
    public void cancel() {
        // Messages cannot be cancelled, only Timers
    }

    /**
     * Handle (or execute) the Event.
     * <br>
     * This occurs after the Event has been removed from the future event list (arrival time reached). Handle for Messages
     * and handle for Timers will execute differently. If this Event represents a network message, then calling handle()
     * will 'process' the message at the destination host.  If the Event is a Timer,
     * then this will execute whatever needs to be done upon timer expiry.
     */
    @Override
    public void handle() {
        nextHop.receive(this);

    }
}
