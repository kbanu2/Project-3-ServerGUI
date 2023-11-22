import java.io.Serializable;
import java.util.ArrayList;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1234567L;
    ArrayList<String> word;
    int length;
    int round_outcome;
    int index_of_guess;
    int category1;
    int category2;
    int category3;
    int guesses_left;
    int words_guessed;
    String guess;
    int gameWon = 0;
    int gameOver = 0;
    int roundWon = 0;

    public GameState(){

    }

    public GameState(GameState state){
        this.word = state.word;
        this.length = state.length;
        this.round_outcome = state.round_outcome;
        this.index_of_guess = state.index_of_guess;
        this.category1 = state.category1;
        this.category2 = state.category2;
        this.category3 = state.category3;
        this.guesses_left = state.guesses_left;
        this.words_guessed = state.words_guessed;
        this.guess = state.guess;
        this.gameWon = state.gameWon;
        this.gameOver = state.gameOver;
        this.roundWon = state.roundWon;
    }
}
