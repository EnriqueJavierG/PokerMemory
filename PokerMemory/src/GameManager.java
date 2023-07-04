/**
 * Main class for Memory game
 *
 * @author Michael Leonhard (Original Author)
 * @author Modified by Bienvenido Vélez (UPRM)
 * @version Sept 2017
 */

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JOptionPane;


public class GameManager 
{
	String difficulty;
	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		// make an instance of the main game class

		int playMore = 2;
		while (playMore != 1) {
			NewMemoryFrame instance = new NewMemoryFrame();
			instance.newGame("easy");
			instance.setScore(0);

			while(!instance.gameOver()) 
			{
				Thread.sleep(500);
			}
			playMore = JOptionPane.showConfirmDialog(null, "No more hands can be made. Play again?", "GAME OVER !!!", JOptionPane.YES_NO_OPTION);
			System.out.println(playMore+"");
		}
		System.exit(0);
	}
}
