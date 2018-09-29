import java.util.Scanner;

public class Player extends Pokemon{
    public Player(String pokemonStat) {
        super(pokemonStat);
    }
    int choice = 0;
    public void Battle(Enemy enemy) {
        System.out.println(enemy.getName() + " HP: " + enemy.getHp());
        System.out.println(this.getName() + " HP: " + this.getHp());
        boolean endTurn = false;
        while(endTurn == false){
            choice = givePlayerOptions();
            if (choice == 1){
                choice = attack();
//            System.out.println("Attack Damages: " + this.getAttacks().get(choice - 1).get(2));
                if (choice != 0){
                    int damage = Integer.valueOf(this.getAttacks().get(choice - 1).get(2));
                    int newEnemyHp = enemy.getHp() - damage;
                    enemy.setHp(newEnemyHp);
//            System.out.println("ENEMY HP: " + enemy.getHp());
                    System.out.println();
                    endTurn = true;
                }
            }else if (choice == 3){
                System.out.println("You passed");
                endTurn = true;
            }else if (choice == 2){
                System.out.println(this.getName() + " is retreating!!");
                PokemonArena.printTeamSelection(PokemonArena.team, enemy);
                PokemonArena.iChooseYou(PokemonArena.team);
                endTurn = true;
            }
        }
    }
    public int givePlayerOptions(){
        String[] options = {"Attack", "Retreat", "Pass"};
        for (int i = 0; i < options.length; i++){
            System.out.println(i + 1 + ". " + options[i]);
        }
        System.out.println();
        System.out.print("Select an option: ");
        Scanner input = new Scanner(System.in);
        return input.nextInt();
    }
    public int attack(){
        System.out.println("0. Back");
        for (int i = 0; i < this.getAttacks().size(); i++){
            System.out.println(i + 1 + ". " + this.getAttacks().get(i).get(0) + " (Cost: " + this.getAttacks().get(i).get(1) + ")");
        }
        System.out.println();
        Scanner input = new Scanner(System.in);
        System.out.print("Select an attack: ");
        int attackSelection = input.nextInt();
        System.out.println();
        return attackSelection;
    }
}
