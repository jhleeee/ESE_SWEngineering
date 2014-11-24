
import javax.swing.SwingUtilities;


public class Game {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUIRunner run = new GUIRunner();
				run.setVisible();
			}
		});
	}
}
