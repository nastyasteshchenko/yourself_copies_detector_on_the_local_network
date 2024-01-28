package networks.lab1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class Sender extends Thread {
    private static final int PORT_TO_SEND = 8800;
    private boolean isRunning;
    private final DatagramSocket socket;
    private final InetAddress group;

    private Sender(DatagramSocket socket, InetAddress group) {
        this.socket = socket;
        this.group = group;
    }

    static Sender create(InetAddress groupIP) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        return new Sender(socket, groupIP);
    }

    /**
     * Sends packets to other copies in multicast group.
     */
    public void run() {
        isRunning = true;
        try {

            byte[] buf = "I'm here".getBytes();

            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, PORT_TO_SEND);

            while (isRunning) {
                socket.send(packet);
                Thread.sleep(4000);
            }

            byte[] lastBuf = "Bye-bye".getBytes();

            packet.setData(lastBuf);
            packet.setLength(lastBuf.length);

            socket.send(packet);

        } catch (IOException | InterruptedException e) {
            close();
            System.err.println(e.getMessage());
        }
    }

    void stopRunning(){
        isRunning = false;
    }

    void close(){
        socket.close();
    }
}
