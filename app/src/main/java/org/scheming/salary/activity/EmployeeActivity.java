package org.scheming.salary.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.scheming.salary.R;
import org.scheming.salary.SalaryApplication;
import org.scheming.salary.adapter.MonthsRecyclerAdapter;
import org.scheming.salary.dao.DaoSession;
import org.scheming.salary.dao.SalaryDao;
import org.scheming.salary.dao.UserDao;
import org.scheming.salary.entity.Salary;
import org.scheming.salary.entity.User;
import org.scheming.salary.utils.ConstField;
import org.scheming.salary.utils.Message;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.event.EventBus;

public class EmployeeActivity extends BaseActivity implements View.OnClickListener,
        MonthsRecyclerAdapter.ItemClickListener {

    @Bind(R.id.add_employee_name_edit)
    EditText mNameET;

    @Bind(R.id.add_employee_base_salary_edit)
    EditText mBaseSalaryET;

    @Bind(R.id.add_employee_join_date_text)
    TextView mJoinDateTV;

    @Bind(R.id.employee_months_recycler)
    RecyclerView mRecyclerView;

    private DaoSession mDaoSession;
    private Date mJoinDate;
    private List<Salary> mSalarys;
    private QueryBuilder mSalaryBuilder;
    private MonthsRecyclerAdapter mAdapter;
    private boolean isAddEmployee;
    private Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setIsInitEventBus(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        mJoinDateTV.setOnClickListener(this);
        mDaoSession = SalaryApplication.getApplication().getSession();

        isAddEmployee = getIntent().getBooleanExtra(ConstField.IS_ADD_EMPLOYEE, false);
        userId = getIntent().getLongExtra(UserDao.Properties.Id.name, -1);

        if (!isAddEmployee) {
            User user = mDaoSession.getUserDao().queryBuilder().where(UserDao.Properties.Id.eq(userId)).list().get(0);
            if (getSupportActionBar() != null)
                getSupportActionBar().setTitle(user.getName());

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(user.getJoin_date());

            mNameET.setText(user.getName());
            mBaseSalaryET.setText(String.valueOf(user.getBase_salary()));
            mJoinDateTV.setText(String.format("%d-%d-%d", calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)));
            mJoinDate = user.getJoin_date();

            mSalaryBuilder = SalaryApplication.getApplication().getSession().getSalaryDao().queryBuilder();
            mSalarys = mSalaryBuilder.list();
            mAdapter = new MonthsRecyclerAdapter(mSalarys);

            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mAdapter.setOnItemClickListener(this);

            mRecyclerView.requestFocus();
        } else mRecyclerView.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        int year;
        int month;
        int day;

        Calendar calendar = Calendar.getInstance();
        if (mJoinDate != null)
            calendar.setTime(mJoinDate);

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog pickerDialog = new DatePickerDialog(this, new DatePickerCallBack(),
                year, month, day);
        pickerDialog.show();
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Intent intent = getIntent().setClass(this, SalaryActivity.class);
        String month = ((TextView) view.findViewById(R.id.months_recycler_item_name)).getText().toString();
        Salary salary = (Salary) mSalaryBuilder.where(SalaryDao.Properties.Current_month.eq(Integer.valueOf(month)),
                SalaryDao.Properties.User.eq(userId)).list().get(0);
        intent.putExtra(ConstField.SALARY_ID, salary.getId());
        intent.putExtra(ConstField.USER_ID, userId);
        startActivity(intent);
    }

    public class DatePickerCallBack implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mJoinDateTV.setText(String.format("%d-%d-%d", year, monthOfYear + 1, dayOfMonth));
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            mJoinDate = calendar.getTime();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isAddEmployee)
            getMenuInflater().inflate(R.menu.menu_employee, menu);
        else getMenuInflater().inflate(R.menu.menu_month, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add_employee_done) {
            if (checkInput()) {
                saveUser();
                EventBus.getDefault().post(new Message(ConstField.UPDATE_USER, FrameActivity.class));
                finish();
            } else {
                Toast.makeText(this, "请输入完整信息", Toast.LENGTH_SHORT).show();
            }
        } else if (item.getItemId() == R.id.menu_month_add) {
            Intent intent = new Intent(this, SalaryActivity.class);
            intent.putExtra(ConstField.IS_ADD_MONTH, true);
            intent.putExtra(ConstField.USER_ID, userId);
            startActivity(intent);
        }
        return true;
    }

    private boolean checkInput() {
        return !(TextUtils.isEmpty(mNameET.getText())
                || TextUtils.isEmpty(mBaseSalaryET.getText())
                || mJoinDate == null);
    }

    private void saveUser() {
        UserDao dao = mDaoSession.getUserDao();

        float base = Float.valueOf(mBaseSalaryET.getText().toString());
        long row = dao.insertOrReplace(new User(userId == -1 ? null : userId, mNameET.getText().toString(),
                mJoinDate, base));
        if (row == userId)
            Toast.makeText(this, "更新成功", Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
    }

    public void onEvent(Message msg) {
        if (msg.clazz.equals(getClass())) {
            mSalarys = mSalaryBuilder.list();
            mAdapter.setDatas(mSalarys);
            mAdapter.notifyDataSetChanged();
        }

    }
}
