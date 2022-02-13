package myapptranslate1.my.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import myapptranslate1.my.MainActivity;
import myapptranslate1.my.R;


public class VipFragment extends Fragment {

    private TextView tv_vip , btn_spin , btn_stop;
    public View view;



   public VipFragment(){

   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_vip, container, false);
        tv_vip = view.findViewById(R.id.tv_text_vip);
        btn_spin = view.findViewById(R.id.btn_spin);
        btn_stop = view.findViewById(R.id.btn_stop);
        MainActivity mainActivity = (MainActivity) getActivity();
        Animation animation1 = AnimationUtils.loadAnimation(mainActivity,R.anim.anim_rotate_0_360);
        Animation animation2 = AnimationUtils.loadAnimation(mainActivity , R.anim.anim_rotate_360_0);

        btn_spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_vip.startAnimation(animation2);
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_vip.clearAnimation();
            }
        });


        return view;
    }
}