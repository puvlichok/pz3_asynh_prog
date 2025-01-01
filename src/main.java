import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;
import java.awt.Desktop;

public class main {

    // Task 1: Find element where value equals sum of indices
    static class FindElementTask extends RecursiveTask<Boolean> {
        private final int[][] array;
        private final int startRow, endRow;

        public FindElementTask(int[][] array, int startRow, int endRow) {
            this.array = array;
            this.startRow = startRow;
            this.endRow = endRow;
        }

        @Override
        protected Boolean compute() {
            if (endRow - startRow <= 10) { // Base case: small enough to process sequentially
                for (int i = startRow; i < endRow; i++) {
                    for (int j = 0; j < array[i].length; j++) {
                        if (array[i][j] == i + j) {
                            System.out.println("Found element at (" + i + ", " + j + "): " + array[i][j]);
                            return true;
                        }
                    }
                }
                return false;
            } else { // Split task
                int midRow = (startRow + endRow) / 2;
                FindElementTask task1 = new FindElementTask(array, startRow, midRow);
                FindElementTask task2 = new FindElementTask(array, midRow, endRow);
                task1.fork();
                boolean result2 = task2.compute();
                boolean result1 = task1.join();
                return result1 || result2;
            }
        }
    }

    public static void task1() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of rows: ");
        int rows = scanner.nextInt();

        System.out.print("Enter number of columns: ");
        int cols = scanner.nextInt();

        System.out.print("Enter minimum value: ");
        int minValue = scanner.nextInt();

        System.out.print("Enter maximum value: ");
        int maxValue = scanner.nextInt();

        Random random = new Random();
        int[][] array = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                array[i][j] = random.nextInt(maxValue - minValue + 1) + minValue;
            }
        }

        System.out.println("Generated Array:");
        for (int[] row : array) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }

        ForkJoinPool pool = new ForkJoinPool();
        long startTime = System.currentTimeMillis();
        boolean found = pool.invoke(new FindElementTask(array, 0, rows));
        long endTime = System.currentTimeMillis();

        if (!found) {
            System.out.println("No such element found.");
        }
        System.out.println("Execution time: " + (endTime - startTime) + " ms");
    }

    // Task 2: Find images in a directory
    static class FindImagesTask extends RecursiveTask<Integer> {
        private final File directory;

        public FindImagesTask(File directory) {
            this.directory = directory;
        }

        @Override
        protected Integer compute() {
            File[] files = directory.listFiles();
            if (files == null) return 0;

            int count = 0;
            for (File file : files) {
                if (file.isDirectory()) {
                    FindImagesTask subTask = new FindImagesTask(file);
                    subTask.fork();
                    count += subTask.join();
                } else if (isImage(file)) {
                    count++;
                }
            }
            return count;
        }

        private boolean isImage(File file) {
            String[] imageExtensions = {".jpg", ".jpeg", ".png", ".bmp", ".gif"};
            String fileName = file.getName().toLowerCase();
            for (String ext : imageExtensions) {
                if (fileName.endsWith(ext)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static void task2(File directory) {
        if (!directory.isDirectory()) {
            System.out.println("Invalid directory.");
            return;
        }

        ForkJoinPool pool = new ForkJoinPool();
        long startTime = System.currentTimeMillis();
        int imageCount = pool.invoke(new FindImagesTask(directory));
        long endTime = System.currentTimeMillis();

        System.out.println("Number of images found: " + imageCount);
        System.out.println("Execution time: " + (endTime - startTime) + " ms");

        if (imageCount > 0) {
            try {
                File lastImage = getLastImage(directory);
                if (lastImage != null) {
                    Desktop.getDesktop().open(lastImage);
                }
            } catch (IOException e) {
                System.out.println("Error opening file: " + e.getMessage());
            }
        }
    }

    private static File getLastImage(File directory) {
        File[] files = directory.listFiles();
        if (files == null) return null;

        File lastImage = null;
        for (File file : files) {
            if (file.isDirectory()) {
                File subLastImage = getLastImage(file);
                if (subLastImage != null) {
                    lastImage = subLastImage;
                }
            } else if (isImage(file)) {
                lastImage = file;
            }
        }
        return lastImage;
    }

    private static boolean isImage(File file) {
        String[] imageExtensions = {".jpg", ".jpeg", ".png", ".bmp", ".gif"};
        String fileName = file.getName().toLowerCase();
        for (String ext : imageExtensions) {
            if (fileName.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Task 1
        System.out.println("Task 1: Finding element where value equals sum of indices.");
        task1();

        // Task 2
        System.out.println("\nTask 2: Finding images in a directory.");
        System.out.print("Enter directory path: ");
        String path = scanner.nextLine();
        File directory = new File(path);
        task2(directory);
    }
}
