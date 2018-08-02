package cn.minivision.nfctest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.io.UnsupportedEncodingException;

/**
 * @author xiekun
 * @date 2018/8/2.
 *
 * @description: NFCActivity
 */
public class NFCActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        // nfc初始化设置
        new NfcUtils(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 当该Activity接收到NFC标签时，运行该方法
        // 调用工具方法，读取NFC数据
        try {
            String str = NfcUtils.readNFCFromTag(intent);
            Log.d("NFCActivity", "读到NFC----" + str);
            EventBus.getDefault().post(new NFCEvent());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 开启前台调度系统
        NfcUtils.mNfcAdapter.enableForegroundDispatch(this, NfcUtils.mPendingIntent, NfcUtils.mIntentFilter, NfcUtils.mTechList);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 关闭前台调度系统
        NfcUtils.mNfcAdapter.disableForegroundDispatch(this);
    }
}
