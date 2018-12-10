import java.io.*;
import java.util.*;

public class PokemonArena {
    public static ArrayList<Player> team = new ArrayList<>();
    public static void main(String[] args) throws FileNotFoundException{
        String[] pokemon = new ReadFile("pokemon.txt").getArray();
        team = select_team(pokemon, team);
        battlePhase(team, pokemon);
    }

    public static ArrayList<Player> select_team(String[] pokemon, ArrayList<Player> team) {
        int counter = 0;
        String[] selection = new String[pokemon.length];
        for (int i = 0; i < pokemon.length; i++) {
                selection[i] = pokemon[i].split(",")[0];
                counter++;
                System.out.println(counter + ": " + selection[i]);
        }

        ArrayList<String> chosen_pokemon = new ArrayList<>();

        while (team.size() < 4) {
            System.out.print("Which pokemon would you like? (Spaces Left: " + (4 - team.size()) + "): ");
            Scanner input = new Scanner(System.in);
            int indexOfSelection = input.nextInt();
            if (indexOfSelection > 0 && indexOfSelection < pokemon.length) {
                Player chosen = new Player(pokemon[indexOfSelection - 1]);
                if (!chosen_pokemon.contains(chosen.getName())) {
                    chosen_pokemon.add(chosen.getName());
                    team.add(chosen);
                } else System.out.println("This Pokemon is already in the team!!");
            } else System.out.println("Please choose one of the selections");
        }
        return team;
    }
    private static void battlePhase(ArrayList<Player> team, String[] listOfPokemon) {
        ArrayList<String> usedPokemon = new ArrayList<>();
        for (Player i : team) usedPokemon.add(i.getName());
        Enemy enemy = getNewEnemy(listOfPokemon, usedPokemon);
        System.out.println();
        printTeamSelection(team, enemy);
        System.out.println("It is the player's turn to fight!!");
        Player fighter = team.get(choosePokemon(team));
        Random rand = new Random();
        int turn = rand.nextInt(2);
        while (usedPokemon.size() <= listOfPokemon.length && team.size() > 0) {
            if (turn == 0){
                fighter.battle(enemy);
                if (fighter.getRetreat()){
                    printTeamSelection(team, enemy);
                    fighter = team.get(choosePokemon(team));
                }
                for (Player i : team) i.setEnergy(i.getEnergy() + (i.getEnergy() == 50 ? 0 : 10));
                fighter.setStun(false);
                fighter.setDisable(false);
                fighter.setRetreat(false);
                turn = 1 - turn;
                if (enemy.getHp() <= 0) {
                    System.out.println(enemy.getName() + " has fainted!!\n");
                    if (usedPokemon.size() == listOfPokemon.length) break;
                    System.out.println(listOfPokemon.length - usedPokemon.size() + " pokemon are left!");
                    enemy = getNewEnemy(listOfPokemon, usedPokemon);
                    printTeamSelection(team, enemy);
                    fighter = team.get(choosePokemon(team));
                    turn = rand.nextInt(2);
                }
            }
         
            if (turn == 1){
                System.out.println("\nIt is the enemy's turn to fight!!");
                enemy.battle(fighter);

                turn = 1 - turn;
                if (fighter.getHp() <= 0) {
                    System.out.println("\n" + fighter.getName() + " has fainted!!");
                    team.remove(fighter);
                    if (team.size() < 1) {
                        break;
                    }
                    printTeamSelection(team, enemy);
                    fighter = team.get(choosePokemon(team));
                    turn = rand.nextInt(2);
                }
            }
        
        }
        System.out.println((team.size() == 0 ? "GAME OVER\nYOU LOST" : "YOU WON!\nCONGRATULATIONS"));
    }

    public static void printTeamSelection(ArrayList<Player> team, Enemy enemy) {
        int counter = 0;
        System.out.println("Your Opponent is " + enemy.getName() + "!!!");
        System.out.println(enemy.getName() + ":\n\tHP: " + enemy.getHp() + "\n\tType: " + enemy.getType());
        System.out.println("\nChoose a Pokemon to battle with!\n");
        for (Player i : team) {
            counter++;
            System.out.println(counter + ". " + i.getName() + ":\n\tHP: " + i.getHp() + "\n\tType: " + i.getType() + "\n\tEnergy: " + i.getEnergy());
        }
    }

    public static int choosePokemon(ArrayList<Player> team) {
        int chosen;
        while (true) {
            System.out.print("Choose a Pokemon: ");
            Scanner input = new Scanner(System.in);
            chosen = input.nextInt() - 1;
            if (chosen + 1 > team.size() || chosen + 1 <= 0) {
                System.out.println("Please make a valid entry!!");
            } else {
                System.out.println("\n");
                System.out.println(team.get(chosen).getName() + "!! I choose you!");
                return chosen;
            }
        }
    }
    
    public static Enemy getNewEnemy(String[] listOfPokemon, ArrayList<String> usedPokemon){
        Enemy enemy;
        Enemy randEnemy;
        while (true) {
            Random rand = new Random();
            int newEnemyIndex = rand.nextInt(listOfPokemon.length);
            randEnemy = new Enemy(listOfPokemon[newEnemyIndex]);
            if (!usedPokemon.contains(randEnemy.getName())) {
                enemy = new Enemy(listOfPokemon[newEnemyIndex]);
                usedPokemon.add(enemy.getName());
                break;
            }
        }
        return enemy;
    }
}