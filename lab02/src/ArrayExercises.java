import java.util.ArrayList;
import java.util.List;

public class ArrayExercises {

    /** Returns an array [1, 2, 3, 4, 5, 6] */
    public static int[] makeDice() {
        int[] myarr = new int[]{1,2,3,4,5,6};
        return myarr;
    }

    /** Returns the positive difference between the maximum element and minimum element of the given array.
     *  Assumes array is nonempty. */
    public static int findMinMax(int[] array) {
        int mininum = array[0];
        int maxnum = array[0];
        for (int index = 0; index < array.length; index += 1) {
            if(array[index] < mininum){
                mininum = array[index];
            } else if (array[index] > maxnum) {
                maxnum = array[index];
            }
        }
        return (maxnum - mininum);
    }

}
