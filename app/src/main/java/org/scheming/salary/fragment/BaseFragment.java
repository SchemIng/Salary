package org.scheming.salary.fragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by Scheming on 2015/10/20.
 */
public class BaseFragment extends DialogFragment {
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        EventBus.getDefault().unregister(this);
        super.onDetach();
    }
}
