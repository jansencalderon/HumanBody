package capstone.tip.simulation;

import android.app.Dialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Locale;

public class HumanBodyActivity extends AppCompatActivity implements View.OnDragListener, View.OnTouchListener, TextToSpeech.OnInitListener {

    ImageView catScratch, hodgkinsDisease, necrotizing, syphilis, tetanus;
    TextToSpeech textToSpeech;
    LinearLayout panel, humanbodyLayout;
    public static final int[] SIMULATION_DRAWABLES = {R.drawable.cat_scratch, R.drawable.hodgkins_disease, R.drawable.necrotizing
            , R.drawable.syphilis, R.drawable.tetanus};


    public static final int[] HUMANBODY_PANEL = {R.id.cat_scratch, R.id.hodgkinds_disease, R.id.necrotizing, R.id.syphilis, R.id.tetanus};

    public View onHold = null;

    int[] drawables_cats = {R.drawable.catscratch_img_1, R.drawable.catscratch_img_2, R.drawable.catscratch_img_3, R.drawable.catscratch_img_4};

    int[] drawables_tetanus = {R.drawable.tetanus_img_1, R.drawable.tetanus_img_2, R.drawable.tetanus_img_3};

    int[] drawables_hodgkins = {R.drawable.hodgkins_img_1,
            R.drawable.hodgkins_img_2,
            R.drawable.hodgkins_img_3,
            R.drawable.hodgkins_img_4,
            R.drawable.hodgkins_img_5,
            R.drawable.hodgkins_img_6,
            R.drawable.hodgkins_img_7,
            R.drawable.hodgkins_img_8,
            R.drawable.hodgkins_img_9};

    int[] drawables_syphilis = {R.drawable.syphilis_img_1,
            R.drawable.syphilis_img_2,
            R.drawable.syphilis_img_3,
            R.drawable.syphilis_img_4,
            R.drawable.syphilis_img_5};
    int[] drawables_necrotizing = {R.drawable.necrotizing_img_1,
            R.drawable.necrotizing_img_2,
            R.drawable.necrotizing_img_3,
            R.drawable.necrotizing_img_4,
            R.drawable.necrotizing_img_4,
            R.drawable.necrotizing_img_5,
            R.drawable.necrotizing_img_6};

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_human_body);

        textToSpeech = new TextToSpeech(this, this);

        panel = (LinearLayout) findViewById(R.id.panel);
        humanbodyLayout = (LinearLayout) findViewById(R.id.humanBody);
        catScratch = (ImageView) findViewById(R.id.cat_scratch);
        hodgkinsDisease = (ImageView) findViewById(R.id.hodgkinds_disease);
        necrotizing = (ImageView) findViewById(R.id.necrotizing);
        syphilis = (ImageView) findViewById(R.id.syphilis);
        tetanus = (ImageView) findViewById(R.id.tetanus);

        panel.setOnDragListener(this);
        humanbodyLayout.setOnDragListener(this);


        ImageView[] imageViews = {catScratch, hodgkinsDisease, necrotizing, syphilis, tetanus};

        for (int i = 0; i < imageViews.length; i++) {
            Glide.with(this)
                    .load(SIMULATION_DRAWABLES[i])
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .priority(Priority.IMMEDIATE)
                    .dontAnimate()
                    .into(imageViews[i]);

            imageViews[i].setOnTouchListener(this);
        }


    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        ImageView imageView = (ImageView) dragEvent.getLocalState();
        switch (dragEvent.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                break;
            case DragEvent.ACTION_DROP:
                if (isEnterable(view, imageView)) {
                    if (onHold != null) {
                        humanbodyLayout.removeView(onHold);
                        panel.addView(onHold);
                        onHold.setVisibility(View.VISIBLE);
                    }
                    View view1 = (View) dragEvent.getLocalState();
                    ViewGroup owner = (ViewGroup) view1.getParent();
                    owner.removeView(view1);
                    LinearLayout container = (LinearLayout) view;
                    container.addView(view1);
                    view1.setVisibility(View.VISIBLE);
                    onHold = view1;
                    showResult(view1.getId());
                } else {
                    imageView.setVisibility(View.VISIBLE);
                }
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                break;
        }
        return true;
    }

    private void showResult(int id) {
        switch (id) {
            case R.id.cat_scratch:
                showDialog("Cat's Scratch", getString(R.string.catscratch_string),getString(R.string.catscratch_string_tts), drawables_cats);
                break;
            case R.id.hodgkinds_disease:
                showDialog("Hodgkin's Disease", getString(R.string.hodgkins_string),getString(R.string.hodgkins_string_tts), drawables_hodgkins);
                break;
            case R.id.necrotizing:
                showDialog("Necrotizing Fasciitis", getString(R.string.necrotizing_string),getString(R.string.necrotizing_string_tts), drawables_necrotizing);
                break;
            case R.id.syphilis:
                showDialog("Syphilis", getString(R.string.syphilis_string), getString(R.string.syphilis_string_tts),drawables_syphilis);
                break;
            case R.id.tetanus:
                showDialog("Tetanus", getString(R.string.tetanus_string), getString(R.string.tetanus_string_tts),drawables_tetanus);
                break;
        }
    }

    private void showDialog(String title, String resultText, String tts, int[] resultDrawables) {
        Dialog d = new Dialog(this);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setCancelable(true);
        d.setContentView(R.layout.dialog_result);
        TextView result = (TextView) d.findViewById(R.id.result);
        TextView titleTv = (TextView) d.findViewById(R.id.title);
        LinearLayout imagesPanel = (LinearLayout) d.findViewById(R.id.imageLayout);
        result.setText(Html.fromHtml(resultText));
        titleTv.setText(title);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(tts, TextToSpeech.QUEUE_FLUSH, null,tts);
        } else {
            //noinspection deprecation
            textToSpeech.speak(resultText, TextToSpeech.QUEUE_FLUSH, null);
        }


        for (int i = 0; i < resultDrawables.length; i++) {
            final ImageView imageView = new ImageView(this);

            Glide.with(this)
                    .load(resultDrawables[i])
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(imageView);
            imagesPanel.addView(imageView);
        }
        d.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (textToSpeech != null) {
                    textToSpeech.stop();
                }
            }
        });
        d.show();

    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            ClipData clipData = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(clipData, shadowBuilder, view, 0);
            view.setVisibility(View.INVISIBLE);
            return true;
        } else {
            return false;
        }
    }

    private boolean isEnterable(View view, ImageView imageView) {
        switch (view.getId()) {
            case R.id.humanBody:
                return asd(HUMANBODY_PANEL, (int) imageView.getId());
            case R.id.panel:
                return false;
            default:
                return false;
        }
    }

    private boolean asd(int[] panels, int id) {
        for (int i = 0; i < panels.length; i++) {
            if (id == panels[i]) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void onInit(int i) {
        if (i == TextToSpeech.SUCCESS) {

            int result = textToSpeech.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
                Toast.makeText(this, "The TextToSpeech Language is not Supported", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "TextToSpeech Initialization Failed!", Toast.LENGTH_SHORT).show();
            Log.e("TTS", "Initilization Failed!");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        textToSpeech.shutdown();
    }
}
