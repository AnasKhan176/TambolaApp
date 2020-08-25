package com.uae.tambolaapp;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.adefruandta.spinningwheel.SpinningWheelView;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.appyvet.materialrangebar.IRangeBarFormatter;
import com.appyvet.materialrangebar.RangeBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.uae.tambolaapp.helper_classes.AppPrefs;
import com.uae.tambolaapp.helper_classes.Constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends BaseActivity implements SpinningWheelView.OnRotationListener<String> {

    Random random;
    TransparentText ranromNumber;
    EditText textView;
    TextView textViewList;
    FloatingActionButton imageButton;
    GridLayoutManager gridLayoutManager;
    HorizontalScrollView randomListParent;
    private SpinningWheelView wheelView;

    NumberGridAdapter adapter;

    ArrayList<Integer> mainArray;
    ArrayList<Integer> value;
    HashMap<Integer, Boolean> hashMap;
    TextToSpeech tts;

    boolean speechStatus = false;
    int k = 0, a = 0;
    AppPrefs prefs;

    private Switch sw_mode, sw_speak, sw_board;
    private RecyclerView mRecyclerView;
    private RelativeLayout outer_recycler;
    //Timer
    Handler timer_handler = new Handler();
    Runnable timer_runnable;
    private int min_time = 14;

    private RangeBar rangebar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ranromNumber = (TransparentText) findViewById(R.id.random_number);
        textView = (EditText) findViewById(R.id.random_number2);
        textViewList = (TextView) findViewById(R.id.random_list);
        imageButton = (FloatingActionButton) findViewById(R.id.fab);
        randomListParent = (HorizontalScrollView) findViewById(R.id.random_list_parent);

        sw_mode = (Switch) findViewById(R.id.sw_mode);
        sw_speak = (Switch) findViewById(R.id.sw_speak);
        sw_board = (Switch) findViewById(R.id.sw_board);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        wheelView = (SpinningWheelView) findViewById(R.id.wheel);
        outer_recycler = (RelativeLayout) findViewById(R.id.outer_recycler);
        rangebar = findViewById(R.id.rangebar1);
        final String[] anas = new String[1];
        // Sets the display values of the indices
        rangebar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex,
                                              int rightPinIndex, String leftPinValue, String rightPinValue) {
                anas[0] = rightPinValue;
            }

            @Override
            public void onTouchEnded(RangeBar rangeBar) {
                Log.d("RangeBar", "Touch ended");

                // toastLong(MainActivity.this, anas[0]);
                if (timer_handler != null) {
                    timer_handler.removeCallbacks(timer_runnable); //stop handler when activity not visible
                }
                if (min_time > Integer.parseInt(anas[0])) {
                    min_time = min_time - Integer.parseInt(anas[0]);
                } else {
                    min_time = Integer.parseInt(anas[0]) - min_time;
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        min_time = Integer.parseInt(anas[0]);
                        startTimer(min_time);
                    }
                }, min_time * 1000);

            }

            @Override
            public void onTouchStarted(RangeBar rangeBar) {
                Log.d("RangeBar", "Touch started");
            }
        });

        sw_board.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    textViewList.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    outer_recycler.setVisibility(View.VISIBLE);
                    randomListParent.setVisibility(View.VISIBLE);
                } else {
                    textViewList.setVisibility(View.INVISIBLE);
                    mRecyclerView.setVisibility(View.INVISIBLE);
                    outer_recycler.setVisibility(View.INVISIBLE);
                    randomListParent.setVisibility(View.INVISIBLE);

                }
            }
        });

        sw_speak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    speechStatus = true;
                } else {
                    speechStatus = false;
                }
            }
        });
        sw_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rangebar.setVisibility(View.VISIBLE);
                    randomListParent.setVisibility(View.VISIBLE);

                    if (timer_handler != null) {
                        timer_handler.removeCallbacks(timer_runnable); //stop handler when activity not visible
                    }
                    startTimer(min_time);

                } else {
                    rangebar.setVisibility(View.GONE);
                    randomListParent.setVisibility(View.INVISIBLE);
                    if (timer_handler != null) {
                        timer_handler.removeCallbacks(timer_runnable); //stop handler when activity not visible
                    }

                }
            }
        });

        random = new Random();
        prefs = new AppPrefs(this);

        resetValues();
        setActivityTitle("Game ID : " + String.valueOf(prefs.getGameID()));

        assert mRecyclerView != null;
        mRecyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(MainActivity.this, 9);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        registerForContextMenu(mRecyclerView);
        adapter = new NumberGridAdapter(mainArray, hashMap);
        mRecyclerView.setAdapter(adapter);

        // wheel start
