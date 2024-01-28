package networks.lab1;

class IPException extends  Exception{
    private IPException(String message) {
        super(message);
    }
    static IPException notMulticastIp() {
        return new IPException("Ip is not for multicast");
    }
    static IPException notValidIp() {
        return new IPException("Ip is not valid");
    }
}
