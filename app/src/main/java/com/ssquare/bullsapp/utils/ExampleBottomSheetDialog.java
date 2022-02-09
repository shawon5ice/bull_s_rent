package com.ssquare.bullsapp.utils;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.ssquare.bullsapp.R;

public class ExampleBottomSheetDialog extends BottomSheetDialogFragment {
    private BottomSheetListener mListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_layout,container,false);

        Button button;
        TextInputLayout title,desc;
        Switch priority;
        button = view.findViewById(R.id.createTask);
        title = view.findViewById(R.id.taskTitle);
        desc = view.findViewById(R.id.taskDescription);
        priority = view.findViewById(R.id.prioritySwitch);
        int switchVal;
        button.setOnClickListener(view1 -> {
            String t = title.getEditText().getText().toString();
            String d = desc.getEditText().getText().toString();

//            priority.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                    if(b){
//                        switchVal = 1;
//                    }else{
//                        switchVal = 0;
//                    }
//                }
//            });
            mListener.onButtonClicked (t,d);
            dismiss();
        });

        return view;
    }

    public interface BottomSheetListener{
        void onButtonClicked(String title,String desc);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomsheetListener");
        }
    }
}
