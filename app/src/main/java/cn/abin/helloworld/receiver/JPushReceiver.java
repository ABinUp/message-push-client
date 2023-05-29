package cn.abin.helloworld.receiver;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

import cn.abin.helloworld.MainActivity;
import cn.abin.helloworld.R;
import cn.abin.helloworld.event.JPushReceiverMessageShowEvent;
import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.service.JPushMessageReceiver;

public class JPushReceiver extends JPushMessageReceiver {

    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        super.onMessage(context, customMessage);
        String jsonString = JSONObject.toJSONString(customMessage, true);
        Log.i("jiguang-receiver", jsonString);
//        try {
//            View view = View.inflate(context, R.layout.fragment_first, null);
//            TextView textView = view.findViewById(R.id.textview_received_message);
//            textView.setText(new Date() + "接收到消息\n");
//            textView.append(jsonString);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        sendToastMsg(context, "接收到自定义消息");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(new Date());
        stringBuilder.append("\n");
        stringBuilder.append(jsonString);

        JPushReceiverMessageShowEvent.getInstance().showMessage(stringBuilder.toString());
    }

    @Override
    public void onConnected(Context context, boolean b) {
        super.onConnected(context, b);
        if (b) {
            // 连接成功
            Log.i("jiguang-connect", "connect success");
            sendToastMsg(context, "极光连接成功");
        } else {
            // 连接断开
            Log.i("jiguang-connect", "connect unlinked");
            sendToastMsg(context, "极光连接已断开");
        }
    }

    @Override
    public void onRegister(Context context, String s) {
        super.onRegister(context, s);

        sendToastMsg(context, "极光连接已注册");

    }

    private void sendToastMsg(Context context, String msg) {
        Looper looper = Looper.myLooper();
        if (looper == null) {
            Looper.prepare();
        }
        Toast.makeText(context, (CharSequence) msg, Toast.LENGTH_SHORT).show();
    }

}
