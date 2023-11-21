import java.io.Serializable;
import java.util.ArrayList;

public class GameState implements Serializable {
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

    //ToDo: Implement serializableID data type
}
