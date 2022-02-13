package myapptranslate1.my;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.viewpager2.widget.ViewPager2;
import myapptranslate1.my.Fragment.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ViewPager2 viewPager2;

    private void initUi() {
        viewPager2 = findViewById(R.id.view_pager2_main_activity);
        bottomNavigationView = findViewById(R.id.bottom_navi);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();


        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);


        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.homes).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.books).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.vip).setChecked(true);
                        break;
                }
            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.homes:
                            viewPager2.setCurrentItem(0);
                            break;
                        case R.id.books:
                            viewPager2.setCurrentItem(1);
                            break;
                        case R.id.vip:
                            viewPager2.setCurrentItem(2);
                            break;
                    }
                    return true;
                });
    }
}