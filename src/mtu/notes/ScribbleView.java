package mtu.notes;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ScribbleView extends View{
	private static final float MINP = 0.25f; 
	private static final float MAXP = 0.75f;

	private static Paint mPaint;
	private Bitmap  mBitmap;
	private Bitmap  hBitmap;
	private Canvas  mCanvas;
	private Canvas hCanvas;
	private Path    mPath;
	private Paint   mBitmapPaint;
	private static Paint highlight;
	Context context;
	static boolean highlighter = false;
	String text="";
	int height = 400;
	int width = 5000;

	public ScribbleView(Context c){
		super(c);
		init(c);
	}
	public ScribbleView(Context c, AttributeSet at){
		this(c, at,0);
		init(c);
	}

	public ScribbleView(Context c, AttributeSet at, int defStyle){
		super(c,at,defStyle);
		init(c);
	}

	public static void highlightColor(int color){
		highlighter=true;
		highlight.setColor(color);
		highlight.setStrokeWidth(20);
	}
	
	public static void penColor(int color){
		highlighter=false;
		mPaint.setColor(color);
	}
	
	public static void setWidth(float width){
		highlight.setStrokeWidth(width);
	}
	
	public  void init(Context c) {
		context = c;
		this.requestFocus();
		this.setFocusableInTouchMode(true);
		mPaint = new Paint();
		mPaint.setColor(Color.BLACK);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(0);
		mPaint.setTextSize(30);
		highlight = new Paint();
		highlight.setStrokeWidth(20);
		highlight.setStyle(Paint.Style.STROKE);
		highlight.setStrokeJoin(Paint.Join.MITER);
		highlight.setStrokeCap(Paint.Cap.SQUARE);
		highlight.setColor(Color.YELLOW);
		mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		hBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);
		hCanvas = new Canvas(hBitmap);
		mPath = new Path();
		mBitmapPaint = new Paint(Paint.DITHER_FLAG);
	}
	
	public void toggleHighlight(){
		if (!highlighter){
			highlighter  = true; 
		}
		else{
			highlighter = false;
		}
	}

	public void Save(EditText editText, Spinner cat, Context context) {
		Bitmap finBit = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas fin = new Canvas(finBit);
		fin.drawBitmap(hBitmap, 0, 0, mBitmapPaint);
		fin.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
		if(!editText.getText().toString().isEmpty())
		{
			int count = -1;
			String filename = editText.getText().toString() + ".png";
			String path;
			if(cat.getSelectedItem().toString().equals("None"))
			{
				path = Environment.getExternalStorageDirectory().getPath();
			}
			else
			{
				path = Environment.getExternalStorageDirectory().getPath() + "/" + cat.getSelectedItem().toString();
			}
			File file = new File(path, filename);
			while(file.exists())
			{
				count++;
				filename = editText.getText().toString() + count + ".png";
				file = new File(Environment.getExternalStorageDirectory().getPath(), filename);
			}
			try {
				FileOutputStream out = new FileOutputStream(file);
				finBit.compress(Bitmap.CompressFormat.PNG, 90, out);
				out.close();
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
			String filename = "note" + count + ".png";
			File file = new File(Environment.getExternalStorageDirectory().getPath(), filename);
			while(file.exists())
			{
				count++;
				filename = "note" + count + ".png";
				file = new File(Environment.getExternalStorageDirectory().getPath(), filename);
			}

			try {
				FileOutputStream out = new FileOutputStream(file);
				finBit.compress(Bitmap.CompressFormat.PNG, 90, out);
				out.close();
				CharSequence toastText = filename + " saved.";
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, toastText, duration);
				toast.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void load(String filename){
		File file = new File(filename);
		Bitmap loaded = Bitmap.createBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
		mBitmap = loaded.copy(Bitmap.Config.ARGB_8888, true);
		hBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		invalidate();
		mCanvas = new Canvas(mBitmap);
		hCanvas = new Canvas(hBitmap);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if(highlighter)
			canvas.drawPath(mPath, highlight);
		canvas.drawBitmap(hBitmap, 0, 0, mBitmapPaint);
		canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
		if (!highlighter)
			canvas.drawPath(mPath,mPaint);
	}
	

	private float mX, mY;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent ev){
		Log.i(text,"T");
		invalidate();
		if (keyCode==KeyEvent.KEYCODE_ENTER)
			text = "";
		return true;
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			System.out.println(text);
			mPath.reset();
			mPath.moveTo(x, y);
			if (!highlighter)
				mCanvas.drawPath(mPath,mPaint);
			else
				hCanvas.drawPath(mPath,highlight);
			mX = x;
			mY = y;
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
			mX = x;
			mY = y;
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			if (!highlighter)
				mCanvas.drawPath(mPath, mPaint);
			else
				hCanvas.drawPath(mPath, highlight);
			mPath.reset();
			invalidate();
			break;
		}
		return true;
	}   
}
