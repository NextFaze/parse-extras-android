package com.nextfaze.android.parse_extras;

import android.os.Bundle;
import android.view.View;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends ParseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.parse_extras_sign_up_activity);
    }

    @Override
    public void buttonPressed(View view) {

        if(view.getId() == R.id.button_sign_up) {
            performSignup();
        }
        else {
            super.buttonPressed(view);
        }
    }

    public void onSignupUser(ParseUser parseUser) {
    }

    private void performSignup() {
        final ParseUser user = new ParseUser();
        user.setUsername(getEditText(R.id.username));
        user.setPassword(getEditText(R.id.password));
        user.setEmail(getEditText(R.id.email));

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null) {
                    onSignupUser(user);
                    finish();
                } else {
                    showError(e);
                }
            }
        });
    }

}
