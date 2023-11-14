


import java.util.ArrayList;

// This class controls one round of the game 
public class GuessingRound {
    int guesses;    //Number of valid guesses during the round
    String word;    //The word needed to be guessed  
    ArrayList<String> guessed_word;  //Array will store word that's guessed correctly and display to player

    //Each round needs a word as an argument
    GuessingRound(String w){
        word = w;
        guesses = 6;
        guessed_word = new ArrayList<>(word.length()); //initializing guessed_word array with length of word
    }

    //Function to determine outcome each guess
    int make_a_guess(char guess){
        
        int c = 0; //frequency of guess appearing in the word
        int i = 0; //index

        //Check guess
        for(char l : word.toCharArray()){
            if(guess == l){
                c++;
                guessed_word.add(i, String.valueOf(l)); //Put letter in array to display to player
            }
            i++;
        }

        // Using a loop to concatenate the so far guessed word
        StringBuilder result = new StringBuilder();
        for (String str : guessed_word) {
            result.append(str);
        }
        
        // Convert StringBuilder to String
        String wordString = result.toString();

        //If no letters matched the guess, amount of valid guesses decreases
        if(c==0){
            guesses--;
            
            if(guesses == 0){   //When last guess happens -1 indicates lost round
                return -1;
            }
        }else if(wordString==word){ //Condition checks if word has been guessed
            return 10;     //10 indicates correctly guessed word
        }

        return c;   //Returns the number of time the guessed letter appears in the word
    }
    
}
