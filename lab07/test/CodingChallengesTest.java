import org.junit.Test;

import static com.google.common.truth.Truth.assertWithMessage;

public class CodingChallengesTest {

    @Test
    public void testMissingNumber() {
	// TODO
        int [] arr =  new int[] {0,1,2,3,5};
        assertWithMessage("actual is not expected").that(CodingChallenges.missingNumber(arr)).isEqualTo(4);
    }

    @Test
    public void testIsPermutation() {
	// TODO
        String s1 = new String("abb");
        String s2 = new String("bab");
        assertWithMessage("actual is not expected").that(CodingChallenges.isPermutation(s1, s2)).isEqualTo(true);
    }
}
