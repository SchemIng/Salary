package org.scheming.salary.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.scheming.salary.R;
import org.scheming.salary.SalaryApplication;
import org.scheming.salary.dao.AllowanceDao;
import org.scheming.salary.dao.ProjectDao;
import org.scheming.salary.dao.SalaryDao;
import org.scheming.salary.entity.Allowance;
import org.scheming.salary.entity.Project;
import org.scheming.salary.entity.Salary;
import org.scheming.salary.fragment.ProjectDialogFragment;
import org.scheming.salary.utils.ConstField;
import org.scheming.salary.utils.Message;
import org.scheming.salary.utils.ProjectMessage;
import org.scheming.salary.utils.StringUtils;

import java.util.Calendar;
import java.util.concurrent.Callable;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

public class SalaryActivity extends BaseActivity implements AdapterView.OnItemSelectedListener,
        View.OnClickListener {
    @Bind(R.id.salary_month_spinner)
    Spinner mSpinner;

    @Bind(R.id.salary_borrow_edit)
    EditText mBorrowET;

    @Bind(R.id.salary_cut_payment_edit)
    EditText mCutPaymentET;

    @Bind(R.id.salary_social_security_edit)
    EditText mSocialSecurityET;

    @Bind(R.id.salary_accident_insurance_edit)
    EditText mAccidentET;

    @Bind(R.id.salary_life_allowance_edit)
    EditText mLifeET;

    @Bind(R.id.salary_other_edit)
    EditText mOtherET;

    @Bind(R.id.salary_special_edit)
    EditText mSpecialET;

    @Bind(R.id.salary_post_edit)
    EditText mPostET;

    @Bind(R.id.salary_personal_security_edit)
    EditText mPersonalSecurityET;

    @Bind(R.id.salary_sale_text)
    TextView mSaleTV;

    @Bind(R.id.salary_progress_text)
    TextView mProgressTV;

    @Bind(R.id.salary_service_text)
    TextView mServiceTV;

    private ProgressDialog mProgressDialog;

    private String[] mSpinnerItems;
    private boolean isAddMonth = true;
    private Long mUserId;

    private SalaryDao mSalaryDao;
    private ProjectDao mProjectDao;
    private AllowanceDao mAllowanceDao;

    private Project mProjectSale;
    private Project mProjectProgress;
    private Project mProjectService;
    private Salary mCurrentSalary;
    private Allowance mAllowance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setIsInitEventBus(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary);

        init();

        if (!isAddMonth) {
            Long mSalaryId = getIntent().getLongExtra(ConstField.SALARY_ID, 0);
            mCurrentSalary = mSalaryDao.queryBuilder().where(
                    SalaryDao.Properties.Id.eq(mSalaryId)).list().get(0);
            mAllowance = mCurrentSalary.getAllowance().get(0);

            for (int i = 0; i < 3; i++) {
                if (mCurrentSalary.getProjects().size() == 0)
                    break;
                Project project = mCurrentSalary.getProjects().get(i);
                switch (project.getType()) {
                    case 1:
                        mProjectSale = project;
                        break;
                    case 2:
                        mProjectSale = project;
                        break;
                    case 3:
                        mProjectSale = project;
                        break;
                }
            }

            initWithData();
        } else
            initWithNew();
    }

    private void init() {
        mProgressDialog = new ProgressDialog(this);

        mSaleTV.setOnClickListener(this);
        mProgressTV.setOnClickListener(this);
        mServiceTV.setOnClickListener(this);

        mSalaryDao = SalaryApplication.getApplication().getSession().getSalaryDao();
        mAllowanceDao = SalaryApplication.getApplication().getSession().getAllowanceDao();
        mProjectDao = SalaryApplication.getApplication().getSession().getProjectDao();

        mUserId = getIntent().getLongExtra(ConstField.USER_ID, 0);
        isAddMonth = getIntent().getBooleanExtra(ConstField.IS_ADD_MONTH, false);

        mSpinnerItems = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mSpinnerItems);
        mSpinner.setAdapter(adapter);
    }

    private void initWithNew() {
        Calendar calendar = Calendar.getInstance();
        mSpinner.setSelection(calendar.get(Calendar.MONTH));
        mSpinner.setOnItemSelectedListener(this);
    }

    private void initWithData() {
        mSpinner.setSelection(mCurrentSalary.getCurrent_month() - 1);
        mSpinner.setEnabled(false);

        mBorrowET.setText(String.valueOf(mCurrentSalary.getBorrow()));
        mCutPaymentET.setText(String.valueOf(mCurrentSalary.getCut_payment()));
        mPersonalSecurityET.setText(String.valueOf(mCurrentSalary.getPersonal_social_security()));

        mAccidentET.setText(String.valueOf(mAllowance.getAccident_insurance()));
        mLifeET.setText(String.valueOf(mAllowance.getLife_allowance()));
        mOtherET.setText(String.valueOf(mAllowance.getOther()));
        mSpecialET.setText(String.valueOf(mAllowance.getSpecial()));
        mPostET.setText(String.valueOf(mAllowance.getPost()));
        mSocialSecurityET.setText(String.valueOf(mAllowance.getSocial_security()));


    }

    private void saveData() {
        Salary salary = new Salary(mCurrentSalary == null ? null : mCurrentSalary.getId(),
                StringUtils.toInteger(mSpinnerItems[mSpinner.getSelectedItemPosition()]),
                StringUtils.toFloat(mBorrowET.getText().toString()),
                StringUtils.toFloat(mCutPaymentET.getText().toString()),
                StringUtils.toFloat(mPersonalSecurityET.getText().toString()),
                mUserId);

        Allowance allowance = new Allowance(mAllowance == null ? null : mAllowance.getId(),
                StringUtils.toFloat(mSocialSecurityET.getText().toString()),
                StringUtils.toFloat(mAccidentET.getText().toString()),
                StringUtils.toFloat(mLifeET.getText().toString()),
                StringUtils.toFloat(mOtherET.getText().toString()),
                StringUtils.toFloat(mSpecialET.getText().toString()),
                StringUtils.toFloat(mPostET.getText().toString()),
                salary.getId());

        String text;

        if (!isAddMonth) {
            //update
            mSalaryDao.update(salary);
            mAllowanceDao.update(allowance);
            if (mProjectSale != null)
            mProjectDao.update(mProjectSale);
            if (mProjectProgress != null)
            mProjectDao.update(mProjectProgress);
            if (mProjectService != null)
            mProjectDao.update(mProjectService);

            text = "信息更新成功";
        } else {
            //insert
            allowance.setSalary(mSalaryDao.insert(salary));

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

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onEvent(ProjectMessage msg) {
        Project project = new Project(null, msg.type, msg.name, msg.cut_rate, msg.total_money, mCurrentSalary.getId());
        switch (msg.type) {
            case 1:
                project.setId(mProjectSale == null ? null : mProjectSale.getId());
                mProjectSale = project;
                break;
            case 2:
                project.setId(mProjectProgress == null ? null : mProjectProgress.getId());
                mProjectProgress = project;
                break;
            case 3:
                project.setId(mProjectService == null ? null : mProjectService.getId());
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
        fragment.show(getFragmentManager(), "project");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_salary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add_month_done) {
            try {
                boolean exists = mSalaryDao.queryBuilder().where(SalaryDao.Properties.User.eq(mUserId),
                        SalaryDao.Properties.Current_month.eq(
                                StringUtils.toInteger(mSpinnerItems[mSpinner.getSelectedItemPosition()]))).list().size() != 0;

                if (exists && isAddMonth) {
                    Toast.makeText(this, "当前月份已添加", Toast.LENGTH_SHORT).show();
                    return true;
                }

                if (SalaryApplication.getApplication().getSession().callInTx(new SaveDataCall()))
                    mProgressDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }

            EventBus.getDefault().post(new Message(ConstField.UPDATE_MONTH, EmployeeActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class SaveDataCall implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {
            saveData();
            return true;
        }
    }
}
