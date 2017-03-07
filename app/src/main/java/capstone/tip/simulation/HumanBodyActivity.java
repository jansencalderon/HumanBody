package capstone.tip.simulation;

import android.app.Dialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Locale;

public class HumanBodyActivity extends AppCompatActivity implements View.OnDragListener, View.OnTouchListener, TextToSpeech.OnInitListener {

    TextToSpeech textToSpeech;
    LinearLayout panel, humanbodyLayout;

    public int[] HUMANBODY;
    private int count = 0;
    private int choice;

    public View onHold = null;

    int[] drawables_cats = {
            R.drawable.catscratch_img_1,
            R.drawable.catscratch_img_2,
            R.drawable.catscratch_img_3,
            R.drawable.catscratch_img_4};

    int[] drawables_tetanus = {
            R.drawable.tetanus_img_1,
            R.drawable.tetanus_img_2,
            R.drawable.tetanus_img_3};

    int[] drawables_hodgkins = {R.drawable.hodgkins_img_1,
            R.drawable.hodgkins_img_2,
            R.drawable.hodgkins_img_3,
            R.drawable.hodgkins_img_4,
            R.drawable.hodgkins_img_5};

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
            R.drawable.necrotizing_img_5};

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_human_body);

        textToSpeech = new TextToSpeech(this, this);

        panel = (LinearLayout) findViewById(R.id.panel);
        humanbodyLayout = (LinearLayout) findViewById(R.id.humanBody);

        panel.setOnDragListener(this);
        humanbodyLayout.setOnDragListener(this);


        chooseDialog();

        TextView reset = (TextView) findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HumanBodyActivity.this, HumanBodyActivity.class));
                finish();
            }
        });


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
                    View view1 = (View) dragEvent.getLocalState();
                    ViewGroup owner = (ViewGroup) view1.getParent();
                    owner.removeView(view1);
                    LinearLayout container = (LinearLayout) view;
                    container.addView(view1);
                    view1.setVisibility(View.VISIBLE);
                    count = count + 1;
                    onHold = view1;
                    if (isFinished(count)) {
                        showResult(choice);
                    }
                } else {
                    imageView.setVisibility(View.VISIBLE);
                }
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                break;
        }
        return true;
    }

    private boolean isFinished(int count) {
        return HUMANBODY.length == count;
    }

    private void showResult(int id) {
        switch (id) {
            case 1:
                showDialog("Cat's Scratch", getString(R.string.catscratch_string), getString(R.string.catscratch_string_tts), drawables_cats);
                break;
            case 2:
                showDialog("Hodgkin's Disease", getString(R.string.hodgkins_string), getString(R.string.hodgkins_string_tts), drawables_hodgkins);
                break;
            case 3:
                showDialog("Necrotizing Fasciitis", getString(R.string.necrotizing_string), getString(R.string.necrotizing_string_tts), drawables_necrotizing);
                break;
            case 4:
                showDialog("Syphilis", getString(R.string.syphilis_string), getString(R.string.syphilis_string_tts), drawables_syphilis);
                break;
            case 5:
                showDialog("Tetanus", getString(R.string.tetanus_string), getString(R.string.tetanus_string_tts), drawables_tetanus);
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
            textToSpeech.speak(tts, TextToSpeech.QUEUE_FLUSH, null, tts);
        } else {
            //noinspection deprecation
            textToSpeech.speak(resultText, TextToSpeech.QUEUE_FLUSH, null);
        }


        for (int resultDrawable : resultDrawables) {
            final ImageView imageView = new ImageView(this);
            Glide.with(this)
                    .load(resultDrawable)
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

    private void chooseDialog() {
        final Dialog d = new Dialog(this);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setCancelable(true);
        d.setContentView(R.layout.dialog_choose);

        LinearLayout buttonCat = (LinearLayout) d.findViewById(R.id.cat_scratch_button);
        LinearLayout buttonHodgkins = (LinearLayout) d.findViewById(R.id.hodgkinds_disease_button);
        LinearLayout buttonNecrotizing = (LinearLayout) d.findViewById(R.id.necrotizing_button);
        LinearLayout buttonSyphilis = (LinearLayout) d.findViewById(R.id.syphilis_button);
        LinearLayout buttonTetanus = (LinearLayout) d.findViewById(R.id.tetanus_button);

        buttonCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice = 1;
                initializeData(1);
                HUMANBODY = drawables_cats;
                d.dismiss();
            }
        });

        buttonHodgkins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice = 2;
                initializeData(2);
                HUMANBODY = drawables_hodgkins;
                d.dismiss();

            }
        });

        buttonNecrotizing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice = 3;
                initializeData(3);
                HUMANBODY = drawables_necrotizing;
                d.dismiss();
            }
        });

        buttonSyphilis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice = 4;
                initializeData(4);
                HUMANBODY = drawables_syphilis;
                d.dismiss();
            }
        });

        buttonTetanus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice = 5;
                initializeData(5);
                HUMANBODY = drawables_tetanus;
                d.dismiss();
            }
        });


        d.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });

        d.show();

    }

    private void initializeData(int choice) {
        int[] drawable = null;
        switch (choice) {
            case 1:
                drawable = drawables_cats;
                break;
            case 2:
                drawable = drawables_hodgkins;
                break;
            case 3:
                drawable = drawables_necrotizing;
                break;
            case 4:
                drawable = drawables_syphilis;
                break;
            case 5:
                drawable = drawables_tetanus;
                break;

        }

        assert drawable != null;
        for (int i = 0; i < drawable.length; i++) {
            final ImageView imageView = new ImageView(this);
            final float scale = getResources().getDisplayMetrics().density;
            int dpWidthInPx = (int) (90 * scale);
            int dpHeightInPx = (int) (60 * scale);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dpWidthInPx, dpHeightInPx);
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(this)
                    .load(drawable[i])
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(imageView);
            imageView.setOnTouchListener(this);
            imageView.setId(i);
            panel.addView(imageView);
        }
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
                return asd(HUMANBODY);
            case R.id.panel:
                return false;
            default:
                return false;
        }
    }

    private boolean asd(int[] panels) {
        for (int i = 0; i < panels.length; i++) {
            if (panels[i] == panels[i]) {
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
