package com.example.hackmanexe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartDisplayActivity extends Activity {

	private Button btnVirusBattle;
	private Button btnBluetoothBattle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_display);
		btnVirusBattle = (Button) findViewById(R.id.virus_battle);
		btnVirusBattle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), VirusBattleActivity.class));
			}
		});
		btnBluetoothBattle = (Button) findViewById(R.id.bluetooth_battle);
		btnBluetoothBattle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), BluetoothBattleActivity.class));
			}
		});
	}
}
