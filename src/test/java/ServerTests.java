import javafx.scene.control.ListView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class ServerTests {

    @Test
    public void testServerConstructorPass(){
        assertDoesNotThrow(() -> {
            Server s = new Server(new Controller(), 1000);
        });
    }

    @Test
    public void testGameStateConstructor(){
        GameState gameState = new GameState();

        assertEquals(0, gameState.length);
        assertEquals(0, gameState.guesses_left);
        assertEquals(0, gameState.gameWon);
        assertEquals(0, gameState.roundWon);
    }

    @Test
    public void testGuessingGameConstructor(){
        GuessingGame guessingGame = new GuessingGame();

        assertEquals(3, guessingGame.category_track.size());
        assertEquals("PIZZA",guessingGame.categories.c1.get(0));
        assertEquals("SNAIL",guessingGame.categories.c2.get(0));
        assertEquals("ENGLAND",guessingGame.categories.c3.get(0));
    }

    @Test
    void testChooseC1() {
        Categories categories = new Categories();
        String randomWordC1 = categories.choose_c1();
        assertNotNull(randomWordC1, "Failed");
        assertTrue(categories.c1_guesses > 0);
    }

    //Testing category 2 choice
    @Test
    void testChooseC2() {
        Categories categories = new Categories();
        String randomWordC2 = categories.choose_c2();
        assertNotNull(randomWordC2, "Failed");
        assertTrue(categories.c2_guesses > 0, "Failed");
    }

    //Testing category 3 choice
    @Test
    void testChooseC3() {
        Categories categories = new Categories();
        String randomWordC3 = categories.choose_c3();
        assertNotNull(randomWordC3, "Failed");
        assertTrue(categories.c3_guesses > 0, "Failed");
    }

    //Testing category word generation
    @Test
    void testRemoveChosenWordFromC1() {
        Categories categories = new Categories();
        String chosenWord = categories.choose_c1();
        assertNotNull(chosenWord, "Failed");
        assertFalse(categories.c1.contains(chosenWord), "Failed");
    }

    //Testing category index change
    @Test
    void testCountersIncrementation() {
        Categories categories = new Categories();
        categories.choose_c2();
        categories.choose_c2();
        categories.choose_c3();
        assertEquals(0, categories.c1_guesses, "Failed");
        assertEquals(2, categories.c2_guesses, "Failed");
        assertEquals(1, categories.c3_guesses, "Failed");
    }

    //These tests are testing the GuessingRound class

    //Testing changes in variables when a correct guess is made
    @Test
    public void testMakeAGuessCorrect() {
        GuessingRound round = new GuessingRound("HELLO");
        int outcome = round.make_a_guess("L");
        assertEquals(1, outcome, "Failed");
        assertEquals(6, round.guesses, "Failed");
        assertEquals("L", round.guessed_word.get(0), "Failed");
    }

    //Testing changes in variables when an incorrect guess is made
    @Test
    public void testMakeAGuessIncorrect() {
        GuessingRound round = new GuessingRound("HELLO");
        int outcome = round.make_a_guess("A");
        outcome = round.make_a_guess("B");
        outcome = round.make_a_guess("C");
        outcome = round.make_a_guess("X");
        outcome = round.make_a_guess("Y");
        outcome = round.make_a_guess("Z");
        assertEquals(-1, outcome, "Failed");
        assertEquals(0, round.guesses, "Failed");
    }

    //Testing correct guess of full word
    @Test
    public void testMakeAGuessAllCorrect() {
        GuessingRound round = new GuessingRound("HELLO");
        round.make_a_guess("H");
        round.make_a_guess("E");
        round.make_a_guess("O");
        int outcome = round.make_a_guess("L");
        assertEquals(10, outcome, "Failed");
    }

    //These tests are testing the GuessingRound class

    //Testing game logic for picking a category
    @Test
    public void testPickFromCategoryValid() {
        GuessingGame game = new GuessingGame();
        int outcome = game.pick_from_category(1);
        assertEquals(1, outcome, "Failed");
        assertNotNull(game.round, "Failed");
    }
}
