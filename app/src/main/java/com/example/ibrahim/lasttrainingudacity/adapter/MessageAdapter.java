package com.example.ibrahim.lasttrainingudacity.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.ParseException;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.VideoView;
import com.example.ibrahim.lasttrainingudacity.R;
import com.example.ibrahim.lasttrainingudacity.data.SharedPrefManager;
import com.example.ibrahim.lasttrainingudacity.model.MessageModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessegeHolder> {
    long minutes;
    long hours;
    long days;
    long second;
    private boolean bVideoIsBeingTouched = false;
    private int position = 0;
    //MediaController to controll the videoes play and  pause
    private MediaController mediaControlsS,mediaControlsR;
    private Context mContext;
   private String mlocale;
  private   String mDatRply,mDateMessage;

    List<MessageModel> dataModelArrayList;
//constructor
    public MessageAdapter(Context context,List<MessageModel> dataModelArrayList) {
        this.dataModelArrayList = dataModelArrayList;
        this.mContext = context;

    }

    @Override
    public MessegeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.
                getContext()).inflate( R.layout.list_item, parent, false);

        MessageAdapter.MessegeHolder holder = new MessegeHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MessegeHolder holder, final int position) {

        // عمل كائن جديد من MessageModel وتخزين مواضع كل عناصر الكائن  dataModelArrayList
        MessageModel dataModel=dataModelArrayList.get(position);


        try {
            Process exec = Runtime.getRuntime().exec(new String[]{"getprop", "persist.sys.language"});
            mlocale = new BufferedReader (new InputStreamReader (exec.getInputStream())).readLine();
            exec.destroy();
            Log.e("", "Device locale: "+mlocale);
        } catch (IOException e) {
            e.printStackTrace();
        }
       /* String S= " second ago";
        String  M= " minutes ago";
        String H= " hours ago";
        String D=" days ago";
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date past = format.parse("01/10/2010");
            Date now = new Date();

            second=TimeUnit.MILLISECONDS.toMillis(now.getTime() - past.getTime());
            minutes=TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
            hours=TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime()) ;
            days=TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime()) ;


        }
        catch (Exception j){
            j.printStackTrace();
        }
        holder.mTextMessage.setText(second+S);

        if (second<60){
            holder.mTextMessage.setText(second+S);

        }else if (minutes<=60){
            holder.mTextMessage.setText(minutes+M);

        }else if (hours<=24){
            holder.mTextMessage.setText(hours+H);

        }else if (days<=5){
            holder.mTextMessage.setText(days+D);

        }else {
         //   holder.mTextTime.setText(dataModel.getTime ());
            holder.mTextMessage.setText(second+S);

        }
*/
       //to set the time from  MessageModel
       // holder.mTextTime.setText(dataModel.getTime ());

            holder.mTextTime.setText (dataModel.getTime ());



        String url = dataModel.getVedio();
        Bitmap bmp = BitmapFactory.decodeFile(dataModel.getImage());
        String urlR = dataModel.getVedio_reply ();
        Bitmap bmpR = BitmapFactory.decodeFile(dataModel.getImage_reply ());
if(SharedPrefManager.getInstance (mContext).getOn ()==1){

    holder.timeImage.setImageResource(R.drawable.ic_seen_all);
    holder.timeImageReply.setImageResource(R.drawable.ic_seen_all);

}
        if(dataModel.getReply ()==null){
            holder.textReply.setVisibility (View.GONE);


        }else {
            holder.textReply.setText(dataModel.getReply ());
            holder.textReply.setVisibility (View.VISIBLE);
            holder.rootR.setVisibility (View.VISIBLE);


        }
        if(dataModel.getTime_reply ()==null){
            holder.timeReply.setVisibility (View.GONE);


        }else {
            holder.timeReply.setText(dataModel.getTime_reply ());
            holder.timeReply.setVisibility (View.VISIBLE);
            holder.rootR.setVisibility (View.VISIBLE);


        }

        if(dataModel.getMessage ()==null){
            holder.mTextMessage.setVisibility (View.GONE);


        }else {


                holder.mTextMessage.setText(dataModel.getMessage ());

            holder.mTextMessage.setVisibility (View.VISIBLE);
            holder.rootS.setVisibility (View.VISIBLE);



        }
         if(dataModel.getImage ()==null){
             holder.mImageMessage.setVisibility (View.GONE);



         }else {
             holder.mImageMessage.setImageBitmap( bmp );
             holder.mImageMessage.setVisibility (View.VISIBLE);
             holder.rootS.setVisibility (View.VISIBLE);

         }


       if(dataModel.getVedio()==null){
           holder.mVideoMessage.setVisibility (View.GONE);


       }else {

           Uri videoUri = Uri.parse(url);

           holder.mVideoMessage.setVisibility (View.VISIBLE);
           holder.rootS.setVisibility (View.VISIBLE);

               if (mediaControlsS == null)
               {
                   mediaControlsS = new MediaController(mContext);
               }

               try
               {
                   holder.mVideoMessage.setMediaController(mediaControlsS);
                   holder.mVideoMessage.setVideoURI(videoUri);

               } catch (Exception e)
               {
                   Log.e("Error", e.getMessage());
                   e.printStackTrace();
               }

               holder.mVideoMessage.requestFocus();
           holder.mVideoMessage.setOnPreparedListener(new MediaPlayer.OnPreparedListener ()
           {

               public void onPrepared(MediaPlayer mediaPlayer)
               {

                   holder.mVideoMessage.seekTo(position);

                   System.out.println("vidio is ready for playing");

                   if (position == 0)
                   {
                       holder.mVideoMessage.start();
                   } else
                   {
                       holder.mVideoMessage.pause();
                   }
               }
           });


       }

        if(dataModel.getImage_reply ()==null){
            holder.mImageReply.setVisibility (View.GONE);



        }else {
            holder.mImageReply.setImageBitmap( bmpR );
            holder.mImageMessage.setVisibility (View.VISIBLE);
            holder.rootR.setVisibility (View.VISIBLE);

        }


        if(dataModel.getVedio_reply ()==null){
            holder.mVideoReply.setVisibility (View.GONE);


        }else {
            holder.mVideoReply.setVisibility (View.VISIBLE);
            holder.rootR.setVisibility (View.VISIBLE);
            Uri videoUri = Uri.parse(urlR);

            if (mediaControlsR == null)
            {
                mediaControlsR = new MediaController(mContext);
            }
            try
            {
                holder.mVideoReply.setMediaController(mediaControlsR);
                holder.mVideoReply.setVideoURI(videoUri);

            } catch (Exception e)
            {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            holder.mVideoReply.requestFocus();
            holder.mVideoReply.setOnPreparedListener(new MediaPlayer.OnPreparedListener ()
            {

                public void onPrepared(MediaPlayer mediaPlayer)
                {

                    holder.mVideoReply.seekTo(position);

                    System.out.println("vidio is ready for playing");

                    if (position == 0)
                    {
                        holder.mVideoReply.start();
                    } else
                    {
                        holder.mVideoReply.pause();
                    }
                }
            });


        }
    }



    @Override
    public int getItemCount()
    {
        if (dataModelArrayList != null) {
            return dataModelArrayList.size();

        }
        return 0;
    }


   public class MessegeHolder extends RecyclerView.ViewHolder  {

        LinearLayout rootR,rootS;
      private TextView mTextTime;
       public TextView mTextMessage;
       private  VideoView mVideoMessage,mVideoReply;
      private  ImageView mImageMessage;
       private ImageView mImageReply;
       public ImageView timeImageReply;
       public ImageView timeImage;
       private TextView textReply;
       private   TextView timeReply;
       private MessegeHolder(View itemView) {
            super(itemView);

           rootR = itemView.findViewById (R.id.rootR);
           rootS = itemView.findViewById (R.id.rootS);
           mTextTime = itemView.findViewById (R.id.mTextTime);
           mTextMessage = itemView.findViewById (R.id.mTextMessage);
           mImageMessage=itemView.findViewById( R.id.mImageMessage );
           mVideoMessage=itemView.findViewById( R.id.mVideoMessage );
           textReply = itemView.findViewById (R.id.textReply);
           timeReply = itemView.findViewById (R.id.timeReply);

           mVideoReply = itemView.findViewById (R.id.mVideoReply);
           mImageReply = itemView.findViewById (R.id.mImageReply);

           timeImageReply = itemView.findViewById (R.id.timeImageReply);
           timeImage = itemView.findViewById (R.id.timeImage);
       }

   }


    }









