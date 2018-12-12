import java.util.*;

public class Enemy extends Pokemon{ // this is a child class of the Pokemon parent class
    public Enemy(String pokemonStat) { // this calls the constructor from the Pokemon parent class
        super(pokemonStat);
    }

    public void battle(Player enemy) { // battle method used in the PokemonArena class
        int lowEnergyCounter = 0;
        for (int i = 0; i < this.getNumAttacks(); i++) { //this will check if the enemy has enough energy to attack
            if(Integer.valueOf(this.getAttacks().get(i).get(1)) > this.getEnergy()) lowEnergyCounter++;
        }
        if (lowEnergyCounter < this.getNumAttacks()){
            if (!this.getStun()){ // this will check if the enemy is stunned
                System.out.println(this.getName() + " is attacking " + enemy.getName());
                Random rand = new Random();
                int choice;
                int cost;
                while (true){ // this will choose a random attack and will check if it is valid
                    choice = rand.nextInt(this.getNumAttacks()); // will select a random attack for the enemy
                    cost = Integer.valueOf(this.getAttacks().get(choice).get(1)); // gets the cost of the attack
                    if (cost <= this.getEnergy()) break;
                    
                }
                System.out.println(this.getName() + " uses " + this.getAttacks().get(choice).get(0) + " on " + enemy.getName());
                int damage = Integer.valueOf(this.getAttacks().get(choice).get(2)); // gets the base damage
                String special = this.getAttacks().get(choice).get(3); // gets the special
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
                            System.out.println(this.getName() + "'s attack has done a total of " + newDamage + " damage to " + enemy.getName());
                        }else{
                            damage = newDamage; // the damage will be 0 and the attack will fail
                            break;
                        }
                    }
                }else System.out.println(this.getName() + " inflicts " + damage + " damage to " + enemy.getName() + "!!");
                if (this.getType().equals(enemy.getWeakness()) && damage != 0){ // this will check if the user type is the enemy's weakness
                    System.out.println("This attack is super effective!!");
                } else if (this.getType().equals(enemy.getResistance()) && damage != 0){ // checks if the enemy is resistant to user's pokemon
                    System.out.println("This attack is not effective!!");
                }
                this.setEnergy(this.getEnergy() - cost);
                if (special.equals("stun")){ // will check if the attack stuns
                    Random stun_rand = new Random();
                    if (stun_rand.nextInt(2) == 0) { // decides whether the attack will stun the enemy
                        enemy.setStun(true); // will set stun boolean to true
                        System.out.println("\n" + enemy.getName() + " has been stunned by " + this.getName());
                        System.out.println(enemy.getName() + " will not be able to attack!!");
                    }
                }else if (special.equals("disable")){ // will check if the user's pokemon is to be disabled
                    enemy.setDisable(true); // will set disabled to true
                    System.out.println("\n" + enemy.getName() + " has been disabled by " + this.getName());
                    System.out.println(enemy.getName() + "'s attacks will do less damage!!");
                }else if (special.equals("recharge")){ // will check if the enemy will recharge from their attack
                    this.setEnergy(this.getEnergy() + 20 > 50 ? 50 : this.getEnergy() + 20); // will add the extra energy without going over
                    System.out.println(this.getName() + " has recharged!!");
                }
                
                //final damage will be calculated, will check if the user's pokemon is disabled and will take the appropriate amount of damage away
                int newEnemyHp = enemy.getHp() - ((this.getDisable() ? damage - 10 : damage) < 0 ? 0 : (this.getDisable() ? damage - 10 : damage));
                enemy.setHp(newEnemyHp); // this will set the HP of the player pokemon
                System.out.print("\n\n");
            }else{ // if the enemy is stunned then the enemy will not attack
                System.out.println(this.getName() + " is stunned and can't attack!!");
            }
        }else{ // this will be the message shown if the enemy can't attack due to low energy
            System.out.println(this.getName() + " doesn't have enough energy to attack!\n");
        }
    }
}