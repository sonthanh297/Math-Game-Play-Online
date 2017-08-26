package pvsasoftware.mathgames.Multiplayer.PlayOnline;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import pvsasoftware.mathgames.MainHaindler;
import pvsasoftware.mathgames.Multiplayer.FriendList.MultiplayerActivity;
import pvsasoftware.mathgames.R;

/**
 * Created by thien on 12/29/2016.
 */

public class ConnectedPlayerDialog extends DialogFragment implements DialogInterface.OnShowListener {
    public ConnectedPlayerDialog(){

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.connecting)
                .setNegativeButton(R.string.addcontact_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_connect_player,null);
        builder.setView(view);
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar2);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnShowListener(this);
        return  dialog;
    }

    @Override
    public void onShow(DialogInterface dialogInterface) {
        final AlertDialog dialog = (AlertDialog)getDialog();
        if (dialog !=null) {
            Button cancelButton = dialog.getButton(Dialog.BUTTON_NEGATIVE);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismisDialog();
                    dismiss();
                }
            });
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                   if (dialog.isShowing()) {
                       Toast.makeText(getActivity(),R.string.timeup,Toast.LENGTH_LONG).show();
                       dismisDialog();
                       dialog.dismiss();

                   }
                }
            },30000);


        }

    }

    private void dismisDialog() {
        MainHaindler.getInstance().setMultiplayer(false);
        MainHaindler.getInstance().setShowDialog(false);
        MultiplayerActivity activity = (MultiplayerActivity) getActivity();
        activity.disConnectToPlayer();
    }
}
