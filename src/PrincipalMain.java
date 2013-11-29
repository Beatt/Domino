import javax.swing.SwingUtilities;

/*
 * Programa: PrincipalMain.java
 * Autor: Francisco...
 * Fecha: 20/11/13
 */
public class PrincipalMain {

	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
			 PrincipalDomino domino = new PrincipalDomino();
			}
		});
	}
}
