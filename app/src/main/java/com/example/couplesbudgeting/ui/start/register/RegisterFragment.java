package com.example.couplesbudgeting.ui.start.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.couplesbudgeting.MainActivity;
import com.example.couplesbudgeting.R;
import com.example.couplesbudgeting.cache.Cache;
import com.example.couplesbudgeting.models.User;
import com.example.couplesbudgeting.services.UsersService;
import com.example.couplesbudgeting.ui.groupID.GroupActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    private RegisterViewModel mViewModel;

    private User registerUser;
    private View view;
    private EditText registerEmailText;
    EditText registerPasswordText;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register, container, false);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        //Initialize User
        registerUser = new User();

        //Register Button
        FrameLayout registerFrame = view.findViewById(R.id.registerButtonFrame);
        registerFrame.setOnClickListener(this);

        //EditTexts
        registerEmailText = view.findViewById(R.id.registerEmailText);
        registerEmailText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                registerUser.setEmailAddress(editable.toString());
            }
        });

        registerPasswordText = view.findViewById(R.id.registerPasswordText);
        registerPasswordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                registerUser.setPassword(editable.toString());
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerButtonFrame:
                if(registerEmailText.getText().length() > 0 &&
                registerPasswordText.getText().length() > 0) {
                    attemptRegister();
                }
                else {
                    Toast.makeText(getActivity(), "Please populate all fields", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void attemptRegister() {

        mAuth.createUserWithEmailAndPassword(registerUser.getEmailAddress(), registerUser.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    UsersService usersService = new UsersService();
                    usersService.AddUser(registerUser);

                    Cache cache = Cache.getInstance();
                    cache.setEmail(registerUser.getEmailAddress());
                    cache.setGroupId(null);
                    new UsersService().GetUser(registerUser.getEmailAddress());
                    Toast.makeText(getActivity(), "Register successful!", Toast.LENGTH_SHORT).show();
                    launchGroupIDActivity();
                }
                else {
                    Toast.makeText(getActivity(), "Log In failed!", Toast.LENGTH_SHORT).show();
                    try {
                        System.out.println(task.getResult());
                    } catch(Exception exception) {
                        System.out.println(exception);
                    }
                }
            }
        });

    }

    private void launchGroupIDActivity() {
        Intent intent = new Intent(getActivity(), GroupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}