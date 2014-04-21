package com.baraccasoftware.securenotes.app;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Asus on 17/04/2014.
 */
public class SecurityQuestionDialogFragment extends DialogFragment {

    public static final String SEC_QUESTION = "SEC_QUESTION";
    public static final String ANSWER_QUEST = "ANSWER_QUEST";



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        final Dialog dialog = new Dialog(getActivity());
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.security_question_layout );
        dialog.setTitle(getActivity().getResources().getString(R.string.title_security_question_dialog));

        // set the custom dialog components - text, image and button
        final EditText questionEditText = (EditText) dialog.findViewById(R.id.question_edit_text);
        final EditText answerEditText = (EditText) dialog.findViewById(R.id.answer_question_edit_text);
        Button dialogButton = (Button) dialog.findViewById(R.id.save_answer_buttton);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(SEC_QUESTION, questionEditText.getText().toString());
                editor.putString(ANSWER_QUEST, answerEditText.getText().toString());
                // Commit the edits!
                editor.commit();
                Context context = getActivity().getApplicationContext();
                CharSequence text = getResources().getString(R.string.secure_question_saved);
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                dialog.dismiss();

            }
        });

        return dialog;
    }
}