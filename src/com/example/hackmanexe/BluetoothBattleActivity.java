package com.example.hackmanexe;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.hackmanexe.action.LongSword;
import com.example.hackmanexe.action.PaladinSword;
import com.example.hackmanexe.action.Sword;
import com.example.hackmanexe.action.WideSword;
import com.example.hackmanexe.fieldobject.Opponent;

public class BluetoothBattleActivity extends Activity {
	private FrameLayout frameLayout;

	// Debugging
	private static final String TAG = "BluetoothChat";
	private static final boolean D = true;

	// Message types sent from the BluetoothChatService Handler
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;

	// Key names received from the BluetoothChatService Handler
	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";

	// Intent request codes
	private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
	private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
	private static final int REQUEST_ENABLE_BT = 3;

	// Name of the connected device
	private String mConnectedDeviceName = null;
	// Local Bluetooth adapter
	public static BluetoothAdapter mBluetoothAdapter = null;
	// Member object for the chat services
	public static BluetoothChatService mChatService = null;

	public static boolean isMaster = false;
	public static boolean isRequest = false;
	public static Activity activity;
	/**
	 * ここから実行
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		activity = this;

		// 画面サイズの取得
		Point point = getWindowSize();

		// height,widthから敵・味方エリアの位置座標を計算
		DrawingPosition.prepareDrawing(point.x, point.y);

		// 描画を行うViewを加える
		frameLayout = (FrameLayout) findViewById(R.id.root_layout);
		setView(frameLayout, point.x, point.y);

		// Get local Bluetooth adapter
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		// If the adapter is null, then Bluetooth is not supported
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
			finish();
			return;
		}
	}

	/**
	 *
	 * @return 画面の縦横サイズ
	 */
	private Point getWindowSize() {
		Point p = new Point();
		WindowManager wm = getWindowManager();
		Display disp = wm.getDefaultDisplay();
		disp.getSize(p);
		return p;
	}

	/**
	 * 各種Viewの追加
	 *
	 * @param frameLayout
	 * @param width
	 *            画面横サイズ
	 * @param height
	 *            画面縦サイズ
	 */
	private void setView(FrameLayout frameLayout, float width, float height) {
		// エリアの区切り線を描画するView
		FieldView fieldView;
		fieldView = new FieldView(this, width, height);
		frameLayout.addView(fieldView);

		// エリア上のオブジェクト(プレイヤー、敵)を描画するView
		BluetoothBattleObjectSurfaceView bluetoothBattleObjectSurfaceView;
		bluetoothBattleObjectSurfaceView = new BluetoothBattleObjectSurfaceView(this, this, width, height);
		frameLayout.addView(bluetoothBattleObjectSurfaceView);

		AttackRangeDrawManager arm = new AttackRangeDrawManager(this, frameLayout, width, height);
	}

	/**
	 * バックボタンが押下された時の処理
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 確認ダイアログの生成
			AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
			alertDlg.setTitle("選択");

			alertDlg.setPositiveButton("終了", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// Okボタン処理
					finish();
				}
			});
			alertDlg.setNeutralButton("いいえ", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// Cancel ボタンクリック処理
				}
			});
			alertDlg.setNegativeButton("Bluetooth接続", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					openOptionsMenu();
				}
			});

			// 表示
			alertDlg.create().show();
			return true;
		}
		return false;
	}

	/**
	 * 受信した情報から相手プレイヤーの動作を同期する
	 *
	 * @param readMessage
	 */
	private void synchronize(String readMessage) {

		//自分の情報を送信
		StringBuilder sb = new StringBuilder();
		sb.append(ObjectManager.getInstance().getPlayer().getCurrentPanelInfo().getIndex());
		sb.append(",");
		sb.append(ObjectManager.getInstance().getPlayer().getHP());
		sb.append(",");
		sb.append(BluetoothBattleObjectSurfaceView.attackcommand);
		sendMessage(sb.toString());
		sb.setLength(0);
		BluetoothBattleObjectSurfaceView.attackcommand = "null";

		if (readMessage.equals("first"))
			return;

		String[] opponentInfo = readMessage.split(",");
		Opponent opponent = ObjectManager.getInstance().getOpponent();

		// null検査
		if (opponent == null)
			return;

		// 相手の位置の同期
		if (isNumber(opponentInfo[0])) {
			int oldIndex = Integer.parseInt(opponentInfo[0]);
			if (oldIndex >= 0 && oldIndex <= 17) {
				int newIndex = convertPanelIndex(oldIndex);
				opponent.warp(newIndex);
			}
		}

		// 相手のHPの同期
		if (isNumber(opponentInfo[1])) {
			int hp = Integer.parseInt(opponentInfo[1]);
			opponent.setHP(hp);
		}

		// 相手の攻撃の同期
		switch (opponentInfo[2]) {
			case "PaladinSword" :
				opponent.addAction(new PaladinSword(this, opponent));
				opponent.action();
				break;
			case "WideSword" :
				opponent.addAction(new WideSword(this, opponent));
				opponent.action();
				break;
			case "LongSword" :
				opponent.addAction(new LongSword(this, opponent));
				opponent.action();
				break;
			case "Sword" :
				opponent.addAction(new Sword(this, opponent));
				opponent.action();
				break;
			default :
				break;
		}
	}

	private boolean sendMessage(String message) {
		// Check that we're actually connected before trying anything
		if (BluetoothBattleActivity.mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
			return false;
		}
		// Check that there's actually something to send
		if (message.length() > 0) {
			// Get the message bytes and tell the BluetoothChatService to write
			byte[] send = message.getBytes();
			BluetoothBattleActivity.mChatService.write(send);
			return true;
		}
		return false;
	}

