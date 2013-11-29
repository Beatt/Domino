import java.util.ArrayList;
import java.util.List;


public class DominoJugador {
	
	private List<DominoFicha> baraja;
	private String jugador;
	
	public DominoJugador(String jugador){
		
		this.jugador = jugador;
		baraja = new ArrayList<DominoFicha>();
	}

	public List<DominoFicha> getBaraja() {
		return baraja;
	}

	public void setBaraja(List<DominoFicha> baraja) {
		this.baraja = baraja;
	}

	public String getJugador() {
		return jugador;
	}

	public void setJugador(String jugador) {
		this.jugador = jugador;
	}
	
}
