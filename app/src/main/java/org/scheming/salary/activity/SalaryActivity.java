package org.scheming.salary.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.scheming.salary.R;
import org.scheming.salary.SalaryApplication;
import org.scheming.salary.dao.AllowanceDao;
import org.scheming.salary.dao.ProjectDao;
import org.scheming.salary.dao.SalaryDao;
import org.scheming.salary.dao.UserDao;
import org.scheming.salary.entity.Allowance;
import org.scheming.salary.entity.Project;
import org.scheming.salary.entity.Salary;
import org.scheming.salary.fragment.ProjectDialogFragment;
import org.scheming.salary.utils.Message;
import org.scheming.salary.utils.ProjectMessage;
import org.scheming.salary.utils.StringUtils;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

public class SalaryActivity extends BaseActivity implements AdapterView.OnItemSelectedListener,
        View.OnClickListener {
    private long userId;

    @Bind(R.id.salary_month_spinner)
    Spinner mSpinner;

    @Bind(R.id.salary_borrow_edit)
    AppCompatEditText mBorrowET;

    @Bind(R.id.salary_cut_payment_edit)
    AppCompatEditText mCutPaymentET;

    @Bind(R.id.salary_social_security_edit)
    AppCompatEditText mSocialSecurityET;

    @Bind(R.id.salary_accident_insurance_edit)
    AppCompatEditText mAccidentET;

    @Bind(R.id.salary_life_allowance_edit)
    AppCompatEditText mLifeET;

    @Bind(R.id.salary_other_edit)
    AppCompatEditText mOtherET;

    @Bind(R.id.salary_special_edit)
    AppCompatEditText mSpecialET;

    @Bind(R.id.salary_post_edit)
    AppCompatEditText mPostET;

    @Bind(R.id.salary_personal_security_edit)
    AppCompatEditText mPersonalSecurityET;

    @Bind(R.id.salary_sale_text)
    AppCompatTextView mSaleTV;

    @Bind(R.id.salary_progress_text)
    AppCompatTextView mProgressTV;

    @Bind(R.id.salary_service_text)
    AppCompatTextView mServiceTV;

    private ProgressDialog mProgressDialog;

    private String[] mSpinnerItems;
    private SalaryDao mSalaryDao;
    private ProjectDao mProjectDao;

    private boolean isExist = true;
    private AllowanceDao mAllowanceDao;
    private Project mProjectSale;
    private Project mProjectProgress;
    private Project mProjectService;
    private List<Salary> mCurrentSalary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setIsInitEventBus(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary);

        init();
    }

    private void init() {
        mSpinnerItems = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mSpinnerItems);
        Calendar calendar = Calendar.getInstance();
        mProgressDialog = new ProgressDialog(this);
        userId = getIntent().getLongExtra(UserDao.Properties.Id.name, 0);

        mSpinner.setAdapter(adapter);
        mSpinner.setSelection(calendar.get(Calendar.MONTH));
        mSpinner.setOnItemSelectedListener(this);

        mSaleTV.setOnClickListener(this);
        mProgressTV.setOnClickListener(this);
        mServiceTV.setOnClickListener(this);

        mSalaryDao = SalaryApplication.getApplication().getSession().getSalaryDao();
        mAllowanceDao = SalaryApplication.getApplication().getSession().getAllowanceDao();
        mProjectDao = SalaryApplication.getApplication().getSession().getProjectDao();

        mCurrentSalary = mSalaryDao.queryBuilder().where(SalaryDao.Properties.User.eq(userId),
                SalaryDao.Properties.Current_month.eq(calendar.get(Calendar.MONTH))).list();
        isExist = mCurrentSalary.size() != 0;
    }

    @Override
    public void onBackPressed() {
        mProgressDialog.show();
        try {
            if (SalaryApplication.getApplication().getSession().callInTx(new SaveDataCall()))
                mProgressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onBackPressed();
    }

    private void saveData() {
        Salary salary = new Salary(null,
                StringUtils.toInteger(mSpinnerItems[mSpinner.getSelectedItemPosition()]),
                StringUtils.toFloat(mBorrowET.getText().toString()),
                StringUtils.toFloat(mCutPaymentET.getText().toString()),
                StringUtils.toFloat(mPersonalSecurityET.getText().toString()),
                userId);
        Allowance allowance = new Allowance(null,
                StringUtils.toFloat(mSocialSecurityET.getText().toString()),
                StringUtils.toFloat(mAccidentET.getText().toString()),
                StringUtils.toFloat(mLifeET.getText().toString()),
                StringUtils.toFloat(mOtherET.getText().toString()),
                StringUtils.toFloat(mSpecialET.getText().toString()),
                StringUtils.toFloat(mPostET.getText().toString()),
                salary.getId());

        String text;

        if (isExist) {
            //update
            mSalaryDao.update(salary);
            mAllowanceDao.update(allowance);
            mProjectDao.update(mProjectSale);
            mProjectDao.update(mProjectProgress);
            mProjectDao.update(mProjectService);

            text = "信息更新成功";
        } else {
            //insert
            mSalaryDao.insert(salary);
            mAllowanceDao.insert(allowance);
            if (mProjectSale != null)
                mProjectDao.insert(mProjectSale);
            if (mProjectProgress != null)
                mProjectDao.insert(mProjectProgress);
            if ((mProjectService != null))
                mProjectDao.insert(mProjectService);

            text = "添加成功";
        }

        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(new Message(MonthsActivity.UPDATE_MONTH, MonthsActivity.class));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mCurrentSalary = mSalaryDao.queryBuilder().where(SalaryDao.Properties.User.eq(userId),
                SalaryDao.Properties.Current_month.eq(mSpinnerItems[position])).list();
        isExist = mCurrentSalary.size() != 0;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onEvent(ProjectMessage msg) {
        Project project = new Project(null, msg.name, msg.cut_rate, msg.total_money, mCurrentSalary.get(0).getId());
        switch (msg.type) {
            case 1:
                mProjectSale = project;
                break;
            case 2:
                mProjectProgress = project;
                break;
            case 3:
                mProjectService = project;
                break;
        }
    }

    @Override
    public void onClick(View v) {
        ProjectDialogFragment fragment = new ProjectDialogFragment();
        Bundle bundle = new Bundle();
        String msg = "";
        int type = 0;

        switch (v.getId()) {
            case R.id.salary_sale_text:
                msg = "销售提成";
                type = 1;
                break;
            case R.id.salary_progress_text:
                msg = "实施提成";
                type = 2;
                break;
            case R.id.salary_service_text:
                msg = "服务提成";
                type = 3;
                break;
        }
        bundle.putInt("type", type);
        bundle.putString("msg", msg);
        fragment.setArguments(bundle);
    }

    class SaveDataCall implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {
            saveData();
            return true;
        }
    }
}
