import java.util.Arrays; 
import java.util.ArrayList;

//This class handles every guess made in a round
public class GuessingRound {
    
    int guesses;    //Number of valid guesses during the round
    String word;    //The word needed to be guessed  
    ArrayList<String> guessed_word;  //Array will keep track of correct letters guessed
    ArrayList<String> word_arr;  //Array will store each letter of word to be guessed

    //Each round needs a word as an argument
    GuessingRound(String w){
        word = w;
        guesses = 6;
        guessed_word = new ArrayList<>(); //initializing empty guessed_word array
        
        //Storing word as array
        String[] strSplit = word.split(""); 
        word_arr = new ArrayList<String>(Arrays.asList(strSplit));
    }

    //Function to determine outcome each guess
    int make_a_guess(String guess){
        
        int c = 0; //frequency of guess appearing in the word

        //Check guess
        for(int i=0; i<word_arr.size(); i++){
            if(guess.equals(word_arr.get(i))){  //If guess matches a letter in the word
                c++;
                guessed_word.add(guess); //Put letter in guess_word array (order doesn't matter in this array)
            }
           
        }

        //If no letters matched the guess, amount of valid guesses decreases
        if(c==0){
            guesses--;
            
            if(guesses == 0){   //When last guess happens -1 indicates lost round
                return -1;
            }
        } else if (guessed_word.size() == word_arr.size()) {    //if the guess_word array is same size as word_arr that means all letters where guessed correctly
            return 10;  //10 indicates round is won
        }

        return 1;   //Returns the number of time the guessed letter appears in the word
    }
    
}
