
package com.example.projectapp.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.projectapp.R;
import com.example.projectapp.databinding.ActivityMainBinding;
import com.example.projectapp.menu.CallsFragment;
import com.example.projectapp.menu.ChatsFragment;
import com.example.projectapp.menu.StatusFragment;
import com.example.projectapp.view.contact.ContactActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
   private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setUpWithViewPager(binding.viewPager);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeFabIcon(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setUpWithViewPager(ViewPager viewPager){
        MainActivity.SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ChatsFragment(), "Chats");
        adapter.addFragment(new StatusFragment(), "Status");
        adapter.addFragment(new CallsFragment(), "Calls");
        viewPager.setAdapter(adapter);
    }

    private void changeFabIcon(final int index){
        binding.fabNewMessage.hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                switch (index){

                    case 0 : binding.fabNewMessage.setImageDrawable(getDrawable(R.drawable.ic_chat));
                    binding.fabNewMessage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(MainActivity.this, ContactActivity.class));
                        }
                    });
                    break;

                    default : binding.fabNewMessage.setImageDrawable(getDrawable(R.drawable.ic_chat));
                }
                binding.fabNewMessage.show();
            }

        },400);

    }

    public static class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> titleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager manager){
            super(manager);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(Fragment fragment, String title){
            fragmentList.add(fragment);
            titleList.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }
}