	/**
	 *
	 * @param message
	 * @return 整数化が可能→true
	 */
	private boolean isNumber(String message) {
		try {
			Integer.parseInt(message);
			return true;
		} catch (NumberFormatException nfex) {
			return false;
		}
	}

	/**
	 *
	 * @param oldIndex
	 * @return 変換したパネルインデックス
	 * @see 実際のパネルインデックスを見かけ上のものに変換する 解析的にできないものか・・・
	 */
	private int convertPanelIndex(int oldIndex) {
		switch (oldIndex) {
			case 0 :
				return 5;
			case 1 :
				return 4;
			case 2 :
				return 3;
			case 3 :
				return 2;
			case 4 :
				return 1;
			case 5 :
				return 0;
			case 6 :
				return 11;
			case 7 :
				return 10;
			case 8 :
				return 9;
			case 9 :
				return 8;
			case 10 :
				return 7;
			case 11 :
				return 6;
			case 12 :
				return 17;
			case 13 :
				return 16;
			case 14 :
				return 15;
			case 15 :
				return 14;
			case 16 :
				return 13;
			case 17 :
				return 12;
			default :
				return -1;
		}
	}

	// ----------以下Bluetooth関連----------

	@Override
	public void onStart() {
		super.onStart();
		// If BT is not on, request that it be enabled.
		// setupChat() will then be called during onActivityResult
		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
			// Otherwise, setup the chat session
		} else {
			if (mChatService == null)
				setupChat();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		// Performing this check in onResume() covers the case in which BT was
		// not enabled during onStart(), so we were paused to enable it...
		// onResume() will be called when ACTION_REQUEST_ENABLE activity
		// returns.
		if (mChatService != null) {
			// Only if the state is STATE_NONE, do we know that we haven't
			// started already
			if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
				// Start the Bluetooth chat services
				mChatService.start();
			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mChatService != null)
			mChatService.stop();
		Field.getInstance().clear();
		ObjectManager.getInstance().clear();
	}

	private void setupChat() {
		Log.d(TAG, "setupChat()");
		// Initialize the BluetoothChatService to perform bluetooth connections
		mChatService = new BluetoothChatService(this, mHandler);
	}

	private void ensureDiscoverable() {
		if (D)
			Log.d(TAG, "ensure discoverable");
		if (mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
			Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
			startActivity(discoverableIntent);
		}
	}

	// The Handler that gets information back from the BluetoothChatService
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case MESSAGE_STATE_CHANGE :
					if (D)
						Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
					switch (msg.arg1) {
						case BluetoothChatService.STATE_CONNECTED :
							break;
						case BluetoothChatService.STATE_CONNECTING :
							break;
						case BluetoothChatService.STATE_LISTEN :
						case BluetoothChatService.STATE_NONE :
							break;
					}
					break;
				case MESSAGE_WRITE :
					byte[] writeBuf = (byte[]) msg.obj;
					// construct a string from the buffer
					String writeMessage = new String(writeBuf);
					// Log.d("Message", writeMessage);
					break;
				case MESSAGE_READ :
					byte[] readBuf = (byte[]) msg.obj;
					// construct a string from the valid bytes in the buffer
					String readMessage = new String(readBuf, 0, msg.arg1);
					//Log.d("Message", readMessage);
					synchronize(readMessage);
					break;
				case MESSAGE_DEVICE_NAME :
					// save the connected device's name
					mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
					Toast.makeText(getApplicationContext(), "Connected to " + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
					if (isRequest) {
						isMaster = true;
						synchronize("first");
					}
					break;
				case MESSAGE_TOAST :
					Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST), Toast.LENGTH_SHORT).show();
					break;
			}
		}
	};

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (D)
			Log.d(TAG, "onActivityResult " + resultCode);
		switch (requestCode) {
			case REQUEST_CONNECT_DEVICE_SECURE :
				// When DeviceListActivity returns with a device to connect
				if (resultCode == Activity.RESULT_OK) {
					connectDevice(data, true);
				}
				break;
			case REQUEST_CONNECT_DEVICE_INSECURE :
				// When DeviceListActivity returns with a device to connect
				if (resultCode == Activity.RESULT_OK) {
					connectDevice(data, false);
				}
				break;
			case REQUEST_ENABLE_BT :
				// When the request to enable Bluetooth returns
				if (resultCode == Activity.RESULT_OK) {
					// Bluetooth is now enabled, so set up a chat session
					setupChat();
				} else {
					// User did not enable Bluetooth or an error occurred
					Log.d(TAG, "BT not enabled");
					Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
					finish();
				}
		}
	}

	private void connectDevice(Intent data, boolean secure) {
		// Get the device MAC address
		String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
		// Get the BluetoothDevice object
		BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
		// Attempt to connect to the device
		mChatService.connect(device, secure);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.option_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent serverIntent = null;
		switch (item.getItemId()) {
			case R.id.secure_connect_scan :
				// Launch the DeviceListActivity to see devices and do scan
				serverIntent = new Intent(this, DeviceListActivity.class);
				startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
				isRequest = true;
				return true;
			case R.id.insecure_connect_scan :
				// Launch the DeviceListActivity to see devices and do scan
				serverIntent = new Intent(this, DeviceListActivity.class);
				startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_INSECURE);
				isRequest = true;
				return true;
			case R.id.discoverable :
				// Ensure this device is discoverable by others
				ensureDiscoverable();
				return true;
		}
		return false;
	}
}
