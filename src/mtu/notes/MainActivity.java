package mtu.notes;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ScribbleView view = new ScribbleView(this);
		view.setBackgroundColor(Color.WHITE);
		new File(Environment.getExternalStorageDirectory().getPath() + "/None/").mkdir();
        //setContentView(view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void newNote(View view)
	{
		Intent intent = new Intent(this, NewNoteActivity.class);
		startActivity(intent);
	}
	
	public void journalView(View view)
	{
		Intent intent = new Intent(this, JournalViewActivity.class);
		startActivity(intent);
	}

	
	public void loadScribble(View view)
	{
		Intent intent = new Intent(this,LoadScribble.class);
		startActivity(intent);
	}
}
