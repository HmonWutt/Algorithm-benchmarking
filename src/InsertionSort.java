public class InsertionSort {
     public static void iterativeSort(int[] input) {
         for (int i = 1; i < input.length; i++){
             int j = i;
             while (j > 0 && input[j-1] > input[j]){
                 int temp = input[j];
                 input[j] = input[j-1];
                 input[j-1] = temp;
                 j--;
             }

         }
     }
//     public static void recursiveSort(int[]input,int ind){
//         if (ind>= input.length) return;
//         int current = ind;
//         while (current > 0 && input[current-1]>input[current]){
//                 int temp = input[current];
//                 input[current] = input[current-1];
//                 input[current-1] = temp;
//                 current--;
//             }
//         InsertionSort.recursiveSort(input,++ind);
//     }

    public static void recursiveSort(int[] arr, int n) {
        // Base case
        if (n <= 1) return;

        // Sort first n-1 elements
        recursiveSort(arr, n - 1);

        // Insert last element at its correct position
        int last = arr[n - 1];
        int j = n - 2;
        while (j >= 0 && arr[j] > last) {
            arr[j + 1] = arr[j];
            j--;
        }
        arr[j + 1] = last;
    }
}
