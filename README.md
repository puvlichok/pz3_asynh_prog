Fork/Join Framework Tasks

This project demonstrates the use of Java's Fork/Join Framework to solve two tasks:
- Finding an element in a 2D array where the value equals the sum of its indices.
- Finding all image files within a directory and opening the last one.

Task 1: Find an Element in a 2D Array

Description:
The program generates a 2D array of random integers and searches for an element where the value equals the sum of its row and column indices. 
The task is divided into smaller subtasks using the Fork/Join Framework for parallel processing.

Input:
The user provides the following inputs:
- Number of rows in the array.
- Number of columns in the array.
- Minimum value of array elements.
- Maximum value of array elements.

Output:
- The generated 2D array.
- The location of the element (if found) or a message indicating no such element exists.
- Execution time of the search process.

How to Run:
Run the program.
Enter the required parameters when prompted.

Task 2: Find Images in a Directory

Description:
The program recursively searches a user-specified directory for image files (e.g., .jpg, .jpeg, .png, .bmp, .gif). 
It counts the number of image files and opens the last image found (if any).

Input:
The user provides the path to the directory to be searched.

Output:
- The total number of image files found.
- Execution time of the search process.
- The last image file is opened in the default viewer.

How to Run:
Run the program.
Enter the path to the directory when prompted.

Technologies Used:
- Java
- Fork/Join Framework
- java.awt.Desktop for opening image files

How to Compile and Run
- Compile the program:
javac ForkJoinTasks.java
- Run the program:
java ForkJoinTasks

Example Usage
Task 1 Example
Input:
Enter number of rows: 5
Enter number of columns: 5
Enter minimum value: 1
Enter maximum value: 20

Output:
Generated Array:

5 10 15 4 8

6 12 14 3 9
...

Found element at (2, 2): 4

Execution time: 12 ms


Task 2 Example

Input:

Enter directory path: /path/to/images

Output:

Number of images found: 10

Execution time: 34 ms



Notes:

Ensure the directory provided for Task 2 is accessible and contains image files.

The program handles large 2D arrays and directories using parallelism for better performance.

License

This project is licensed under the MIT License.
