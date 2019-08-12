import com.monologica.poetica.PoeticaController;
import com.monologica.poetica.items.PoeticaItem;
import com.monologica.poetica.modules.ItemGenerator;

public class Test {

    private PoeticaController controller;

    public Test() {
        try {
            controller = new PoeticaController();
        } catch (Exception e) {
            System.out.println("Poetica was not able to initialize. Disabling.");
            e.printStackTrace();
            System.exit(1);
        }

    }

    public void runTests() {
        System.out.println("=========================================================================================");
        System.out.println("=                                                                                       =");
        System.out.println("=                               POETICA TESTER                                          =");
        System.out.println("=                                                                                       =");
        System.out.println("=========================================================================================");
        System.out.println("Initialization completled. Starting tests...");
        System.out.println();
        testGenerateNames(0);
        testGenerateCustomItems(10, true);
        testGenerateItems(20, true);
        testGenerateMods(0, true);
    }

    public void testGenerateNames(int amount) {
        if(amount <= 0) {
            System.out.println("Skipping generating names test");
            return;
        }

        System.out.println("Generating names...");
        long start = System.currentTimeMillis();
        ItemGenerator gen = controller.getItemGenerator();
        for(int i = 0; i < amount; i++) {
            String name = gen.nextName();
            System.out.println(name);
        }

        long end = System.currentTimeMillis();
        long elapsed = end - start;
        double average = new Long(elapsed).doubleValue() / amount;
        double tickTime = (average / 50) * 100;
        System.out.println("Complete! Operation took " + elapsed + "ms total. Avg " + average + "ms per operation, or " + tickTime + "% of a tick each");
    }

    public void testGenerateItems(int amount, boolean verbose) {
        if(amount <= 0) {
            System.out.println("Skipping generating item test");
            return;
        }

        System.out.println("Generating items...");

        long start = System.currentTimeMillis();
        ItemGenerator gen = controller.getItemGenerator();
        for(int i = 0; i < amount; i++) {
            PoeticaItem item = gen.nextItem(10);
            if (verbose) {
                System.out.println("=========================================================================================");
                System.out.println(item);
                System.out.println("=========================================================================================");
            }
        }

        long end = System.currentTimeMillis();
        long elapsed = end - start;
        double average = new Long(elapsed).doubleValue() / amount;
        double tickTime = (average / 50) * 100;
        System.out.println("Complete! Operation took " + elapsed + "ms total. Avg " + average + "ms per operation, or " + tickTime + "% of a tick each");
    }

    public void testGenerateCustomItems(int amount, boolean verbose) {
        if(amount <= 0) {
            System.out.println("Skipping generating custom item test");
            return;
        }

        System.out.println("Generating custom items...");
        long start = System.currentTimeMillis();
        ItemGenerator gen = controller.getItemGenerator();
        for(int i = 0; i < amount; i++) {
            PoeticaItem item = gen.nextCustom(10);
            if (verbose) {
                System.out.println("=========================================================================================");
                System.out.println(item);
                System.out.println("=========================================================================================");
            }
        }

        long end = System.currentTimeMillis();
        long elapsed = end - start;
        double average = new Long(elapsed).doubleValue() / amount;
        double tickTime = (average / 50) * 100;
        System.out.println("Complete! Operation took " + elapsed + "ms total. Avg " + average + "ms per operation, or " + tickTime + "% of a tick each");
    }

    public void testGenerateMods(int amount, boolean verbose) {
        if(amount <= 0) {
            System.out.println("Skipping generating mods test");
            return;
        }

        // Do the test
        System.out.println("Generating mods...");
        long start = System.currentTimeMillis();
        ItemGenerator gen = controller.getItemGenerator();
        for (int i = 0; i < amount; i++) {
            PoeticaItem item = gen.nextMod();
            if (verbose) {
                System.out.println(item);
            }
        }
        long end = System.currentTimeMillis();
        long elapsed = end - start;
        double average = new Long(elapsed).doubleValue() / amount;
        double tickTime = (average / 50) * 100;
        System.out.println("Complete! Operation took " + elapsed + "ms total. Avg " + average + "ms per operation, or " + tickTime + "% of a tick each");
    }

    public static void main(String[] args) {
        Test t = new Test();
        t.runTests();
    }



}
