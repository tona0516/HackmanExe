package com.example.hackmanexe;

import android.app.Activity;
import android.graphics.Color;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

/**
 *
 * @author meem activityが複数になったので、このクラスを経由してt,t2を参照させる
 */

public class AttackRangeDrawManager {
	public static SurfaceView[] t = new SurfaceView[18]; // 攻撃範囲を描画するViewをパネル数作成
	public static SurfaceView[] t2 = new SurfaceView[18]; // 攻撃範囲を描画するViewをパネル数作成

	public AttackRangeDrawManager(Activity activity, FrameLayout frameLayout,
			float width, float height) {
		for (int i = 0; i < 18; i++) {
			t[i] = new SurfaceView(activity);
			t[i].setLayoutParams(new LayoutParams((int) width / 6, (int) height / 3)); // パネルの大きさにする
			t[i].setBackgroundColor(Color.YELLOW); // 黄色で描画
			t[i].setAlpha(0.5f); // 半透明に
			// 描画位置を設定
			t[i].setX(DrawingPosition.area.upperLeftPoint[i].x);
			t[i].setY(DrawingPosition.area.upperLeftPoint[i].y);
			t[i].setVisibility(View.GONE); // 普段は非表示に
			frameLayout.addView(t[i]);
		}

		for (int i = 0; i < 18; i++) {
			t2[i] = new SurfaceView(activity);
			t2[i].setLayoutParams(new LayoutParams((int) width / 6, (int) height / 3)); // パネルの大きさにする
			t2[i].setBackgroundColor(Color.CYAN); // 黄色で描画
			t2[i].setAlpha(0.5f); // 半透明に
			// 描画位置を設定
			t2[i].setX(DrawingPosition.area.upperLeftPoint[i].x);
			t2[i].setY(DrawingPosition.area.upperLeftPoint[i].y);
			t2[i].setVisibility(View.GONE); // 普段は非表示に
			frameLayout.addView(t2[i]);
		}
	}
}
