package com.fais.hexorms.presentation;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;

        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.RelativeLayout;
        import android.widget.SeekBar;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toolbar;

        import com.fais.hexorms.R;

        import butterknife.Bind;
        import butterknife.ButterKnife;
        import butterknife.OnCheckedChanged;
        import butterknife.OnClick;
        import butterknife.OnItemSelected;


public class MenuActivity extends Activity {

    @Bind(R.id.seekbar)
    SeekBar sizePicker;
    @Bind(R.id.boardSize)
    TextView boardSizeTextView;
    @Bind(R.id.opponent_checkbox_human)
    CheckBox humanCheckbox;
    @Bind(R.id.opponent_checkbox_ai)
    CheckBox aiCheckbox;
    @Bind(R.id.difficulty_spinner)
    Spinner difficultySpinner;
    @Bind(R.id.start_game_button)
    Button startButton;
    @Bind(R.id.difficulty_layout)
    RelativeLayout difficultyLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private int difficultyPosition = -1;
    private int boardSize = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set content view
        setContentView(R.layout.activity_menu);
        // bind this view
        ButterKnife.bind(this);
        // setup Toolbar
        setActionBar(toolbar);
        if (getActionBar() != null) {
            getActionBar().setTitle(R.string.title_activity_menu);
        }
        // set initial values for checkboxes
        humanCheckbox.setChecked(true);
        aiCheckbox.setChecked(false);
        // setup  board size picker
        initSizePicker();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // All clicks on menu items needs to be here
        return super.onOptionsItemSelected(item);
    }

    @OnCheckedChanged(R.id.opponent_checkbox_human)
    public void onHumanChecked(boolean checked){
        if(checked){
            aiCheckbox.setChecked(false);
            difficultyLayout.setVisibility(View.INVISIBLE);
            startButton.setClickable(true);
        }else {
            aiCheckbox.setChecked(true);
            difficultyLayout.setVisibility(View.VISIBLE);
        }
    }
    @OnCheckedChanged(R.id.opponent_checkbox_ai)
    public void onAIChecked(boolean checked){
        if(checked){
            humanCheckbox.setChecked(false);
            difficultyLayout.setVisibility(View.VISIBLE);
            difficultySpinner.setSelection(0);
        }else {
            humanCheckbox.setChecked(true);
            startButton.setClickable(true);
            difficultyLayout.setVisibility(View.INVISIBLE);
        }
    }

    @OnItemSelected(R.id.difficulty_spinner)
    void onItemSelected(int position){
        if (aiCheckbox.isChecked()) {
            startButton.setClickable(true);
            difficultyPosition = position;
        }
    }

    @OnClick(R.id.start_game_button)
    public void onClick(View view){
    }


    private void initSizePicker() {
        boardSizeTextView.setText(boardSize + " x " + boardSize);
        sizePicker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // add minimal size
                boardSize = 3 + progress;
                boardSizeTextView.setText(boardSize + " x " + boardSize);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


}