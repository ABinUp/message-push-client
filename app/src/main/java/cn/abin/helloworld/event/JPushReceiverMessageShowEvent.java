package cn.abin.helloworld.event;

import android.widget.TextView;

import java.util.concurrent.ConcurrentHashMap;

public class JPushReceiverMessageShowEvent {

    private static Object object = new Object();
    private static volatile JPushReceiverMessageShowEvent instance;

    private JPushReceiverMessageShowEvent() {
    }

    public static JPushReceiverMessageShowEvent getInstance() {
        if (instance == null) {
            synchronized (object) {
                if (instance == null) {
                    instance = new JPushReceiverMessageShowEvent();
                }
            }
        }
        return instance;
    }

    private ConcurrentHashMap<Integer, TextView> textViewtextViewConcurrentHashMap = new ConcurrentHashMap<>();

    public void registerMessageShow(TextView textView) {
        int textViewId = textView.getId();
        textViewtextViewConcurrentHashMap.put(textViewId, textView);
    }

    public void unregisterMessageShow(TextView textView) {
        int textViewId = textView.getId();
        textViewtextViewConcurrentHashMap.remove(textViewId);
    }

    public void showMessage(String msg) {
        textViewtextViewConcurrentHashMap.forEach((id, textView) -> {
            textView.setText(msg);
        });
    }
}
