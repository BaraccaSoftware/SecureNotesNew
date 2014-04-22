package com.baraccasoftware.securenotes.app;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baraccasoftware.app.utils.UtilsClass;
import com.baraccasoftware.securenotes.object.PasswordPreference;

/**
 * Created by angelo on 24/02/14.
 * LogIn Fragment
 * user must insert password to enter in app
 */
public class LogInFragment extends Fragment {
    TextView ins_password;
    Button enter;

    MainActivity mActivity;

    public LogInFragment(){}

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (MainActivity) activity;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.log_in_fragment_layout,container,false);
        
        TextView mainTitle = (TextView) view.findViewById(R.id.textView_appname2);
        UtilsClass.setCustomFontToTextView(mainTitle, getActivity().getAssets());

        TextView retrievePassword = (TextView) view.findViewById(R.id.retrieve_password_text);
        retrievePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // custom dialog
                FragmentManager fm = getFragmentManager();
                RetrievePasswordDialogFragment alert = new RetrievePasswordDialogFragment();
                alert.setCancelable(true);
                alert.show(fm, "Alert_Dialog");
            }
        });

        
        TextView accessText = (TextView) view.findViewById(R.id.access_text_view);

        UtilsClass.setCustomFontToTextView(accessText, getActivity().getAssets());
        
        ins_password = (EditText) view.findViewById(R.id.editText_password);


        ins_password.invalidate();
        enter = (Button) view.findViewById(R.id.button_accedi);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPassword();
            }
        });
        return view;
    }

    private void checkPassword(){
        PasswordPreference passwordPreference = new PasswordPreference(mActivity.getApplicationContext());
        String password = passwordPreference.getPassword();
        String inputPassword = ins_password.getText().toString().trim();
        if(password.equals(inputPassword)){
            passwordPreference.setLockedApp(false);
            //match password
            ins_password.setText("");
            ins_password.setHint(R.string.esito_ok);
            ins_password.setHintTextColor(Color.GREEN);
            //start activity
            startNoteActivity();


        }else{
            ins_password.setText("");
            Toast.makeText(mActivity.getApplicationContext(), R.string.password_diverse,Toast.LENGTH_LONG).show();
        }
    }

    private void startNoteActivity(){
        //start note activity
        //Toast.makeText(mActivity.getApplicationContext(),"caricamento nuova activity",Toast.LENGTH_LONG).show(); //DEBUG
        mActivity.startNoteActivity();
    }
}
