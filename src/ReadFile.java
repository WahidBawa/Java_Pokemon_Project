import java.io.*;
import java.util.*;

public class ReadFile{
	private String lines = "";
	public ReadFile(String f) throws FileNotFoundException{
		File file = new File(f);
        Scanner text = new Scanner(file);
        text.nextLine();
        while (text.hasNextLine()) {
            lines += text.nextLine() + "\n";
        }
	}

	public String[] getArray(){
		return lines.split("\n");
	}
	
}