package org.scheming.salary.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by Scheming on 2015/10/22.
 */
public abstract class BaseFragment extends Fragment {

    public BaseFragment() {
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(view);
        init();
    }

    public abstract void init();
}
