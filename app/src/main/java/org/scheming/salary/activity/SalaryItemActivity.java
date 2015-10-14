package org.scheming.salary.activity;

import android.os.Bundle;
import android.widget.EditText;

import org.scheming.salary.R;

import butterknife.Bind;

public class SalaryItemActivity extends BaseActivity {
    @Bind(R.id.add_employee_borrow_edit)
    EditText mBorrowET;

    @Bind(R.id.add_employee_cut_payment_edit)
    EditText mCutET;

    @Bind(R.id.add_employee_personal_security_edit)
    EditText mPersonalSecurityET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_item);
    }
}
