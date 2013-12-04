package mtu.notes;

import java.io.File;
import java.io.LineNumberReader;
import java.io.FileReader;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import android.content.Intent;

public class ViewNoteActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_note);
		// Show the Up button in the action bar.
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		String path = bundle.getString("path");
		String name = bundle.getString("name");
		TextView text = (TextView)findViewById(R.id.textView);
		ScribbleView scribbleView = (ScribbleView)findViewById(R.id.loadView);
		if(Character.isDigit(name.charAt(name.length()-1)))
		{
			name = name.substring(0, name.length()-2);
		}
		File file = new File(path, name + ".txt");
		String fileText = "";
		if(file.exists())
		{
			String read;
			try
			{
				LineNumberReader in = new LineNumberReader(new FileReader(file.getPath()));
				while((read = in.readLine()) != null)
				{
					fileText += read;
				}
				in.close();
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		if(!fileText.equals(""))
		{
			text.setText(fileText);
		}
		scribbleView.load(path, name + ".png");
		setupActionBar();	
	}
	
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_note, menu);
		return true;
	}
}
