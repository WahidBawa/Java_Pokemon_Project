import java.io.*;
import java.util.*;

public class PokemonArena {
    public static ArrayList<Player> team = new ArrayList<>(); //creates ArrayList that holds Player type objects
    public static void main(String[] args) throws FileNotFoundException{ // main method
        String[] pokemon = new ReadFile("pokemon.txt").getArray(); // Creates a String array which stores the lines from file
        String[] title = new ReadFile("./texts/title.txt").getArray(); // Creates a String array which stores the lines from file
        for (String i : title) System.out.println(i); // displays the title ascii art
        team = select_team(pokemon, team); // this will let the player create a team
        battlePhase(team, pokemon);// this will begin the battle phase
    }

    public static ArrayList<Player> select_team(String[] pokemon, ArrayList<Player> team) {

        ArrayList<String> chosen_pokemon = new ArrayList<>(); // This ArrayList will store the chosen pokemon

        int counter = 0;
        while (team.size() < 4) { // will run while the team is not at max capacity
            String[] selection = new String[pokemon.length];
            for (int i = 0; i < pokemon.length; i++) { // This will display the name and the number of the pokemon
                                                       // to let the user choose a pokemon
                    selection[i] = pokemon[i].split(",")[0];
                    counter++;
                    System.out.println(counter + ": " + selection[i]);
            }
            counter = 0; // resets the counter
            System.out.print("Which pokemon would you like? (Spaces Left: " + (4 - team.size()) + "): ");
            Scanner input = new Scanner(System.in); // creates a Scanner object for input from user
            int indexOfSelection = input.nextInt(); // this is the choice of the user
            if (indexOfSelection > 0 && indexOfSelection <= pokemon.length) { // will check if the choice is valid
                Player chosen = new Player(pokemon[indexOfSelection - 1]); // creates a Player object
                if (!chosen_pokemon.contains(chosen.getName())) { // checks if the pokemon has been chosen before by user
                    chosen_pokemon.add(chosen.getName()); // will add the chosen pokemon to ArrayList to check for duplicates
                    try{
                        String[] pokemonAscii = new ReadFile("./images/" + chosen.getName().toLowerCase() + ".txt").getArray();
                        for (String i : pokemonAscii) System.out.println(i); // this prints out the ascii art                    
                    }catch (FileNotFoundException e){ // catches the exception
                        System.out.println("pokemon file doesn't exist");
                    }
                    //waits for the player to press enter so that they can see the pokemon ascii
                    System.out.print("Press <Enter>");
                    Scanner wait = new Scanner(System.in);
                    wait.nextLine();
                    team.add(chosen); // adds the chosen pokemon to team
                } else System.out.println("This Pokemon is already in the team!!");
            } else System.out.println("Please choose one of the selections");
        }
        return team; // returns the chosen team
    }
    private static void battlePhase(ArrayList<Player> team, String[] listOfPokemon) {
        ArrayList<String> usedPokemon = new ArrayList<>(); // this will store all used pokemon, fainted or in use
        for (Player i : team) usedPokemon.add(i.getName()); // will add the players chosen pokemon to this ArrayList
        Enemy enemy = getNewEnemy(listOfPokemon, usedPokemon); // This will create a new enemy object which will be chosen randomly
        System.out.println();
        printTeamSelection(team, enemy); //prints out the team selection
        Player fighter = team.get(choosePokemon(team)); // lets the user create a Player object of the pokemon they want to use to fight
        Random rand = new Random(); // this creates a random object to decide which pokemon goes first
        int turn = rand.nextInt(2); // this will store the result of the random number
        boolean enemyTurnDone = false;
        boolean playerTurnDone = false;
        while (usedPokemon.size() <= listOfPokemon.length && team.size() > 0) { // this will run while the team is alive and there are pokemon left to fight
            if (turn == 0){ // if it is the player's turn
                fighter.battle(enemy); // the player's pokemon will battle the enemy pokemon
                if (fighter.getRetreat()){ // if the user chooses to retreat, they will be allowed to choose another pokemon
                    printTeamSelection(team, enemy);
                    fighter = team.get(choosePokemon(team)); // lets the user choose a pokemon out of their team
                }
                //the following status effects will be set to false as they last for only one turn 
                fighter.setStun(false);
                fighter.setRetreat(false);
                playerTurnDone = true;
                turn = 1 - turn; // this will toggle the turn to the opposing side
                if (enemy.getHp() <= 0) { // if the enemy pokemon is to faint
                    System.out.println(enemy.getName() + " has fainted!!\n");
                    if (usedPokemon.size() == listOfPokemon.length) break; // if the last pokemon has fainted then the loop will break
                    System.out.println(listOfPokemon.length - usedPokemon.size() + " pokemon are left!");
                    for (Player i : team) i.setEnergy(50); // this will set the energy
                    System.out.println("\nAll of your pokemon have gained 20 HP for defeating " + enemy.getName());
                    for (Player i : team) i.setHp(i.getHp() + 20 > i.maxHp ? i.maxHp : i.getHp() + 20); // this will add 20 to the pokemon's hp for defeating enemy
                    enemy = getNewEnemy(listOfPokemon, usedPokemon); // will create a random enemy pokemon object
                    printTeamSelection(team, enemy);
                    fighter = team.get(choosePokemon(team)); // this will let the player choose a pokemon to battle the new enemy
                    turn = rand.nextInt(2); // this will randomly choose which side goes first
                    fighter.setDisable(false); // this will reset disable as the battle has ended
                }
            }
         
            if (turn == 1){ // if it is the enemy turn
                System.out.println("\nIt is the enemy's turn to fight!!");
                enemy.battle(fighter); // the enemy pokemon will battle with the user's selected pokemon
                //the following status effects will be set to false as they last for only one turn 
                enemy.setStun(false); // this will reset the stun once the enemy's turn is over
                enemyTurnDone = true;
                turn = 1 - turn; // this will toggle the turn
                if (fighter.getHp() <= 0) { // if the user's pokemon were to faint
                    System.out.println("\n" + fighter.getName() + " has fainted!!");
                    team.remove(fighter); // the fainted pokemon will be removed from the team
                    if (team.size() < 1) { // if there are no more pokemon left in the team the loop will break
                        break;
                    }
                    printTeamSelection(team, enemy);
                    fighter = team.get(choosePokemon(team)); // user gets to choose new pokemon to use
                    turn = rand.nextInt(2); // will randomly select which side goes first
                }
            }
            if (playerTurnDone && enemyTurnDone){
                System.out.println("All pokemon's energy will increase by 10!!");
                for (Player i : team) i.setEnergy(i.getEnergy() + (i.getEnergy() == 50 ? 0 : 10)); // this will add ten to the player's pokemon
                enemy.setEnergy(enemy.getEnergy() + (enemy.getEnergy() == 50 ? 0 : 10)); // increases enemy's energy
                playerTurnDone = enemyTurnDone = false;
            }
        }
        try{
            String[] winText = new ReadFile("./texts/win.txt").getArray();
            String[] loseText = new ReadFile("./texts/lose.txt").getArray();
            if (team.size() == 0) for (String i : loseText) System.out.println(i); // this prints out the ascii art
            else for (String i : winText) System.out.println(i); // this prints out the ascii art
        }catch (FileNotFoundException e){ // catches the exception
            System.out.println("file doesn't exist");
        }
        // String[] loseText = new ReadFile("./texts/" + chosen.getName().toLowerCase() + ".txt").getArray();
        // for (String i : winText) System.out.println(i);
    }

