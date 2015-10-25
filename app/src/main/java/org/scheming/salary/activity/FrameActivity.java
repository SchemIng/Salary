package org.scheming.salary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.scheming.salary.R;
import org.scheming.salary.SalaryApplication;
import org.scheming.salary.adapter.FrameRecyclerAdapter;
import org.scheming.salary.dao.UserDao;
import org.scheming.salary.entity.User;
import org.scheming.salary.utils.ConstField;
import org.scheming.salary.utils.Message;

import java.util.List;

import butterknife.Bind;
import de.greenrobot.dao.query.QueryBuilder;

public class FrameActivity extends BaseActivity implements FrameRecyclerAdapter.ItemClickListener {
    @Bind(R.id.frame_recycler)
    RecyclerView mUserRecyclerV;

    private List<User> users;
    private QueryBuilder mUserDaoBuilder;
    private FrameRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setIsInitEventBus(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);

        mUserDaoBuilder = SalaryApplication.getApplication().getSession().getUserDao().queryBuilder();
        users = mUserDaoBuilder.list();

        mAdapter = new FrameRecyclerAdapter(users);
        mUserRecyclerV.setAdapter(mAdapter);
        mUserRecyclerV.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_frame, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_frame_add) {
            Intent intent = new Intent(this, EmployeeActivity.class);
            intent.putExtra(ConstField.IS_ADD_EMPLOYEE, true);
            startActivity(intent);
        }
        return true;
    }

    public void onEvent(Message msg) {
        if (msg.clazz.equals(getClass())) {
            switch (msg.what) {
                case ConstField.UPDATE_USER:
                    users = mUserDaoBuilder.list();
                    mAdapter.setDatas(users);
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Intent intent = new Intent(this, EmployeeActivity.class);
        intent.putExtra(UserDao.Properties.Id.name, users.get(position).getId());
        startActivity(intent);
    }
}
