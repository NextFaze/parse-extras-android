package com.nextfaze.android.parse_extras;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ParseActivity extends Activity {

    private static final String TAG = ParseActivity.class.getCanonicalName();

    public void setLogo(Drawable logo) {
        TextView title = (TextView) findViewById(R.id.title_text);
        ImageView imageView = (ImageView) findViewById(R.id.title_image);

        title.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageDrawable(logo);
    }

    public void buttonPressed(View view) {
        int viewId = view.getId();

        if(viewId == R.id.button_close) {
            finish();
        } else {
            Log.d(TAG, "unhandled button press: " + view.getId());
        }
    }

    protected String getEditText(int resId) {
        EditText editText = (EditText) findViewById(resId);
        return editText == null ? null : editText.getText().toString();
    }

    protected void showError(Exception e) {
        if(e != null)
            showMessage(e.getMessage());
    }

    protected void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void showMessage(int resId) {
        showMessage(getString(resId));
    }
}
