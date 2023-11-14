


import java.util.ArrayList;

// GuessingGame class controls the general logic of the game
public class GuessingGame {
    Categories categories;  //Initilizing categories
    GuessingRound round; //Each round that will be played
    ArrayList<Integer> category_track = new ArrayList<>();  //Array that keeps track of valid categories to choose from
    int category_played; //the index of the category being played at a time
   

    GuessingGame(){
        categories = new Categories(); 

        //1 at index i means ctegory i is available for choosing
        category_track.add(1);
        category_track.add(1);
        category_track.add(1);
    }

    int pick_from_category(int cat){
        

        //If user chooses category 1
        if(cat==1){

            //Check to see if chose category is valid for playing
            if(categories.c1_guesses<3 && category_track.get(0)==1){

                String word = categories.choose_c1(); //Starting round with word from category 1
                round = new GuessingRound(word); //Initializing Guessing round
                category_played = 0;

            }else{
                return 0; //0 means chose category cannot be played; It either has been played and won, or it has been played more than 3 times
            }
            
        }else if(cat==2){

            //Check to see if category has been chosen
            if(categories.c2_guesses<3 && category_track.get(1)==1){

                String word = categories.choose_c2(); //Starting round with word from category 2
                round = new GuessingRound(word); //Initializing Guessing round
                category_played = 1;

            }else{
                return 0;
            }


        }else{

            //Check to see if category has been chosen
            if(categories.c3_guesses<3 && category_track.get(2)==1){

                String word = categories.choose_c3(); //Starting round with word from category 2
                round = new GuessingRound(word); //Initializing Guessing round
                category_played = 2;

            }else{
                return 0;
            }
        }

        return 1;   //1 means category is valid and can be played
        
    }

    //Function that plays round and checks outcome
    int play_round(char guess){
        int r = round.make_a_guess(guess); //r hold outcome of round
        
        if(r == -1){    //-1 means round lost
            return -1; 

        }else if(r == 10){  //10 means round has been won

            category_track.set(category_played, 0); //0 in category array means category cannot be played anymore
            return 10; 

        }
        
        return 1;   //returning 1 means round still going, guesses can be made

    }

}
