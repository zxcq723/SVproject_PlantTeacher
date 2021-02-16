package com.example.plantteacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Fragment loginActivity = new LogInActivity();
    Fragment CommunityActivity = new CommunityActivity();
    Fragment WriteActivity = new WriteActivity();
    Fragment CameraFragment = new CameraFragment();
    Fragment CommentDetailFragment = new CommentDetailFragment();
    Fragment MarketFragment = new MarketFragment();
    Fragment MedicineFragment = new MedicineFragment();
    Fragment PotFragment = new PotFragment();
    Fragment SellFragment = new SellFragment();
    Fragment TipFragment = new TipFragment();
    Fragment Tip1Fragment = new Tip1Fragment();
    Fragment Tip_Explain = new TipExplainFragment();
    Fragment CoopFragment = new CoopFragment();
    Fragment DetectFragment = new DetectFragment();
    Fragment MentorFragment = new MentorFragment();

    @Override
    public void onBackPressed() {
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        for(Fragment fragment : fragmentList){
            if(fragment instanceof onBackPressedListener){
                ((onBackPressedListener)fragment).onBackPressed();
                return;
            }
        }
    }

    TextView community;
    TextView market;
    Button subscribe;
    static Bundle mbundle = new Bundle();
    static String id;
    String message;

    TextView tips;
    TextView cooperating;
    TextView detect;
    TextView mentor;
    TextView premium;

    NotificationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        community = (TextView)findViewById(R.id.community);
        subscribe = (Button) findViewById(R.id.subscribe);

        subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = "subscribe";showMessage();
            }
        });

        community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChangedFragment(2,null);
            }
        });

        market = (TextView) findViewById(R.id.market);
        market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChangedFragment(7,null);
            }
        });

        tips = (TextView) findViewById(R.id.tips);
        tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChangedFragment(11,null);
            }
        });

        cooperating = (TextView) findViewById(R.id.cooperating);
        cooperating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChangedFragment(14,null);
            }
        });

        detect = (TextView) findViewById(R.id.detect);
        detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChangedFragment(15,null);
            }
        });

        mentor = (TextView) findViewById(R.id.mentor);
        mentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChangedFragment(16,null);
            }
        });

        premium = (TextView) findViewById(R.id.premium);
        premium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = "premium";
                showMessage();
            }
        });
        //수정 필요, database에서 subscribe 부분을 수정해야하는 코드 다시 작성을 해야 함.
        /*if(cursor.getInt(2) == 1)
        {
            subscribe.setBackgroundResource(R.drawable.linear_border_smooth_orange);
        }
        else
            subscribe.setBackgroundResource(R.drawable.linear_border_smooth_purple);

        subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cursor.getInt(2) == 1)
                {
                    //LogInTable.modify(0);
                    Toast.makeText(getApplicationContext(),""+cursor.getInt(2),Toast.LENGTH_LONG).show();
                    subscribe.setBackgroundResource(R.drawable.linear_border_smooth_purple);
                }
                else
                {
                    //LogInTable.modify(1);
                    Toast.makeText(getApplicationContext(),""+cursor.getInt(2),Toast.LENGTH_LONG).show();
                    subscribe.setBackgroundResource(R.drawable.linear_border_smooth_orange);
                }
            }
        });
        community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cursor.getInt(2) == 0)
                    Toast.makeText(getApplicationContext(),"You should subscribe to use community",Toast.LENGTH_LONG).show();
                else
                    onChangedFragment(2,null);
            }
        });*/
        // 상단 바 설정
        /*toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        if(mbundle != null)
        {
            String move = "";
            move += mbundle.getString("move");
            if(move.equals("move"))
            {
                mbundle = null;
                onChangedFragment(1,null);
            }
        }

    }

    public void onChangedFragment(int position,Bundle bundle)
    {
        Fragment fragment = null;
        if(position == 1)
        {
            fragment = loginActivity;
            //toolbar.setTitle("LOGIN");
        }
        else if(position == 2)
        {
            fragment = CommunityActivity;
        }
        else if(position == 3)
        {
            fragment = WriteActivity;
            if(bundle != null)
            {
                mbundle = bundle;
                byte[] array = mbundle.getByteArray("photo");
                mbundle.putByteArray("photo",array);

            }
        }
        else if(position == 4)
        {
            fragment = CameraFragment;
        }
        else if(position == 6)
        {
            fragment = CommentDetailFragment;

            mbundle = bundle;
            String title = mbundle.getString("title");
            String contents = mbundle.getString("contents");
            byte[] array = mbundle.getByteArray("image");
            int i = mbundle.getInt("position");

            mbundle.putString("title",title);
            mbundle.putString("contents",contents);
            mbundle.putByteArray("image",array);
            mbundle.putInt("position",i);
        }
        else if(position == 7)
        {
            fragment = MarketFragment;
        }
        else if(position == 8)
        {
            fragment = MedicineFragment;
        }
        else if(position == 9)
        {
            fragment = PotFragment;
        }
        else if(position == 10)
        {
            fragment = SellFragment;
            mbundle = bundle;
            String name = mbundle.getString("name");
            byte[] array = mbundle.getByteArray("image");
            int back = mbundle.getInt("back");
            mbundle.putString("name",name);
            mbundle.putByteArray("image",array);
            mbundle.putInt("back",back);
        }
        else if(position == 11)
        {
            fragment = TipFragment;
        }
        else if(position == 12)
        {
            fragment = Tip1Fragment;
        }
        else if(position == 13)
        {
            fragment = Tip_Explain;

            mbundle = bundle;
            String title = mbundle.getString("title");
            String contents = mbundle.getString("contents");
            byte[] image = mbundle.getByteArray("image");
            mbundle.putString("title",title);
            mbundle.putString("contents",contents);
            mbundle.putByteArray("image",image);
        }
        else if(position == 14)
        {
            fragment = CoopFragment;
        }
        else if(position == 15)
        {
            fragment = DetectFragment;
        }
        else if(position == 16)
            fragment = MentorFragment;

        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
    }

    private void showMessage()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("caution");
        builder.setMessage("Are you sure to "+ message);
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(message == "subscribe") {
                    subscribe.setBackgroundResource(R.drawable.linear_border_smooth_orange);
                }

                if(message == "premium")
                {
                    premium.setBackgroundResource(R.drawable.linear_border_smooth_orange);
                    notice();
                }
            }
        });

        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(message == "subscribe") {
                    subscribe.setBackgroundResource(R.drawable.linear_border_smooth_purple);
                }
                else if(message == "premium")
                {
                    premium.setBackgroundResource(R.drawable.linear_border_smooth_purple);
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void notice()
    {
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = null;

        builder = new NotificationCompat.Builder(this);
        builder.setContentText("time to water your plant");
        builder.setSmallIcon(android.R.drawable.ic_menu_view);
        Notification noti = builder.build();

        manager.notify(1,noti);
    }
}