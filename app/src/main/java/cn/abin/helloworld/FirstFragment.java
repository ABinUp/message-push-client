package cn.abin.helloworld;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import cn.abin.helloworld.databinding.FragmentFirstBinding;
import cn.abin.helloworld.event.JPushReceiverMessageShowEvent;
import cn.jpush.android.api.JPushInterface;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        binding.buttonRegistrationId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String registrationID = JPushInterface.getRegistrationID(view.getContext());
                binding.textviewRegistrationId.setText(registrationID);
            }
        });

        String registrationID = JPushInterface.getRegistrationID(view.getContext());
        if (registrationID == null || registrationID.isEmpty()) {
            binding.textviewRegistrationId.setText("极光连接未初始化完成");
        } else {
            binding.textviewRegistrationId.setText(registrationID);
        }

        TextView textViewReceivedMsg = (TextView) view.findViewById(R.id.textview_received_message);
        textViewReceivedMsg.setText("等待接收消息");

        // 文本框 注册极光消息接收事件
        JPushReceiverMessageShowEvent.getInstance().registerMessageShow(textViewReceivedMsg);
    }

    @Override
    public void onResume() {
        super.onResume();
        // 恢复时，注册
        JPushReceiverMessageShowEvent.getInstance().registerMessageShow(binding.textviewReceivedMessage);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("custom-log", "调用destroy");
        // 文本框 取消注册极光消息接收事件
//        JPushReceiverMessageShowEvent.getInstance().unregisterMessageShow(binding.textviewReceivedMessage);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 文本框 取消注册极光消息接收事件
        JPushReceiverMessageShowEvent.getInstance().unregisterMessageShow(binding.textviewReceivedMessage);
        binding = null;
    }

}