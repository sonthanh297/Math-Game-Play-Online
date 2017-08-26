package pvsasoftware.mathgames.Multiplayer.AddFriend;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import pvsasoftware.mathgames.R;

/**
 * Created by thien on 12/27/2016.
 */

public class Fragment_AddFriend extends DialogFragment implements AddFriend ,DialogInterface.OnShowListener {
        private ProgressBar progressBar;
        private EditText editText;
        private AddFriendHandler addFriendHandler;
    public Fragment_AddFriend() {
        addFriendHandler = new AddFriendHandler(this);


    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.addcontact_title)
                .setPositiveButton(R.string.addcontact_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton(R.string.addcontact_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_add_contact,null);
        builder.setView(view);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar2);
        editText = (EditText)view.findViewById(R.id.etEmail);
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(this);
        return  dialog;
    }


    @Override
    public void onShow(DialogInterface dialogInterface) {
            final AlertDialog dialog = (AlertDialog)getDialog();
        if (dialog !=null) {
            Button addButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
            Button cancelButton = dialog.getButton(Dialog.BUTTON_NEGATIVE);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addFriendHandler.addContact(editText.getText().toString());
                }
            });
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

        }
    }

    @Override
    public void showInput() {
            editText.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideInput() {
            editText.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
            progressBar.setVisibility(View.GONE);
    }

    @Override
    public void contactAdded() {
        Toast.makeText(getActivity(),R.string.addcontact_contactadded,Toast.LENGTH_LONG).show();
        dismiss();
    }

    @Override
    public void contactNotAdded() {
            hideProgress();
            showInput();
            editText.setText("");
            editText.setError(getString(R.string.addcontact_error));
    }
}
