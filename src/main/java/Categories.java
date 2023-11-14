import java.util.ArrayList;
import java.util.Random;

//Categories class will keep track of each category and the words in it
public class Categories {

    //Arrays that will hold category words
    ArrayList<String> c1 = new ArrayList<>();
    ArrayList<String> c2 = new ArrayList<>();
    ArrayList<String> c3 = new ArrayList<>();

    //Counters that keep track of amount of words chosen from category
    int c1_guesses;
    int c2_guesses;
    int c3_guesses;

    Categories(){
        //Initialize counters to 0
        c1_guesses = 0;
        c2_guesses = 0;
        c3_guesses = 0;

        //Populate category arrays
        //TO DO
    }

    //Choose one word from c1 category
    String choose_c1(){
        c1_guesses++;

        // Generate a random index
        Random random = new Random();
        int randomIndex = random.nextInt(c1.size());

        //Retreiving word
        String word =  c1.get(randomIndex);

        //Removing Word from array
        c1.remove(randomIndex);

        return word;
    }

    //Choose one word from c2 category
    String choose_c2(){
        c2_guesses++;

        // Generate a random index
        Random random = new Random();
        int randomIndex = random.nextInt(c2.size());

        //Retreiving word
        String word =  c2.get(randomIndex);

        //Removing Word from array
        c2.remove(randomIndex);

        return word;
    }

    //Choose one word from c3 category
    String choose_c3(){
        c3_guesses++;

        // Generate a random index
        Random random = new Random();
        int randomIndex = random.nextInt(c3.size());

        //Retreiving word
        String word =  c3.get(randomIndex);

        //Removing Word from array
        c3.remove(randomIndex);

        return word;
    }
    
}
