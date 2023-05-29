package cn.abin.helloworld;

import static androidx.fragment.app.FragmentManager.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.snackbar.Snackbar;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.util.EMLog;

import cn.abin.helloworld.databinding.ActivityMainBinding;
import cn.jpush.android.api.JPushInterface;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        String registrationID = JPushInterface.getRegistrationID(this);
        Log.i("jiguang", "registrationID-" + registrationID);

        EMOptions emOptions = new EMOptions();
        EMClient.getInstance().init(this, emOptions);
        EMClient.getInstance().setDebugMode(true);
//        try {
//            EMClient.getInstance().createAccount("test123", "123");
//        } catch (HyphenateException e) {
//            Log.e("error", e.getMessage(), e);
//        }
        EMClient.getInstance().login("test1234", "123", new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.d("main", "登录聊天服务器成功！");
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.d("main", "登录聊天服务器失败！");
            }
        });

        String currentUser = EMClient.getInstance().getCurrentUser();
        Log.i("huanxin", "currentUser-" + currentUser);

        //注册一个监听连接状态的listener
        EMClient.getInstance().addConnectionListener(new EMConnectionListener() {
            @Override
            public void onConnected() {
                Log.e("huanxin-error", "账号连接成功");
            }

            @Override
            public void onDisconnected(int error) {
                EMLog.d("global listener", "onDisconnect" + error);
                if (error == EMError.USER_REMOVED) {
                    Log.e("huanxin-error", "账号被移除");
                } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                    Log.e("huanxin-error", "账号冲突");
                } else if (error == EMError.SERVER_SERVICE_RESTRICTED) {
                    Log.e("huanxin-error", "账号被限制");
                } else if (error == EMError.USER_KICKED_BY_CHANGE_PASSWORD) {
                    Log.e("huanxin-error", "账号密码已修改");
                } else if (error == EMError.USER_KICKED_BY_OTHER_DEVICE) {
                    Log.e("huanxin-error", "账号被挤下线");
                }
            }

        });

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


}