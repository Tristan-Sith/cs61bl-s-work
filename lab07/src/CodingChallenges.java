import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public class CodingChallenges {

    /**
     * Return the missing number from an array of length N containing all the
     * values from 0 to N except for one missing number.
     */
    public static int missingNumber(int[] values) {
        // TODO
        Set<Integer> seen = new HashSet<>();
        for (int i = 0; i < values.length + 1; i += 1) {
            seen.add(i);
        }

        for (int i = 0; i < values.length; i += 1) {
            seen.remove(values[i]);
        }

        return seen.stream().findFirst().get();
    }

    /**
     * Returns true if and only if s1 is a permutation of s2. s1 is a
     * permutation of s2 if it has the same number of each character as s2.
     */
    public static boolean isPermutation(String s1, String s2) {
        // TODO
        Map<Character, Integer> characterCounts1 = new HashMap<>();
        Map<Character, Integer> characterCounts2 = new HashMap<>();
        for (int i = 0; i < s1.length(); i += 1) {
            char c = s1.charAt(i);
            characterCounts1.merge(c, 1, Integer::sum);
        }
        for (int i = 0; i < s2.length(); i += 1) {
            char c = s2.charAt(i);
            characterCounts2.merge(c, 1, Integer::sum);
        }

        return characterCounts1.equals(characterCounts2);
    }
}
