import edu.princeton.cs.algs4.StdRandom;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BombMain {
    public static String shufflePassword(String s) {
        String code = "" + s.hashCode();
        StdRandom.setSeed(1337);
        char[] chars = code.toCharArray();
        StdRandom.shuffle(chars);

        return String.valueOf(chars);
    }

    public static int[] shufflePasswordArr(String s) {
        String code = "" + s.hashCode();
        StdRandom.setSeed(61833);
        char[] chars = code.toCharArray();
        StdRandom.shuffle(chars);

        int[] arr = new int[chars.length];
        for (int i = chars.length - 1; i >= 0; i--) {
            arr[i] = Integer.parseInt(String.valueOf(chars[i]));
        }

        return arr;
    }




    public static void main(String[] args) {
        int phase = 2;
        if (args.length > 0) {
            phase = Integer.parseInt(args[0]);
        }
        // TODO: Find the correct passwords to each phase using debugging techniques.
        //       Tip: if you don't know where a method is defined, hover your mouse over
        //              the method name, and CMD + click (or CTRL + click). This will
        //              take you to the method definition.


        Bomb b = new Bomb();
        if (phase >= 0) {
            b.phase0(shufflePassword("hello")); // Figure this out. I wonder where the phases are defined...
        }
        if (phase >= 1) {
            b.phase1(shufflePasswordArr("bye")); // Figure this out next
        }
        if (phase >= 2) {
            b.phase2("1 2 3");
        }
    }


}
