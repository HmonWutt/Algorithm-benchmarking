public class BinarySearch {

    public static boolean search(int[] arr, int target, int low, int high) {
        if (low > high) {
            return false;
        }
        int mid = low + (high - low) / 2;
        if (arr[mid] == target) {
            return true;
        }
        if (target < arr[mid]) {
            return search(arr, target, low, mid - 1);
        }
        else {
            return search(arr, target, mid + 1, high);
        }
    }
}
