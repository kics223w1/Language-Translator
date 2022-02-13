package myapptranslate1.my.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import myapptranslate1.my.MainActivity;
import myapptranslate1.my.R;


public class InstructionFragment extends Fragment {

    public static final String TAG = InstructionFragment.class.getName();
    private MainActivity mainActivity;
    private View view;

    public InstructionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_instruction, container, false);
        ImageButton btnBack = view.findViewById(R.id.btn_back_action_bar);
        TextView txTitile = view.findViewById(R.id.tx_title_action_bar);
        mainActivity = (MainActivity) getActivity();

        txTitile.setText(R.string.Instruction);

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(mainActivity , MainActivity.class);
            startActivity(intent);
            requireActivity().overridePendingTransition(R.anim.anim_enter_activity , R.anim.anim_exit_activity);
        });


        return view;
    }
}