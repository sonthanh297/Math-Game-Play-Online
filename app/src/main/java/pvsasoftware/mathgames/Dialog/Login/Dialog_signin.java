package pvsasoftware.mathgames.Dialog.Login;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;

import pvsasoftware.mathgames.Dialog.DialogListener;
import pvsasoftware.mathgames.R;

/**
 * Created by thien on 12/21/2016.
 */

public class Dialog_signin extends Dialog implements View.OnClickListener,LoginInterface {
        private Context context;
        //private Button btn_signin,btn_login;
        private SignInButton btnGoogleSignIn;
        //private EditText et_email,et_pass;
        private DialogListener.MultiplayerInterface dialogListener;
        private DialogListener.SignInInterface siginInInterface;
        private ProgressBar progressBar;
        private LoginHandler loginHandler;

    public Dialog_signin(Context context,DialogListener.MultiplayerInterface dialogListener,DialogListener.SignInInterface siginInInterface){
        super(context);
        this.context = context;
        this.dialogListener = dialogListener;
        this.siginInInterface = siginInInterface;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_signin);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(false);
        init();
        loginHandler = new LoginHandler(this);
    }
    private void init() {
//        btn_login = (Button)findViewById(R.id.btn_login);
//        btn_login.setOnClickListener(this);
//        btn_signin = (Button)findViewById(R.id.btn_signup);
//        btn_signin.setOnClickListener(this);
//        et_email = (EditText)findViewById(R.id.et_signin_email);
//        et_email.setOnClickListener(this);
//        et_pass = (EditText)findViewById(R.id.et_signin_password);
//        et_pass.setOnClickListener(this);
        btnGoogleSignIn = (SignInButton)findViewById(R.id.btnGoogleSignin);
        btnGoogleSignIn.setOnClickListener(this);
        progressBar = (ProgressBar)findViewById(R.id.progressBar2);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            case R.id.btn_login:
//                   handleSignIn();
//                break;
//            case R.id.btn_signup:
//                handleSignUp();
//                break;
            case R.id.btnGoogleSignin:
                handleGoogleSign();
                break;
//            case R.id.et_signin_email:
//                break;
//            case R.id.et_signin_password:
//                break;
            default:
                break;


        }
    }

    @Override
    public void enableInputs() {
                enableInput(true);
    }

    @Override
    public void disableInputs() {
            enableInput(false);
    }
    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);

    }
    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }
    @Override
    public void handleSignUp() {
       // loginHandler.signUp(et_email.getText().toString(),et_pass.getText().toString());
    }
    @Override
    public void handleSignIn() {
       // loginHandler.signIn(et_email.getText().toString(),et_pass.getText().toString());
    }

    @Override
    public void handleGoogleSignIn(GoogleSignInAccount account) {
        loginHandler.googleSignIn(account);
    }

    private void handleGoogleSign() {
        siginInInterface.openGoogleSignin();
    }

    @Override
    public void gotoMultiPlayerActivity() {
            dialogListener.openMultiplayer();
           dismiss();
    }

    @Override
    public void loginError(String error) {
           // et_pass.setText("");
          //  String msgError = String.format(context.getString(R.string.login_error_signin),error);
           // et_pass.setError(msgError);
    }

    @Override
    public void newUserError(String error) {
      //  et_pass.setText("");
        String msgError = String.format(context.getString(R.string.login_error_signup),error);
      //  et_pass.setError(msgError);
    }
    private void enableInput(boolean enable){
     //   btn_signin.setEnabled(enable);
     //   btn_login.setEnabled(enable);
     //   et_email.setEnabled(enable);
     //   et_pass.setEnabled(enable);
        btnGoogleSignIn.setEnabled(enable);
    }
}
