import java.util.*;

public class Player extends Pokemon{ // this is a child class which is the child of the Pokemon class
    public Player(String pokemonStat) { // this calls the constructor from the Pokemon parent class
        super(pokemonStat);
    }
    int choice = 0;
    public void battle(Enemy enemy){ // this is the method for battle which is used in the battlePhase method in the PokemonArena class
        System.out.println(this.getName() + "\n\tHP: " + this.getHp() + "\n\tEnergy: " + this.getEnergy()); // this displays info about user pokemon
        System.out.println(enemy.getName() + "\n\tHP: " + enemy.getHp() + "\n\tEnergy: " + enemy.getEnergy()); // displays info about the enemy pokemon
        boolean endTurn = false; //boolean to check if the turn has ended or not
        while(!endTurn){
            choice = givePlayerOptions(); // this method will give a user some options and will let them choose one of those options
            if (choice == 1){ // if the user chooses "Attack"
                if (this.getStun()){ // this will check if the user is stunned, if so the user will be sent back to the options menu
                    System.out.println("\n" + this.getName() + " is stunned and can't attack!!\n");
                    continue;
                }
                while (true){ // this will let the user select an attack
                    System.out.println("\nEnergy: " + this.getEnergy() + "\n"); // displays the remaining energy of the user's pokemon
                    choice = attack(); // this will let the user choose which attack they want to perform
                    if (choice <= this.getNumAttacks() && choice >= 0){ // this will check if the user chose a valid option
                        break; // breaks the loop if the user picked a valid option
                    }
                    System.out.println("\nPlease select a valid option!\n"); // informs the user to pick a valid option
                }
                if (choice != 0){ // if the user does not want to go back
                    int cost = Integer.valueOf(this.getAttacks().get(choice - 1).get(1)); // this will get the cost of the attack
                    if (cost > this.getEnergy()){ // this will check if the user has enough energy to use the selected attack
                        System.out.println("You don't have enough energy to use this attack!!");
                        continue; // if the user doesn't have enough energy they will be sent back to the options menu
                    }

                    this.setEnergy(this.getEnergy() - cost); // this will subtract the cost of the attack from the pokemon's energy
                    String special = this.getAttacks().get(choice - 1).get(3); // this will get the special effect of the attack
                    int damage = Integer.valueOf(this.getAttacks().get(choice - 1).get(2)); // this will get the base damage of the attack
                    
                    System.out.println(this.getName() + " is attacking " + enemy.getName());
                    System.out.println(this.getName() + " uses " + this.getAttacks().get(choice - 1).get(0) + " on " + enemy.getName());
                    if (special.equals("wild card")){ // checks for wild card
                    Random wildcard_rand = new Random();
                    if (wildcard_rand.nextInt(2) == 0) { // decides whether the attack will fail or not
                        damage = 0;
                        System.out.println(this.getName() + "'s attack has failed!!");
                    }
                } 
                if (this.getType().equals(enemy.getWeakness()) && damage != 0){ // this will check if the user type is the enemy's weakness
                    damage *= 2; // will double the damage
                } else if (this.getType().equals(enemy.getResistance()) && damage != 0){ // checks if the enemy is resistant to user's pokemon
                    damage /= 2; // halves the damage
                }
                if (special.equals("wild storm")){
                    Random wildstorm_rand = new Random();
                    int newDamage = 0;
                    while(true){
                        if (wildstorm_rand.nextInt(2) == 0) { // if the random number is 0
                            newDamage += damage; // the damage will be 0 and the attack will fail
                        }else{
                            damage = newDamage; // the damage will be 0 and the attack will fail
                            break;
                        }
                    }
                    System.out.println(this.getName() + "'s attack has done a total of " + newDamage + " damage to " + enemy.getName());
                }else System.out.println(this.getName() + " inflicts " + damage + " damage to " + enemy.getName() + "!!");
                if (this.getType().equals(enemy.getWeakness()) && damage != 0){ // this will check if the user type is the enemy's weakness
                    System.out.println("This attack is super effective!!");
                } else if (this.getType().equals(enemy.getResistance()) && damage != 0){ // checks if the pokemon is resistant to enemy
                    System.out.println("This attack is not effective!!");
                }

                    if (special.equals("stun")){ // will check if the attack stuns
                        Random stun_rand = new Random();
                        if (stun_rand.nextInt(2) == 0) { // decides whether the attack will stun the enemy
                            enemy.setStun(true); // will set stun boolean to true
                            System.out.println("\n" + enemy.getName() + " has been stunned by " + this.getName());
                            System.out.println(enemy.getName() + " will not be able to attack!!");
                        }
                    }else if (special.equals("disable")){ // will check if the enemy is to be disabled
                        enemy.setDisable(true); // will set disabled to true
                        System.out.println("\n" + enemy.getName() + " has been disabled by " + this.getName());
                        System.out.println(enemy.getName() + "'s attacks will do less damage!!");
                    }else if (special.equals("recharge")){ // will check if the user's pokemon will recharge from their attack
                        this.setEnergy(this.getEnergy() + 20 > 50 ? 50 : this.getEnergy() + 20); // will add the extra energy without going over
                        System.out.println(this.getName() + " has recharged!!");
                    }
                    //final damage will be calculated, will check if the user's pokemon is disabled and will take the appropriate amount of damage away
                    int newEnemyHp = enemy.getHp() - ((this.getDisable() ? damage - 10 : damage) < 0 ? 0 : (this.getDisable() ? damage - 10 : damage));
                    enemy.setHp(newEnemyHp < 0 ? 0 : newEnemyHp); // this will set the enemy HP
                    System.out.println();
                    endTurn = true; // this will end the turn
                }else{ // if the user select 0 then the user will be sent back to the options menu
                    continue; 
                }
            }else if (choice == 3){ // if the user selects to pass then the turn will end
                System.out.println("You passed");
                endTurn = true;
            }else if (choice == 2){ // if the user decides to retreat
                System.out.println(this.getName() + " is retreating!!");
                this.setRetreat(true); // sets retreat boolean to true
                endTurn = true; // ends the turn
            }
        }
    }
    public int givePlayerOptions(){
        String[] options = {"Attack", "Retreat", "Pass"}; // These are the seperate options
        for (int i = 0; i < options.length; i++) System.out.println(i + 1 + ". " + options[i]); // this will print out all the options
        System.out.print("\nSelect an option: "); 
        Scanner input = new Scanner(System.in); // this will be used to get user input
        return input.nextInt(); // this will return what the user chose
    }
    public int attack(){
        System.out.println("0. Back"); // prints out the option for going back
        for (int i = 0; i < this.getAttacks().size(); i++){ // this will go through and print out the attacks and the cost of the attacks
            System.out.println(i + 1 + ". " + this.getAttacks().get(i).get(0) + " (Cost: " + this.getAttacks().get(i).get(1) + ")");
        }
        System.out.println();
        Scanner input = new Scanner(System.in); // Scanner object to get the choice of the user
        System.out.print("Select an attack: ");
        int attackSelection = input.nextInt(); // this will store the attack selection of the user
        System.out.println();
        return attackSelection; // returns the users choice
    }
}