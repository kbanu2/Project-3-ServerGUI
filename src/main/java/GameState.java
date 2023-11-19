import java.io.Serializable;

public class GameState implements Serializable {
    int round_outcome;
    int index_of_guess;
    int category;
    int guesses_left;
    int words_guessed;
    String guess;
    boolean gameWon;
    boolean roundWon;

    //ToDo: Implement serializableID data type
}
