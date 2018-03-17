package com.example.ibrahim.lasttrainingudacity.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ibrahim.lasttrainingudacity.model.MessageModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.ibrahim.lasttrainingudacity.data.Contract.Entry.DATABASE_NAME;
import static com.example.ibrahim.lasttrainingudacity.data.Contract.Entry.ID;
import static com.example.ibrahim.lasttrainingudacity.data.Contract.Entry.IMAGE;
import static com.example.ibrahim.lasttrainingudacity.data.Contract.Entry.IMAGE_REPLY;
import static com.example.ibrahim.lasttrainingudacity.data.Contract.Entry.MESSAGE;
import static com.example.ibrahim.lasttrainingudacity.data.Contract.Entry.REPLY;
import static com.example.ibrahim.lasttrainingudacity.data.Contract.Entry.TABLE;
import static com.example.ibrahim.lasttrainingudacity.data.Contract.Entry.TIME;
import static com.example.ibrahim.lasttrainingudacity.data.Contract.Entry.TIME_REPLY;
import static com.example.ibrahim.lasttrainingudacity.data.Contract.Entry.VEDIO;
import static com.example.ibrahim.lasttrainingudacity.data.Contract.Entry.VEDIO_REPLY;


/**
 * Created by ibrahim on 19/12/17.
 */

public class MDbHelber extends SQLiteOpenHelper {

    private static final int SCHEMA = 1;
    private static final String TAG = MDbHelber.class.getSimpleName ();

    public MDbHelber (Context context) {
        super (context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate (SQLiteDatabase sqLiteDatabase) {
        final String CREATE_TB_SEND =
                "CREATE TABLE " + TABLE + "(" +
                        ID + " INTEGER  PRIMARY KEY AUTOINCREMENT , " +
                        TIME + " VARCHAR(60) , " +
                        TIME_REPLY + " VARCHAR(60) , " +
                        MESSAGE + " TEXT ," +
                        IMAGE + " TEXT ," +
                        VEDIO + " TEXT  ," +
                        REPLY + " TEXT , " +
                        IMAGE_REPLY + " TEXT ," +
                        VEDIO_REPLY + " TEXT  " +
                        ")";



        sqLiteDatabase.execSQL (CREATE_TB_SEND);


    }

    @Override
    public void onUpgrade (SQLiteDatabase sqLiteDatabase, int i, int i1) {
        throw new UnsupportedOperationException ("This shouldn't happen yet!");

    }





    public List<MessageModel> getdata() {
        List<MessageModel> data = new ArrayList<> ();
        SQLiteDatabase db = this.getWritableDatabase ();
        Cursor cursor = db.rawQuery ("select * from " + TABLE + " ;", null);
        StringBuffer stringBuffer = new StringBuffer ();
        MessageModel dataModel = null;
        while (cursor.moveToNext ()) {
             String time = cursor.getString (cursor.getColumnIndexOrThrow (TIME));
            String message = cursor.getString (cursor.getColumnIndexOrThrow (MESSAGE));
            String image = cursor.getString (cursor.getColumnIndexOrThrow (IMAGE));
            String vedio = cursor.getString (cursor.getColumnIndexOrThrow (VEDIO));
            String reply = cursor.getString (cursor.getColumnIndexOrThrow (REPLY));
            String time_reply = cursor.getString (cursor.getColumnIndexOrThrow (TIME_REPLY));
            String image_reply = cursor.getString (cursor.getColumnIndexOrThrow (IMAGE_REPLY));
            String vedio_reply = cursor.getString (cursor.getColumnIndexOrThrow (VEDIO_REPLY));

            dataModel = new MessageModel (time,message,image,vedio,reply,time_reply,image_reply,vedio_reply);

            dataModel.setTime (time);
            dataModel.setMessage (message);
            dataModel.setImage (image);
            dataModel.setVedio (vedio);
            dataModel.setTime_reply (time_reply);
            dataModel.setReply (reply);
            dataModel.setImage_reply (image_reply);
            dataModel.setVedio_reply (vedio_reply);


            stringBuffer.append (dataModel);
            // stringBuffer.append(dataModel);
            data.add (dataModel);
        }

        for (MessageModel mo : data) {

        }

        //

        return data;
    }
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }


    public void insertTextWithImage(String time,String message,
                                    String imagePath,String videoPath ,String reply,
                                    String time_reply,String image_reply,String vedio_reply){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();


        contentValues.put(TIME, time);
        contentValues.put(MESSAGE, message);
        contentValues.put(IMAGE, imagePath);
        contentValues.put(VEDIO, videoPath);
        contentValues.put(REPLY, reply);
        contentValues.put(TIME_REPLY, time_reply);
        contentValues.put(IMAGE_REPLY, image_reply);
        contentValues.put(VEDIO_REPLY, vedio_reply);

        db.insert(TABLE,null,contentValues);


    }


}