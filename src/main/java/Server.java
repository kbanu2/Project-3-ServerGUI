import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Consumer;

public class Server {
    int port;
    ServerThread serverThread;
    Controller callback;

    public Server(Controller callback, int port){
        this.callback = callback;
        this.port = port;
        serverThread = new ServerThread();
        serverThread.start();
    }

    public class ServerThread extends Thread{
        public void run(){
            try(ServerSocket serverSocket = new ServerSocket(port);){
                callback.accept("Server is running on port: " + port);
                while (true){
                    ClientThread clientThread = new ClientThread(serverSocket.accept());
                    clientThread.start();
                }
            }
            catch(Exception e){
                System.out.println(e.getMessage());
                callback.accept("Server did not launch");
            }
        }
    }

    public class ClientThread extends Thread {
        String username;
        GameState game_played;
        Socket connection;
        ObjectInputStream in;
        ObjectOutputStream out;
        GuessingGame game; //Game instance
       

        public ClientThread(Socket server){
            this.connection = server;
            startGame();  //Initializing game once client connects to thread
        }

        private int readCategory() throws Exception{
            int category = (int) in.readObject();
            callback.accept("Client '" + username + "' is in category " + category);

            return category;
        }

        private String readGuess() throws Exception{
            String guess = in.readObject().toString();
            callback.accept("Client '" + username + "' guessed: " + guess);

            return guess;
        }
        
        public void run() {
            try{
                in = new ObjectInputStream(connection.getInputStream());
                out = new ObjectOutputStream(connection.getOutputStream());
                connection.setTcpNoDelay(true);

                username = in.readObject().toString();
                callback.accept("Client '" + username + "' connected to the server");
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                callback.accept("Client's streams could not connect");
            }

            try {
                while (true){
                    game.pick_from_category(readCategory());
                    game_played.word = new ArrayList<>(game.round.word_arr.size());
                    System.out.println("WORD: " + game.round.word);
                    game_played.length = game.round.word_arr.size();
                    for(int i=0; i<game.round.word_arr.size(); i++){
                        game_played.word.add(i, "_");
                    }
                    game_played.guesses_left = game.round.guesses;

                    out.reset();
                    out.writeObject(game_played);


                    while (game_played.gameWon != 1 && game_played.gameWon != -1 && game_played.roundWon != 1 && game_played.roundWon != -1){
                        makeGuess(readGuess());

                        out.reset();
                        out.writeObject(game_played);
                    }

                    if (game_played.gameWon == 1 || game_played.gameWon == -1){
                        String result;
                        if (game_played.gameWon == 1)
                            result = "Player '" + username + "' has won their game!";
                        else
                            result = "Player '" + username + "' has lost their game!";

                        game = new GuessingGame();
                        callback.accept(result);
                    }

                    game_played = new GameState();
                }
            }
            catch (Exception e){
                callback.accept("Client '" + username + "' disconnected from the server");
            }
        }

        public void startGame(){
          game = new GuessingGame();
          game_played = new GameState();
        }

        public void makeGuess(String guess){
            int g = game.play_round(guess); //makeing a guess on behalf of the client
            game_played.guess = guess;
            game_played.round_outcome = g;

            //When a guess has been made
            if(g==1){   //Valid guess 

                //Check if it is a hit or a miss and update label
                for(int i=0; i<game.round.word_arr.size(); i++){
                    if(guess.equals(game.round.word_arr.get(i))){
                        game_played.word.set(i, guess);
                        for(String s:game_played.word){
                            System.out.print(s);
                        }
                        System.out.println();
                    }
                }

                game_played.guesses_left = game.round.guesses;
                

            }else if(g==-1){    //Wasted all the guesses 
                game_played.guesses_left = game.round.guesses;

                //Checking which category has been played and if the game is over or if the player has additional attempts
        
                //If category 1 has been played 3 times
                if(game.categories.c1_guesses==3 || game.categories.c2_guesses==3 || game.categories.c3_guesses==3){
                    game_played.gameWon = -1;
                }else{      //If player has more attempts left to play the game
                    game_played.roundWon = -1;
                }

            }else{  //Guessed the word
                game_played.words_guessed = game.words_guessed;
                for(int i=0; i<game.round.word_arr.size(); i++){
                    if(guess.equals(game.round.word_arr.get(i))){
                        game_played.word.set(i, guess);
                        for(String s:game_played.word){
                            System.out.print(s);
                        }
                        System.out.println();
                    }
                }
                
                if(game.words_guessed==3){
                    game_played.gameWon = 1;
                }else{
                    game_played.roundWon = 1;    
                    
                    //If a round has been won check to see which category cannot be played anymore
                    if(game.category_track.get(0)==0){
                        game_played.category1 = 1;
                    }
                    
                    if(game.category_track.get(1)==0){
                        game_played.category2 = 1;
                    }
            
                    if(game.category_track.get(2)==0){
                        game_played.category3 = 1;
                    }
                }
            }
        }
    }
}
