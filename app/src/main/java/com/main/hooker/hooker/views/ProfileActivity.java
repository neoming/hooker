package com.main.hooker.hooker.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.main.hooker.hooker.R;
import com.main.hooker.hooker.components.LoginFragment;
import com.main.hooker.hooker.components.ProfileDetailFragment;
import com.main.hooker.hooker.entity.User;
import com.main.hooker.hooker.model.UserModel;
import com.main.hooker.hooker.utils.http.ApiFailException;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        if(getIntent().getExtras() != null && getIntent().getExtras().getParcelable("user") != null){
            User user = getIntent().getExtras().getParcelable("user");
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, ProfileDetailFragment.newInstance(user))
                    .commit();
            return;
        }
        new Thread(() -> {
            try {
                getSupportFragmentManager().beginTransaction()
                        //.add(R.id.fragment, ProfileDetailFragment.newInstance(new User()))
                        .add(R.id.fragment, UserModel.hasLogin() ?
                                ProfileDetailFragment.newInstance(UserModel.getMe())
                                : new LoginFragment())
                        .commit();
            } catch (ApiFailException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void finish(View view) {
        finish();
    }


    public void toProfile() {
        try {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, ProfileDetailFragment.newInstance(UserModel.getMe()))
                    .commit();
        } catch (ApiFailException e) {
            e.printStackTrace();
        }
    }
}
