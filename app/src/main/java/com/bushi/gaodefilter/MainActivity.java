package com.bushi.gaodefilter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView statusText = findViewById(R.id.status_text);
        Button btnOpenLsp = findViewById(R.id.btn_open_lsp);

        // 检测模块是否激活 (通过检查 MainHook 里的静态变量或其他方式)
        if (isModuleActive()) {
            statusText.setText(R.string.status_active);
            statusText.setTextColor(getColor(R.color.status_active)); // 绿色
        } else {
            statusText.setText(R.string.status_inactive);
            statusText.setTextColor(getColor(R.color.status_inactive)); // 红色
        }

        btnOpenLsp.setOnClickListener(v -> {
            try {
                // 尝试直接打开 LSPosed 管理器中本模块的页面
                Intent intent = new Intent("org.lsposed.manager.MODULE_SETTINGS");
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } catch (Exception e) {
                // 如果没安装 LSPosed 管理器或不支持该 Action，跳转到普通应用信息页
                Toast.makeText(this, R.string.lsp_open_failed, Toast.LENGTH_SHORT).show();
                try {
                    Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.parse("package:" + getPackageName()));
                    startActivity(intent);
                } catch (Exception ignored) {}
            }
        });
    }

    // 这是一个简单的激活检测方法
    private boolean isModuleActive() {
        return false; // 默认返回 false，如果被 Hook 了，MainHook 会把这里改掉
    }
}
