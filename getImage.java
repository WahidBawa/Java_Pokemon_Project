import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


/**
 * Generates the pokemons' images
 *
 * TEAM RASTERA 2018
 *
 * RELEASED UNDER:
 *
 * Attribution-NonCommercial-NoDerivatives 4.0 International (CC BY-NC-ND 4.0)
 */
public class getImage {
    public static void getImages() {
        BufferedImage pokemonImage;  // Creating a bufferedimage object to load the file later
        try {
            Scanner input = new Scanner(new File("allPokemon.txt"));
            String pokeName;
            int width;
            int height;
            Color color;
            URL url;  // Enables to connects to the internet and load a file
            HttpURLConnection connection;
            PrintWriter out;
            while (input.hasNextLine()) {

                pokeName = input.nextLine().split(",")[0].toLowerCase().replace(".", "").replace(" ", "-").replace("(", "-").replace(")", "").replace("'", "");
                System.out.println(pokeName);
                url = new URL("https://img.pokemondb.net/sprites/red-blue/normal/" + pokeName + ".png");
                connection = (HttpURLConnection) url.openConnection();  // Creating a new http connection
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");  // Adding a user-agent so the program wont be blocked
                pokemonImage = ImageIO.read(connection.getInputStream());  // Downloading the image
                out = new PrintWriter(new File("" + pokeName + ".txt"));
                width = pokemonImage.getWidth();
                height = pokemonImage.getHeight();
`
                for (int y = 0; y < height; y++) {  // Iterating through every pixel converting them to text
                    for (int x = 0; x < width; x++) {
                        color = new Color(pokemonImage.getRGB(x, y));
                        //System.out.print(color.getRed() + "|");
                        if (color.getRed() > 254) {
                            out.print("  ");
                        } else if (color.getRed() < 223 && color.getRed() > 177) {
                            out.print("░░");
                        } else if (color.getRed() < 178 && color.getRed() > 160){
                            out.println("▒▒");
                        } else if (color.getRed() < 161 && color.getRed() > 100) {
                            out.print("▓▓");
                        } else if (color.getRed() < 20) {
                            out.print("██");
                        }
                    }
                    out.println();
                }

                out.close();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
