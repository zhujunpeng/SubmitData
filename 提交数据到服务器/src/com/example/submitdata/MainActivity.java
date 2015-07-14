package com.example.submitdata;

import com.example.submitdata.netUtils.NetUtils;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{

    private static final String TAG = "MainActivity";
	private EditText etUsername;
	private EditText etPassword;
	private Button btnGet;
	private Button btnPost;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnGet = (Button) findViewById(R.id.btn_get);
        btnGet.setOnClickListener(this);
        btnPost = (Button) findViewById(R.id.btn_post);
        btnPost.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_get:
			{
				final String username = etUsername.getText().toString();
				final String password = etPassword.getText().toString();
				Log.i(TAG, "运行。。。。");
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						final String state = NetUtils.LoginForGet(username, password);
						
						//在主线程中运行的线程
						runOnUiThread( new Runnable() {
							public void run() {
								Toast.makeText(MainActivity.this, state, 0).show();
							}
						});
					}
				}).start();
			}
			break;
		case R.id.btn_post:
			{
				final String username = etUsername.getText().toString();
				final String password = etPassword.getText().toString();
				Log.i(TAG, "运行。。。。");
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						final String state = NetUtils.LoginForPost(username, password);
						
						//在主线程中运行的线程
						runOnUiThread( new Runnable() {
							public void run() {
								Toast.makeText(MainActivity.this, state, 0).show();
							}
						});
					}
				}).start();
			}
			break;
		default:
			break;
		}
		
	}
    
}