/*
        wheelView.setItems(R.array.dummy);
*/
        wheelView.setOnRotationListener(this);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStartButtonStatus();
                randomListParent.setVisibility(View.VISIBLE);
            }
        });

        tts = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onInit(int status) {

                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("error", "This Language is not supported");
                    } else {
                        speechStatus = true;
                    }
                } else
                    Log.e("error", "Initilization Failed!");
            }
        });

    }

    private void startGame() {

        // max angle 50
        // duration 5 second
        // every 2 ms rander rotation
        wheelView.rotate(50, 5000, 2);

        ranromNumber.setVisibility(View.VISIBLE);
        imageButton.setClickable(false);

        if (value.size() > a) {
            k = 0;
            // yaha change
            //imageButton.setImageResource(R.drawable.media_play);
            timedRandomNumber();
        } else {
            if (tts != null) {
                tts.stop();
                tts.shutdown();
                Log.d("TAG", "TTS Destroyed");
            }
            timer_handler.removeCallbacks(timer_runnable); //stop handler when activity not visible
            long time_dalay = 0;
            if (sw_mode.isChecked()) {
                time_dalay = 6 * 1000;
            } else {
                time_dalay = time_dalay * 1000;
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    resetValues();
                    adapter.reFill(hashMap);
                    textView.setText("- -");
                    ranromNumber.setText("- -");
                    sw_mode.setChecked(false);
                    randomListParent.setVisibility(View.INVISIBLE);
                    gameOverDialog();
                }
            }, time_dalay);
        }
    }

    void resetValues() {

        a = 0;
        value = new ArrayList<>();
        mainArray = new ArrayList<>();
        hashMap = new HashMap<>();
        textViewList.setText("");

        for (int i = 1; i <= 90; i++) {
            mainArray.add(i);
            hashMap.put(i, false);
        }

        value.addAll(mainArray);
        Collections.shuffle(value);
    }

    public void timedRandomNumber() {

        imageButton.setClickable(true);

        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {

                ranromNumber.setText("");
                ranromNumber.setText(String.valueOf(random.nextInt(89)));

                textView.setText("");
                textView.setText(String.valueOf(random.nextInt(89)));

                if (k == 20) {
                    setOriginalValue();
                    adapter.reFill(hashMap);
                    adapter.notifyDataSetChanged();
                } else {
                    k++;
                    timedRandomNumber();
                }

            }
        }, 150);

    }

    @Override
    protected void onDestroy() {

        //Close the Text to Speech Library
        if (tts != null) {

            tts.stop();
            tts.shutdown();
            Log.d("TAG", "TTS Destroyed");
        }
        super.onDestroy();
    }

    public void setOriginalValue() {

        String temp = textViewList.getText() + String.valueOf(value.get(a) + "  ");
        textViewList.setText(temp);
        Log.d("TAG", textViewList.getText().toString() + "");
        randomListParent.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
        hashMap.put(value.get(a), true);
        textView.setText(String.valueOf(value.get(a)));
        ranromNumber.setVisibility(View.INVISIBLE);

        if (speechStatus) {
            try {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    tts.speak(textView.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, null);
                } else {
                    tts.speak(textView.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        ++a;

        if (a % 10 == 0) {
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.reSet:
                new MaterialDialog.Builder(this)
                        .title("Restart Game !!!")
                        .content("Are you sure, You want to restart the game.")
                        .positiveText(" Yes ")
                        .negativeText(" No ")
                        .autoDismiss(false)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull final MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.setContent("Please wait ..");
                                timer_handler.removeCallbacks(timer_runnable); //stop handler when activity not visible

                                long time_dalay = 0;
                                if (sw_mode.isChecked()) {
                                    time_dalay = 6 * 1000;
                                } else {
                                    time_dalay = time_dalay * 1000;
                                }
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog.dismiss();
                                        resetValues();
                                        adapter.reFill(hashMap);
                                        textView.setText("- -");
                                        ranromNumber.setText("- -");

                                        sw_mode.setChecked(false);
                                        prefs.setGameID(prefs.getGameID() + 1);
                                        setActivityTitle("Game ID : " + String.valueOf(prefs.getGameID()));
                                        randomListParent.setVisibility(View.INVISIBLE);
                                    }
                                }, time_dalay);
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();

                            }
                        })
                        .show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {

        new MaterialDialog.Builder(this)
                .title("Exit game !!!")
                .content("Are you sure? You want to exit from game.")
                .positiveText(" Yes ")
                .negativeText(" No ")
                .autoDismiss(true)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (timer_handler != null) {
                            timer_handler.removeCallbacks(timer_runnable); //stop handler when activity not visible
                        }
                        if (tts != null) {
                            tts.stop();
                            tts.shutdown();
                            Log.d("TAG", "TTS Destroyed");
                        }
                        MainActivity.super.onBackPressed();
                        finish();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                })
                .show();
    }

    @Override
    public void onRotation() {
        // Log.d("XXXX", "On Rotation");
    }

    @Override
    public void onStopRotation(String item) {
        //Toast.makeText(this, item, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkCheckbox();
    }

    @Override
    protected void onPause() {
        timer_handler.removeCallbacks(timer_runnable); //stop handler when activity not visible
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void checkCheckbox() {

        if (sw_mode.isChecked()) {
            if (min_time > 0) {
                startTimer(min_time);
            }
        } else {
            if (timer_handler != null) {
                timer_handler.removeCallbacks(timer_runnable); //stop handler when activity not visible
            }
        }
    }

    private void startTimer(int time) {
        min_time = time;
        final long delay = time * 1000;
        //start handler as activity become visible
        timer_handler.postDelayed(timer_runnable = new Runnable() {
            public void run() {
                startGame();
                timer_handler.postDelayed(timer_runnable, delay);
            }
        }, 2 * 1000);
    }

    private void gameOverDialog() {
        new MaterialDialog.Builder(this)
                .title("Game Over !!!")
                .cancelable(false)
                .content("Congratulation! Please start new Game.")
                .positiveText(" OK ")
                .autoDismiss(true)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        MainActivity.super.onBackPressed();
                        finish();
                    }
                }).show();
    }

    // set start button status
    private void setStartButtonStatus() {
        imageButton.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                imageButton.setEnabled(true);
                startGame();
            }
        }, 2000);
    }
}
