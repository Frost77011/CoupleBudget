package com.example.couplesbudgeting.ui.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.couplesbudgeting.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddTransactionBottomDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    public static AddTransactionBottomDialogFragment newInstance() {
        return new AddTransactionBottomDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_add_transaction, container,
                false);

        Button add_goal = view.findViewById(R.id.add_item_popup_button);
        add_goal.setOnClickListener(this);

        // get the views and attach the listener

        return view;

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.add_item_popup_button:
                //TODO: Send information from popup to firebase, close popup.
                break;
        }
    }
}
