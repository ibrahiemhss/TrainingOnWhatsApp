package com.example.ibrahim.lasttrainingudacity.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;


import com.example.ibrahim.lasttrainingudacity.MainActivity2;
import com.example.ibrahim.lasttrainingudacity.R;
import com.example.ibrahim.lasttrainingudacity.data.SharedPrefManager;
import com.example.ibrahim.lasttrainingudacity.model.MessageModel;

import java.util.List;

/**
 * Created by Administrator on 08/07/2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessegeHolder> {
    private Cursor mCursor;
    private Context mContext;
    private MediaPlayer mMediaPlayer;
    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {

                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources

            }
        }
    };
    /** Handles audio focus when playing a sound file */
    private AudioManager mAudioManager;

    List<MessageModel> dataModelArrayList;

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
    public void onBindViewHolder(final MessageAdapter.MessegeHolder holder, final int position) {
        MessageModel dataModel=dataModelArrayList.get(position);
        holder.mTextTime.setText(dataModel.getTime ());
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
           holder.mVideoMessage.setVideoURI(videoUri);

           holder.mVideoMessage.setVisibility (View.VISIBLE);
           holder.rootS.setVisibility (View.VISIBLE);

           try{
               holder.mVideoMessage.setVideoURI(videoUri);
               holder.mVideoMessage.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                   @Override
                   public void onPrepared(MediaPlayer mp) {
                       mp.setLooping(true);
                       holder.mVideoMessage.start ();
                       holder.mVideoMessage.setOnCompletionListener (new MediaPlayer.OnCompletionListener () {
                           @Override
                           public void onCompletion (MediaPlayer mediaPlayer) {
                               holder.mVideoMessage.pause ();

                           }
                       });
                       holder.mVideoMessage.canPause ();


                   }
               });
           } catch (Exception e) {
               e.printStackTrace ();
           }
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


            try{
                Uri videoUri = Uri.parse(urlR);
                holder.mVideoReply.setVideoURI(videoUri);
                holder.mVideoReply.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.setLooping(true);
                        holder.mVideoReply.start();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace ();
            }
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
      private   TextView mTextTime;
       private   TextView mTextMessage;
       private  VideoView mVideoMessage,mVideoReply;
      private  ImageView mImageMessage,mImageReply, timeImageReply,timeImage;
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

   }    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}







