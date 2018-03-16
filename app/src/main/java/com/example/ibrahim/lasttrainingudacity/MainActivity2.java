package com.example.ibrahim.lasttrainingudacity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.ibrahim.lasttrainingudacity.adapter.MessageAdapter;
import com.example.ibrahim.lasttrainingudacity.data.MDbHelber;
import com.example.ibrahim.lasttrainingudacity.data.SharedPrefManager;
import com.example.ibrahim.lasttrainingudacity.model.MessageModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity2 extends AppCompatActivity {
    private static final String TAG = MainActivity2.class.getSimpleName ();
    private static final int RESULT_VEDIO_CAPTURE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;

    MDbHelber database;
    RecyclerView recyclerView;
    MessageAdapter messageAdapter;
    List<MessageModel> datamodel;
    private ImageView mTakePhoto, mImgSend;
    private EditText mEtSend;
    private Uri mCapturedImageURI;
    String part_image,part_vedio;
    File actualImageFile,actualVedioFile;
    String slocale;
    public  static String slocalelanggg;
   TextView nameProfile;
    String message;
    Locale localelang;
    public  static   Drawable drawable;
    @TargetApi (Build.VERSION_CODES.M)
    private String getCurrentDefaultLocaleStr(Context context) {
        localelang = context.getResources().getConfiguration().locale;
        return localelang.getDefault().toString();
    }
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main2);
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        getCurrentDefaultLocaleStr(this);
        setSupportActionBar(tb);
        slocalelanggg= String.valueOf (localelang);
        Log.e("", " getCurrentDefaultLocaleStr: "+slocalelanggg);

        Bundle iBundle;
       showToolBar(tb,this);
        database = new MDbHelber (this);
        mTakePhoto = findViewById (R.id.mTakePhoto);
        nameProfile = findViewById (R.id.nameProfile);
        mImgSend = findViewById (R.id.mImgSend);
        nameProfile.setText (SharedPrefManager.getInstance (this).getNamesOfUsers ());

       // datamodel = new ArrayList<MessageModel> ();
        recyclerView = (RecyclerView) findViewById (R.id.mRv);
        changeListSend ();


        try {
            Process exec = Runtime.getRuntime().exec(new String[]{"getprop", "persist.sys.language"});
             slocale = new BufferedReader (new InputStreamReader (exec.getInputStream())).readLine();
            exec.destroy();
            Log.e("", "Device locale: "+slocale);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mImgSend.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                addMessag ();
                changeListSend ();

            }
        });

        mTakePhoto.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                final Dialog dialog = new Dialog (MainActivity2.this, R.style.AppTheme_Dark_Dialog);
                dialog.setContentView (R.layout.custom_dialog_box);

                dialog.findViewById (R.id.ChoosePath)
                        .setOnClickListener (new View.OnClickListener () {
                            @Override
                            public void onClick (View v) {
                                activeTakeVedio ();
                                dialog.dismiss ();


                            }
                        });
                dialog.findViewById (R.id.TakePhoto)
                        .setOnClickListener (new View.OnClickListener () {
                            @Override
                            public void onClick (View v) {
                                activeTakePhoto ();
                                dialog.dismiss ();


                            }
                        });
                dialog.show ();
            }
        });
    }

    private void changeListSend () {

        database = new MDbHelber (MainActivity2.this);
        datamodel = database.getdata ();
        Log.i ("HIteshdata", "" + datamodel);
        RecyclerView.LayoutManager reLayoutManager = new LinearLayoutManager (getApplicationContext ());
        recyclerView.setLayoutManager (reLayoutManager);
        recyclerView.setItemAnimator (new DefaultItemAnimator ());
       messageAdapter = new MessageAdapter (this,datamodel);
        recyclerView.setAdapter (messageAdapter);


    }
    public static void showToolBar(Toolbar toolbar,
                                   final AppCompatActivity activity) {
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
                if(slocalelanggg.contains ("ar")) {
                    drawable = activity.getResources ().getDrawable (
                            R.drawable.ic_arrow_right);
                }else if (slocalelanggg.contains ("en")){
                    drawable = activity.getResources ().getDrawable (
                            R.drawable.ic_arrow_left);
                }


        drawable.setColorFilter(
                activity.getResources().getColor(R.color.colorWhite),
                android.graphics.PorterDuff.Mode.SRC_ATOP);

        activity.getSupportActionBar().setHomeAsUpIndicator(drawable);
        toolbar.setBackgroundColor(activity.getResources().getColor(
                R.color.colorPrimary));
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        database = new MDbHelber (MainActivity2.this);
        Locale locale = new Locale (slocale);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE/ dd/ MMM /  yyy", locale);
      //  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", locale);
        Date currDate = new Date();
        String formattedDate = sdf.format(currDate);


        int id = item.getItemId();

        if (id == R.id.action_done) {
            if(message!=null){
                SharedPrefManager.getInstance (MainActivity2.this).saveOn (0);
                database.insertTextWithImage (null,null,null,
                        null,SharedPrefManager.getInstance (this).getPeply (),formattedDate,null,null);
                changeListSend ();

                message=null;

            }else if(part_image!=null) {
                SharedPrefManager.getInstance (MainActivity2.this).saveOn (0);

                database.insertTextWithImage (null,null,null,
                        null,null,formattedDate,part_image,null);
                part_image=null;
                changeListSend ();

            }
            else if(part_vedio!=null) {
                SharedPrefManager.getInstance (MainActivity2.this).saveOn (0);

                database.insertTextWithImage (null,null,null,
                        null,null,formattedDate,null,part_vedio);
                part_vedio=null;
                changeListSend ();

                //  changeListSend ();
            }else{
                Toast.makeText (this,getResources ().getString (R.string.no_message),Toast.LENGTH_SHORT);

            }


            return true;
        }

        if (id == R.id.action_done_all) {
            if(SharedPrefManager.getInstance (MainActivity2.this).getOn ()==0){
                SharedPrefManager.getInstance (MainActivity2.this).saveOn (1);
            }
            changeListSend ();

            return true;
        }

        if (id == R.id.action_compare) {
            database.deleteAll ();
            changeListSend ();

            return true;
        }
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addMessag () {
        database = new MDbHelber (MainActivity2.this);
        mEtSend = findViewById (R.id.mEtSend);
        message=mEtSend.getText ().toString ();

        Locale locale = new Locale (slocale);
    SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM dd, yyy", locale);
       // SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", locale);
        Date currDate = new Date();
        String formattedDate = sdf.format(currDate);

        if(message!=null) {
            SharedPrefManager.getInstance (this).saveReply (message);
           database.insertTextWithImage (formattedDate,mEtSend.getText ().toString (),null,null,null,null,null,null);
            SharedPrefManager.getInstance (MainActivity2.this).saveOn (0);

            messageAdapter = new MessageAdapter (this,datamodel);
           // recyclerView.setAdapter (messageAdapter);
            messageAdapter.notifyDataSetChanged ();

        }

        mEtSend.clearFocus ();
    }





    private void activeTakePhoto () {
        Intent takePictureIntent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity (getPackageManager ()) != null) {
            String fileName = "temp.jpg";
            ContentValues values = new ContentValues ();
            values.put (MediaStore.Images.Media.TITLE, fileName);
            mCapturedImageURI = getContentResolver ()
                    .insert (MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            values);
            takePictureIntent
                    .putExtra (MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
            startActivityForResult (takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    /**
     * to gallery
     */
    private void activeTakeVedio () {
        Intent intent = new Intent (MediaStore.ACTION_VIDEO_CAPTURE);
        if (intent.resolveActivity (getPackageManager ()) != null) {
            String fileName = "temp.3gp";
            ContentValues values = new ContentValues ();
            values.put (MediaStore.Images.Media.TITLE, fileName);
            mCapturedImageURI = getContentResolver ()
                    .insert (MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                            values);
            intent
                    .putExtra (MediaStore.EXTRA_OUTPUT, mCapturedImageURI);

            startActivityForResult (intent, RESULT_VEDIO_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult (int requestCode, int resultCode,
                                     Intent data) {

        Locale locale2 = new Locale (slocale);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM dd, yyy", locale2);
        Date currDate = new Date();
        String formattedDate = sdf.format(currDate);

        super.onActivityResult (requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_VEDIO_CAPTURE:
                if (requestCode == RESULT_VEDIO_CAPTURE &&
                        resultCode == RESULT_OK && null != data) {
                    Uri selectedImage = data.getData ();
                    String[] filePathColumn = { MediaStore.Video.Media.DATA };
                    Cursor cursor = getContentResolver ()
                            .query (selectedImage, filePathColumn, null, null,
                                    null);
                    cursor.moveToFirst ();
                    int columnIndex = cursor.getColumnIndex (filePathColumn[ 0 ]);
                    part_vedio = cursor.getString (columnIndex);

                    if (part_vedio != null) {
                        actualVedioFile = new File( part_vedio );
                        //mTakePhoto.setImageBitmap( BitmapFactory.decodeFile( actualVedioFile.getAbsolutePath() ) );
                    //    imagMessage.setVedio (part_vedio);
                        database.insertTextWithImage (formattedDate,null,null,part_vedio,null,null,null,null);
                        SharedPrefManager.getInstance (MainActivity2.this).saveOn (0);

                        changeListSend ();
                        Log.v("formattedDate",formattedDate);


                    }
                    cursor.close ();

                 //   Log.v("part_vedio",part_vedio);

                }
            case REQUEST_IMAGE_CAPTURE:
                if (requestCode == REQUEST_IMAGE_CAPTURE &&
                        resultCode == RESULT_OK) {
                    String[] projection = { MediaStore.Images.Media.DATA };
                    Cursor cursor =
                            managedQuery (mCapturedImageURI, projection, null,
                                    null, null);
                    int column_index_data = cursor.getColumnIndexOrThrow (
                            MediaStore.Images.Media.DATA);
                    cursor.moveToFirst ();
                    part_image = cursor.getString (column_index_data);
                    if (part_image != null) {
                        actualImageFile = new File( part_image );
                   //     mTakePhoto.setImageBitmap( BitmapFactory.decodeFile( actualImageFile.getAbsolutePath() ) );
                  //      imagMessage.setImage (part_image);
                        database.insertTextWithImage (formattedDate,null,part_image,null,null,null,null,null);
                        SharedPrefManager.getInstance (MainActivity2.this).saveOn (0);

                        changeListSend ();


                    }
                   // Log.v("pathphoto",part_image);

                }
        }
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        if (mCapturedImageURI != null) {
            outState.putString ("mCapturedImageURI",
                    mCapturedImageURI.toString ());
        }
        super.onSaveInstanceState (outState);
    }

    @Override
    protected void onRestoreInstanceState (Bundle savedInstanceState) {
        super.onRestoreInstanceState (savedInstanceState);
        if (savedInstanceState.containsKey ("mCapturedImageURI")) {
            mCapturedImageURI = Uri.parse (
                    savedInstanceState.getString ("mCapturedImageURI"));
        }
    }


}

