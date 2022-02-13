package myapptranslate1.my.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import my_interface.IClickDeleteUserLangListener;
import my_interface.IClickEditUserLangListener;
import myapptranslate1.my.Adapter.UserLangAdapter;
import myapptranslate1.my.Class.UserLang;
import myapptranslate1.my.MainActivity;
import myapptranslate1.my.R;
import myapptranslate1.my.database.UserDatabase;


public class UserLangFragment extends Fragment {

    public View view;
    private MainActivity mainActivity;
    private List<UserLang> listFull;
    private RecyclerView rcv;
    private UserLangAdapter userLangAdapter;
    private TextView txTitile;
    private TextView btn_1_page;
    private LinearLayout layout_2_page, layout_1_page;
    private int page_number = 0;
    private int idbook;
    private int page_last;
    private Boolean middle = false;
    private int countDelete = 0;

    public UserLangFragment() {
        // Required empty public constructor
    }

    public void initUi(View view) {

    }

    private void show(String s) {
        Toast.makeText(mainActivity, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_lang, container, false);
        TextView btnpage_right = view.findViewById(R.id.btn_bottom_page_right);
        TextView btnpage_left = view.findViewById(R.id.btn_bottom_page_left);
        ImageButton btnback = view.findViewById(R.id.btn_back_action_bar);
        txTitile = view.findViewById(R.id.tx_title_action_bar);
        layout_2_page = view.findViewById(R.id.bottom_page_2);
        layout_1_page = view.findViewById(R.id.bottom_page_1);
        btn_1_page = view.findViewById(R.id.btn_bottom_page_1);
        mainActivity = (MainActivity) getActivity();
        rcv = view.findViewById(R.id.rcv_user_languages);


        setTiltepage_And_Page_number();
        setIdbook_And_Page_last();
        setBtnpage();
        // Get list full line
        listFull = getListBook(idbook);
        setCoundDelete();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        rcv.setLayoutManager(linearLayoutManager);
        //Make listFull has 21 line
        userLangAdapter = new UserLangAdapter(new IClickEditUserLangListener() {
            @Override
            public void onClickEditUserLang(UserLang userLang) {
                show_dialog(idbook, userLang.getUsernameL());

            }
        }, new IClickDeleteUserLangListener() {
            @Override
            public void onClickDeleteUserLang(UserLang userLang) {
                DeleteLine(userLang);
            }
        });
        LayoutAnimationController layoutAnimationController
                = AnimationUtils.loadLayoutAnimation(mainActivity, R.anim.layout_right_to_left);
        rcv.setLayoutAnimation(layoutAnimationController);

        userLangAdapter.setData(getList21Line(page_number));
        rcv.setAdapter(userLangAdapter);

        btnpage_right.setOnClickListener(v -> UpPage());

        btnpage_left.setOnClickListener(v -> DownPage());

        btn_1_page.setOnClickListener(v -> Btn_1_Page());

        btnback.setOnClickListener(v -> {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStack();
            }
        });

