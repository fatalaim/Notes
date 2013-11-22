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
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_note, menu);
		return true;
	}
}
