import java.io.*;
import java.util.*;

public class PokemonArena {
    public static ArrayList team;

    public static void main(String[] args) throws FileNotFoundException {
        // String[] pokemon_stats = pokemons.split("\n");
        String[] pokemon = new ReadFile("pokemon.txt").getArray();

        ArrayList team = select_team(pokemon);
        System.arraycopy(pokemon, 1, pokemon, 0, pokemon.length - 1);
        ArrayList<Player> finalTeam = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            finalTeam.add(new Player(pokemon[(int) team.get(i)]));
        }
        battlePhase(finalTeam, team, pokemon);
    }

    private static ArrayList select_team(String[] pokemon_choice) {
        ArrayList<Integer> team = new ArrayList<>();
        // ArrayList<Player> team = new ArrayList<>();
        int counter = 0;
        String[] selection = new String[pokemon_choice.length - 1];
        for (int i = 1; i < pokemon_choice.length; i++) {
                selection[i - 1] = pokemon_choice[i].split(",")[0];
                counter++;
                System.out.println(counter + ": " + selection[i - 1]);
        }
        while (team.size() < 4) {
            System.out.print("Which pokemon would you like?: ");
            Scanner input = new Scanner(System.in);
            int indexOfSelection = input.nextInt() - 1;
            if (indexOfSelection + 1 > 0 && indexOfSelection + 1 < pokemon_choice.length) {
                if (!team.contains(indexOfSelection)) {
                    team.add(indexOfSelection);
                } else {
                    System.out.println("This Pokemon is already in the team!!");
                }
            } else {
                System.out.println("Please choose one of the selections");
            }
        }
        return team;
    }

    private static void battlePhase(ArrayList<Player> team, ArrayList<Integer> usedPokemon, String[] listOfPokemon) {
        String[] listOfPokemon = new String[listOfPokemon.length - 1];
        System.arraycopy(listOfPokemon, 0, listOfPokemon, 0, listOfPokemon.length);
        Enemy enemy;
        while (true) {
            Random rand = new Random();
            int newEnemy = rand.nextInt(listOfPokemon.length);
            if (!usedPokemon.contains(newEnemy)) {
                enemy = new Enemy(listOfPokemon[newEnemy]);
//                System.out.println(newEnemy);
                break;
            }
        }
        System.out.println();
        printTeamSelection(team, enemy);
        System.out.println("It is the player's turn to fight!!");
        Player fighter = team.get(iChooseYou(team));
        while (usedPokemon.size() < listOfPokemon.length && team.size() > 0) {
            fighter.Battle(enemy);
            if (enemy.getHp() > 0) {
                System.out.println();
                System.out.println("It is the enemy's turn to fight!!");
                enemy.Battle(fighter);
            } else {
                System.out.println(enemy.getName() + " has fainted!!");
                while (true) {
                    Random rand = new Random();
                    int newEnemy = rand.nextInt(listOfPokemon.length);
                    if (!usedPokemon.contains(newEnemy)) {
                        enemy = new Enemy(listOfPokemon[newEnemy]);
                        // System.out.println(newEnemy);
                        usedPokemon.add(newEnemy);
                        break;
                    }
                }
            }
            if (fighter.getHp() <= 0) {
                System.out.println();
                System.out.println(fighter.getName() + " has died!!");
                team.remove(fighter);
                if (team.size() < 1) {
                    break;
                }
                printTeamSelection(team, enemy);
                fighter = team.get(iChooseYou(team));
            }
        }
        System.out.println((team.size() == 0 ? "GAME OVER\nYOU LOST" : "YOU WON!\nCONGRATULATIONS"));
    }

    public static void printTeamSelection(ArrayList<Player> team, Enemy enemy) {
        int counter = 0;
        System.out.println("Your Opponent is " + enemy.getName() + "!!!");
        System.out.println("Choose a Pokemon to battle with!");
        for (Player i : team) {
            counter++;
            System.out.println(counter + ". " + i.getName());
        }
    }

    public static int iChooseYou(ArrayList<Player> team) {
        int chosen;
        while (true) {
            System.out.print("Choose a Pokemon: ");
            Scanner input = new Scanner(System.in);
            chosen = input.nextInt() - 1;
            if (chosen + 1 > team.size() || chosen + 1 <= 0) {
                System.out.println("Please make a valid entry!!");
            } else {
                System.out.println("\n\n");
                System.out.println(team.get(chosen).getName() + "!! I choose you!");
                return chosen;
            }
        }
    }
}
// Thread.sleep(5000);