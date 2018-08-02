package cn.minivision.nfctest;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author xiekun
 * @date 2018/8/2.
 *
 * @description: 主页
 */
public class MainActivity extends NFCActivity {

    private LinearLayout rootView;
    public String[] colorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootView = findViewById(R.id.root_view);
        colorList = getResources().getStringArray(R.array.color_list);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(NFCEvent nfcEvent) {
        int index = (int)(Math.random() * colorList.length);
        rootView.setBackgroundColor(Color.parseColor(colorList[index]));
    }
}
