import java.io.*;
import java.util.*;

public class PokemonArena {
    public static ArrayList team;

    public static void main(String[] args) throws FileNotFoundException {
        File pokemon_file = new File("src/data/pokemon.txt");
        Scanner text = new Scanner(pokemon_file);
        String pokemons = "";
        while (text.hasNextLine()) {
            pokemons += text.nextLine() + "\n";
        }
        String[] pokemon_stats = pokemons.split("\n");
        ArrayList team = select_team(pokemon_stats);
        System.arraycopy(pokemon_stats, 1, pokemon_stats, 0, pokemon_stats.length - 1);
        ArrayList<Player> finalTeam = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            finalTeam.add(new Player(pokemon_stats[(int) team.get(i)]));
        }
        battlePhase(finalTeam, team, pokemon_stats);
    }

    private static ArrayList select_team(String[] pokemon_choice) {
        ArrayList<Integer> team = new ArrayList<>();
        int counter = 0;
        String[] selection = new String[pokemon_choice.length - 1];
        for (int i = 0; i < pokemon_choice.length; i++) {
            if (counter > 0) {
                selection[i - 1] = pokemon_choice[i].split(",")[0];
            }
            counter++;
        }
        counter = 0;
        for (String i : selection) {
            counter++;
            System.out.println(counter + ": " + i);
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

    private static void battlePhase(ArrayList<Player> team, ArrayList<Integer> usedPokemon, String[] oldlistOfPokemon) {
        String[] listOfPokemon = new String[oldlistOfPokemon.length - 1];
        System.arraycopy(oldlistOfPokemon, 0, listOfPokemon, 0, listOfPokemon.length);
        Enemy enemy;
        while (true) {
            Random rand = new Random();
            int newEnemy = rand.nextInt(oldlistOfPokemon.length);
            if (!usedPokemon.contains(newEnemy)) {
                enemy = new Enemy(oldlistOfPokemon[newEnemy]);
//                System.out.println(newEnemy);
                break;
            }
        }
        System.out.println();
        printTeamSelection(team, enemy);
        System.out.println("It is the player's turn to fight!!");
        Player fighter = team.get(iChooseYou(team));
        while (usedPokemon.size() < oldlistOfPokemon.length && team.size() > 0) {
            fighter.Battle(enemy);
            if (enemy.getHp() > 0) {
                System.out.println();
                System.out.println("It is the enemy's turn to fight!!");
                enemy.Battle(fighter);
            } else {
                System.out.println(enemy.getName() + " has fainted!!");
                while (true) {
                    Random rand = new Random();
                    int newEnemy = rand.nextInt(oldlistOfPokemon.length);
                    if (!usedPokemon.contains(newEnemy)) {
                        enemy = new Enemy(oldlistOfPokemon[newEnemy]);
                        System.out.println(newEnemy);
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
        if (team.size() == 0) {
            System.out.println("GAME OVER");
            System.out.println("YOU LOST");
        } else {
            System.out.println("YOU WON!");
            System.out.println("CONGRATULATIONS");
        }
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
                System.out.println();
                System.out.println();
                System.out.println(team.get(chosen).getName() + "!! I choose you!");
                return chosen;
            }
        }
    }
}
// Thread.sleep(5000);