    public static void printTeamSelection(ArrayList<Player> team, Enemy enemy) {
        int counter = 0;
        System.out.println("Your Opponent is " + enemy.getName() + "!!!"); // this informs the user of which pokemon they are fighting
        System.out.println(enemy.getName() + ":\n\tHP: " + enemy.getHp() + "\n\tType: " + enemy.getType()); // this will tell the user the HP and type of the enemy pokemon
        System.out.println("\nChoose a Pokemon to battle with!\n");
        for (Player i : team) { // this will display the user's available pokemon and their HP, type, and energy
            counter++;
            System.out.println(counter + ". " + i.getName() + ":\n\tHP: " + i.getHp() + "\n\tType: " + i.getType() + "\n\tEnergy: " + i.getEnergy());
        }
    }

    public static int choosePokemon(ArrayList<Player> team) {
        int chosen;
        while (true) {
            System.out.print("Choose a Pokemon: ");
            Scanner input = new Scanner(System.in); // gets input from the user
            chosen = input.nextInt() - 1; // stores the chosen pokemon
            if (chosen + 1 > team.size() || chosen + 1 <= 0) { // will check if a valid entry was made
                System.out.println("Please make a valid entry!!");
            } else {
                System.out.println("\n");
                System.out.println(team.get(chosen).getName() + "!! I choose you!");
                return chosen; // this returns the chosen pokemon
            }
        }
    }
    
    public static Enemy getNewEnemy(String[] listOfPokemon, ArrayList<String> usedPokemon){
        Enemy enemy;
        Enemy randEnemy;
        while (true) {
            Random rand = new Random(); // this Random object will be used to decide randomly which pokemon will be used
            int newEnemyIndex = rand.nextInt(listOfPokemon.length); // this will store the new enemy's index
            randEnemy = new Enemy(listOfPokemon[newEnemyIndex]); // this will create a enemy object of the random pokemon
            if (!usedPokemon.contains(randEnemy.getName())) { // if the enemy pokemon has not been used
                enemy = new Enemy(listOfPokemon[newEnemyIndex]); // the pokemon will have a enemy object created
                usedPokemon.add(enemy.getName()); // the name will be added to usedPokemon
                break; // breaks the loop
            }
        }
        return enemy; // returns the new enemy for the player to battle
    }
}