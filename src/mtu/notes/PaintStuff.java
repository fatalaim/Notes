package mtu.notes;

import android.graphics.Paint;
import android.graphics.Path;

public class PaintStuff {
	Path path;
	Paint paint;
	boolean highlighter = false;
	public Paint getColor() { return paint; }
    public void setColor(Paint _paint){paint = _paint;}
    public Path getPath() { return path; }
    public void setPath(Path _path){path = _path;}
    public void setHighlighter() { highlighter = true; }
    public boolean isHighlighter() { return highlighter; }
}
