import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class AutomatedSorter {

    ///     Universal Variables
    private static int size;
    private static int sizeCount = 0;
    private static int[] a;
    private static ArrayList<SortTime> sortTimes = new ArrayList<>();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random rand = new Random();
        String choice;
        int points;
        int max;
        int min;

        //// Intro
        System.out.println("Welcome to the Automated Sorting Program\n");
        do {
            do {            /// Setting Array Size
                System.out.println("Please enter the size of the array you would like to sort.");
                System.out.print("Array Size: ");

                try {
                    size = sc.nextInt();
                } catch (Exception e) {
                    size = 0;
                }
                if (size <= 0) System.out.println("**** Invalid Array Size ****\n" + "Enter an integer 1 or greater.");
            } while (size <= 0);

            sizeCount++;

            int[] unsorted = new int[size];

            do{
                System.out.println("Creating an array of " + size + " elements.");
                System.out.print("Highest Number: ");

                try {
                    max = sc.nextInt();
                }
                catch (Exception e){
                    System.out.print("Invalid max. Default setting.");
                    max = 100;
                }

                System.out.print("Lowest Number:");

                try {
                    min = sc.nextInt();
                }
                catch (Exception e){
                    System.out.print("Invalid min. Default setting.");
                    min = 0;
                }

                if (max == min)
                    System.out.println("Invalid max/min.");
                else if (max < min){
                    int temp = min;
                    min = max;
                    max = temp;
                }
            }while(max == min);

            //// Creating random array with #s from min to max
            for (int i = 0; i < size; i++)
                unsorted[i] = rand.nextInt((max-min)+1)+min;

            //// Setting sorting repetitions
            do {
                System.out.println("\nPlease enter the number of repetitions you would like.");
                System.out.print("Repetitions: ");

                try {
                    points = sc.nextInt();
                } catch (Exception e) {
                    points = 0;
                }
                if (points <= 0) System.out.println("**** Invalid Repetitions ****\n"
                        + "Enter an integer 1 or greater.");
            } while (points <= 0);

            runProgram(unsorted, points);

            System.out.println("Would you like to quit?");
            System.out.print("Y/N ");
            choice = sc.next();
        } while (!choice.equalsIgnoreCase("Y"));

        listResults();

        System.out.println("Thank you for using ArraySorter.");

    }

    //// Run Program
    private static void runProgram(int[] unsorted, int points) {

        long start;
        long stop;

        int[] sorted = new int[size];

        for (int i = 0; i < points; i++) {

            arrayCopy(unsorted, sorted); // BubbleSort
            start = System.nanoTime();
            bubbleSort(sorted);
            stop = System.nanoTime() - start;
            sortTimes.add(new SortTime(size));
            sortTimes.get(sortTimes.size() - 1).setTime(stop);
            sortTimes.get(sortTimes.size() - 1).setType("BubbleSort");

            arrayCopy(unsorted, sorted); // InsertionSort
            start = System.nanoTime();
            insertionSort(sorted);
            stop = System.nanoTime() - start;
            sortTimes.add(new SortTime(size));
            sortTimes.get(sortTimes.size() - 1).setTime(stop);
            sortTimes.get(sortTimes.size() - 1).setType("InsertionSort");

            arrayCopy(unsorted, sorted); // QuickSort
            start = System.nanoTime();
            quickSort(sorted);
            stop = System.nanoTime() - start;
            sortTimes.add(new SortTime(size));
            sortTimes.get(sortTimes.size() - 1).setTime(stop);
            sortTimes.get(sortTimes.size() - 1).setType("QuickSort");

            /*arrayCopy(unsorted, sorted); // Top Down MergeSort
            start = System.nanoTime();
            mergeSort(sorted);
            stop = System.nanoTime() - start;
            sortTimes.add(new SortTime((size)));
            sortTimes.get(sortTimes.size() - 1).setTime(stop);
            sortTimes.get(sortTimes.size() - 1).setType("MergeSort");*/
        }
    }

    // Copying the Array
    private static void arrayCopy(int[] array1, int[] array2) {
        System.arraycopy(array1, 0, array2, 0, array1.length);
    }

    // Sorting Methods
    private static void bubbleSort(int[] array) {
        a = array;
        for (int i = (array.length - 1); i >= 0; i--) {
            for (int j = 1; j <= i; j++) {
                if (array[j - 1] > array[j]) {
                    swap(j - 1, j);
                }
            }
        }
    }

    private static void insertionSort(int[] array) {
        a = array;

        for (int i = 1; i < array.length; i++) {
            int index = array[i];
            int j = i;

            while (j > 0 && array[j - 1] > index) {
                array[j] = array[j - 1];
                j--;
            }
            array[j] = index;
        }
    }

    private static void quickSort(int[] array) {
        a = array;
        quickSort(0, a.length - 1);
    }

    private static void quickSort(int low, int high) {
        if (low >= high) return;
        int p = partition(low, high);
        quickSort(low, p);
        quickSort(p + 1, high);
    }

    private static void mergeSort(int[] array) {
        sort(array, 0, array.length-1);
    }

    private static void merge(int[] array, int left, int mid, int right){
        // Array Sizes
        int l = mid - left + 1;
        int r = right - mid;

        //Creating Arrays
        int[] Left = new int[l];
        int[] Right = new int[r];

        System.arraycopy(array,l,Left,0,l);
        System.arraycopy(array,mid+1,Right,0,r);

        // Merge the two arrays

        int i = 0; int j = 0;

        int k = left;
        while (i < l && j < r){
            if (Left[i] <= Right[j]){
                array[k] = Left[i];
                i++;
            }
            else{
                array[k] = Right[j];
                j++;
            }
            k++;
        }
        while(i < l){
            array[k] = Left[i];
            i++;
            k++;
        }
        while(j < r){
            array[k] = Right[j];
            j++;
            k++;
        }
    }

    private static void sort(int[] array, int left, int right){
        if (left < right){
            int mid = (left + right)/2;         //// Find Mid Point

            sort(array, 1, mid);           //// Sort Left
            sort(array, mid+1, right);     //// Sort Right

            merge(array, left, mid, right);   //// Merge the Halves
        }
    }

    /////////////////////////////////////////////////////////////////////////
    //                                                                     //
    //                         General Methods                             //
    //                                                                     //
    /////////////////////////////////////////////////////////////////////////

    private static int partition(int low, int high) {
        int pivot = a[low];
        int i = low - 1;
        int j = high + 1;

        while (i < j) {
            for (i++; a[i] < pivot; i++) ;
            for (j--; a[j] > pivot; j--) ;
            if (i < j) swap(i, j);
        }
        return j;
    }

    private static void swap(int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    /////// Sort Time sortTimes /////////

    static class SortTime {
        private String type;
        private long time;
        private int size;

        SortTime(int size) {
            this.size = size;
        }

        private void setTime(long time) {
            this.time = time;
        }

        private void setType(String type) {
            this.type = type;
        }

        private long getTime() {
            return time;
        }

        private String getType() {
            return type;
        }

        private int getSize() {
            return size;
        }
    }

    private static void listResults(){
        listSort();
        printListAll();
        printResults(calcFastList(), calcSlowList(), calcAvgList());
    }

    private static void listSort(){
        for (int i = (sortTimes.size()- 1); i >= 0; i--) {
            for (int j = 1; j <= i; j++) {
                if (sortTimes.get(j-1).getTime() > sortTimes.get(j).getTime()) {
                    listSwap(j - 1, j);
                }
            }
        }
    }

    private static void listSwap(int i, int j){
        SortTime temp = new SortTime(sortTimes.get(i).getSize());
        temp.setTime(sortTimes.get(i).getTime());
        temp.setType(sortTimes.get(i).getType());
        sortTimes.set(i, sortTimes.get(j));
        sortTimes.set(j, temp);
    }

    private static SortTime[] calcFastList(){
        SortTime[] fastList = new SortTime[sizeCount];
        fastList[0] = sortTimes.get(0);
        int k;
        boolean match;

        if (sizeCount >= 2) {
            for (int i = 1; i < sizeCount; i++) {
                k = i;
                do {
                    match = false;
                    for (int j = i; j > 0; j--) {
                        if (fastList[j - 1].getSize() == sortTimes.get(k).getSize()) {
                            match = true;
                            break;
                        }
                    }
                    if (!match)
                        fastList[i] = sortTimes.get(k);
                    else
                        k++;
                } while (match);
            }
        }
        return fastList;
    }

    private static SortTime[] calcSlowList(){
        SortTime[] slowList = new SortTime[sizeCount];
        slowList[0] = sortTimes.get(sortTimes.size()-1);
        int k;
        boolean match;

        if (sizeCount >= 2) {
            for (int i = 1; i < sizeCount; i++) {
                k = sortTimes.size()-(i+1);
                do{
                    match = false;
                for (int j = i; j > 0; j--){
                     if (slowList[j-1].getSize() == sortTimes.get(k).getSize()) {
                         match = true;
                         break;
                     }
                }
                    if (!match)
                        slowList[i] = sortTimes.get(k);
                     else
                         k--;
                }while(match);
            }
        }
        return slowList;
    }

    private static SortTime[] calcAvgList(){
        SortTime[] avgList = new SortTime[sizeCount];
        avgList[0] = sortTimes.get(0);
        long sum;
        int k;
        boolean match;

        for (int i = 0; i < sizeCount; i++){
            sum = 0;
            k = 0;

            if (sizeCount > 2){
                for (SortTime S : sortTimes){
                    match = false;
                    for (int j = i; j > 0; j--) {       //// Check for a match
                        if (avgList[j-1].getSize() == S.getSize()){
                            match = true;
                            break;
                        }
                    }
                    if (!match && k == 0) {
                        avgList[i] = S;
                        sum += S.getTime();
                        k++;
                    }
                    else if (!match){
                        sum += S.getTime();
                        k++;
                    }
                }
                avgList[i].setTime(sum/k);
            }
        }
        return avgList;
    }

    private static void printResults(SortTime[] fast, SortTime[] slow, SortTime[] avg) {

        ///// Printing Fastest Times
        if (sizeCount < 2)
            System.out.println("\nFastest Time");
        else
            System.out.println("\nFastest Times");

        for (SortTime f : fast) {
            System.out.println(f.getTime() + " nanoseconds\t\t"
                    + "Using " + f.getType() + "\t\t"
                    + f.getSize() + " Elements");
        }

        ///// Printing Slowest Times
        if (sizeCount < 2)
            System.out.println("Slowest Time");
        else
            System.out.println("Slowest Times");

        for (SortTime s : slow){
            System.out.println(s.getTime() + " nanoseconds\t\t"
                    + "Using " + s.getType() +"\t\t"
                    + s.getSize() + " Elements");
        }

        ////// Printing Average Times
        if (sizeCount > 2)
            System.out.println("Average Times");
        else
            System.out.println("Average Time");

        for (SortTime a : avg){
            System.out.println(a.getTime() + " nanoseconds\t\t"
                    + a.getSize() + " Elements");
        }
    }

    private static void printListAll(){
        for (SortTime a : sortTimes)
            System.out.println("Time: " + a.getTime() + " nanoseconds"
            + "\t\tMethod: " + a.getType()
            + "\t\tSize: " + a.getSize());
    }
}
