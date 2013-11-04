package mtu.notes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

public class NewNoteActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_note);
		Spinner spinner = (Spinner) findViewById(R.id.journalspinner);
		
		ArrayList<String> list = new ArrayList<String>();
		File file = new File(Environment.getExternalStorageDirectory() + "/category.txt");
		if(file.exists())
		{
			String read;
			try {
				LineNumberReader in = new LineNumberReader(new FileReader(Environment.getExternalStorageDirectory() +"/category.txt"));
				while((read = in.readLine()) != null)
				{
					System.out.println(read);
					list.add(read);
				}
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		list.add("None");
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		// Show the Up button in the action bar.
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_note, menu);
		return true;
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

	public void save(View view)
	{
		EditText text = (EditText) findViewById(R.id.notetext);
		if(text != null) {
			EditText editText = (EditText)findViewById(R.id.titletext);
			if(!editText.getText().toString().isEmpty())
			{
				int count = -1;
				String filename = editText.getText().toString() + ".txt";
				File file = new File(Environment.getExternalStorageDirectory().getPath(), filename);
				while(file.exists())
				{
					count++;
					filename = editText.getText().toString() + count + ".txt";
					file = new File(Environment.getExternalStorageDirectory().getPath(), filename);
				}
				try {
					FileOutputStream out = new FileOutputStream(file);
					out.write(text.getText().toString().getBytes());
					out.close();
					Context context = getApplicationContext();
					CharSequence toastText = filename + " saved.";
					int duration = Toast.LENGTH_SHORT;

					Toast toast = Toast.makeText(context, toastText, duration);
					toast.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else
			{
				int count = 0;
				String filename = "note" + count + ".txt";
				File file = new File(Environment.getExternalStorageDirectory().getPath(), filename);
				while(file.exists())
				{
					count++;
					filename = "note" + count + ".txt";
					file = new File(Environment.getExternalStorageDirectory().getPath(), filename);
				}

				try {
					FileOutputStream out = new FileOutputStream(file);
					out.write(text.getText().toString().getBytes());
					out.close();
					Context context = getApplicationContext();
					CharSequence toastText = filename + " saved.";
					int duration = Toast.LENGTH_SHORT;

					Toast toast = Toast.makeText(context, toastText, duration);
					toast.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
