package ebase.hkgrox.com.ebase.util;

import android.app.Activity;
import android.content.Context;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by root on 27/9/16.
 */

public class AppUtil {
    public static final String DATA = "data";
    public static final String POSITION = "position";
    private static final String CONTENT = "content";
    private static final String CATEGORY = "category";
    private static final String CONTENT_BOOKMARK = "content-bookmark";
    public static final String NEWS_SHARE_MESSAGE ="Read more news download ProjectOne App from PlayStore" ;
    public static final String URL = "url";
    public static final java.lang.String TITLE = "title";
    public static final String EMAIL ="rajeev0814@gmail.com" ;
    public static final String REFERENCE ="cache-data" ;
    public static final String TAB_TYPE = "tab-type";
    public static final String IS_BOOKMARKED = "bookmarked" ;
    public static final String FROM ="from" ;
    public static  boolean IS_NIGHT_MODE = false;


    public static  int SIZE_TITLE  = 18;
    public static  int SIZE_MESSAGE = 15;
    public static int getSizeMessage() {
        return SIZE_MESSAGE;
    }

    public static void setSizeMessage(int sizeMessage) {
        SIZE_MESSAGE = sizeMessage;
    }

    public static int getSizeTitle() {
        return SIZE_TITLE;
    }

    public static void setSizeTitle(int sizeTitle) {
        SIZE_TITLE = sizeTitle;
    }






   /* private static List<Content> contents ;
    public static DatabaseReference getContentReference(Activity activity) {
        return  MUtil.getFirebaseReference(activity).child("DATA").child("CONTENT");
    }

    public static synchronized boolean writeContentToFile(Context context, ArrayList<Content> data) {
        return FileReaderWriter.writeToFile(context,MUtil.getStringOfJson(data),CONTENT);
    }

    public static synchronized boolean writeCategoryToFile(Context context, ArrayList<CATEGORY> data) {
        return FileReaderWriter.writeToFile(context,MUtil.getStringOfJson(data),CATEGORY);
    }

    public static synchronized Collection<Content> readContentFromFile(Context context) {
        String data = FileReaderWriter.readFile(context,CONTENT);
         if(data!=null && !data.equalsIgnoreCase("ERROR")){
             Type collectionType = new TypeToken<Collection<Content>>(){}.getType();
             Collection<Content> enums = MUtil.getGsonObject().fromJson(data, collectionType);

            // return MUtil.getGsonObject().fromJson(data, Content.class);
             return (ArrayList<Content>)enums;
        }
        return null;
    }

    public static synchronized Collection<CATEGORY> readCategoryFromFile(Context context) {
        String data = FileReaderWriter.readFile(context,CATEGORY);
        if(data!=null && !data.equalsIgnoreCase("ERROR")){
            Type collectionType = new TypeToken<Collection<CATEGORY>>(){}.getType();
            Collection<CATEGORY> enums = MUtil.getGsonObject().fromJson(data, collectionType);
            return enums;
        }
        return null;
    }

    public static synchronized boolean writeBookmarkContentToFile(Context context, ArrayList<Content> data) {
        return FileReaderWriter.writeToFile(context,MUtil.getStringOfJson(data),CONTENT_BOOKMARK);
    }

    public static synchronized ArrayList<Content> readBookmarkContentFromFile(Context context) {
        String data = FileReaderWriter.readFile(context,CONTENT_BOOKMARK);
        if(data!=null && !data.equalsIgnoreCase("ERROR")){
            Type collectionType = new TypeToken<Collection<Content>>(){}.getType();
            Collection<Content> enums = MUtil.getGsonObject().fromJson(data, collectionType);

            // return MUtil.getGsonObject().fromJson(data, Content.class);
            return (ArrayList<Content>) enums;
        }
        return null;
    }*/


    public static DatabaseReference getFeedbackReference(Context context) {
        return  MUtil.getFirebaseReference(context).child("DATA").child("FEEDBACK");
    }

    public static String getUID(Context context) {
       return MUtil.readString(context,MUtil.UID,MUtil.getRandomID());
    }
    public static String getEmail(Context context) {
        return MUtil.readString(context,MUtil.EMAIL,MUtil.getRandomID());
    }

    public static DatabaseReference getAdvertisementReference(Activity activity) {
        return  MUtil.getFirebaseReference(activity).child("DATA").child("ADVERTISEMENT");

    }

    public static DatabaseReference getRealStateReference(Activity activity) {
        return  MUtil.getFirebaseReference(activity).child("DATA").child("REAL_STATE");

    }

    public static DatabaseReference getCouponReference(Activity activity) {
        return  MUtil.getFirebaseReference(activity).child("DATA").child("COUPON");

    }
    public static DatabaseReference getUserReference(Activity activity) {
        return  MUtil.getFirebaseReference(activity).child("DATA").child("USER");

    }

    public static DatabaseReference getGiftReference(Activity activity) {
        return  MUtil.getFirebaseReference(activity).child("DATA").child("GIFT");

    }

    public static DatabaseReference getHistoryReference(Activity activity) {
        return  MUtil.getFirebaseReference(activity).child("DATA").child("HISTORY");

    }

    public static DatabaseReference getDataReference(Activity activity) {
        return  MUtil.getFirebaseReference(activity).child("DATA");

    }
}
