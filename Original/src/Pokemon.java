import java.util.ArrayList;
public class Pokemon {
    private String[] stats;
    private String name;
    private int hp;
    private String type;
    private String resistance;
    private String weakness;
    private int damage;
    private int numAttacks;

    private ArrayList<ArrayList<String>> attacks = new ArrayList<>();
    public Pokemon(String pokemonStat) {
        stats = pokemonStat.split(",");
        name = stats[0];
        hp = Integer.valueOf(stats[1]);
        type = stats[2];
        resistance = stats[3];
        weakness = stats[4];
        numAttacks = Integer.valueOf(stats[5]);
        for (int i = 0; i < numAttacks; i++) {
            ArrayList<String> tmpAttack = new ArrayList<>();
            for (int n = 6 + (4 * i); n < (6 + (4 * i) + 4); n++) {
                tmpAttack.add(stats[n]);
            }
            attacks.add(tmpAttack);
        }
    }
//    public String[] getStats() {
//        return stats;
//    }

    public String[] getStats() {
        return stats;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public String getType() {
        return type;
    }

    public String getResistance() {
        return resistance;
    }

    public String getWeakness() {
        return weakness;
    }

    public int getNumAttacks() {
        return numAttacks;
    }

    public ArrayList<ArrayList<String>> getAttacks() {
        return attacks;
    }

}
