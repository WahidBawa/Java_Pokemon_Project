import java.util.*;

public class Player extends Pokemon{
    public Player(String pokemonStat) {
        super(pokemonStat);
    }
    int choice = 0;
    public void battle(Enemy enemy){
        System.out.println(this.getName() + "\n\tHP: " + this.getHp() + "\n\tEnergy: " + this.getEnergy());
        System.out.println(enemy.getName() + "\n\tHP: " + enemy.getHp());
        boolean endTurn = false;
        while(!endTurn){
            choice = givePlayerOptions();
            if (choice == 1){
                if (this.getStun()){
                    System.out.println("\n" + this.getName() + " is stunned and can't attack!!\n");
                    continue;
                }
                while (true){
                    System.out.println("\nEnergy: " + this.getEnergy() + "\n");
                    choice = attack();
                    if (choice <= this.getNumAttacks() && choice >= 0){
                        break;
                    }
                    System.out.println("\nPlease select a valid option!\n");
                }
                if (choice != 0){
                    int cost = Integer.valueOf(this.getAttacks().get(choice - 1).get(1));
                    if (cost > this.getEnergy()){
                        System.out.println("You don't have enough energy to use this attack!!");
                        continue;
                    }

                    this.setEnergy(this.getEnergy() - cost);
                    String special = this.getAttacks().get(choice - 1).get(3);
                    int damage = Integer.valueOf(this.getAttacks().get(choice - 1).get(2));
                    
                    System.out.println(this.getName() + " is attacking " + enemy.getName());
                    System.out.println(this.getName() + " uses " + this.getAttacks().get(choice - 1).get(0) + " on " + enemy.getName());
                    if (special.equals("wild card")){
                        Random wildcard_rand = new Random();
                        if (wildcard_rand.nextInt(2) == 0) {
                            damage = 0;
                            System.out.println(this.getName() + "'s attack has failed!!");
                        }
                    }
                    
                    if (this.getType().equals(enemy.getWeakness())){
                        for (int i = 0; i < 10; i++) System.out.println("This attack will be super effective!!");
                        damage *= 2;
                    } else if (this.getType().equals(enemy.getResistance())){
                        for (int i = 0; i < 10; i++) System.out.println("This attack will not be effective!!");
                        damage /= 2;
                    }
                    System.out.println(this.getName() + " inflicts " + damage + " damage to " + enemy.getName() + "!!");
                    

                    if (special.equals("stun")){
                        Random stun_rand = new Random();
                        if (stun_rand.nextInt(2) == 0) {
                            enemy.setStun(true);
                            System.out.println("\n" + enemy.getName() + " has been stunned by " + this.getName());
                            System.out.println(enemy.getName() + " will not be able to attack!!");
                        }
                    }else if (special.equals("disable")){
                        enemy.setDisable(true);
                        System.out.println("\n" + enemy.getName() + " has been disabled by " + this.getName());
                        System.out.println(enemy.getName() + "'s attacks will do less damage!!");
                    }else if (special.equals("recharge")){
                        this.setEnergy(this.getEnergy() + 20 > 50 ? 50 : this.getEnergy() + 20);
                        System.out.println(this.getName() + " has recharged!!");
                    }

                    int newEnemyHp = enemy.getHp() - ((this.getDisable() ? damage - 10 : damage) < 0 ? 0 : (this.getDisable() ? damage - 10 : damage));
                    enemy.setHp(newEnemyHp < 0 ? 0 : newEnemyHp);
                    System.out.println();
                    endTurn = true;
                }else{
                    continue;
                }
            }else if (choice == 3){
                System.out.println("You passed");
                endTurn = true;
            }else if (choice == 2){
                System.out.println(this.getName() + " is retreating!!");
                this.setRetreat(true);
                endTurn = true;
            }
        }
    }
    public int givePlayerOptions(){
        String[] options = {"Attack", "Retreat", "Pass"};
        for (int i = 0; i < options.length; i++) System.out.println(i + 1 + ". " + options[i]);
        System.out.print("\nSelect an option: ");
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