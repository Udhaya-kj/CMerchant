package life.corals.merchant.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.adapter.MessageTemplateAdapter;
import life.corals.merchant.utils.MessageDataModel;

import static life.corals.merchant.utils.Constants.ABSEND_DAYS;
import static life.corals.merchant.utils.Constants.TEMP_CODE;

public class NotificationTemplateList extends BaseFragment {

    private View rootview;

    private String daysofAbsence = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.frag_template_list, container, false);
        Toolbar toolbar = rootview.findViewById(R.id.toolbar_template_list);
        toolbar.setTitleTextColor(getResources().getColor(R.color.matt_black));
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            if (getActivity() != null && activity.getSupportActionBar() != null && getActivity().getResources() != null) {
                activity.getSupportActionBar().setHomeButtonEnabled(true);
                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                activity.getSupportActionBar().setHomeAsUpIndicator(getActivity().getResources().getDrawable(R.drawable.ic_arrow_left));
                toolbar.setNavigationOnClickListener(v -> onBackPressed());
            }
        }
        findView();
        return rootview;
    }

    private void findView() {
        ListView listView = rootview.findViewById(R.id.list_view);
        Button createNew = rootview.findViewById(R.id.create_new_btn);
        TextView infoTv = rootview.findViewById(R.id.absence_info);
        daysofAbsence = Objects.requireNonNull(getArguments()).getString(ABSEND_DAYS);
        String msg = "Message set up for After ::ansence:: days of from last visit";
        infoTv.setText(msg.replace("::ansence::", daysofAbsence));
        ArrayList<MessageDataModel> dataModels = new ArrayList<>();

        dataModels.add(new MessageDataModel(getActivity().getResources().getString(R.string.message_one_title), getActivity().getResources().getString(R.string.message_one_two)));
        dataModels.add(new MessageDataModel(getActivity().getResources().getString(R.string.message_one_title), getActivity().getResources().getString(R.string.message_one_two)));
        dataModels.add(new MessageDataModel(getActivity().getResources().getString(R.string.message_one_title), getActivity().getResources().getString(R.string.message_one_two)));
        dataModels.add(new MessageDataModel(getActivity().getResources().getString(R.string.message_one_title), getActivity().getResources().getString(R.string.message_one_two)));

        MessageTemplateAdapter adapter = new MessageTemplateAdapter(dataModels, getActivity(), daysofAbsence);

        listView.setAdapter(adapter);

        createNew.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(ABSEND_DAYS, daysofAbsence);
            bundle.putInt(TEMP_CODE, 1);
            NotificationEnterPage enterPage = new NotificationEnterPage();
            enterPage.setArguments(bundle);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
            fragmentTransaction.replace(R.id.send_notification_frame_layout, enterPage);
            fragmentTransaction.commit();
        });
    }

    @Override
    public boolean onBackPressed() {
        NotificationHome fragment1 = new NotificationHome();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
        fragmentTransaction.replace(R.id.send_notification_frame_layout, fragment1);
        fragmentTransaction.commit();
        return true;
    }
}
