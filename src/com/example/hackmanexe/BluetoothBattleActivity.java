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

	/**
	 * ここから実行
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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
					// Log.d("Message", readMessage);
					// 受信した値から相手プレイヤーの位置を反映
					if(isNumber(readMessage)){
						int oldIndex = Integer.parseInt(readMessage);
						if(oldIndex >= 0 && oldIndex <= 17 && ObjectManager.getInstance().getOpponent() != null){
							int newIndex = convertPanelIndex(oldIndex);
							ObjectManager.getInstance().getOpponent().warp(newIndex);
						}
					}
					break;
				case MESSAGE_DEVICE_NAME :
					// save the connected device's name
					mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
					Toast.makeText(getApplicationContext(), "Connected to " + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
					break;
				case MESSAGE_TOAST :
					Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST), Toast.LENGTH_SHORT).show();
					break;
			}
		}
	};

	private boolean isNumber(String message){
		try {
			 Integer.parseInt(message);
			return true;
		} catch (NumberFormatException nfex) {
			return false;
		}
	}

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
				return true;
			case R.id.insecure_connect_scan :
				// Launch the DeviceListActivity to see devices and do scan
				serverIntent = new Intent(this, DeviceListActivity.class);
				startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_INSECURE);
				return true;
			case R.id.discoverable :
				// Ensure this device is discoverable by others
				ensureDiscoverable();
				return true;
		}
		return false;
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

		AttackRangeManager arm = new AttackRangeManager(this, frameLayout, width, height);
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
}
