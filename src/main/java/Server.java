import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Consumer;

public class Server {
    private int port;
    private String ip;
    private ServerThread serverThread;
    Consumer<Serializable> callback;
    public class ServerThread extends Thread{
        ArrayList<ClientThread> clientThreads;
        @Override
        public void run(){
            //ToDo: Add while loop to initialize clients
        }
    }

    public class ClientThread extends Thread {
        Map<String, ArrayList<String>> categories;
        ArrayList<Integer> numAttempts;
        //ToDo: Add objects to validate the user input
        //ToDo: Add method to create and return new GameState object using the outputs from the GuessingGame/GuessingRound
        //public GameState validateInput(String input);
        @Override
        public void run() {
            //ToDo: Add while loop to send GameStates back to client
        }
    }
}
