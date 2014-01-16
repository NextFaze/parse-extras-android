package com.nextfaze.android.parse_extras;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class LoginActivity extends ParseActivity {

    private static final String TAG = LoginActivity.class.getCanonicalName();
    private static final int DIALOG_FORGOT = 1000;

    private LogInCallback mLoginCallback = new LogInCallback() {
        @Override
        public void done(ParseUser parseUser, ParseException e) {
            Log.d(TAG, "login done, user: " + parseUser);
            if (parseUser != null) {
                onLoginUser(parseUser);
                finish();
            }
            else if(e != null) {
                showError(e);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.parse_extras_login_activity);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ParseUser user = ParseUser.getCurrentUser();
        if(user != null && user.isAuthenticated())
            finish();
    }

    public void buttonPressed(View view) {
        int viewId = view.getId();

        if(viewId == R.id.button_facebook) {
            onFacebookButtonPressed();
        }
        else if(viewId == R.id.button_forgot) {
            onForgotButtonPressed();
        }
        else if(viewId == R.id.button_log_in) {
            onLoginButtonPressed();
        }
        else if(viewId == R.id.button_sign_up) {
            onSignupButtonPressed();
        }
        else {
            super.buttonPressed(view);
        }
    }

    public void onLoginUser(ParseUser parseUser) {
    }

    public void onSignupButtonPressed() {
        startActivity(new Intent(this, SignupActivity.class));
    }

    public void onForgotButtonPressed() {
        showDialog(DIALOG_FORGOT);
    }

    public void onLoginButtonPressed() {
        ParseUser.logInInBackground(getEditText(R.id.username), getEditText(R.id.password), mLoginCallback);
    }

    public void onFacebookButtonPressed() {
        ParseFacebookUtils.logIn(this, mLoginCallback);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch(id) {
            case DIALOG_FORGOT:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.parse_extras_forgot_dialog, null);

                builder.setView(dialogView);
                builder.setTitle(R.string.parse_extras_forgot_dialog_title);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText emailView = (EditText) dialogView.findViewById(R.id.email);
                        final String email = emailView == null ? null : emailView.getText().toString();
                        ParseUser.requestPasswordResetInBackground(email, new RequestPasswordResetCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e != null) {
                                    showError(e);
                                } else {
                                    showMessage(getString(R.string.parse_extras_password_reset, email));
                                }
                            }
                        });
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                return builder.create();

            default:
                return super.onCreateDialog(id);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
    }
}
