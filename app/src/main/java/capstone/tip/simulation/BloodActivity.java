package capstone.tip.simulation;

import android.content.ClipData;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Locale;

public class BloodActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood);
        textToSpeech = new TextToSpeech(this, this);

        initiateStep1();
    }

    private void initiateStep1() {
        final RelativeLayout step1 = (RelativeLayout) findViewById(R.id.step1);
        step1.setVisibility(View.VISIBLE);
        LinearLayout panel = (LinearLayout) findViewById(R.id.step1_panel);
        ImageView syringe = (ImageView) findViewById(R.id.step1_syringe);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.patient);
        final Button button = (Button) findViewById(R.id.proceed_to_step2);
        button.setVisibility(View.GONE);

        step1.setOnDragListener(new View.OnDragListener() {
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
                        if (isEnterableStep1(view, imageView)) {
                            View view1 = (View) dragEvent.getLocalState();
                            ViewGroup owner = (ViewGroup) view1.getParent();
                            owner.removeView(view1);
                            LinearLayout container = (LinearLayout) view;
                            container.addView(view1);
                            view1.setVisibility(View.VISIBLE);
                            imageView.setImageResource(R.drawable.syringe);
                            button.setVisibility(View.VISIBLE);
                        } else {
                            imageView.setVisibility(View.VISIBLE);
                        }
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        break;
                }
                return true;
            }
        });
        linearLayout.setOnDragListener(new View.OnDragListener() {
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
                        if (isEnterableStep1(view, imageView)) {
                            View view1 = (View) dragEvent.getLocalState();
                            ViewGroup owner = (ViewGroup) view1.getParent();
                            owner.removeView(view1);
                            LinearLayout container = (LinearLayout) view;
                            container.addView(view1);
                            view1.setVisibility(View.VISIBLE);
                            imageView.setImageResource(R.drawable.syringe);
                            button.setVisibility(View.VISIBLE);
                        } else {
                            imageView.setVisibility(View.VISIBLE);
                        }
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        break;
                }
                return true;
            }
        });
        panel.setOnDragListener(new View.OnDragListener() {
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
                        if (isEnterableStep1(view, imageView)) {
                            View view1 = (View) dragEvent.getLocalState();
                            ViewGroup owner = (ViewGroup) view1.getParent();
                            owner.removeView(view1);
                            LinearLayout container = (LinearLayout) view;
                            container.addView(view1);
                            view1.setVisibility(View.VISIBLE);
                            imageView.setImageResource(R.drawable.syringe);
                            button.setVisibility(View.VISIBLE);
                        } else {
                            imageView.setVisibility(View.VISIBLE);
                        }
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        break;
                }
                return true;
            }
        });

        syringe.setOnTouchListener(new View.OnTouchListener() {
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
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step1.setVisibility(View.GONE);
                initializeStep2();
            }
        });
    }

    private void initializeStep2() {
        final RelativeLayout step2 = (RelativeLayout) findViewById(R.id.step2);
        step2.setVisibility(View.VISIBLE);
        LinearLayout panel = (LinearLayout) findViewById(R.id.step2_panel);
        ImageView syringe = (ImageView) findViewById(R.id.step2_syringe);
        final ImageView tube = (ImageView) findViewById(R.id.step2_tube);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.patient_step2);
        final Button button = (Button) findViewById(R.id.show_result);
        button.setVisibility(View.GONE);

        step2.setOnDragListener(new View.OnDragListener() {
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
                        if (isEnterableStep2(view, imageView)) {
                            View view1 = (View) dragEvent.getLocalState();
                            ViewGroup owner = (ViewGroup) view1.getParent();
                            owner.removeView(view1);
                            LinearLayout container = (LinearLayout) view;
                            container.addView(view1);
                            view1.setVisibility(View.VISIBLE);
                            imageView.setImageResource(R.drawable.syringe);
                            button.setVisibility(View.VISIBLE);
                            tube.setImageResource(R.drawable.tube);
                        } else {
                            imageView.setVisibility(View.VISIBLE);
                        }
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        break;
                }
                return true;
            }
        });


        linearLayout.setOnDragListener(new View.OnDragListener() {
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
                        if (isEnterableStep2(view, imageView)) {
                            View view1 = (View) dragEvent.getLocalState();
                            ViewGroup owner = (ViewGroup) view1.getParent();
                            owner.removeView(view1);
                            LinearLayout container = (LinearLayout) view;
                            container.addView(view1);
                            view1.setVisibility(View.VISIBLE);
                            imageView.setImageResource(R.drawable.syringe);
                            button.setVisibility(View.VISIBLE);
                            tube.setImageResource(R.drawable.tube);
                        } else {
                            imageView.setVisibility(View.VISIBLE);
                        }
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        break;
                }
                return true;
            }
        });
        panel.setOnDragListener(new View.OnDragListener() {
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
                        if (isEnterableStep2(view, imageView)) {
                            View view1 = (View) dragEvent.getLocalState();
                            ViewGroup owner = (ViewGroup) view1.getParent();
                            owner.removeView(view1);
                            LinearLayout container = (LinearLayout) view;
                            container.addView(view1);
                            view1.setVisibility(View.VISIBLE);
                            imageView.setImageResource(R.drawable.syringe);
                            button.setVisibility(View.VISIBLE);
                            tube.setImageResource(R.drawable.tube);
                        } else {
                            imageView.setVisibility(View.VISIBLE);
                        }
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        break;
                }
                return true;
            }
        });

        syringe.setOnTouchListener(new View.OnTouchListener() {
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
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step2.setVisibility(View.GONE);
                initializeResult();
            }
        });
    }

    private void initializeResult() {
        RelativeLayout step3 = (RelativeLayout) findViewById(R.id.step3);
        step3.setVisibility(View.VISIBLE);
        Button button = (Button) findViewById(R.id.close);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String tts = "The patient's washed RBCs are incubated with antihuman antibodies(Coombs reagent), RBCs agglutinate: antihuman antibodies form links between RBCs by binding to the human antibodies on the RBCs";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(tts, TextToSpeech.QUEUE_FLUSH, null, tts);
        } else {
            //noinspection deprecation
            textToSpeech.speak(tts, TextToSpeech.QUEUE_FLUSH, null);
        }


    }


    private boolean isEnterableStep1(View view, ImageView imageView) {
        switch (view.getId()) {
            case R.id.patient:
                return true;
            case R.id.step1_panel:
                return false;
            default:
                return false;
        }
    }

    private boolean isEnterableStep2(View view, ImageView imageView) {
        switch (view.getId()) {
            case R.id.patient:
                return false;
            case R.id.step2_panel:
                return true;
            default:
                return false;
        }
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
