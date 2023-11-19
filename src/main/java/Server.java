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
        GameState game_played;
        Socket connection;
        ObjectInputStream in;
        ObjectOutputStream out;
        GuessingGame game; //Game instance
       

        public ClientThread(Socket server, int count){
            startGame();  //Initializing game once client connects to thread
            connection = server;
            this.count = count;
        }

        private int readCategory() throws Exception{
            int category = (int) in.readObject();
            callback.accept(category);

            return category;
        }

        private String readGuess() throws Exception{
            String guess = in.readObject().toString();
            callback.accept("Client # " + " guessed: " + guess);

            return guess;
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
                    game.pick_from_category(readCategory());

                    while (!game_played.gameOver){ //FIXME: Add functionality to update if the game is over in gameOver
                        makeGuess(readGuess());
                        out.writeObject(game_played);

                        if(game_played.gameWon == true || game_played.gameWon == false){ //Fixme:  These helper functions don't really belong here
                            game_played = new GameState();
                        }
                    }
                }
                catch (Exception e){
                    callback.accept("Client # " + " disconnected");
                    clientThreads.remove(this);
                }
            }
        }

        //Helper function that starts the game //Fixme:  These helper functions don't really belong here
        public void startGame(){
          game = new GuessingGame();  
          game_played = new GameState();
        }

        //Helper function that makes a guess //Fixme:  These helper functions do not belong here
        public void makeGuess(String guess){
            int g = game.play_round(guess); //makeing a guess on behalf of the client
            game_played.guess = guess;
            game_played.round_outcome = g;
            
            //When a guess has been made
            if(g==1){   //Valid guess 

                //Check if it is a hit or a miss and update label
                for(int i=0; i<game.round.word_arr.size(); i++){
                    if(guess.equals(game.round.word_arr.get(i))){
                        game_played.roundWon = true;
                        game_played.index_of_guess = i;
                    }
                }

                game_played.guesses_left = game.round.guesses;

            }else if(g==-1){    //Wasted all the guesses 
                game_played.guesses_left = game.round.guesses;

                //Checking which category has been played and if the game is over or if the player has additional attempts
        
                //If category 1 has been played 3 times
                if(game.categories.c1_guesses==3 || game.categories.c2_guesses==3 || game.categories.c3_guesses==3){
                    game_played.gameWon = false;
                }else{      //If player has more attempts left to play the game
                    game_played.roundWon = false;
                }

            }else{  //Guessed the word
                game_played.words_guessed = game.words_guessed;
                
                if(game.words_guessed==3){
                    game_played.gameWon = true;
                }else{
                    game_played.roundWon = true;    
                    
                    //If a round has been won check to see which category cannot be played anymore
                    if(game.category_track.get(0)==0){
                        game_played.category = 1;
                    }
                    
                    if(game.category_track.get(1)==0){
                        game_played.category = 2;
                    }
            
                    if(game.category_track.get(2)==0){
                        game_played.category = 3;
                    }
                    
                }

            }
        }
    }
}
