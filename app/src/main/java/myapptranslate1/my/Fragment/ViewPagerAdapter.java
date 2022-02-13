package myapptranslate1.my.Fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class ViewPagerAdapter extends FragmentStateAdapter {


    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new TranslateFragment();
            case 1:
                return new ListBookUserFragment();
            case 2:
                return new VipFragment();
            default:
                return new TranslateFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
