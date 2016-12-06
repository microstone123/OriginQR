package com.origin.act;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.origin.R;
import com.origin.util.C;
import com.origin.util.ToastUtil;

public class NFCActivity extends FragmentActivity {
	private NfcAdapter nfcAdapter;
	private PendingIntent pendingIntent;
	private IntentFilter[] mFilters;
	private String[][] mTechLists;
	private boolean isFirst = true;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 获取nfc适配器，判断设备是否支持NFC功能
		nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		checkNFC();
		setContentView(R.layout.nfc_layout);

		findViewById(R.id.button_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		pendingIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
		ndef.addCategory("*/*");
		mFilters = new IntentFilter[] { ndef };// 过滤器
		mTechLists = new String[][] { new String[] { MifareClassic.class.getName() },
				new String[] { NfcA.class.getName() } };// 允许扫描的标签类型

	}

	@SuppressLint("NewApi")
	private void checkNFC() {
		if (nfcAdapter == null) {
			ToastUtil.show(this, getResources().getString(R.string.no_nfc));
			finish();
			return;
		} else if (!nfcAdapter.isEnabled()) {
			ToastUtil.show(this, getResources().getString(R.string.open_nfc));
			finish();
			return;
		}
	}

	@SuppressLint("NewApi")
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		nfcAdapter.enableForegroundDispatch(this, pendingIntent, mFilters, mTechLists);
		if (isFirst) {
			if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction())) {
				// String result = processIntent(getIntent());
			}
			isFirst = false;
		}

	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		try {
			if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
				// String result = processIntent(intent);
				// resultText.setText(result);
				link();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void link() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(C.PRIGIN_URL));
		startActivity(intent);
		finish();
	}

	/**
	 * 获取tab标签中的内容
	 * 
	 * @param intent
	 * @return
	 */
	@SuppressLint("NewApi")
	private String processIntent(Intent intent) {
		Parcelable[] rawmsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
		NdefMessage msg = (NdefMessage) rawmsgs[0];
		NdefRecord[] records = msg.getRecords();
		String resultStr = new String(records[0].getPayload());
		return resultStr;
	}

	@Override
	protected void onStop() {
		super.onStop();
		// NfcAdapter.getDefaultAdapter(this).e
	}

}
