public class QuickSort {

    /**
     * @param arr
     *
     * Sort the array arr using quicksort with the 3-scan partition algorithm.
     * The quicksort algorithm is as follows:
     * 1. Select a pivot, partition array in place around the pivot.
     * 2. Recursively call quicksort on each subsection of the modified array.
     */
    public static int[] sort(int[] arr) {
        quickSort(arr, 0, arr.length);
        return arr;
    }

    /**
     * @param arr
     * @param start
     * @param end
     *
     * Helper method for sort: runs quicksort algorithm on array from [start:end)
     */
    private static void quickSort(int[] arr, int start, int end) {
        // TODO: Implement quicksort
        if (end - start <= 1) {
            return;
        }

        int[] bounds = partition(arr, start, end);
        quickSort(arr, start, bounds[0]);
        quickSort(arr, bounds[1], end);
        
    }

    /**
     * @param arr
     * @param start
     * @param end
     *
     * Partition the array destructively following the 3-scan partitioning scheme.
     * You may assume that first item is always selected as the pivot.
     * 
     * Returns a length-2 int array of indices:
     * [end index of "less than" section, start index of "greater than" section]
     *
     * Most of the code for quicksort is in this function
     */
    private static int[] partition(int[] arr, int start, int end) {
        // TODO: Implement partition
        int pivot = arr[start];
        int lessEnd = start;
        int greaterStart = end;
        int i = start + 1;

        while (i < greaterStart) {
            if (arr[i] < pivot) {
                int temp = arr[i];
                arr[i] = arr[lessEnd];
                arr[lessEnd] = temp;
                lessEnd++;
                i++;
            } else if (arr[i] > pivot) {
                greaterStart--;
                int temp = arr[i];
                arr[i] = arr[greaterStart];
                arr[greaterStart] = temp;
            } else {
                i++;
            }
        }

        return new int[]{lessEnd, greaterStart};
    }
}   