        return view;
    }


    private void show_dialog(int idbook, String nameL) {
        AlertDialog.Builder alert = new AlertDialog.Builder(mainActivity);
        View mview = getLayoutInflater().inflate(R.layout.layout_dialog_edit_userlang, null);

        EditText editText = mview.findViewById(R.id.edit_text_dialog);
        Button btn_ok = mview.findViewById(R.id.btn_dialog_rename);
        Button btn_cancel = mview.findViewById(R.id.btn_dialog_cancel);

        alert.setView(mview);
        AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameR = editText.getText().toString().trim();
                if (!nameR.equals("")) {
                    Edit_UserLang(idbook, nameL, nameR);
                    listFull = getListBook(idbook);
                    Reset_Page_last(listFull);
                    userLangAdapter.setData(getList21Line(page_number));
                    LayoutAnimationController layoutAnimationController
                            = AnimationUtils.loadLayoutAnimation(mainActivity, R.anim.layout_right_to_left);
                    rcv.setLayoutAnimation(layoutAnimationController);
                    rcv.setAdapter(userLangAdapter);
                    alertDialog.dismiss();
                } else {
                    show("Can not be empty");
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void setCoundDelete() {
        countDelete = getList21Line(page_number).size();
    }

    private void Reset_Page_last(List<UserLang> list) {
        int line_in_1_page = 21;
        int n = list.size();
        if (n <= line_in_1_page) {
            page_last = 1;
        } else {
            page_last = n / line_in_1_page;
            if (n % line_in_1_page != 0) {
                page_last++;
            }
        }
    }

    private void DeleteLine(UserLang userLang) {
        DeleteUserLang(idbook, userLang.getUsernameL());
        listFull.remove(userLang);
        countDelete--;
        if (countDelete == 0) {
            if (page_number == page_last) {
                page_last--;
            }
            if (page_number >= 1 && page_number < page_last) {
                page_last--;
                middle = true;
            }
            return;
        }
    }

    private void Btn_1_Page() {
        if (page_last > 1) {
            String name = btn_1_page.getText().toString();
            if (name.equals("Next page")) {
                page_number++;
            } else {
                page_number--;
            }
            List<UserLang> list = getList21Line(page_number);
            countDelete = list.size();
            userLangAdapter.setData(list);
            rcv.setAdapter(userLangAdapter);

            LayoutAnimationController layoutAnimationController
                    = AnimationUtils.loadLayoutAnimation(mainActivity, R.anim.layout_right_to_left);
            rcv.setLayoutAnimation(layoutAnimationController);

            String tilte = "Page " + page_number + "....";
            txTitile.setText(tilte);

            if (page_number == page_last || page_number == 1) {
                layout_2_page.setVisibility(View.INVISIBLE);
                layout_1_page.setVisibility(View.VISIBLE);
                if (page_number == 1) {
                    btn_1_page.setText(R.string.Next_page);
                    return;
                }
                btn_1_page.setText(R.string.Previous_page);
                return;
            }
            layout_1_page.setVisibility(View.INVISIBLE);
            layout_2_page.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(mainActivity, "Store words to create page 2", Toast.LENGTH_SHORT).show();
        }
    }

    private void UpPage() {
        if (!middle) {
            page_number++;
        } else {
            middle = false;
        }
        List<UserLang> list = getList21Line(page_number);
        countDelete = list.size();
        String tilte = "Page " + page_number + "....";
        txTitile.setText(tilte);
        if (page_number == page_last) {
            layout_2_page.setVisibility(View.INVISIBLE);
            layout_1_page.setVisibility(View.VISIBLE);
            btn_1_page.setText(R.string.Previous_page);
        }
        userLangAdapter.setData(list);
        rcv.setAdapter(userLangAdapter);

        LayoutAnimationController layoutAnimationController
                = AnimationUtils.loadLayoutAnimation(mainActivity, R.anim.layout_right_to_left);
        rcv.setLayoutAnimation(layoutAnimationController);
    }

    private void DownPage() {
        if (middle) {
            middle = false;
        }
        page_number--;
        List<UserLang> list = getList21Line(page_number);
        countDelete = list.size();
        String tilte = "Page " + page_number + "....";
        txTitile.setText(tilte);
        if (page_number == 1) {
            layout_2_page.setVisibility(View.INVISIBLE);
            layout_1_page.setVisibility(View.VISIBLE);
            btn_1_page.setText(R.string.Next_page);
        }

        userLangAdapter.setData(list);
        rcv.setAdapter(userLangAdapter);

        LayoutAnimationController layoutAnimationController
                = AnimationUtils.loadLayoutAnimation(mainActivity, R.anim.layout_right_to_left);
        rcv.setLayoutAnimation(layoutAnimationController);

    }


    private void setBtnpage() {
        Bundle bundle = getArguments();
        assert bundle != null;
        if (page_number == 1) {
            //Layout_1_page already visible
            return;
        }
        if (page_number == page_last) {
            btn_1_page.setText(R.string.Previous_page);
            return;
        }
        if (page_number < page_last) {
            layout_1_page.setVisibility(View.INVISIBLE);
            layout_2_page.setVisibility(View.VISIBLE);
        }
    }

    private void setTiltepage_And_Page_number() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String name = "Page " + bundle.getInt("Page number") + "....";
            page_number = bundle.getInt("Page number");
            txTitile.setText(name);
        }
    }

    private void setIdbook_And_Page_last() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            idbook = bundle.getInt("Id book");
            page_last = bundle.getInt("Last page");
        }
    }

    private List<UserLang> getListBook(int id) {
        List<UserLang> list = new ArrayList<>();
        switch (id) {
            case 1:
                list = UserDatabase.getInstance(mainActivity).userDao().getListEnglish();
                return list;
            case 2:
                list = UserDatabase.getInstance(mainActivity).userDao().getListBelarus();
                return list;
            case 3:
                list = UserDatabase.getInstance(mainActivity).userDao().getListGermany();
                return list;
            case 4:
                list = UserDatabase.getInstance(mainActivity).userDao().getListFrance();
                return list;
            case 5:
                list = UserDatabase.getInstance(mainActivity).userDao().getListRussian();
                return list;
            case 6:
                list = UserDatabase.getInstance(mainActivity).userDao().getListPoland();
                return list;
        }
        return list;
    }

    private void Edit_UserLang(int idbook, String nameL, String nameR) {
        switch (idbook) {
            case 1:
                UserDatabase.getInstance(mainActivity).userDao().update_nameL_UserLang(nameR, nameL);
                break;
            case 2:
                UserDatabase.getInstance(mainActivity).userDao().update_nameL_BelarusLang(nameR, nameL);
                break;
            case 3:
                UserDatabase.getInstance(mainActivity).userDao().update_nameL_GermanyLang(nameR, nameL);
                break;
            case 4:
                UserDatabase.getInstance(mainActivity).userDao().update_nameL_FranceLang(nameR, nameL);
                break;
            case 5:
                UserDatabase.getInstance(mainActivity).userDao().update_nameL_RussianLang(nameR, nameL);
                break;
            case 6:
                UserDatabase.getInstance(mainActivity).userDao().update_nameL_PolandLang(nameR, nameL);
                break;
        }

    }

    private List<UserLang> getList21Line(int page_number) {
        if (listFull.size() == 0) {
            return listFull;
        } else {
            //reset page_last
            int line_in_1_page = 21;
            if (listFull.size() <= line_in_1_page) {
                page_last = 1;
            } else {
                page_last = listFull.size() / line_in_1_page;
                if (listFull.size() % line_in_1_page != 0) {
                    page_last++;
                }
            }
        }
        List<UserLang> list = new ArrayList<>();
        int line_in_1_page = 21;
        int start = page_number * line_in_1_page - line_in_1_page;
        int end = start + line_in_1_page;
        while (start < end) {
            list.add(listFull.get(start));
            start++;
            if (start == listFull.size()) {
                return list;
            }
        }
        return list;
    }


    private void DeleteUserLang(int IDBook, String nameL) {
        switch (IDBook) {
            case 1:
                UserDatabase.getInstance(mainActivity).userDao().DeleteUserEnglish(nameL);
                break;
            case 2:
                UserDatabase.getInstance(mainActivity).userDao().DeleteUserBelarus(nameL);
                break;
            case 3:
                UserDatabase.getInstance(mainActivity).userDao().DeleteUserGermany(nameL);
                break;
            case 4:
                UserDatabase.getInstance(mainActivity).userDao().DeleteUserFrance(nameL);
                break;
            case 5:
                UserDatabase.getInstance(mainActivity).userDao().DeleteUserRussian(nameL);
                break;
            case 6:
                UserDatabase.getInstance(mainActivity).userDao().DeleteUserPoland(nameL);
                break;
        }
    }
}