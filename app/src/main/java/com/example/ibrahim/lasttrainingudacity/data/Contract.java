package com.example.ibrahim.lasttrainingudacity.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ibrahim on 19/12/17.
 */

public class Contract {

    public static class Entry implements BaseColumns {


        public static final String TAG = "MyMessag";
      //  public static final String DATE_TIME_FORMATE = "dd/MM/yyyy";
      //public static final String DATE_TIME_FORMATE = "EEE,dd,MMM,yyy";
      public static final String DATE_TIME_FORMATE = "EEE,dd,MMM,yyy  HH:mm";

        public static final String DATABASE_NAME = "whatsapp";
        public static final String TABLE = "users";
        public static final String REPLY = "reply";
        public static final String TIME_REPLY = "time_reply";
        public static final String IMAGE_REPLY = "image_reply";
        public static final String VEDIO_REPLY = "vedio_reply";

        public static final String ID = "id";
        public static final String MESSAGE = "message";
        public static final String TIME = "time";
        public static final String IMAGE = "image";
        public static final String VEDIO = "vedio";
        public static final String ONLINE = "online";


    }
}
