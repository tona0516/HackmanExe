package com.example.hackmanexe.fieldobject;

import java.util.Timer;
import java.util.TimerTask;

import com.example.hackmanexe.MainActivity;
import com.example.hackmanexe.PanelInfo;
import com.example.hackmanexe.action.LongSword;
import com.example.hackmanexe.action.WideSword;

public class Swordin extends Enemy {

	private final static int HP = 90;
	private Player player;
	private Timer timer;
	private MainActivity mainactivity;
	private Swordin swordin;
	private LongSword ls = null;
	private WideSword ws = null;

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
				if(ws == null || !ws.isActing() || ls == null || !ls.isActing()){ // 行動の前提
					if(swordin.getCurrentPanelInfo().getRow()
							!= swordin.getCurrentPanelInfo().getFrontrowindex()+1){ // こいつが最前列にいないなら
						if(currentPlayerLine < currentOwnLine){
							moveUp();
						}
						else if(currentPlayerLine > currentOwnLine){
							moveDown();
						}
						try {
							Thread.sleep(1000);
							moveLeft(); // 1マス前進
						} catch (InterruptedException e) {
							// TODO 自動生成された catch ブロック
							e.printStackTrace();
						}
					}
					else if(swordin.getCurrentPanelInfo().getRow()
							== swordin.getCurrentPanelInfo().getFrontrowindex()+1){ // こいつが最前列にいるなら
						if(player.getCurrentPanelInfo().getRow()
								== player.getCurrentPanelInfo().getFrontrowindex()){ // プレイヤーが最前列なら
							if(Math.abs(currentPlayerLine - currentOwnLine) > 1){ // widesword の布石
								if(currentPlayerLine == 0){ // 相手に行を合わせる
									moveUp();
								}
								else if(currentPlayerLine == 2){ // 行を合わせる
									moveDown();
								}
							}
							else{
								if(prePlayerLine == currentPlayerLine
										&& preOwnLine == currentOwnLine){
									ws = new WideSword(mainactivity, swordin);
									swordin.addAction(ws);
									swordin.action();
								}
							}
						}
						else{														// プレイヤーが最前列でない、ロングソードの布石
							if(currentPlayerLine != currentOwnLine){				// 行一致でない
								if(currentPlayerLine < currentOwnLine){ // 相手に行を合わせる
									moveUp();
								}
								else if(currentPlayerLine > currentOwnLine){ // 行を合わせる
									moveDown();
								}
							}
							else{													// 行一致
								if(prePlayerLine == currentPlayerLine
										&& preOwnLine == currentOwnLine){
									ls = new LongSword(mainactivity, swordin);
									swordin.addAction(ls);
									swordin.action();
								}
							}
						}
					}
				}

				prePlayerLine = currentPlayerLine;
				preOwnLine = currentOwnLine;
			}
		}
		, 1000, 1000);
	}

	@Override
	protected void deathProcess() {
		super.deathProcess();
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

}
