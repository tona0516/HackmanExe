package com.example.hackmanexe;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class MainActivity extends Activity {
	private ObjectSurfaceView objectSurfaceView;
	//public static EffectSurfaceView effectSurfaceView;
	private FieldView fieldView;
	private FrameLayout frameLayout;
	public static SurfaceView[] t = new SurfaceView[9];
	/**
	 * ここから実行
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//t.setVisibility(View.INVISIBLE);
		// 画面サイズの取得
		WindowManager wm = getWindowManager();
		Display disp = wm.getDefaultDisplay();
		Point point = new Point();
		disp.getSize(point);
		float height = point.y;
		float width = point.x;
		// height,widthから敵・味方エリアの位置座標を計算
		DrawingPosition.prepareDrawing(width, height);
		// 描画を行うViewを加える
		frameLayout = (FrameLayout) findViewById(R.id.root_layout);
		fieldView = new FieldView(this, width, height);
		objectSurfaceView = new ObjectSurfaceView(this, width, height);
		//effectSurfaceView = new EffectSurfaceView(this);
		frameLayout.addView(fieldView);
		frameLayout.addView(objectSurfaceView);
		for(int i = 0;i < 9;i++){
			t[i] = new SurfaceView(this);
			t[i].setLayoutParams(new LayoutParams((int)width/6, (int)height/3));
			t[i].setBackgroundColor(Color.YELLOW);
			t[i].setX(DrawingPosition.enemyArea.upperLeftPoint[i].x);
			t[i].setY(DrawingPosition.enemyArea.upperLeftPoint[i].y);
			t[i].setVisibility(View.GONE);
			frameLayout.addView(t[i]);
		}
		//frameLayout.addView(effectSurfaceView);
	}
}
