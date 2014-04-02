package com.baraccasoftware.securenotes.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.AndroidCharacter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.baraccasoftware.securenotes.object.ActivityUtilityInterface;
import com.baraccasoftware.securenotes.object.Note;
import com.baraccasoftware.securenotes.widget.SlidingDrawer;
import com.baraccasoftware.securenotes.widget.UndoBarController;

/**
 * Created by Maruta on 01/04/2014.
 */
public class ShowNoteDetailFragment extends Fragment {



    public static final String ARG_ITEM = "item_note";
    public static final  int SHOW_MODIFY_NOTE = 10 ;
    private Note mItem;
    private int idNota;


    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);


        if (getArguments().containsKey(ARG_ITEM)) {

            mItem =  getArguments().getParcelable(ARG_ITEM);
            idNota = mItem.getmId();
            //log("ID NOTE LOADED: "+ idNota);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_detail_note_fragment_layout, container, false);
        return rootView;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem saveMenuItem = menu.add("modify");
        saveMenuItem.setIcon(R.drawable.ic_content_edit);
        saveMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String title =  item.getTitle().toString();
        if(title.equals("modify")  ){
            if(getActivity() instanceof  NoteDetailActivity) {
                ((NoteDetailActivity) getActivity()).openModifyFragment();
            }
            //Tablet options
            else{
                ((NoteListActivity) getActivity()).openModifyFragment(mItem);
            }
        }
        return super.onOptionsItemSelected(item);
    }

}