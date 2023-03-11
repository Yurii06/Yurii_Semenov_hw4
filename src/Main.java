import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int golemHealth = 500;
    public static int heal = 80;
    public static int[] heroesHealth = {270, 260, 250, 300, 500, 200};
    public static int[] heroesDamage = {10, 15, 20, 0, 5, 12};
    public static String[] heroesAttackType = {"Physihcal", "Magical", "Kinetic", "Medic", "Golem", "Lucky"};
    public static int roundNumber = 0;
    public static String message = "";

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            playRound();
        }
    }

    public static void playRound() {
        roundNumber++;
        message = "";
        chooseBossDefence();
        bossHits();
        heroesHit();
        golemHits();
        printStatistics();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    if (heroesAttackType[i] == "Lucky") {
                        Random random = new Random();
                        int evasionChance = random.nextInt(9) + 1;
                        if (evasionChance <= 3) {
                            System.out.println("Lucky dodged boss's attack!");
                            continue;
                        }
                    }
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            } else {
                System.out.println(heroesAttackType[i] + " Dead");
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coefficient = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10;
                    damage = damage * coefficient;
                    message = "Critical damage: " + damage;
                }

                if (heroesAttackType[i] == "Medic") {
                    for (int j = 0; j < heroesAttackType.length; j++) {
                        if (heroesAttackType[j] != "Medic") {
                            if (heroesHealth[j] > 0 && heroesHealth[j] <= 100) {

                                heroesHealth[j] += heal;
                                System.out.println("Medic cured: " + heroesAttackType[j] + "," + " Heal: " + heal + "," + " Сurrent Health: " + heroesAttackType[j] + " = :" + heroesHealth[j]);
                                break;
                            }
                        }

                    }

                }
                if (damage > 0) {
                    if (bossHealth - damage < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - damage;
                    }

                }
            }
        }
    }

    public static void golemHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && heroesAttackType[i] != "Golem") {
                int hit = bossDamage / 5;
                heroesHealth[i] -= hit;
                if(heroesHealth[i] < 0){
                 heroesHealth[i] = 0;
                }
            }
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead && golemHealth <= 0) {
            System.out.println("Boss won!!!");
        } else if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }

        return allHeroesDead;
    }

    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " ----------");
        /*String defence;
        if (bossDefence == null) {
            defence = "No defence";
        } else {
            defence = bossDefence;
        }*/
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage + " defence: " + (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {

            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
        System.out.println(message);
    }
}
