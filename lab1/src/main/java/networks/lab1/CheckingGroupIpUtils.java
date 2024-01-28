package networks.lab1;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class CheckingGroupIpUtils {
    static void checkValidIp(String address) throws IPException {
        try {
            InetAddress inetAddress = InetAddress.getByName(address);
            if (!inetAddress.isMulticastAddress()) {
                throw IPException.notMulticastIp();
            }
        } catch (UnknownHostException e) {
            throw IPException.notValidIp();
        }
    }
}
