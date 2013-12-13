package mtu.notes;

import java.io.File;

import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.ArrayList;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class LoadScribble extends Activity {

	private ArrayList<String> list = new ArrayList<String>();
	private ArrayList<String> notes = new ArrayList<String>();
	private ArrayList<String> path = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load_scribble);
		Spinner spinner = (Spinner) findViewById(R.id.loadSpinner);
		File file = new File(Environment.getExternalStorageDirectory() + "/category.txt");
		if(file.exists())
		{
			String read;
			try {
				LineNumberReader in = new LineNumberReader(new FileReader(Environment.getExternalStorageDirectory() +"/category.txt"));
				while((read = in.readLine()) != null)
				{
					list.add(read);
				}
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		File folder = new File(Environment.getExternalStorageDirectory().toString());
		File category;
		File note;
		if(folder.exists())
		{
			String[] folders = folder.list();
			if(folders != null)
			{
				for(String name : folders)
				{
					category = new File(folder + "/" + name);
					if(category.isDirectory() && list.contains(name))
					{
						for(String names : category.list())
						{
							note = new File(folder + "/" + name + "/" + names);
							if(note.isDirectory())
							{
								notes.add(names);
								path.add(folder + "/" + name + "/" + names);
							}
						}
					}
				}
			}
		}
		
		//Check None Category
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, notes);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
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
		getMenuInflater().inflate(R.menu.load_scribble, menu);
		return true;
	}
	
	public void Load(View view)
	{
		Spinner note = (Spinner)findViewById(R.id.loadSpinner);
		String _path = path.get(notes.indexOf(note.getSelectedItem().toString()));
		Intent intent = new Intent(getApplicationContext(), ViewNoteActivity.class);
		intent.putExtra("path", _path);
		intent.putExtra("name", note.getSelectedItem().toString());
		startActivity(intent);
	}
}
