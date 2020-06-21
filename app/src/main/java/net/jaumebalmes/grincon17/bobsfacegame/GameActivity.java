package net.jaumebalmes.grincon17.bobsfacegame;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{
    private final int LENGHT = 24;
    private int [] faces;
    private ImageView [] imageViews;
    private int [] imageViewReferences;
    private ArrayList<Integer> ids = new ArrayList<>();
    private AnimationDrawable timerAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView timer = findViewById(R.id.imageViewTimer);
        timer.setBackgroundResource(R.drawable.anim_timer);
        timerAnim = (AnimationDrawable) timer.getBackground();
        timerAnim.start();
        faces = getFaceCards();
        imageViews = getImageView();
        imageViewReferences = getImageViewReferences();

        for(int i = 0; i < LENGHT; i++) {
            imageViews[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        for(int i = 0; i < LENGHT; i++) {
            if(v.getId() == imageViewReferences[i]) {
                imageViews[i].setImageResource(faces[i]);
            }
        }
        handleMatch(v.getId());
    }

    public int [] getImageViewReferences() {
        return new int[] {
                R.id.imageView0, R.id.imageView1, R.id.imageView2, R.id.imageView3, R.id.imageView4,
                R.id.imageView5, R.id.imageView6, R.id.imageView7, R.id.imageView8, R.id.imageView9,
                R.id.imageView10, R.id.imageView11, R.id.imageView12, R.id.imageView13, R.id.imageView14,
                R.id.imageView15, R.id.imageView16, R.id.imageView17, R.id.imageView18, R.id.imageView19,
                R.id.imageView20, R.id.imageView21, R.id.imageView22, R.id.imageView23
        };
    }

    public int[] getFaceCards() {
        int[] id = new int[]{
                R.drawable.bean, R.drawable.bean, R.drawable.bush, R.drawable.bush,
                R.drawable.chuck, R.drawable.chuck, R.drawable.eli, R.drawable.eli,
                R.drawable.jeff, R.drawable.jeff, R.drawable.johnson, R.drawable.johnson,
                R.drawable.kim, R.drawable.kim, R.drawable.nixon, R.drawable.nixon,
                R.drawable.ophra, R.drawable.ophra, R.drawable.putin, R.drawable.putin,
                R.drawable.arnold, R.drawable.arnold, R.drawable.trump, R.drawable.trump
        };
        Random random = new Random();
        for (int i = id.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = id[i];
            id[i] = id[j];
            id[j] = temp;
        }
        return id;
    }

    public ImageView [] getImageView() {
        return new ImageView[]{
                findViewById(R.id.imageView0), findViewById(R.id.imageView1), findViewById(R.id.imageView2),
                findViewById(R.id.imageView3), findViewById(R.id.imageView4), findViewById(R.id.imageView5),
                findViewById(R.id.imageView6), findViewById(R.id.imageView7), findViewById(R.id.imageView8),
                findViewById(R.id.imageView9), findViewById(R.id.imageView10), findViewById(R.id.imageView11),
                findViewById(R.id.imageView12), findViewById(R.id.imageView13), findViewById(R.id.imageView14),
                findViewById(R.id.imageView15), findViewById(R.id.imageView16), findViewById(R.id.imageView17),
                findViewById(R.id.imageView18), findViewById(R.id.imageView19), findViewById(R.id.imageView20),
                findViewById(R.id.imageView21), findViewById(R.id.imageView22), findViewById(R.id.imageView23)
        };
    }

    public void handleMatch(Integer id) {
        Bitmap bitmap = null;
        Bitmap bitmap1 = null;
        ImageView i1 = null;
        ImageView i2 = null;
        int posOldId = 0;
        int posNewId = 0;
       ids.add(id);
        if (ids.size() == 2) {
            Integer newId = ids.get(0);
            Integer oldId = ids.get(1);
            if(newId.intValue() != oldId.intValue()) {
                for(int i = 0; i < LENGHT; i++) {
                    if(oldId == imageViewReferences[i]) {
                        bitmap = ((BitmapDrawable) imageViews[i].getDrawable()).getBitmap();
                        i1 = imageViews[i];
                        posOldId = i;
                    }
                }
                for(int i = 0; i < LENGHT; i++) {
                    if(newId == imageViewReferences[i]) {
                        bitmap1 = ((BitmapDrawable) imageViews[i].getDrawable()).getBitmap();
                        i2 = imageViews[i];
                        posNewId = i;
                    }
                }
                if (!bitmap.sameAs(bitmap1)) {
                    flipCards(posOldId, posNewId);
                } else {
                    i1.setClickable(false);
                    i2.setClickable(false);
                    TextView msg = findViewById(R.id.textViewMsg);
                    msg.setText(R.string.match);
                    msg.setTextColor(getResources().getColor(R.color.colorAccentGreen));
                    msg.setVisibility(TextView.VISIBLE);
                    Animation matchMsg = AnimationUtils.loadAnimation(this, R.anim.match_msg);
                    msg.startAnimation(matchMsg);
                    msg.setVisibility(TextView.INVISIBLE);

                }
            }
            ids.remove(1);
            ids.remove(0);
        }
        endGame();
    }

    private void flipCards(final int posOldId, final int posNewId) {
        Handler handler = new Handler();
        TextView msg = findViewById(R.id.textViewMsg);
        msg.setText(R.string.fail);
        msg.setTextColor(getResources().getColor(R.color.colorAccentRed));
        msg.setVisibility(TextView.VISIBLE);
        Animation failMsg = AnimationUtils.loadAnimation(this, R.anim.fail_msg);
        msg.startAnimation(failMsg);
        msg.setVisibility(TextView.INVISIBLE);
        handler.postDelayed(new Runnable() {
            public void run() {
                imageViews[posOldId].setImageResource(R.drawable.bob_card_2);
                imageViews[posNewId].setImageResource(R.drawable.bob_card_2);
            }
        }, 800);
    }

    private void endGame() {
        Bitmap [] bitmaps = new Bitmap[24];
        Drawable drawableToCheck = getResources().getDrawable(R.drawable.bob_card_2);
        Bitmap bitmapToPutDrawable = ((BitmapDrawable) drawableToCheck).getBitmap();
        for(int i = 0; i < LENGHT; i++) {
            bitmaps[i] = ((BitmapDrawable)imageViews[i].getDrawable()).getBitmap();
        }
        if (!bitmaps[0].sameAs(bitmapToPutDrawable) && !bitmaps[1].sameAs(bitmapToPutDrawable) &&
                !bitmaps[2].sameAs(bitmapToPutDrawable) && !bitmaps[3].sameAs(bitmapToPutDrawable) &&
                !bitmaps[4].sameAs(bitmapToPutDrawable) && !bitmaps[5].sameAs(bitmapToPutDrawable) &&
                !bitmaps[6].sameAs(bitmapToPutDrawable) && !bitmaps[7].sameAs(bitmapToPutDrawable) &&
                !bitmaps[10].sameAs(bitmapToPutDrawable) && !bitmaps[11].sameAs(bitmapToPutDrawable) &&
                !bitmaps[12].sameAs(bitmapToPutDrawable) && !bitmaps[13].sameAs(bitmapToPutDrawable) &&
                !bitmaps[14].sameAs(bitmapToPutDrawable) && !bitmaps[15].sameAs(bitmapToPutDrawable) &&
                !bitmaps[16].sameAs(bitmapToPutDrawable) && !bitmaps[17].sameAs(bitmapToPutDrawable) &&
                !bitmaps[18].sameAs(bitmapToPutDrawable) && !bitmaps[19].sameAs(bitmapToPutDrawable) &&
                !bitmaps[20].sameAs(bitmapToPutDrawable) && !bitmaps[21].sameAs(bitmapToPutDrawable) &&
                !bitmaps[22].sameAs(bitmapToPutDrawable) && !bitmaps[23].sameAs(bitmapToPutDrawable)) {

                startActivity(new Intent(GameActivity.this, GameWonActivity.class));
        }
    }

}
