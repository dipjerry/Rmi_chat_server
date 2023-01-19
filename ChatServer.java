import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ChatServer extends UnicastRemoteObject implements ChatServerInterface {
    private Map<String, ChatClientInterface> clients = new HashMap<>();
    // private Set<String> clients = new HashSet<String>();
    private int serverPort = 1099;  // configure the port to use for RMI
    // private String hostIP = "localhost";  // configure the IP of the server
    // private String hostIP = "192.168.23.1";  // configure the IP of the server
    private String hostIP = "localhost";  // use 'localhost' if testing locally

    // Registry registry = LocateRegistry.getRegistry(hostIP, serverPort);
    // if (registry == null) {
    //     System.out.println("RMI registry is not running on " + hostIP + ":" + serverPort);
    //     System.exit(1);
    


    public ChatServer() throws RemoteException {
        super();
    }

    @Override
    public void registerClient(String name, ChatClientInterface client) throws RemoteException {
        clients.put(name, client);
        broadcastMessage(name + " has joined the chat.");
    }

    @Override
    public void unregisterClient(String name) throws RemoteException {
        clients.remove(name);
        broadcastMessage(name + " has left the chat.");
    }

    @Override
    public void broadcastMessage(String message) throws RemoteException {
        for (ChatClientInterface client : clients.values()) {
            client.receiveMessage(message);
        }
    }

    @Override
    public void sendMessage(String sender, String recipient, String message) throws RemoteException {
        clients.get(recipient).receiveMessage(sender + ": " + message);
    }

    @Override
    public Set<String> getConnectedClients() throws RemoteException {
        // return clients.keySet();
        return new HashSet<>(clients.keySet());
    }

    public static void main(String[] args) throws Exception {
        try {
            ChatServer server = new ChatServer();
            // Registry registry = LocateRegistry.createRegistry(server.serverPort);
            java.rmi.Naming.rebind("rmi://"+server.hostIP+"/chatserver", server);
            System.out.println("Server started on "+ server.hostIP +":"+server.serverPort);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}

interface ChatServerInterface extends java.rmi.Remote {
    void registerClient(String name, ChatClientInterface client) throws RemoteException;
    void broadcastMessage(String message) throws RemoteException;
    void sendMessage(String sender, String recipient, String message) throws RemoteException;
    void unregisterClient(String name) throws RemoteException;
    Set<String> getConnectedClients() throws RemoteException;
}






