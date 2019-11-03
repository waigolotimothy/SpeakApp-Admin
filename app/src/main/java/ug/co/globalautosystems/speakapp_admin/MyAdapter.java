package ug.co.globalautosystems.speakapp_admin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    ArrayList<Cases> cases;
    Intent intent;
    final Calendar calendar=Calendar.getInstance();
    CardView cardView;
    Context context;
    long hours;
    private TextView textView,timer;
    String today;
    String timeDiff;
    Date d1=null,d2=null;
    String format="MM/dd/yyyy HH:mm:ss";
    SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format, Locale.US);


    MyAdapter(Context context, ArrayList<Cases> cases) {
        this.cases = cases;
        this.context = context;
        today =simpleDateFormat.format(calendar.getTime());


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        textView = itemView.findViewById(R.id.caseTitle);
        cardView = itemView.findViewById(R.id.cardView);
        timer = itemView.findViewById(R.id.timer);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        try {
            d1=simpleDateFormat.parse(today);
            d2=simpleDateFormat.parse(cases.get(position).getCasedate());
            long diff=d1.getTime()-d2.getTime();
             hours=diff/(60*60*1000)%24;
             if (hours==0){
                 hours=diff/(60*1000)%60;
             }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.textView.setText(this.cases.get(position).getNature());
        holder.time.setText(hours+" Hrs");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(context, DetailActivity.class);
                intent.putExtra("CASE_CLASS",cases.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return cases.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView,time;
        CardView cardView;


        MyViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.caseTitle);
            cardView = v.findViewById(R.id.cardView);
            time = v.findViewById(R.id.timer);
        }
    }
}
