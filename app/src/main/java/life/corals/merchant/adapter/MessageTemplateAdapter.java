package life.corals.merchant.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.fragments.NotificationPreview;
import life.corals.merchant.utils.MessageDataModel;

import static life.corals.merchant.utils.Constants.ABSEND_DAYS;
import static life.corals.merchant.utils.Constants.MESSAGE;
import static life.corals.merchant.utils.Constants.TEMP_CODE;
import static life.corals.merchant.utils.Constants.TITLE;

public class MessageTemplateAdapter extends ArrayAdapter<MessageDataModel> implements View.OnClickListener {

    private ArrayList<MessageDataModel> dataSet;
    private Context mContext;
    private String daysAbsence;

    // View lookup cache
    private static class ViewHolder {
        TextView txtTitle;
        TextView txtContent;
        ImageButton preview;
    }

    public MessageTemplateAdapter(ArrayList<MessageDataModel> data, Context context, String daysAbsence) {
        super(context, R.layout.list_item_message_template, data);
        this.dataSet = data;
        this.mContext = context;
        this.daysAbsence = daysAbsence;

    }

    @Override
    public void onClick(View v) {

        int position = (Integer) v.getTag();
        MessageDataModel Model = getItem(position);

        if (v.getId() == R.id.preview) {
            Bundle bundle = new Bundle();
            bundle.putString(TITLE, Model.getTitle());
            bundle.putString(MESSAGE, Model.getContent());
            bundle.putString(ABSEND_DAYS, daysAbsence);
            bundle.putInt(TEMP_CODE, 11);
            NotificationPreview fragment1 = new NotificationPreview();
            fragment1.setArguments(bundle);
            FragmentManager fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
            fragmentTransaction.replace(R.id.send_notification_frame_layout, fragment1);
            fragmentTransaction.commit();
        }
    }


    private int lastPosition = -1;

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        MessageDataModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_message_template, parent, false);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.message_title);
            viewHolder.txtContent = (TextView) convertView.findViewById(R.id.message_content);
            viewHolder.preview = (ImageButton) convertView.findViewById(R.id.preview);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtTitle.setText(dataModel.getTitle());
        viewHolder.txtContent.setText(dataModel.getContent());

        viewHolder.preview.setOnClickListener(this);
        viewHolder.preview.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}