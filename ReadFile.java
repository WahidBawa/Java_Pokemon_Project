import java.io.*;
import java.util.*;

public class ReadFile{ // this class will be used to read in a file
	private String lines = "";
	public ReadFile(String f) throws FileNotFoundException{ //this is the constructor
		File file = new File(f); // this will open a file using the passed in directory
        Scanner text = new Scanner(file); // creates a scanner object which will be used to read the lines of the file
        text.nextLine(); // this is to get rid of the first line as it is not needed
        while (text.hasNextLine()) { // while there is a line to be read in the file
            lines += text.nextLine() + "\n"; // this will store the lines in a String seperated by \n
        }
	}

	public String[] getArray(){ // this method will be used to get the file information as a String array
		return lines.split("\n"); // returns the array after splitting the lines String
	}
	
}