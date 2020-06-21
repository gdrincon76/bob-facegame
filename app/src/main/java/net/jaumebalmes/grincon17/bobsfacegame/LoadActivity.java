package net.jaumebalmes.grincon17.bobsfacegame;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class LoadActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        getSupportActionBar().setTitle(R.string.title_action_bar);
        ImageView bobIntro = findViewById(R.id.imageViewBob);
        bobIntro.setBackgroundResource(R.drawable.anim_bob_intro);
        AnimationDrawable bob = (AnimationDrawable) bobIntro.getBackground();
        bob.start();
        bobIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoadActivity.this, GameActivity.class));
            }
        });

        TextView helloTop = findViewById(R.id.textViewTopHello);
        TextView helloBottom = findViewById(R.id.textViewBottomHello);
        Animation helloTopAnim = AnimationUtils.loadAnimation(this, R.anim.hello_msg_1);
        Animation helloBottomAnim = AnimationUtils.loadAnimation(this, R.anim.hello_msg_2);
        helloTop.startAnimation(helloTopAnim);
        helloBottom.startAnimation(helloBottomAnim);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                loadSubtitleAnim();
            }
        }, 1000);
    }
    private void loadSubtitleAnim(){
        TextView helloSubtitle = findViewById(R.id.textViewSubtitle);
        helloSubtitle.setVisibility(TextView.VISIBLE);
        Animation helloSubtitleAnim = AnimationUtils.loadAnimation(this, R.anim.hello_subtitle);
        helloSubtitle.startAnimation(helloSubtitleAnim);
    }
}
