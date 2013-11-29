import java.util.Comparator;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class DominoFicha extends JLabel{
	
	private String valor;
	private ImageIcon destapada;
	
	public DominoFicha(ImageIcon destapada, String valor) {
		this.destapada = destapada;
		this.valor = valor;
		setIcon(destapada);
		setSize(60, 30);
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public ImageIcon getDestapada() {
		return destapada;
	}

	public void setDestapada(ImageIcon destapada) {
		this.destapada = destapada;
	}
}
