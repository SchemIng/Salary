package org.scheming.salary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.scheming.salary.R;
import org.scheming.salary.SalaryApplication;
import org.scheming.salary.adapter.MonthsRecyclerAdapter;
import org.scheming.salary.entity.SalaryItem;
import org.scheming.salary.utils.Message;

import java.util.List;

import butterknife.Bind;
import de.greenrobot.dao.query.QueryBuilder;

public class MonthsActivity extends BaseActivity implements MonthsRecyclerAdapter.ItemClickListener {
    @Bind(R.id.months_recycler)
    RecyclerView recyclerView;

    private List<SalaryItem> mSalaryItems;
    private QueryBuilder mSalaryItemBuilder;
    private MonthsRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setIsInitEventBus(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_months);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(getIntent().getExtras().getString("name"));

        mSalaryItemBuilder = SalaryApplication.getApplication().getSession().getSalaryItemDao().queryBuilder();
        mSalaryItems = mSalaryItemBuilder.list();
        mAdapter = new MonthsRecyclerAdapter(mSalaryItems);

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Intent intent = new Intent(this, SalaryActivity.class);
        startActivity(intent);
    }

    @Override
    public void onEvent(Message msg) {

    }
}
