package networks.lab1;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class CopiesTracker {
    private final HashMap<InetAddress, LocalTime> copies = new HashMap<>();
    private final List<InetAddress> currentInterfaceAddresses;

    CopiesTracker(List<InetAddress> currentInterfaceAddressed) {
        this.currentInterfaceAddresses = currentInterfaceAddressed;
    }

    /**
     * Checks if it is a packet from a new copy and if any copies have not sent packages for a long time.<br>
     * Prints new list of ip-addresses if some changes were detected.
     * @param packet new received packet
     */
    void checkChanges(DatagramPacket packet) {

        if (isNewCopy(packet) || isDetectedNotExistingCopies()) {
            System.out.println("Something was changed!\n----------------------------");
            if (copies.isEmpty()){
                System.out.println("Empty list");
            } else {
                copies.keySet().forEach((address) -> System.out.println(address.getHostAddress()));
            }
        }

    }

    private boolean isDetectedNotExistingCopies() {
        List<InetAddress> copiesToDelete = new ArrayList<>();

        copies.forEach((address, time) -> {
            if (LocalTime.now().isAfter(time.plusSeconds(4))) {
                copiesToDelete.add(address);
            }
        });

        if (!copiesToDelete.isEmpty()) {
            copiesToDelete.forEach(copies::remove);
            return true;
        }

        return false;
    }

    private boolean isNewCopy(DatagramPacket packet) {
        InetAddress senderAddress = packet.getAddress();
        if (!currentInterfaceAddresses.contains(senderAddress)) {
            return copies.put(senderAddress, LocalTime.now()) == null;
        }
        return false;
    }
}
