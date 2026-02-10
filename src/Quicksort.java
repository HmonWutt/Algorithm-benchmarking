import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Quicksort {
    private static Pivot pivotMethod;
    public static void run(int[]original, int methodInput, int pivotInput) {
        Method method = Method.values()[methodInput];
        pivotMethod = Pivot.values()[pivotInput];
        switch (method){
            case RECURSIVE -> {
                Quicksort.recursiveSort(original, 0, original.length-1 ,pivotMethod);
            }
            case ITERATIVE -> {
                Quicksort.iterativeSort(original,pivotMethod);
            }
        }

    }
    public static void iterativeSort(int[] original, Pivot pivotMethod){
        if (original.length < 2) return ;
        ArrayList<int[]> stack = new ArrayList<>();
        int[] first = {0,original.length-1} ;
        stack.add(first);
        while (!stack.isEmpty()){
            int[] current = stack.removeLast();
            int low = current[0];
            int high = current[1];
            if (low < high) {
                int pivot = Quicksort.pivotPicker(original, low, high,pivotMethod);
                int partitionInd = Quicksort.partition(original, low, high, pivot);
                int[] left = {low, partitionInd - 1};
                int[] right = {partitionInd + 1, high};
                stack.add(left);
                stack.add(right);
            }
        }

    }

    public static int medianOfThree(int[]original , int low, int mid, int high){
        if (original[low]> original[mid]) {
            int temp = original[mid];
            original[mid] = original[low];
            original[low] = temp;
        }
        if (original[mid]> original[high]){
            int temp = original[mid];
            original[mid] = original[high];
            original[high] = temp;
        }
        return mid;
    }

    public static int pivotPicker(int[]original,int low, int high, Pivot pivotMethod){
        switch (pivotMethod){
            case MEDIAN -> {
                int mid = low + (high-low)/2;
                return Quicksort.medianOfThree(original,low,mid,high);
            }
            case RANDOM -> {
                return ThreadLocalRandom.current().nextInt(low, high + 1);

            }
            default -> {
                return low;

            }

        }
    }
    private static int partition(int[] arr, int low, int high, int pivot) {
        int pivotValue = arr[pivot];
        swap(arr, pivot, low);

        int i = low + 1;
        int j = high;

        while (i <= j) {
            while (i <= j && arr[i] <= pivotValue) i++;
            while (i <= j && arr[j] > pivotValue) j--;

            if (i < j) {
                swap(arr, i, j);
            }
        }
        swap(arr, low, j);
        return j;
    }
    public static void recursiveSort(int[] arr, int low, int high, Pivot pivotMethod) {
        if (low >= high) return;
        int pivotIndex = Quicksort.pivotPicker(arr,low,high,pivotMethod);
        int pivotValue = arr[pivotIndex];
        swap(arr,pivotIndex,low);
        int start = low+1;
        int end = high;
        while (start <= end){
            while (start < high && arr[start] < pivotValue){
                start++;
            }
            while(end > low && arr[end] > pivotValue){
                end--;
            }
            if (start <end){
                swap(arr,start,end);
                start++;
                end--;
            }
            else{
                break;
            }
        }
        swap(arr,low,end);
        Quicksort.recursiveSort(arr,low,end-1, pivotMethod);
        Quicksort.recursiveSort(arr,end+1,high,pivotMethod);
    }
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
enum Pivot {
    FIRST, RANDOM, MEDIAN
        }
enum Method{
    RECURSIVE, ITERATIVE
}