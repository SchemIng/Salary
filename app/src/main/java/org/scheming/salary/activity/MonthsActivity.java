package org.scheming.salary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.scheming.salary.R;
import org.scheming.salary.SalaryApplication;
import org.scheming.salary.adapter.MonthsRecyclerAdapter;
import org.scheming.salary.dao.SalaryDao;
import org.scheming.salary.dao.UserDao;
import org.scheming.salary.entity.Salary;
import org.scheming.salary.utils.Message;

import java.util.List;

import butterknife.Bind;
import de.greenrobot.dao.query.QueryBuilder;

public class MonthsActivity extends BaseActivity implements MonthsRecyclerAdapter.ItemClickListener {
    public static final int UPDATE_MONTH = 1;

    @Bind(R.id.months_recycler)
    RecyclerView recyclerView;

    private List<Salary> mSalarys;
    private QueryBuilder mSalaryBuilder;
    private MonthsRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setIsInitEventBus(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_months);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(
                    getIntent().getExtras().getString(UserDao.Properties.Name.name));

        mSalaryBuilder = SalaryApplication.getApplication().getSession().getSalaryDao().queryBuilder();
        mSalarys = mSalaryBuilder.list();
        mAdapter = new MonthsRecyclerAdapter(mSalarys);

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Intent intent = getIntent().setClass(this, SalaryActivity.class);
        String month = ((TextView) view.findViewById(R.id.months_recycler_item_name)).getText().toString();
//        mSalaryBuilder.where(SalaryDao.Properties.Current_month.eq(Integer.valueOf(month)),
//                SalaryDao.Properties.User.eq());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_month, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_month_add) {
            Intent intent = getIntent().setClass(this, SalaryActivity.class);
            startActivity(intent);
        }
        return true;
    }

    public void onEvent(Message msg) {
        if (msg.clazz == getClass() && msg.what == UPDATE_MONTH) {
            mSalarys = mSalaryBuilder.list();
            mAdapter.setDatas(mSalarys);
            mAdapter.notifyDataSetChanged();
        }
    }
}
