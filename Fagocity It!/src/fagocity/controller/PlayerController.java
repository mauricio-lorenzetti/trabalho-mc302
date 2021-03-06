package fagocity.controller;

import java.util.ArrayList;

import fagocity.controller.Interfaces.IActorController;
import fagocity.model.Actor;
import fagocity.model.GameModel;
import fagocity.model.Player;
import fagocity.view.GameView;

public class PlayerController implements IActorController {
	private GameView view;
	private GameModel model;
	private Player p;
	private CameraController camera;
	private BoundsController bounds;
	private MouseController mouse;
	
	public PlayerController (Player player){
		this.p = player;
		this.view = GameView.getInstance();
		this.model = GameModel.getInstance();
		this.bounds = BoundsController.getInstance();
		this.camera = CameraController.getInstance();
		
		this.mouse = MouseController.getInstance();
	}
	
	public void update() {
		updateSpeed();
		updatePosition();
	}
	
	private void updatePosition() {
		p.setX(p.getX() + (int)p.getVelX());
		p.setY(p.getY() + (int)p.getVelY());
		
		/*reestabelece x e y se eles passarem dos limites do mapa*/
		p.setX(bounds.playerRepositioning (p.getX(),view.getMinXBounds(),view.getMaxXBounds() - p.getRadius()));
		p.setY(bounds.playerRepositioning (p.getY(),view.getMinYBounds(),view.getMaxYBounds() - p.getRadius()));
	}
	
	private void updateSpeed() {
		double velTotal = p.getDefaultSpeed();
		 
		/*pega as posicoes de input do mouse e soma-as aos parametros de translacao*/
		int mouseX = (int) (mouse.getMouseX() - camera.getTX());
		int mouseY = (int) (mouse.getMouseY() - camera.getTY());
		
		/* Coordenadas do centro do player */
		int xCenter = p.getX() + p.getRadius()/2;
		int yCenter = p.getY() + p.getRadius()/2;
		
		/* Distance between mouse and player */
		double distance = Math.sqrt( (mouseY - yCenter)*(mouseY - yCenter) +  (mouseX - xCenter)*(mouseX - xCenter) );
		
		/* Componentes da velocidade */
		int velX = (int) ((velTotal/distance) * (mouseX - xCenter));
		int velY = (int) ((velTotal/distance) * (mouseY - yCenter));
		
		/* Toler�ncia de erro. Ele � necess�rio pois evita uma divis�o por 0, evita o flickering do player
		 * e permite que a velocidade do player diminua conforme o mouse ele aproxima do ponteiro do mouse */
		int tolerance = 100;
		if(distance > tolerance) {
			p.setVelX ( velX );
			p.setVelY ( velY );
		}
		else {
			p.setVelX ((int)(velX * distance/tolerance));
			p.setVelY ((int)(velY * distance/100));
		}
	}
	
	
	public Player getPlayer() {
		ArrayList<Actor> list = model.getActorsList();
		Player player = null;
		
		for(int i = 0; i < list.size(); i++) {
			Actor actor = list.get(i);
			if(actor.getType().equalsIgnoreCase("player"))
				player = (Player) actor;
		}
		return player;
	}

}
