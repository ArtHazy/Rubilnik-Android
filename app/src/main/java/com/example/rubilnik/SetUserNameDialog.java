package com.example.rubilnik;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

public class SetUserNameDialog extends DialogFragment {

    public interface activityContains_getStringFromDialog {
        void getStringFromDialog(String value);
    };
    Button usernameButton;
    TextInputEditText renameDialogInputText;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View dialogView = inflater.inflate(R.layout.rename_dialog,null);


        renameDialogInputText = dialogView.findViewById(R.id.renameDialogInputText);


        Dialog dialog = new Dialog(getContext(), android.R.style.Theme_Dialog);
        dialog.setContentView(R.layout.rename_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));




//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(requireContext());
//        alertDialogBuilder.setView(dialogView);
//        alertDialogBuilder.setPositiveButton("Rename", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                activityContains_getStringFromDialog activity = (activityContains_getStringFromDialog) getActivity();
//                activity.getStringFromDialog(renameDialogInputText.getText().toString());
//            }
//        });
//        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });

//        AlertDialog dialog = alertDialogBuilder.create();

        dialog.getWindow().setGravity(Gravity.BOTTOM);

        return dialog;
    }
}
