package ebase.hkgrox.com.ebase.ui;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class FirebaseInstanceIdService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        if(remoteMessage.getNotification().getTitle()!=null) {
            notifyuser(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                sendPushNotification(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void sendPushNotification(JSONObject json) {

        try {
            //getting the json data
            JSONObject data = json.getJSONObject("data");

            //parsing json data
            String title = data.getString("title");
            String message = data.getString("message");
            String imageUrl = data.getString("image");

            //creating MyNotificationManager object
            MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());

            //creating an intent for the notification
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

            //if there is no image
            if(imageUrl.equals("null")){
                //displaying small notification
                mNotificationManager.showSmallNotification(title, message, intent);
            }else{
                //if there is an image
                //displaying a big notification
                mNotificationManager.showBigNotification(title, message, imageUrl, intent);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    @Override
    public void onNewToken(@NonNull String s) {
        Log.d("TAG","newtoken"+s);

        super.onNewToken(s);
    }
    public void notifyuser(String title,String message)
    {
        MyNotificationManager notificationManager=new MyNotificationManager(getApplicationContext());
//        Intent intent=new Intent(getApplicationContext(),ExpandMessage.class);
//        intent.putExtra("title",title);
//        intent.putExtra("message",message);

//        notificationManager.showSmallNotification(title,message,intent);
      //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      //  startActivity(intent);
    }
}
