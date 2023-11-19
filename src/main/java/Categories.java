import java.util.ArrayList;
import java.util.Random;

//This class handles the categories and generates a random word
public class Categories {

    //Arrays that will hold category words
    ArrayList<String> c1;
    ArrayList<String> c2;
    ArrayList<String> c3;

    //Counters that keep track of amount of words chosen from category
    int c1_guesses;
    int c2_guesses;
    int c3_guesses;

    Categories(){
        c1 = new ArrayList<>();
        c2 = new ArrayList<>();
        c3 = new ArrayList<>();

        //Initialize counters to 0
        c1_guesses = 0;
        c2_guesses = 0;
        c3_guesses = 0;

        //Populate category1 array
        c1.add("PIZZA");
        c1.add("HOTDOG");
        c1.add("SANDWICH");
        c1.add("BURRITO");
        c1.add("SOUP");
        c1.add("PIE");
        c1.add("APPLE");
        c1.add("BANANA");
        c1.add("ORANGE");
        c1.add("WAFFLE");

        //Populate category2 array
        c2.add("SNAIL");
        c2.add("CAT");
        c2.add("COW");
        c2.add("BAT");
        c2.add("LION");
        c2.add("MONKEY");
        c2.add("SNAKE");
        c2.add("SHARK");
        c2.add("LIZARD");
        c2.add("EAGLE");

        //Populate category2 array
        c3.add("ENGLAND");
        c3.add("FRANCE");
        c3.add("JAMAICA");
        c3.add("BOLIVIA");
        c3.add("AUSTRIA");
        c3.add("URUGUAY");
        c3.add("JAPAN");
        c3.add("ETHIOPIA");
        c3.add("ALGERIA");
        c3.add("CYPRUS");

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
