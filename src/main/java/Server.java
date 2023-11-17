import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Consumer;

public class Server {
    private final int port;
    private final ServerThread serverThread;
    Consumer<Serializable> callback;
    ArrayList<ClientThread> clientThreads;
    public Server(Consumer<Serializable> callback, int port){
        this.callback = callback;
        this.port = port;
        serverThread = new ServerThread();
    }
    public class ServerThread extends Thread{
        int count = 1;
        @Override
        public void run(){
            try (ServerSocket serverSocket = new ServerSocket(port);){
                while (true){
                    ClientThread clientThread = new ClientThread(serverSocket.accept(), count);
                    callback.accept("Client # " + count + " has connected");
                    clientThreads.add(clientThread);
                    clientThread.start();

                    count++;
                }
            }
            catch(Exception e){
                callback.accept("Server did not launch");
            }
        }
    }

    public class ClientThread extends Thread {
        Map<String, ArrayList<String>> categories;
        ArrayList<Integer> numAttempts;
        int count;
        Socket connection;
        ObjectInputStream in;
        ObjectOutputStream out;
        //ToDo: Add objects to validate the user input
        //ToDo: Add method to create and return new GameState object using the outputs from the GuessingGame/GuessingRound
        //public GameState validateInput(String input);

        public ClientThread(Socket server, int count){
            connection = server;
            this.count = count;
        }
        @Override
        public void run() {
            try{
                in = new ObjectInputStream(connection.getInputStream());
                out = new ObjectOutputStream(connection.getOutputStream());
                connection.setTcpNoDelay(true);
            }
            catch (Exception e){
                callback.accept("Client #  " + " streams could not connect");
            }

            while (true){
                try{
                    char selection = in.readObject().toString().charAt(0);
                    callback.accept("Client # " + " guessed: " + selection);
                    //ToDo: Create new GameState using new char
                    GameState gameState = new GameState();
                    out.writeObject(gameState);
                }
                catch (Exception e){
                    callback.accept("Client # " + " disconnected");
                    clientThreads.remove(this);
                }
            }
        }
    }
}
