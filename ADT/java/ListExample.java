import java.util.List;
import java.util.ArrayList;

public class ListExample {
    public static void main(String[] args) {
        // Create a list to store Strings
        List<String> fruits = new ArrayList<>();

        // Add elements to the list
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Cherry");
        fruits.add("Cherry");

        // Access elements by index
        System.out.println("First fruit: " + fruits.get(0)); // Output: Apple

        // Remove an element by index
        fruits.remove(1); // Removes Banana
        System.out.println("List after removal: " + fruits);

        // Iterate over the list
        System.out.println("Iterating through the list:");
        for (String fruit : fruits) {
            System.out.println(fruit);
        }

        List<Object> mixedList = new ArrayList<>();

        // Add elements of different types
        mixedList.add("Hello");   // String
        mixedList.add(42);        // Integer
        mixedList.add(3.14);      // Double
        mixedList.add(true);      // Boolean

        // Accessing and printing elements
        for (Object obj : mixedList) {
            System.out.println(obj);
        }
    }
    
}
