
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PrincipalDomino implements Runnable, ActionListener{
	
	public List<DominoFicha> domino;
	
	public JFrame panelPrincipal;
	public JPanel[] panelJugadores = new JPanel[2];
	public JPanel  panelSopa, panelMesa;
	public JLabel fin;
	public JButton botonJugar;
	public DominoJugador[] jugadores = new DominoJugador[2];
	public static final int TOTALFICHASREPARTIDAS = 7;
	public int moverIx, moverIy, moverDx, moverDy;
	public int contadorDeTiros = 0;
	public Thread hilo;
	boolean suspendido;
	public StringBuilder fichaActual;
	public boolean primerTiro = true;
	public DominoFicha fichaLanzada;
	public GridBagConstraints constraints = new GridBagConstraints();
	public int juegoEntablado = 0;
	public int countIx, countIy, countDx, countDy;
	private boolean rotarImagenI;
	private boolean rotarImagenD;
	
	public PrincipalDomino() {
		
		contruirVentanaPrincipal();
		crearFichas();

		fichaActual = new StringBuilder("xx");
		
		jugadores[0] = new DominoJugador("Jugador A");
		jugadores[1] = new DominoJugador("Jugador B");
		
		repartirFichas(jugadores[0], panelJugadores[0]);
		repartirFichas(jugadores[1], panelJugadores[1]);
		
		hilo = new Thread(this);
		hilo.start();
	}
	
	//Crea la ventana principal.
	public void contruirVentanaPrincipal() {
		
		Container container = null;
		Color color = new Color(0);
		color = color.darkGray;
		
		//Frame 
		panelPrincipal = new JFrame("DOMINO");
		panelPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panelPrincipal.setSize(900, 1000);
		panelPrincipal.setLayout(null);
		container = panelPrincipal.getContentPane();
		
		//Panel Mesa
		panelMesa = new JPanel();
		panelMesa.setSize(700, 600);
		panelMesa.setLayout(null);
		panelMesa.setBackground(color);
		container.add(panelMesa);
		
		//Panel Sopa
		panelSopa = new JPanel();
		panelSopa.setLayout(new BoxLayout(panelSopa, BoxLayout.Y_AXIS));
		panelSopa.setSize(300, panelMesa.getHeight()-100);
		panelSopa.setLocation(panelMesa.getWidth(), 0);
		panelSopa.setBackground(color);
		container.add(panelSopa);
		
		//Panel Jugador a
		panelJugadores[0] = new JPanel();
		panelJugadores[0].setLayout(new FlowLayout(FlowLayout.LEFT));
		panelJugadores[0].setSize(panelMesa.getWidth()/2, 80);
		panelJugadores[0].setLocation(0, panelMesa.getHeight());
		panelJugadores[0].setBackground(color);
		container.add(panelJugadores[0]);
		
		//Panel jugador b
		panelJugadores[1] = new JPanel();
		panelJugadores[1].setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelJugadores[1].setSize(panelMesa.getWidth()/2, 80);
		panelJugadores[1].setLocation(panelJugadores[1].getWidth(), panelMesa.getHeight());
		panelJugadores[1].setBackground(color);
		container.add(panelJugadores[1]);
		
		//Boton jugar
		botonJugar = new JButton("Tirar");
		botonJugar.setLocation(760, 580);
		botonJugar.setSize(80, 20);
		botonJugar.addActionListener(this);
		container.add(botonJugar);
		
		//Label resultado
		fin = new JLabel();
		fin.setLocation(400, 200);
		fin.setBackground(color.GREEN);
		fin.setText("Resultado");
		fin.setSize(300, 200);
		panelMesa.add(fin);
		
		panelPrincipal.setLayout(null);
		panelPrincipal.setVisible(true);
	}
	
	//Crea fichas y las agrega al domino.
	public void crearFichas() {
		
		ImageIcon imagenIcon = null;
		domino = new ArrayList<DominoFicha>();
		
		/* 0 */
		imagenIcon = new ImageIcon("../imagenesDomino/00.png");
		domino.add(new DominoFicha(imagenIcon, "0/0"));
		panelSopa.add(domino.get(0));
		
		/* 1 */
		imagenIcon = new ImageIcon("../imagenesDomino/01.png");
		domino.add(new DominoFicha(imagenIcon, "0/1"));
		panelSopa.add(domino.get(1));
		
		imagenIcon = new ImageIcon("../imagenesDomino/11.png");
		domino.add(new DominoFicha(imagenIcon, "1/1"));
		panelSopa.add(domino.get(2));
		
		/* 2 */
		imagenIcon = new ImageIcon("../imagenesDomino/02.png");
		domino.add(new DominoFicha(imagenIcon, "0/2"));
		panelSopa.add(domino.get(3));
		
		imagenIcon = new ImageIcon("../imagenesDomino/12.png");
		domino.add(new DominoFicha(imagenIcon, "1/2"));
		panelSopa.add(domino.get(4));
		
		imagenIcon = new ImageIcon("../imagenesDomino/22.png");
		domino.add(new DominoFicha(imagenIcon, "2/2"));
		panelSopa.add(domino.get(5));
		
		/* 3 */
		imagenIcon = new ImageIcon("../imagenesDomino/03.png");
		domino.add(new DominoFicha(imagenIcon, "0/3"));
		panelSopa.add(domino.get(6));
		
		imagenIcon = new ImageIcon("../imagenesDomino/13.png");
		domino.add(new DominoFicha(imagenIcon, "1/3"));
		panelSopa.add(domino.get(7));
		
		imagenIcon = new ImageIcon("../imagenesDomino/23.png");
		domino.add(new DominoFicha(imagenIcon, "2/3"));
		panelSopa.add(domino.get(8));
		
		imagenIcon = new ImageIcon("../imagenesDomino/33.png");
		domino.add(new DominoFicha(imagenIcon, "3/3"));
		panelSopa.add(domino.get(9));
		
		/* 4 */
		imagenIcon = new ImageIcon("../imagenesDomino/04.png");
		domino.add(new DominoFicha(imagenIcon, "0/4"));
		panelSopa.add(domino.get(10));
		
		imagenIcon = new ImageIcon("../imagenesDomino/14.png");
		domino.add(new DominoFicha(imagenIcon, "1/4"));
		panelSopa.add(domino.get(11));
		
		imagenIcon = new ImageIcon("../imagenesDomino/24.png");
		domino.add(new DominoFicha(imagenIcon, "2/4"));
		panelSopa.add(domino.get(12));
		
		imagenIcon = new ImageIcon("../imagenesDomino/34.png");
		domino.add(new DominoFicha(imagenIcon, "3/4"));
		panelSopa.add(domino.get(13));
		
		imagenIcon = new ImageIcon("../imagenesDomino/44.png");
		domino.add(new DominoFicha(imagenIcon, "4/4"));
		panelSopa.add(domino.get(14));
		
		/* 5 */
		imagenIcon = new ImageIcon("../imagenesDomino/05.png");
		domino.add(new DominoFicha(imagenIcon, "0/5"));
		panelSopa.add(domino.get(15));
		
		imagenIcon = new ImageIcon("../imagenesDomino/15.png");
		domino.add(new DominoFicha(imagenIcon, "1/5"));
		panelSopa.add(domino.get(16));
		
		imagenIcon = new ImageIcon("../imagenesDomino/25.png");
		domino.add(new DominoFicha(imagenIcon, "2/5"));
		panelSopa.add(domino.get(17));
		
		imagenIcon = new ImageIcon("../imagenesDomino/35.png");
		domino.add(new DominoFicha(imagenIcon, "3/5"));
		panelSopa.add(domino.get(18));
		
		imagenIcon = new ImageIcon("../imagenesDomino/45.png");
		domino.add(new DominoFicha(imagenIcon, "4/5"));
		panelSopa.add(domino.get(19));
		
		imagenIcon = new ImageIcon("../imagenesDomino/55.png");
		domino.add(new DominoFicha(imagenIcon, "5/5"));
		panelSopa.add(domino.get(20));
		
		/* 6 */
		imagenIcon = new ImageIcon("../imagenesDomino/06.png");
		domino.add(new DominoFicha(imagenIcon, "0/6"));
		panelSopa.add(domino.get(21));
		
		imagenIcon = new ImageIcon("../imagenesDomino/16.png");
		domino.add(new DominoFicha(imagenIcon, "1/6"));
		panelSopa.add(domino.get(22));
		
		imagenIcon = new ImageIcon("../imagenesDomino/26.png");
		domino.add(new DominoFicha(imagenIcon, "2/6"));
		panelSopa.add(domino.get(23));
		
		imagenIcon = new ImageIcon("../imagenesDomino/36.png");
		domino.add(new DominoFicha(imagenIcon, "3/6"));
		panelSopa.add(domino.get(24));
		
		imagenIcon = new ImageIcon("../imagenesDomino/46.png");
		domino.add(new DominoFicha(imagenIcon, "4/6"));
		panelSopa.add(domino.get(25));
		
		imagenIcon = new ImageIcon("../imagenesDomino/56.png");
		domino.add(new DominoFicha(imagenIcon, "5/6"));
		panelSopa.add(domino.get(26));
		
		imagenIcon = new ImageIcon("../imagenesDomino/66.png");
		domino.add(new DominoFicha(imagenIcon, "6/6"));
		panelSopa.add(domino.get(27));
		
		panelSopa.updateUI();
	}
	
	//Repartir fichas a un jugador.
	public void repartirFichas(DominoJugador jugador, JPanel panelJugador) {
		
		int longitudDomino = 0, contadorFichas = 0;
		Random random = new Random();
		DominoFicha fichaRandom = null;
		ImageIcon imagenIcon = null;
		int numeroAleatorio = 0;
			
		while(contadorFichas < TOTALFICHASREPARTIDAS) {
			
			//Longitud del domino.
			longitudDomino = domino.size();
			
			//Un número aleatorio de 0 al 27
			numeroAleatorio = random.nextInt(28);
			
			if(longitudDomino > numeroAleatorio ) { 
				
				//Ficha del domino elejida aleatoriamente
				fichaRandom = domino.get(numeroAleatorio);
				
				//Si se encuentra en el domino, se le asigna al jugador.
				if(domino.contains(fichaRandom)) {
					
					//Guarda imageIcon de la ficha random.
					imagenIcon = fichaRandom.getDestapada();
					
					fichaRandom.setIcon(imagenIcon);
					
					//Se le asigna la ficha random a la baraja del jugador.
					jugador.getBaraja().add(fichaRandom);
					
					//Pone en el tablero del jugador la ficha random.
					panelJugador.add(fichaRandom);
					
					//Elimina ficha del panelSopa
					panelSopa.remove(fichaRandom);
					
					//Borra la ficha del domino.
					domino.remove(fichaRandom);
					
					contadorFichas++;
				}
			}
		}
	}

	
	//Pausar juego y actualizar paneles.
	public void pausarYActualizarPaneles() {
		while(!suspendido) {
			panelJugadores[0].updateUI();
			panelJugadores[1].updateUI();
			panelMesa.updateUI();
			panelSopa.updateUI();
		}
	}
	
	public RotatedIcon girarImagen(String mov) {
		
		CompoundIcon ci = new CompoundIcon(fichaLanzada.getDestapada());
		RotatedIcon imgGir = null;
		
		switch(mov) {
			case "UPSIDE_DOWN":
				imgGir = new RotatedIcon(ci, RotatedIcon.Rotate.UPSIDE_DOWN);
				break;
			case "ABOUT_CENTER": 
				imgGir = new RotatedIcon(ci, RotatedIcon.Rotate.ABOUT_CENTER);
				break;
			case "UP":
				fichaLanzada.setSize(30, 60);
				imgGir = new RotatedIcon(ci, RotatedIcon.Rotate.UP);
				break;
			case "DOWN": 
				fichaLanzada.setSize(30, 60);
				imgGir = new RotatedIcon(ci, RotatedIcon.Rotate.DOWN);
				break;
		}
		
		return imgGir;
	}
	
	//Da comienzo al juego.
	public void comenzarJuego(DominoJugador[] jugador, JPanel[] panelJugador, StringBuilder fichaActual, DominoFicha fichaLanzada) {
		
		int tiroSiguiente = 1;

		
		//Jugador a lanza primera ficha
		lanzarFicha(jugador[0], panelJugador[0]);
		
		do {
			
			//Despues de 6 tiros, verificara que el juego no se halla entablado.
			if(contadorDeTiros > 6){
				
				juegoEntablado = juegoEntablado(jugador[1], fichaActual, panelJugador[1]);
				
				if(juegoEntablado == 1) juegoEntablado = 1;
				
				else {
					
					if(tiroSiguiente == 1) tiroSiguiente = tiroDeJugadores(jugador[1], panelJugador[1]);
					
					else {
						
						juegoEntablado = juegoEntablado(jugador[0], fichaActual,  panelJugador[0]);
						
						if(juegoEntablado == 1) juegoEntablado = 1;
						
						else tiroSiguiente = tiroDeJugadores(jugador[0], panelJugador[0]);
					}
				}
				
			}else {
				
				if(tiroSiguiente == 1) tiroSiguiente = tiroDeJugadores(jugador[1], panelJugador[1]);
				
				else tiroSiguiente = tiroDeJugadores(jugador[0], panelJugador[0]);
				
			}
			
			pausarYActualizarPaneles();
			suspendido = !suspendido;
			
			//Si la bajara de los jugadores esta vacia o el el juego se entablo, el juego sale del ciclo.
		}while(!jugadores[0].getBaraja().isEmpty() && !jugadores[1].getBaraja().isEmpty() && juegoEntablado == 0);
		
		quienGano(jugador, juegoEntablado);
	}
	
	//Determina quien gano o si el juego se ha entablado.
	public void quienGano(DominoJugador[] jugador, int juegoEntablado) {
		
		if(juegoEntablado == 1) {
			fin.setText("Juego cerrado");

		}
		else if(jugador[0].getBaraja().isEmpty()) {
			fin.setText("Gano el jugador A");
	
		}
		else if(jugador[1].getBaraja().isEmpty()) {
			fin.setText("Gano el jugador B");
		}	
	}
	
	//Determina si el jugador lanza la ficha o traga si es que no cuenta con los valores de las esquinas de la ficha actual.
	public int tiroDeJugadores(DominoJugador jugador, JPanel panelJugador) {
		int success;
		
		success = tieneFichaJugador(jugador, fichaActual);
		if(success == 1) lanzarFicha(jugador, panelJugador);
		else {
			if(!domino.isEmpty()) {
				success = tomarFichaDelDomino(jugador, panelJugador);
				if(success == 1) lanzarFicha(jugador, panelJugador);
			}
		}
		if(jugador.equals(jugadores[0])) return 1;
		else if(jugador.equals(jugadores[1])) return 0;
		return 1;
	}
	
	//Lanza la ficha correcta al tablero.
	public void lanzarFicha(DominoJugador jugador, JPanel panelJugador) {
		
		Random random = new Random();
		int numeroAleatorio = 0;
		RotatedIcon imagenrotada = null;
		
		//El jugador a realiza el primer tiro
		if(primerTiro) {
			
			//Número random del 0 al 7
			numeroAleatorio = random.nextInt(7);
			
			//Asigna la ficha aleatoria de la bajara del jugador
			fichaLanzada = jugador.getBaraja().get(numeroAleatorio);
			
			//Cordenadas que sirven para mover las fichas.
			moverIx = 0;
			moverIy = 0;
			moverDx = 0;
			moverDy = 0;
			
			//Inicializa contador de lado derecho
			countDx = 60;
			countDy = 0;
			
			//Inicializa contador de lado izquierdo
			countIy = 60;
			countIx = 0;
			
			//Colocar la ficha en la posición.
			fichaLanzada.setLocation(0, 0);
			
			//Coloca la ficha en el tablero
			panelMesa.add(fichaLanzada);
			
			//Borra la ficha del jugador
			jugador.getBaraja().remove(fichaLanzada);
			
			//Asigna los valores de la ficha del jugadora a las esquinas de la fichaActual
			fichaActual.setCharAt(0, fichaLanzada.getValor().charAt(0));
			fichaActual.setCharAt(1, fichaLanzada.getValor().charAt(2));
			
			primerTiro = false;
			
			pausarYActualizarPaneles();
			suspendido = !suspendido;
			
		} else {
			
			jugador.getBaraja().remove(fichaLanzada);
			
			//Esquina izquierda de la ficha actual es igual la esquina izq de la ficha tirada
			if(fichaActual.charAt(0) == fichaLanzada.getValor().charAt(0)){
				
				imagenrotada = girarImagen("DOWN");
				fichaLanzada.setIcon(imagenrotada);
				desplazarFichaIzquierda();
				fichaActual.setCharAt(0, fichaLanzada.getValor().charAt(2));
			}
			
			//Esquina derecha de la ficha actual es igual la esquina izq de la ficha tirada
			else if(fichaActual.charAt(1) == fichaLanzada.getValor().charAt(0)){
				
				if(rotarImagenD) {
					imagenrotada = girarImagen("UPSIDE_DOWN");
					fichaLanzada.setIcon(imagenrotada);	
				}
				desplazarFichaDerecha();
				fichaActual.setCharAt(1, fichaLanzada.getValor().charAt(2));
			}
			
			//Esquina izq de la ficha actual es igual la esquina derecha de la ficha tirada
			else if(fichaActual.charAt(0) == fichaLanzada.getValor().charAt(2)){
				
				imagenrotada = girarImagen("UP");
				fichaLanzada.setIcon(imagenrotada);
				
				desplazarFichaIzquierda();
				fichaActual.setCharAt(0, fichaLanzada.getValor().charAt(0));
			}
			
			//Esquina derecha de la ficha actual es igual la esquina derecha de la ficha tirada
			else if(fichaActual.charAt(1) == fichaLanzada.getValor().charAt(2)){
				
				if(!rotarImagenD){
					imagenrotada = girarImagen("UPSIDE_DOWN");
					fichaLanzada.setIcon(imagenrotada);	
				}
				
				desplazarFichaDerecha();
				
				fichaActual.setCharAt(1, fichaLanzada.getValor().charAt(0));
			}
			
			panelMesa.add(fichaLanzada);
		
		}
		
		contadorDeTiros++;
	
	}
	
	//Desplazar ficha lado izquierdo.
	public void desplazarFichaIzquierda() {
		
		if(moverIy > panelMesa.getHeight()-180) {
	
			countIx = 30;
			countIy = 0;
			rotarImagenI = !rotarImagenI;
		} 
		
		moverIy += countIy;
		moverIx += countIx;
		
		fichaLanzada.setLocation(moverIx, moverIy);
		
		panelMesa.add(fichaLanzada);
		
	}
	
	//Desplazar ficha lado derecho.
	public void desplazarFichaDerecha() {
		
		RotatedIcon imagenrotada = null;
		
		if(moverDx > panelMesa.getWidth()-120) {
			
			countDx = -countDx;
			countDy += 35;
			
			rotarImagenD = !rotarImagenD;
		
			imagenrotada = girarImagen("UPSIDE_DOWN");
			fichaLanzada.setIcon(imagenrotada);
			
		} 
			moverDx += countDx;
			moverDy = countDy;
		
		fichaLanzada.setLocation(moverDx, moverDy);
		
		panelMesa.add(fichaLanzada);
	}
	
	@Override
	public void run() {
		
		//Comienza juego.
		comenzarJuego(jugadores, panelJugadores, fichaActual, fichaLanzada);
		
	}
	
	//Determina si el juego se ha entablado.
	public int juegoEntablado(DominoJugador jugador, StringBuilder fichaActual, JPanel panelJugador) {
		
		int successJugador = 0;
		int successDomino = 0;
		
		//Si las esquinas de la ficha actual son iguales
		if(fichaActual.charAt(0) == fichaActual.charAt(1)) {
			
			successJugador = tieneFichaJugador(jugador, fichaActual);
			if(successJugador == 0) {
				successDomino = tieneFichaDomino(fichaActual);
				if(successDomino == 0)
				{
					return 1;
				}	
			}
		}
		
		return 0;
	}
	
	//Si el domino contiene alguna ficha similar a la ficha actual.
	public int tieneFichaDomino(StringBuilder fichaActual) {
		
		for (DominoFicha ficha : domino)	{
			if(ficha.getValor().charAt(0) == fichaActual.charAt(0) || 
					ficha.getValor().charAt(0) == fichaActual.charAt(1))
			{
				return 1;
			}
			else if(ficha.getValor().charAt(2) == fichaActual.charAt(0) || 
					ficha.getValor().charAt(2) == fichaActual.charAt(1))
			{
				return 1;
			}
		}
		
		return 0;
	}
	
	//Toma fichas del domino y las agrega a su panel.
	public int tomarFichaDelDomino(DominoJugador jugador, JPanel panelJugador) {
		
		Random random = new Random();
		int numeroAleatorio = 0;
		boolean salir = false;
		int longitudDomino = 0;
		DominoFicha ficha = null;
		
		while(!salir) {
			
			longitudDomino = domino.size()-1;
			
			if(longitudDomino == 0) {
				jugador.getBaraja().add(domino.get(0));
				fichaLanzada = jugador.getBaraja().get(0);
				domino.remove(0);
				salir = true;
				
			} else {
				
				
				
				numeroAleatorio = random.nextInt(14);
				
				if(longitudDomino > numeroAleatorio) {
						
					//Ficha elejida aleatoriamente.
					ficha = domino.get(numeroAleatorio);
					
					//Existe la ficha en el domino
					if(domino.contains(ficha)) {
						
						//ficha.setIcon(icon);
						
						//Agrega ficha del domino a la baraja del jugador
						jugador.getBaraja().add(ficha);
						
						//Agrega ficha al panel del jugador
						panelJugador.add(ficha);
						
						//Elimina ficha del domino
						domino.remove(ficha);
						
						//Eliminar panel sopa
						panelSopa.remove(ficha);
						
						//Si la ficha tomada del domino es igual a una de las esquinas de la ficha actual la toma para lanzarla.
						if(jugador.getBaraja().get((jugador.getBaraja().size()-1)).getValor().charAt(0) == fichaActual.charAt(0)
								|| jugador.getBaraja().get((jugador.getBaraja().size()-1)).getValor().charAt(0) == fichaActual.charAt(1))
							{	
								
								fichaLanzada = jugador.getBaraja().get( (jugador.getBaraja().size()-1) );			
								return 1;
								
							}
							else if(jugador.getBaraja().get((jugador.getBaraja().size()-1)).getValor().charAt(2) == fichaActual.charAt(0)
									|| jugador.getBaraja().get((jugador.getBaraja().size()-1)).getValor().charAt(2) == fichaActual.charAt(1))
							{
								fichaLanzada = jugador.getBaraja().get( (jugador.getBaraja().size()-1) );
								return 1;
							}
					}
				}
			}
			panelSopa.updateUI();
		}
		
		return 0;
	}
	
	//Si el jugador contiene alguna ficha de su bajara, que sea igual al de las esquinas.
	public int tieneFichaJugador(DominoJugador jugador, StringBuilder fichaActual) {
		
		int longitudFichasJugador = 0;
		int contador = 0;
		
		longitudFichasJugador = jugador.getBaraja().size();
		
		//Si la longitud de las fichas del jugador es igual a 0
		if(longitudFichasJugador == 0){
			
			if(jugador.getBaraja().get(0).getValor().charAt(0) == fichaActual.charAt(0) 
					|| jugador.getBaraja().get(0).getValor().charAt(0) == fichaActual.charAt(1))
			{
				fichaLanzada = jugador.getBaraja().get(0);
				return 1;
			}
			else if(jugador.getBaraja().get(0).getValor().charAt(2) == fichaActual.charAt(0) 
					|| jugador.getBaraja().get(0).getValor().charAt(2) == fichaActual.charAt(1))
			{
				fichaLanzada = jugador.getBaraja().get(0);
				return 1;
			}
		} else {
			
			//Busca una ficha similar al de las esquinas de la ficha actual.
			while(contador < longitudFichasJugador) {
				if(jugador.getBaraja().get(contador).getValor().charAt(0) == fichaActual.charAt(0) 
						|| jugador.getBaraja().get(contador).getValor().charAt(0) == fichaActual.charAt(1))
				{
					fichaLanzada = jugador.getBaraja().get(contador);
					return 1;
				}
				else if(jugador.getBaraja().get(contador).getValor().charAt(2) == fichaActual.charAt(0) 
						|| jugador.getBaraja().get(contador).getValor().charAt(2) == fichaActual.charAt(1))
				{
					fichaLanzada = jugador.getBaraja().get(contador);
					return 1;
				}
				contador ++;
			}
		}
	return 0;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		suspendido = !suspendido;
	}
	
}
