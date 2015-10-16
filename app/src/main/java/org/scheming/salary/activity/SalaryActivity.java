package org.scheming.salary.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import org.scheming.salary.R;

import butterknife.Bind;

public class SalaryActivity extends AppCompatActivity {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary);
    }
}
