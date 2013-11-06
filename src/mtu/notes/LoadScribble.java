package mtu.notes;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class LoadScribble extends Activity {

	int LOAD_REQUEST = 1;
	ScribbleView view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load_scribble);
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.setType("file/*");
		startActivityForResult(intent,LOAD_REQUEST);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.load_scribble, menu);
		return true;
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == LOAD_REQUEST) {
	        if (resultCode == RESULT_OK) {
	        	ScribbleView scribble = (ScribbleView) findViewById(R.id.scribbleView1);
	        	scribble.load(data.getDataString().substring(7));
	        }
	    }
	}
}
