package com.origin.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.origin.R;
import com.origin.util.ToastUtil;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViewById(R.id.dk_bttn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				ToastUtil.show(MainActivity.this, getResources().getString(R.string.latter_plus));
			}
		});
		findViewById(R.id.qr_bttn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(MainActivity.this, MipcaActivityCapture.class));
			}
		});
		findViewById(R.id.nfc_bttn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(MainActivity.this, NFCActivity.class));
			}
		});
	}
}
