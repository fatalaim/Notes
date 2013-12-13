package mtu.notes;

import android.app.Activity;
import android.graphics.Color;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.AdapterView.OnItemSelectedListener;

public class ColorSelectedListener implements OnItemSelectedListener{
	
	private Activity activity;
	
	public ColorSelectedListener(Activity activity){
		this.activity = activity;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		EditText note = (EditText) activity.findViewById(R.id.notetext);
		Editable text = note.getText();
		String value = (String) parent.getItemAtPosition(pos);
		if(value.equals("Black")){
			note.setTextColor(Color.BLACK);
			note.setText(text);
		}
		else if(value.equals("Red")){
			note.setTextColor(Color.RED);
			note.setText(text);
		}
		else if(value.equals("Blue")){
			note.setTextColor(Color.BLUE);
			note.setText(text);
		}
		else if(value.equals("Yellow")){
			note.setTextColor(Color.YELLOW);
			note.setText(text);
		}
		else if(value.equals("Green")){
			note.setTextColor(Color.GREEN);
			note.setText(text);
		}
		else if(value.equals("Magenta")){
			note.setTextColor(Color.MAGENTA);
			note.setText(text);
		}
		else if(value.equals("Gray")){
			note.setTextColor(Color.GRAY);
			note.setText(text);
		}
		else if(value.equals("Dark Gray")){
			note.setTextColor(Color.DKGRAY);
			note.setText(text);
		}
		else if(value.equals("Light Gray")){
			note.setTextColor(Color.LTGRAY);
			note.setText(text);
		}
		else if(value.equals("White")){
			note.setTextColor(Color.WHITE);
			note.setText(text);
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
