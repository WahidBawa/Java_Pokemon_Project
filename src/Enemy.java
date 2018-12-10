import java.util.*;

public class Enemy extends Pokemon{
    public Enemy(String pokemonStat) {
        super(pokemonStat);
    }

    public void battle(Player enemy) {
        if (!this.getStun()){
            System.out.println(this.getName() + " is attacking " + enemy.getName());
            Random rand = new Random();
            int choice = rand.nextInt(this.getNumAttacks());
            System.out.println(this.getName() + " uses " + this.getAttacks().get(choice).get(0) + " on " + enemy.getName());
            String special = this.getAttacks().get(choice).get(3);
            int damage = Integer.valueOf(this.getAttacks().get(choice).get(2));
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
            enemy.setHp(newEnemyHp);
            System.out.print("\n\n");
        }else{
            System.out.println(this.getName() + " is stunned and can't attack!!");
        }
    }
}