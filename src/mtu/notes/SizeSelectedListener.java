package mtu.notes;

import android.app.Activity;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;

public class SizeSelectedListener implements OnItemSelectedListener{
	
	private Activity activity;
	
	public SizeSelectedListener(Activity activity){
		this.activity = activity;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		
		EditText note = (EditText) activity.findViewById(R.id.notetext);
		Editable text = note.getText();
		String value = (String) parent.getItemAtPosition(pos);
		if(!value.equals("Font Size")){
			note.setTextSize(Float.valueOf(value));
			note.setText(text);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
