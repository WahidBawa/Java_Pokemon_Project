import java.util.Random;

public class Enemy extends Pokemon{
    public Enemy(String pokemonStat) {
        super(pokemonStat);
    }

    public void Battle(Player enemy) {
        System.out.println(this.getName() + " is attacking " + enemy.getName());
        Random rand = new Random();
        int choice = rand.nextInt(this.getNumAttacks());
        System.out.println(this.getName() + " uses " + this.getAttacks().get(choice).get(0) + " on " + enemy.getName());
        int damage = Integer.valueOf(this.getAttacks().get(choice).get(2));
        int newEnemyHp = enemy.getHp() - damage;
        System.out.println(this.getName() + " inflicts " + damage + " damage to " + enemy.getName() + "!!");
        enemy.setHp(newEnemyHp);
        System.out.println();
        System.out.println();
    }

}
