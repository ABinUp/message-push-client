package cn.abin.helloworld.receiver;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.hyphenate.notification.EMNotificationMessage;
import com.hyphenate.notification.core.EMNotificationIntentReceiver;

public class HXPushReceiver extends EMNotificationIntentReceiver {

    @Override
    public void onNotifyMessageArrived(Context context, EMNotificationMessage emNotificationMessage) {
        super.onNotifyMessageArrived(context, emNotificationMessage);
        String toJSONString = JSONObject.toJSONString(emNotificationMessage);
        Log.i("huanxin-notification", "received-" + toJSONString);
    }
}
