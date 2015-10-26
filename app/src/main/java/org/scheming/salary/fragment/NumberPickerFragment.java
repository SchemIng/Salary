package org.scheming.salary.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import org.scheming.salary.R;
import org.scheming.salary.utils.AttendanceMsg;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by Scheming on 2015/10/25.
 */
public class NumberPickerFragment extends DialogFragment {
    @Bind(R.id.dialog_number_picker)
    NumberPicker picker;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getArguments().getString("msg"));
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_number_picker, null);
        builder.setView(view);
        ButterKnife.bind(this, view);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EventBus.getDefault().post(new AttendanceMsg(getArguments().getInt("type"), picker.getValue()));
            }
        });

        builder.setNegativeButton("取消", null);

        initPicker();

        return builder.create();
    }

    private void initPicker() {
        picker.setMaxValue(20);
        picker.setMinValue(0);
        picker.setValue(0);
    }
}
