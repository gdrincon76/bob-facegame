package net.jaumebalmes.grincon17.bobsfacegame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class GameWonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_won);
        getSupportActionBar().setHomeButtonEnabled(true);

        ImageView winnerImage = findViewById(R.id.imageViewWinner);
        winnerImage.setBackgroundResource(R.drawable.anim_game_won_version_b);
        AnimationDrawable winnerSprite = (AnimationDrawable) winnerImage.getBackground();
        winnerSprite.start();

        TextView winnerMsg = findViewById(R.id.textViewWinnerMsg);
        Animation winnerMsgAnim = AnimationUtils.loadAnimation(this, R.anim.win_msg);
        winnerMsg.startAnimation(winnerMsgAnim);
    }
}
