import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.Set;

public class ChatClient extends UnicastRemoteObject implements ChatClientInterface {
    private String name;
    private int serverPort = 1099;  // configure the port to use for RMI
    // private String hostIP = "13.233.122.16";  // configure the IP of the server
    private String hostIP = "localhost";  // use 'localhost' if testing locally

    protected ChatClient(String name) throws RemoteException {
        super();
        this.name = name;
    }

    @Override
    public void receiveMessage(String message) throws RemoteException {
        System.out.println(message);
    }

    public static void main(String[] args) throws Exception {
        String name = args[0];
        ChatClient client = new ChatClient(name);
        // ChatServerInterface server = (ChatServerInterface) java.rmi.Naming.lookup("rmi://"+client.hostIP+"/chatserver");
        ChatServerInterface server = (ChatServerInterface) java.rmi.Naming.lookup("rmi://"+client.hostIP+":"+client.serverPort+"/chatserver");
        server.registerClient(name, client); 
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("list")) {
                Set<String> clients = server.getConnectedClients();
                System.out.println("Connected clients: " + clients);
            }
            else if (line.equals("logout")) {
                server.unregisterClient(name);
                System.out.println("You have logged out.");
                break;
            }
            else if (line.startsWith("@")) {
                String[] parts = line.split(" ", 2);
                String recipient = parts[0].substring(1);
                String message = parts[1];
                server.sendMessage(name, recipient, message);
            } else {
                server.broadcastMessage(name + ": " + line);
            }
        }
    }
}

interface ChatClientInterface extends java.rmi.Remote {
    void receiveMessage(String message) throws RemoteException;
}
