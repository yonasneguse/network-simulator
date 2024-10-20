public class SimpleHost extends Host {

    private int destAddr;
    private int interval;
    private int intervalID;
    private int durationID;
    private int firstArrival;

    public SimpleHost() {
        this.destAddr = -1;
        this.interval = -1;
    }

    /**
     * This is called when a host receives a Message event from a neighboring host.
     *
     * @param msg the Message event received
     */

    @Override
    protected void receive(Message msg) {
        String message = msg.getMessage();
        switch (message) {
            case ("request"): { // case for request
                int senderAddress = msg.getSrcAddress();
                int currentTime = getCurrentTime();
                System.out.println("[" + currentTime + "ts] Host " + getHostAddress() + ": Ping request from host " + senderAddress);
                sendPingResponse(senderAddress); // priv helper method
                break;
            }
            case ("response"): { // case for response
                int senderAddress = msg.getSrcAddress();
                int rtt = getCurrentTime() - firstArrival; // rtt calc
                System.out.println("[" + getCurrentTime() + "ts] Host " + getHostAddress() + ": Ping response from host " + senderAddress + " (RTT = " + rtt + "ts)");
                break;
            }
        }
    }
    /**
     * This is called after a Timer event expires, and enables you to write code to do something upon timer
     * expiry.  A timer expires when the duration set for the timer is reached.
     *
     * @param eventId the event id of the Timer event which expired
     */
    @Override
    protected void timerExpired(int eventId) {
        if (eventId == intervalID) { // if eventId is equal to intervalID
            sendPingRequest(destAddr); // priv helper method
            intervalID = newTimer(interval); // create new timer


        }
        else if (eventId == durationID) {
            cancelTimer(intervalID); // callback to Host
        }
    }

    @Override
    protected void timerCancelled(int eventId) {
        System.out.println("[" + getCurrentTime() + "ts] Host " + getHostAddress() + ": Stopped sending pings ");
    }

    public void sendPings(int destAddr, int interval, int duration) {
        this.destAddr = destAddr;
        this.interval = interval;

        intervalID = newTimer(interval); // create timers
        durationID = newTimer(duration);


        //System.out.println(interval + " " + duration); debug comment

    }

    //private helper methods to reduce redundancy
    private void sendPingRequest(int destAddr) {
        Message pingRequest = new Message("request", getHostAddress(), destAddr);
        pingRequest.setInsertionTime(getCurrentTime());
        firstArrival = pingRequest.getArrivalTime();
        sendToNeighbor(pingRequest);

        System.out.println("[" + getCurrentTime() + "ts] Host " + getHostAddress() + ": Sent ping to host " + destAddr);
    }

    private void sendPingResponse(int destAddr) {
        Message pingResponse = new Message("response", getHostAddress(), destAddr);
        sendToNeighbor(pingResponse);
    }
}
