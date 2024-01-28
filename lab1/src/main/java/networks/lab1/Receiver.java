package networks.lab1;

import java.io.IOException;
import java.net.*;
import java.util.List;

class Receiver extends Thread {
    private static final String NETWORK_INTERFACE_NAME = "wlan0";
    private static final int PORT = 8800;
    private boolean isRunning;
    private final MulticastSocket socket;
    private final InetSocketAddress group;
    private final NetworkInterface networkInterface;

    private Receiver(MulticastSocket socket, InetSocketAddress group, NetworkInterface networkInterface) {
        this.socket = socket;
        this.group = group;
        this.networkInterface = networkInterface;
    }

    static Receiver create(InetAddress groupIP) throws IOException {
        MulticastSocket socket = new MulticastSocket(PORT);
        InetSocketAddress group = new InetSocketAddress(groupIP, 0);
        NetworkInterface networkInterface = NetworkInterface.getByName(NETWORK_INTERFACE_NAME);
        socket.joinGroup(group, networkInterface);
        return new Receiver(socket, group, networkInterface);
    }


    /**
     * Receives packets from other copies in multicast group.
     */
    public void run() {
        isRunning = true;
        byte[] buf = new byte[64];

        List<InterfaceAddress> interfaceAddresses =  networkInterface.getInterfaceAddresses();

        List<InetAddress> inetAddresses = interfaceAddresses.stream().map(InterfaceAddress::getAddress).toList();

        CopiesTracker copiesTracker = new CopiesTracker(inetAddresses);

        try {
            while (isRunning) {

                DatagramPacket packet = new DatagramPacket(buf, buf.length);

                socket.receive(packet);

                copiesTracker.checkChanges(packet);
            }
        } catch (IOException e) {
            close();
            System.err.println(e.getMessage());
        }
    }

    void stopRunning() {
        isRunning = false;
    }

    void close() {
        try {
            socket.leaveGroup(group, networkInterface);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        socket.close();
    }
}
