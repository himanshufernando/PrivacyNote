package tkhub.project.PrivacyNote.ui.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Himanshu on 11/26/2015.
 */
public class FontSpalsh extends TextView {
    public FontSpalsh(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public FontSpalsh(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FontSpalsh(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Font/Comfortaa-Light.ttf");
        setTypeface(tf);
    }

}
