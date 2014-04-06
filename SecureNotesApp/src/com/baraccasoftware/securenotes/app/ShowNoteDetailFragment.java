package com.baraccasoftware.securenotes.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.AndroidCharacter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.baraccasoftware.securenotes.object.ActivityUtilityInterface;
import com.baraccasoftware.securenotes.object.BitmapUtility;
import com.baraccasoftware.securenotes.object.Note;
import com.baraccasoftware.securenotes.object.NoteUtility;
import com.baraccasoftware.securenotes.widget.SlidingDrawer;
import com.baraccasoftware.securenotes.widget.UndoBarController;

import org.apache.http.util.EncodingUtils;

/**
 * Created by Maruta on 01/04/2014.
 */
public class ShowNoteDetailFragment extends Fragment {



    public static final String ARG_ITEM = "item_note";
    public static final  int SHOW_MODIFY_NOTE = 10 ;
    private Note mItem;
    private int idNota;
    private ImageView mImageView;


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
        TextView dateView = (TextView) rootView.findViewById(R.id.noteData);
        TextView titleNote = (TextView) rootView.findViewById(R.id.noteTitle);
        TextView descNote = (TextView) rootView.findViewById(R.id.descNote);
        mImageView = (ImageView) rootView.findViewById(R.id.noteImage);

        if(mItem.getmDate() != null){
            dateView.setText(getActivity().getResources().getString(R.string.last_modified_data)+": "+mItem.getmDataString());
        }
        if(mItem.getmName() != null && !mItem.getmName().equals("")){
            titleNote.setText(mItem.getmName());
        }
        else{
            titleNote.setText(getActivity().getResources().getString(R.string.no_title));
            titleNote.setTextColor(getActivity().getResources().getColor(R.color.popup_gray));
        }

        if(mItem.getmDesc() != null && !mItem.getmDesc().equals("")){
            descNote.setText(mItem.getmDesc());
        }
        else{
            descNote.setText(getActivity().getResources().getString(R.string.no_desc));
            descNote.setTextColor(getActivity().getResources().getColor(R.color.popup_gray));
        }

        if(mItem.getmImage() != null && mItem.getmImage().length>1){
            LoadPicTask task = new LoadPicTask(null,mItem);
            task.execute();
        }
        else{
            mImageView.setImageDrawable((getActivity().getResources().getDrawable(R.drawable.no_image_available)));
        }
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

    public class LoadPicTask extends AsyncTask<Void,Void,Boolean> {


        Bitmap bitmap;
        byte[] arrayPic;
        String filePath;
        Note fromFile;

        public  LoadPicTask(String filePath, Note fromFile){

            this.filePath = filePath;
            this.fromFile = fromFile;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            if(fromFile == null){

                bitmap = BitmapUtility.decodeFile(filePath);
                try{
                    arrayPic = BitmapUtility.compressBitmap(bitmap,45);
                }catch (Exception e){
                    arrayPic = new byte[1];
                    return false;
                }
            }else {
                //from note
                try{

                    arrayPic = NoteUtility.decryptImg(fromFile.getmImage(), getActivity().getApplicationContext());

                }catch (Exception e){
                    //nothing
                    arrayPic = fromFile.getmImage();
                }finally {
                    bitmap = BitmapFactory.decodeByteArray(arrayPic, 0, arrayPic.length);
                }
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                setImage(bitmap, arrayPic);
            }else{
                //
                setImage(bitmap,null);
            }
        }
    }

    public void setImage(final Bitmap bitmap,byte[] imgCompressed){
        mImageView.setImageBitmap(bitmap);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog nagDialog = new Dialog(getActivity());
                nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                nagDialog.setCancelable(true);
                nagDialog.setContentView(R.layout.preview_image_detail_note);

                ImageView ivPreview = (ImageView)nagDialog.findViewById(R.id.iv_preview_image);
                ivPreview.setImageBitmap(bitmap);


                nagDialog.show();
            }
        });


    }


}