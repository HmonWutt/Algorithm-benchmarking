import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        int[] input = Main.readFromFile("input.txt");
        char choice = 'z';
        do {
            Main.printMenu();
            choice = scanner.next().charAt(0);
            if (choice == 'x') return;
            if (choice =='a') {
                System.out.println("Note: Recursive insertion sort will only run up to 10,0000");
                Main.runAll(input);
            }
            else {
                try {
                    System.out.println("Set array size: ");
                    int arraySize = scanner.nextInt();
                    int[] copy = Arrays.copyOf(input, arraySize);
                    System.out.println("""
                            \nType 
                            i to run insertion sort
                            q to run quick sort
                            b to run binary search
                            x to exit
                            """);
                    char algo = scanner.next().charAt(0);
                    if (algo == 'x') return;

                    String combo = String.valueOf(choice) + String.valueOf(algo);
                    switch (combo) {
                        case "ri" -> Main.runRecursiveInsertionSort(copy);
                        case "ii" -> Main.runIterativeInsertionSort(copy);
                        case "rq" -> {
                            System.out.println("""
                                    \ntype 1 to choose first element as pivot
                                    type 2 for random pivot
                                    type 3 for median of 3 pivot
                                    type x to exit
                                    """);
                            try {
                                char pivotInput = scanner.next().charAt(0);
                                if (pivotInput == 'x') return;
                                int pivot = Integer.parseInt(String.valueOf(pivotInput));
                                Main.runRecursiveQuickSort(copy, 0, pivot - 1);
                            } catch (Exception e) {
                                System.out.println("Invalid option");
                            }

                        }
                        case "iq" -> {
                            System.out.println("""
                                    \ntype 1 to choose first element as pivot
                                    type 2 for random pivot
                                    type 3 for median of 3 pivot
                                    type x to exit.
                                    """);
                            int pivot = scanner.nextInt();
                            Main.runIterativeQuickSort(copy, 1, pivot - 1);
                        }
                        case "rb" -> Main.runBinarySearch(copy);
                        default -> System.out.println("""
                                \nPlease choose a valid option or type x to exit""");
                    }
                } catch (Exception e) {
                    System.out.println("Input invalid.");
                }
            }

        } while (choice != 'x');



    }
    public static void runAll(int[] input){
        Main.loop(arr->InsertionSort.recursiveSort(arr,arr.length-1),input, 100_000,5, "recursiveInsertionSort");
        Main.loop(InsertionSort::iterativeSort,input,1_000_000,5, "iterativeInsertionSort");
        Main.loop(arr->Quicksort.run(arr,0,0),input,1_000_000,5,"recursiveQuicksort_median");
        Main.loop(arr->Quicksort.run(arr,0,1),input,1_000_000,5,"recursiveQuickSort_random");
        Main.loop(arr->Quicksort.run(arr,0,2),input,1_000_000,5,"recursiveQuickSort_first");
        Main.loop(arr->Quicksort.run(arr,1,0),input,1_000_000,5,"iterativeQuickSort_median");
        Main.loop(arr->Quicksort.run(arr,1,1),input,1_000_000,5,"iterativeQuickSort_random");
        Main.loop(arr->Quicksort.run(arr,1,2),input,1_000_000,5,"iterativeQuickSort_first");
        Main.loop(arr->
        {
            int target = ThreadLocalRandom.current().nextInt(-100, 100 + 1);
            boolean found = BinarySearch.search(arr,target,0,arr.length-1);
        }, input,1_000_000,5,"binarySearch");
    }
    public static void runBinarySearch(int[]copy) throws InterruptedException{
        int target = ThreadLocalRandom.current().nextInt(-100, 100 + 1);
        Quicksort.run(copy,0,2);
        System.out.println("\nBinary searching target element "+target+" in array size of "+copy.length+"...");
        Main.timeTaken(arr -> {
            BinarySearch.search(copy, target, 0, copy.length - 1);

        }, copy.length);
        Thread.sleep(1000);
    }
    public static void runIterativeInsertionSort(int[]copy)  throws InterruptedException {
        System.out.println("\nSorting an array size of "+copy.length+" using iterative insertion sort...");
        Main.timeTaken(arr -> {
                InsertionSort.iterativeSort(copy);
            }, copy.length);
        Thread.sleep(1000);
    }
    public static void runRecursiveInsertionSort(int[]copy) throws InterruptedException{
        if (copy.length > 100_000){
            System.out.println("\nThis algorithm will not run for bigger arrays. The size has been reduced to 100_000");
            int [] reduced = Arrays.copyOf(copy,100_000);
            System.out.println("Sorting an array size of "+reduced.length+" using recursive insertion sort...");
            Main.timeTaken(arr->{
                InsertionSort.recursiveSort(reduced, reduced.length);
            }, copy.length);
            Thread.sleep(1000);
            return;
        }
        System.out.println("\nSorting an array size of "+copy.length+" using recursive insertion sort...");
        Main.timeTaken(arr->{
               InsertionSort.recursiveSort(copy, copy.length);
           }, copy.length);
        Thread.sleep(1000);
    }

    public static void runIterativeQuickSort(int[]copy, int methodInput, int pivotInput) throws InterruptedException {
        String pivot = "";
        if (pivotInput == 0) pivot = "first element as pivot";
        else if (pivotInput == 1) pivot = "random pivot";
        else pivot = "median of three as pivot";
        System.out.println("\nSorting an array size of "+copy.length+"using iterative quick sort using "+pivot+"...");
        Main.timeTaken(arr->{
                Quicksort.run(copy,methodInput,pivotInput);
            },copy.length);
        Thread.sleep(1000);
    }
    public static void runRecursiveQuickSort(int[]copy, int methodInput, int pivotInput) throws InterruptedException {
        String pivot = "";
        if (pivotInput == 0) pivot = "first element as pivot";
        else if (pivotInput == 1) pivot = "random pivot";
        else pivot = "median of three as pivot";
        System.out.println("\nSorting an array size of "+copy.length+"using recursive quick sort using "+ pivot+"...");
        Main.timeTaken(arr->{
                Quicksort.run(copy,methodInput,pivotInput);
            }, copy.length);
        Thread.sleep(1000);
    }
    public static void timeTaken(Consumer<int[]> callback, int length)  {
        long startTime = System.nanoTime();
        callback.accept(new int[0]);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime)  ; // measured in nano seconds as ms gives 0
        System.out.println("Time taken: " + duration+", array length: "+ length);
        Main.printDivider();
    }

    public static void loop(Consumer<int[]> callback, int[] input, int maxArraySize, int numInterations, String metaData) {
        long time = 0;
        for (int n = 10; n < maxArraySize+1; n *= 10) {
            for (int i = 0; i < numInterations; i++) {
                int[] copy = Arrays.copyOf(input, n);
                long startTime = System.nanoTime();
                callback.accept(copy);
                long endTime = System.nanoTime();
                long duration = endTime - startTime;
                time += duration;
            }
            long timeTaken = time/numInterations;
            System.out.println("Time taken: " + timeTaken );
            String content = metaData+","+ n+","+ timeTaken + "\n" ;
            Main.writeToFile(content);
        }
    }

    public static int[] readFromFile(String filepath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filepath));
        int[] input= new int[lines.size()];
        for (int i = 0; i < lines.size();i++){
            input[i]  = Integer.parseInt(lines.get(i));
        }
        return input;

    }
    public static void writeToFile(String content){
        try {
            Files.write(Paths.get("output.txt"), content.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void printMenu(){
        String content = """
                \nChoose your action. You will be prompted for array size.
                Type
                i to choose from iterative algorithms.
                r to choose from recursive algorithms.
                a to run all algorithms specified number of iterations.
                  You will be ask for the number of iterations.
                x to exit.
                """;
        System.out.println(content);
    }
    public static void printDivider(){
        System.out.println("\n==============================================================================================");
    }
}