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
        if (this.getType() == enemy.getWeakness()){
            damage *= 2;
            System.out.println(this.getName() + "'s attack is super effective!");
        }
        int newEnemyHp = enemy.getHp() - damage;
        System.out.println(this.getName() + " inflicts " + damage + " damage to " + enemy.getName() + "!!");
        enemy.setHp(newEnemyHp);
        System.out.println();
        System.out.println();
    }

}
