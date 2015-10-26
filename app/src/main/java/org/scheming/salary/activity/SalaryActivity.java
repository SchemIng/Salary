package org.scheming.salary.activity;

import android.app.DialogFragment;
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
import org.scheming.salary.dao.AttendanceDao;
import org.scheming.salary.dao.ProjectDao;
import org.scheming.salary.dao.SalaryDao;
import org.scheming.salary.entity.Allowance;
import org.scheming.salary.entity.Attendance;
import org.scheming.salary.entity.Project;
import org.scheming.salary.entity.Salary;
import org.scheming.salary.fragment.NumberPickerFragment;
import org.scheming.salary.fragment.ProjectDialogFragment;
import org.scheming.salary.utils.AttendanceMsg;
import org.scheming.salary.utils.ConstField;
import org.scheming.salary.utils.Message;
import org.scheming.salary.utils.ProjectMessage;
import org.scheming.salary.utils.StringUtils;
import org.scheming.salary.utils.TypeUtils;

import java.util.Calendar;
import java.util.concurrent.Callable;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

public class SalaryActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {
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

    @Bind(R.id.salary_business_leave_text)
    TextView mBusinessTV;

    @Bind(R.id.salary_sick_leave_text)
    TextView mSickTV;

    @Bind(R.id.salary_late_text)
    TextView mLateTV;

    @Bind(R.id.salary_leave_early_text)
    TextView mEarlyTV;

    @Bind(R.id.salary_absenteeism_text)
    TextView mAbsenteeismTV;

//    private ProgressDialog mProgressDialog;

    private String[] mSpinnerItems;
    private boolean isAddMonth = true;
    private Long mUserId;

    private SalaryDao mSalaryDao;
    private ProjectDao mProjectDao;
    private AllowanceDao mAllowanceDao;
    private AttendanceDao mAttendanceDao;

    private Project mProjectSale;
    private Project mProjectProgress;
    private Project mProjectService;
    private Salary mCurrentSalary;
    private Allowance mAllowance;
    private Attendance mAttendance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setIsInitEventBus(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary);

        init();

