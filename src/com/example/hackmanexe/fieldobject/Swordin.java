package com.example.hackmanexe.fieldobject;

import java.util.Timer;
import java.util.TimerTask;

import com.example.hackmanexe.MainActivity;
import com.example.hackmanexe.PanelInfo;

public class Swordin extends Enemy {

	private final static int HP = 90;
	private Player player;
	private Timer timer;
	private MainActivity mainactivity;
	private Swordin swordin;

	private int prePlayerLine = -1;
	private int preOwnLine = -1;

	/** to describe this object
	 *
	 * @param _mainactivity
	 * to describe this animation on View
	 * @param _panelinfo
	 * infomation of field panel
	 * @param _player
	 * which line the player exists
	 * @param swordin
	 * to describe this object on View
	 */

	public Swordin(MainActivity _mainactivity, PanelInfo _panelinfo, Player _player){
		super(_panelinfo, HP);
		this.mainactivity = _mainactivity;
		this.player = _player;
		swordin = this;

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask(){
			@Override
			public void run(){
				int currentPlayerLine = player.getCurrentPanelInfo().getLine();
				int currentOwnLine = swordin.getCurrentPanelInfo().getLine();

				if(swordin.getCurrentPanelInfo().getRow()
						!= swordin.getCurrentPanelInfo().getFrontrowindex()+1){ // 最前列にいないなら
					if(currentPlayerLine < currentOwnLine){
						moveUp();
					}
					else if(currentPlayerLine > currentOwnLine){
						moveDown();
					}
					try {
						Thread.sleep(300); // 少し遅れて前進したい
						moveLeft(); // 1マス前進
					} catch (InterruptedException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}
				}
				else if(swordin.getCurrentPanelInfo().getRow()
						== swordin.getCurrentPanelInfo().getFrontrowindex()+1){ // 最前列なら
					if(player.getCurrentPanelInfo().getRow()
							== player.getCurrentPanelInfo().getFrontrowindex()){
						
					}
				}
			}
		}, 0, 1000);
	}

	@Override
	protected void deathProcess() {
		super.deathProcess();
	}

}
