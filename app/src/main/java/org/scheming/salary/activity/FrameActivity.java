package org.scheming.salary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.scheming.salary.R;
import org.scheming.salary.SalaryApplication;
import org.scheming.salary.adapter.FrameRecyclerAdapter;
import org.scheming.salary.dao.DaoSession;
import org.scheming.salary.entity.User;
import org.scheming.salary.utils.Message;

import java.util.List;

import butterknife.Bind;
import de.greenrobot.dao.query.QueryBuilder;

public class FrameActivity extends BaseActivity {
    public static final int UPDATE_USER = 1;

    @Bind(R.id.frame_recycler)
    RecyclerView mUserRecyclerV;

    private List<User> users;
    private DaoSession daoSession;
    private QueryBuilder mUserDaoBuilder;
    private FrameRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setIsInitEventBus(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);

        daoSession = SalaryApplication.getApplication().getSession();
        mUserDaoBuilder = daoSession.getUserDao().queryBuilder();
        users = mUserDaoBuilder.list();

        mAdapter = new FrameRecyclerAdapter(users);
        mUserRecyclerV.setAdapter(mAdapter);
        mUserRecyclerV.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_frame, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_frame_add) {
            Intent intent = new Intent(this, AddEmployeeActivity.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public void onEvent(Message msg) {
        if (msg.clazz.equals(getClass())) {
            switch (msg.what) {
                case UPDATE_USER:
                    users = mUserDaoBuilder.list();
                    mAdapter.setDatas(users);
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }
}
