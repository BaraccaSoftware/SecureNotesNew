package com.baraccasoftware.securenotes.app;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baraccasoftware.securenotes.object.PasswordPreference;

/**
 * Created by Asus on 18/04/2014.
 */
public class RetrievePasswordDialogFragment extends DialogFragment {

    private String secQuestion;
    private String response;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        //dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //setStyle(STYLE_NORMAL, android.R.style.Theme_Holo_Dialog_NoActionBar);
        dialog.setContentView(R.layout.retrieve_password_dialog_fragment );
        dialog.setTitle(getActivity().getResources().getString(R.string.title_retrieve_password_dialog));


        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        secQuestion  = settings.getString(SecurityQuestionDialogFragment.SEC_QUESTION, getResources().getString(R.string.no_available_question));
        response = settings.getString(SecurityQuestionDialogFragment.ANSWER_QUEST, "");

        // set the custom dialog components - text, image and button
        final TextView questionEditText = (TextView) dialog.findViewById(R.id.security_question);
        questionEditText.setText(secQuestion);

        final EditText answerEditText = (EditText) dialog.findViewById(R.id.get_response_edit_text);
        Button dialogButton = (Button) dialog.findViewById(R.id.check_response_buttton);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(answerEditText.getText().toString().equals(response)){
                    Context context = getActivity().getApplicationContext();

                    PasswordPreference passwordPreference = new PasswordPreference(context);
                    String password = passwordPreference.getPassword();

                    CharSequence text = getResources().getString(R.string.the_password_is) +" "+password;
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    dialog.dismiss();
                }
                else{
                    Context context = getActivity().getApplicationContext();
                    CharSequence text = getResources().getString(R.string.response_incorrect);
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    dialog.dismiss();
                }


            }
        });

        return dialog;
    }
}