        if (!isAddMonth) {
            initWithData();
        } else
            initWithNew();
    }

    private void init() {
//        mProgressDialog = new ProgressDialog(this);

        ClickListener listener1 = new ClickListener(1);

        mSaleTV.setOnClickListener(listener1);
        mProgressTV.setOnClickListener(listener1);
        mServiceTV.setOnClickListener(listener1);

        ClickListener listener2 = new ClickListener(2);

        mBusinessTV.setOnClickListener(listener2);
        mSickTV.setOnClickListener(listener2);
        mEarlyTV.setOnClickListener(listener2);
        mLateTV.setOnClickListener(listener2);
        mAbsenteeismTV.setOnClickListener(listener2);


        mSalaryDao = SalaryApplication.getApplication().getSession().getSalaryDao();
        mAllowanceDao = SalaryApplication.getApplication().getSession().getAllowanceDao();
        mProjectDao = SalaryApplication.getApplication().getSession().getProjectDao();
        mAttendanceDao = SalaryApplication.getApplication().getSession().getAttendanceDao();

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
        Long mSalaryId = getIntent().getLongExtra(ConstField.SALARY_ID, 0);
        mCurrentSalary = mSalaryDao.queryBuilder().where(
                SalaryDao.Properties.Id.eq(mSalaryId)).list().get(0);
        mAllowance = mCurrentSalary.getAllowance().get(0);
        mAttendance = mCurrentSalary.getAttendance().get(0);

        for (int i = 0; i < 3; i++) {
            if (mCurrentSalary.getProjects().size() == 0)
                break;
            Project project = mCurrentSalary.getProjects().get(i);
            switch (project.getType()) {
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

        mBusinessTV.setText(String.valueOf(mAttendance.getBusiness_leave()));
        mSickTV.setText(String.valueOf(mAttendance.getSick_leave()));
        mLateTV.setText(String.valueOf(mAttendance.getLate()));
        mEarlyTV.setText(String.valueOf(mAttendance.getLeave_early()));
        mAbsenteeismTV.setText(String.valueOf(mAttendance.getAbsenteeism()));

        mSaleTV.setText(String.format("%s:%f*%f=%f", mProjectSale.getName(), mProjectSale.getTotal_money(),
                mProjectSale.getCut_rate(), mProjectSale.getResult_money()));
        mProgressTV.setText(String.format("%s:%f*%f=%f", mProjectProgress.getName(), mProjectProgress.getTotal_money(),
                mProjectProgress.getCut_rate(), mProjectProgress.getResult_money()));
        mServiceTV.setText(String.format("%s:%f*%f=%f", mProjectService.getName(), mProjectService.getTotal_money(),
                mProjectService.getCut_rate(), mProjectService.getResult_money()));
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

        Attendance attendance = new Attendance(mAttendance == null ? null : mAttendance.getId(),
                StringUtils.toInteger(mBusinessTV.getText().toString()),
                StringUtils.toInteger(mSickTV.getText().toString()),
                StringUtils.toInteger(mLateTV.getText().toString()),
                StringUtils.toInteger(mEarlyTV.getText().toString()),
                StringUtils.toInteger(mAbsenteeismTV.getText().toString()),
                salary.getId());

        String text;

        if (!isAddMonth) {
            //update
            mSalaryDao.update(salary);
            mAllowanceDao.update(allowance);
            mAttendanceDao.update(attendance);
            mProjectDao.update(mProjectSale);
            mProjectDao.update(mProjectProgress);
            mProjectDao.update(mProjectService);

            text = "信息更新成功";
        } else {
            //insert
            long salary_id = mSalaryDao.insert(salary);
            allowance.setSalary(salary_id);
            attendance.setSalary(salary_id);

            mAllowanceDao.insert(allowance);
            mAttendanceDao.insert(attendance);
            if (mProjectSale == null)
                mProjectSale = new Project(null, 1, "", 0f, 0f, 0f, salary.getId());

            if (mProjectProgress == null)
                mProjectProgress = new Project(null, 2, "", 0f, 0f, 0f, salary.getId());

            if ((mProjectService == null))
                mProjectService = new Project(null, 3, "", 0f, 0f, 0f, salary.getId());

            mProjectDao.insert(mProjectSale);
            mProjectDao.insert(mProjectProgress);
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

    class ClickListener implements View.OnClickListener {
        private int type;

        public ClickListener(int type) {
            this.type = type;
        }

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            int type = 0;

            if (this.type == 1) {
                ProjectDialogFragment fragment = new ProjectDialogFragment();
                switch (v.getId()) {
                    case R.id.salary_sale_text:
                        type = 1;
                        break;
                    case R.id.salary_progress_text:
                        type = 2;
                        break;
                    case R.id.salary_service_text:
                        type = 3;
                        break;
                }

                bundle.putInt("type", type);
                bundle.putString("msg", TypeUtils.toString(type, Project.class));
                fragment.setArguments(bundle);
                fragment.show(getFragmentManager(), "project");

            } else {
                NumberPickerFragment fragment = new NumberPickerFragment();
                switch (v.getId()) {
                    case R.id.salary_business_leave_text:
                        type = 1;
                        break;
                    case R.id.salary_sick_leave_text:
                        type = 2;
                        break;
                    case R.id.salary_late_text:
                        type = 3;
                        break;
                    case R.id.salary_leave_early_text:
                        type = 4;
                        break;
                    case R.id.salary_absenteeism_text:
                        type = 5;
                        break;
                }
                bundle.putInt("type", type);
                bundle.putString("msg", TypeUtils.toString(type, Attendance.class));
                fragment.setArguments(bundle);
                fragment.show(getFragmentManager(), "fragment");
            }

        }

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

                SalaryApplication.getApplication().getSession().callInTx(new SaveDataCall());
//                    mProgressDialog.dismiss();
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

    public void onEvent(ProjectMessage msg) {
        Project project = new Project(null, msg.type, msg.name, msg.cut_rate, msg.total_money,
                msg.total_money * msg.cut_rate, mCurrentSalary.getId());
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

    public void onEvent(AttendanceMsg msg) {
        switch (msg.type) {
            case 1:
                mBusinessTV.setText(String.valueOf(msg.times));
                break;
            case 2:
                mSickTV.setText(String.valueOf(msg.times));
                break;
            case 3:
                mLateTV.setText(String.valueOf(msg.times));
                break;
            case 4:
                mEarlyTV.setText(String.valueOf(msg.times));
                break;
            case 5:
                mAbsenteeismTV.setText(String.valueOf(msg.times));
                break;
        }

    }
}
