package ug.co.globalautosystems.speakapp_admin;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    TextView date,accused,gender,nature,detail;
    RelativeLayout sms,call;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    Context context;
    ProgressDialog progressDialog;
    EditText message;
    RelativeLayout send,cancel;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle extra=getIntent().getExtras();
        context=this;
        progressDialog=new ProgressDialog(context);
        progressDialog.setTitle("Sending message");
        progressDialog.setMessage("Please wait while we send the message");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        builder=new AlertDialog.Builder(context);
        View view=this.getLayoutInflater().inflate(R.layout.sms_alert,null);
        builder.setView(view);
        builder.setTitle("Please type a message that we shall send to the victim");
        message=view.findViewById(R.id.message);
        alertDialog=builder.create();
        send=view.findViewById(R.id.send);
        cancel=view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               alertDialog.dismiss();
            }
        });

        assert extra != null;
        final Cases caseClass= (Cases) extra.getSerializable("CASE_CLASS");

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mess=message.getText().toString();
                if (!mess.equals("")){
                    progressDialog.show();
                    SmsManager smsManager=SmsManager.getDefault();
                    smsManager.sendTextMessage(caseClass.getPhone(),null,mess,null,null);
                    progressDialog.hide();
                    alertDialog.dismiss();
//                    Snackbar.make(this,"Your message was sent",Snackbar.LENGTH_LONG).show();
                }
            }
        });
        date=findViewById(R.id.title);
        accused=findViewById(R.id.accused);
        gender=findViewById(R.id.gender);
        nature=findViewById(R.id.nature);
        detail=findViewById(R.id.detailsTxt);
        sms=findViewById(R.id.smsLayout);
        call=findViewById(R.id.callLayout);

        date.setText("Occured on: "+caseClass.getDate());
        accused.setText("Accused: "+caseClass.getAlleged());
        nature.setText("Case Nature: "+caseClass.getNature());
        detail.setText("Details:\n "+caseClass.getDetails());

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();

            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callintent=new Intent(Intent.ACTION_DIAL);
                callintent.setData(Uri.parse("tel:"+caseClass.getPhone()));
                startActivity(callintent);
            }
        });



    }
}
