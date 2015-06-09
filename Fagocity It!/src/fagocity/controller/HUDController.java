package fagocity.controller;

import fagocity.model.GameModel;
import fagocity.model.GameStatus;
import fagocity.model.GameStatus.STATUS;
import fagocity.model.HUDModel;
import fagocity.model.Player;
import fagocity.view.GameView;
import fagocity.view.HUDView;

public class HUDController {

	private long initialTime = 0;
	private long currentTime;
	private int score = 0;
	private double fagocityStreak = 0;
	private int currentKillStreak = 0;
	private int minimumKillStreak = 2;
	private int streaksPassed = 0;
	private int bossScore = 1;
	private int totalFagocitedRadius = 0; // soma do raio dos inimigos fagocitados
	private Player player;
	private BossController boss;
	private HUDModel hudModel;
	private HUDView hudView;
	private GameController controller;
	
	public HUDController (Player player, ActorFactory actorFactory, GameView view, GameModel model,
			BoundsController bounds, CameraController camera, GameController controller){
		this.player = player;
		this.controller = controller;
		this.hudModel = model.getHudModel();
		this.hudView = view.getHUD();
		
		boss = new BossController ( this, actorFactory, view, model,(Player) player, bounds, camera, controller);
	}
	
	public void update() {
		if(controller.getPlayer() != null) {
			if(GameStatus.status == STATUS.Fagocity)
			{
				currentTime = System.currentTimeMillis();
				score = (1200*streaksPassed) +totalFagocitedRadius + (int)(currentTime/50 - initialTime/50)*(streaksPassed+1);
			}
			
			hudModel.setScore(score);
			boss.checkScore(); //chama o boss se o score for o suficiente
		}
		else
			score = 0;
	}
	
	public void setInitialTime(long initialTime) {
		this.initialTime = initialTime;
	}
	
	public void updateFagocityStreak() {
		currentKillStreak++;
		
		fagocityStreak = (double)(currentKillStreak / ((double)minimumKillStreak + (double)streaksPassed));
		System.out.println("streak: " +fagocityStreak);
		
		hudModel.setFagocityStreak(fagocityStreak);
		
		/* Se o streak estiver completo */
		if(fagocityStreak >= 1) {
			hudView.fagocityStreakTrigger();
			streaksPassed++;
			hudView.setStreaksPassed(streaksPassed);
			resetFagocityStreak();
			player.setGrowingRadius(player.getDefaultRadius() - player.getRadius());
			controller.getAudioPlayer().playAudio("StreakSound");
		}
	}
	
	public void resetFagocityStreak() {
		currentKillStreak = 0;
		hudModel.setFagocityStreak(0);
	}

	public void setTotalFagocitedRadius(int radius) {
		totalFagocitedRadius += radius;		
	}
	
	public int getStreaksPassed() {
		return streaksPassed;
	}
	
	public int getBossScore()
	{
		return bossScore;
	}
	
	public void incrementBossScore ()
	{
		bossScore++;
	}
	
	/*verifica se as coordenadas dadas do mouse estao dentro dos limites da caixa do botao*/
	public boolean mouseOver (int mx, int my, int x, int y, int largura, int altura) {
		if ((mx > x) && (mx < x + largura) && (my > y) && (my < y + altura))
			return true;
		else
			return false;
	}
	
}
