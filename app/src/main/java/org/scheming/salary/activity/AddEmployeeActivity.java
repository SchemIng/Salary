package org.scheming.salary.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.scheming.salary.R;
import org.scheming.salary.SalaryApplication;
import org.scheming.salary.dao.DaoSession;
import org.scheming.salary.dao.UserDao;
import org.scheming.salary.entity.User;
import org.scheming.salary.utils.Message;

import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

public class AddEmployeeActivity extends BaseActivity implements View.OnClickListener {
    private Date mJoinDate;

    @Bind(R.id.add_employee_name_edit)
    EditText mNameET;

    @Bind(R.id.add_employee_base_salary_edit)
    EditText mBaseSalaryET;

    @Bind(R.id.add_employee_join_date_text)
    TextView mJoinDateTV;

    private DaoSession mDaoSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        mJoinDateTV.setOnClickListener(this);
        mDaoSession = SalaryApplication.getApplication().getSession();

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
        getMenuInflater().inflate(R.menu.menu_add_employee, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add_employee_done) {
            if (checkInput()) {
                insertUser();
                EventBus.getDefault().post(new Message(FrameActivity.UPDATE_USER, FrameActivity.class));
                finish();
            } else {
                Toast.makeText(this, "请输入完整信息", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }

    private boolean checkInput() {
        return !(TextUtils.isEmpty(mNameET.getText())
                || TextUtils.isEmpty(mBaseSalaryET.getText())
                || mJoinDate == null);
    }

    private void insertUser() {
        UserDao dao = mDaoSession.getUserDao();
        dao.insert(new User(null, mNameET.getText().toString(), mJoinDate,
                Float.valueOf(mBaseSalaryET.getText().toString())));
    }
}
