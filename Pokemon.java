import java.util.*;
public class Pokemon{
    private String[] stats; // String array which will store all the stats of the pokemon
    private String name, type, resistance, weakness;// this will store the name of the pokemon 
                                                    // stores the type of the pokemon
                                                    // stores which type this pokemon is resistant to
                                                    // this will store which type this pokemon is weak against
    private int hp, numAttacks; // stores the HP of the pokemon
                                // this will store the number of attacks this pokemon has
    private int energy = 50; // this will store the energy of this pokemon
    private boolean retreat, stunned, disabled = false; // retreat will check whether the user wants to retreat their pokemon or not
                                                        // stunned will check if the pokemon is stunned
                                                        // disabled will check if the pokemon has been disabled
    private ArrayList<ArrayList<String>> attacks = new ArrayList<>(); // this will store the attacks as an ArrayList that contains String ArrayLists
    public final int maxHp; //this will store the max Hp this pokemon can have
    public Pokemon(String pokemonStat) { // this is the constructor, this will take the stats and attribute them to their seperate variables
        stats = pokemonStat.split(","); // this will split the String that contains the stats of the pokemon
        name = stats[0]; // this will be the name of the pokemon
        hp = Integer.valueOf(stats[1]); // gets the HP of the pokemon
        maxHp = Integer.valueOf(stats[1]); // gets the HP of the pokemon
        type = stats[2]; // gets the type of the pokemon
        resistance = stats[3]; // gets the resistance of the pokemon
        weakness = stats[4]; // gets the weakness of the pokemon
        numAttacks = Integer.valueOf(stats[5]); // this gets the number of attacks of the pokemon
        for (int i = 0; i < numAttacks; i++) { // This loop will run for as many attacks as the pokemon has
            ArrayList<String> tmpAttack = new ArrayList<>(); // this will store the attack
            for (int n = 6 + (4 * i); n < (6 + (4 * i) + 4); n++) { // this will go through the stats array
                tmpAttack.add(stats[n]); // adds the stats into the ArrayList
            }
            attacks.add(tmpAttack); //this will add the String ArrayList into a master ArrayList which will hold all of the attacks
        }
    }
    // The rest of these methods below are a series of "getters" and "setters" used to access private variables of the pokemon securely
    public boolean getStun(){return stunned;}
    public void setStun(boolean stun){stunned = stun;}
    public boolean getDisable(){return disabled;}
    public void setDisable(boolean disabled){this.disabled = disabled;}
    public boolean getRetreat(){return retreat;}
    public void setRetreat(boolean retreat){this.retreat = retreat;}
    public int getEnergy(){return energy;}
    public void setEnergy(int energy){this.energy = energy;}
    public String getName() {return name;}
    public int getHp() {return hp;}
    public void setHp(int hp) {this.hp = hp;}
    public String getType() {return type;}
    public String getResistance() {return resistance;}
    public String getWeakness() {return weakness;}
    public int getNumAttacks() {return numAttacks;}
    public ArrayList<ArrayList<String>> getAttacks() {return attacks;}
}
