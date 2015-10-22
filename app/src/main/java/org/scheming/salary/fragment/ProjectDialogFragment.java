package org.scheming.salary.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import org.scheming.salary.R;
import org.scheming.salary.utils.ProjectMessage;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by Scheming on 2015/10/20.
 */
public class ProjectDialogFragment extends BaseFragment {
    @Bind(R.id.dialog_project_name_edit)
    EditText mName;

    @Bind(R.id.dialog_project_money_edit)
    EditText mMoney;

    @Bind(R.id.dialog_project_rate_edit)
    EditText mRate;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_project, null);

        builder.setView(view);
        ButterKnife.bind(view);


        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ProjectMessage message = new ProjectMessage(getArguments().getInt("type"),
                        mName.getText().toString(),
                        Float.valueOf(mMoney.getText().toString()),
                        Float.valueOf(mRate.getText().toString()));
                EventBus.getDefault().post(message);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getDialog().cancel();
            }
        });

        builder.setTitle(getArguments().getString("msg"));
        return builder.create();
    }
}
