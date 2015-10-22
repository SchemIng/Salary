package org.scheming.salary.activity;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.scheming.salary.utils.Message;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by Scheming on 2015/9/26.
 */
public class BaseActivity extends AppCompatActivity {

    private boolean isInitEventBus = false;

    public void setIsInitEventBus(boolean isInitEventBus) {
        this.isInitEventBus = isInitEventBus;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        if (isInitEventBus)
            EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        if (isInitEventBus)
            EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
