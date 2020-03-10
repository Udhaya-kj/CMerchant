package life.corals.merchant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import life.corals.merchant.R;
import life.corals.merchant.client.model.InlineResponse20019Listofmessages;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.MyViewHolder> {

    private List<InlineResponse20019Listofmessages> scheduledList;
    private Context mCtx;

    public NotificationListAdapter(Context mCtx, List<InlineResponse20019Listofmessages> scgeduledList) {
        this.mCtx = mCtx;
        this.scheduledList = scgeduledList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title, schduleDt,value, messageTV;

        private MyViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.msg_title);
            schduleDt = (TextView) view.findViewById(R.id.create_dt);
            value = (TextView) view.findViewById(R.id.req_value);
            messageTV = (TextView) view.findViewById(R.id.schedule_dt);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_msg_scheduled_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        InlineResponse20019Listofmessages list = scheduledList.get(position);
        holder.title.setText(list.getMsgTitle());
        holder.messageTV.setText(list.getMsgText());
        if (list.getRequestType().equals("A")){
            String a = list.getRequestValue().replace(">","");
            holder.value.setText("Not visited for more than "+a+" days");

            holder.schduleDt.setText("Daily once");
        }else if(list.getRequestType().equals("L")){
            holder.value.setText("All Customers");
            Timestamp time = Timestamp.valueOf(list.getSendDatetime());
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMM yyyy");
            // holder.createDt.setText("Scheduled on "+ sdfDate.format(time));
            holder.schduleDt.setText(sdfDate.format(time));
        }else{
            holder.value.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return scheduledList.size();
    }

    public List<InlineResponse20019Listofmessages> getScheduledList()   {
        return scheduledList;
    }
}