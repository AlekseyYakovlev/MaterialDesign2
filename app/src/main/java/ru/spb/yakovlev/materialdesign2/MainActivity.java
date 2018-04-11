package ru.spb.yakovlev.materialdesign2;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    private static final int MIN_TEXT_LENGTH = 8;
    private static final String EMPTY_STRING = "";
    private TextInputLayout mTextInputLayout;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextInputLayout = findViewById(R.id.textInputLayout);
        mEditText = findViewById(R.id.editTextName);
        mTextInputLayout.setHint(getString(R.string.hint));
        mEditText.setOnEditorActionListener(ActionListener.newInstance(this));
    }

    private boolean shouldShowError() {
        int textLength = mEditText.getText().length();
        return textLength > 0 && textLength < MIN_TEXT_LENGTH;
    }

    private void showError() {
        mTextInputLayout.setError(getString(R.string.error));
    }

    private void hideError() {
        mTextInputLayout.setError(EMPTY_STRING);
    }

    private static final class ActionListener implements TextView.OnEditorActionListener {
        private final WeakReference<MainActivity> mainActivityWeakReference;

        private ActionListener(WeakReference<MainActivity> mainActivityWeakReference) {
            this.mainActivityWeakReference = mainActivityWeakReference;
        }

        public static ActionListener newInstance(MainActivity mainActivity) {
            WeakReference<MainActivity> mainActivityWeakReference = new
                    WeakReference<>(mainActivity);
            return new ActionListener(mainActivityWeakReference);
        }

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            MainActivity mainActivity = mainActivityWeakReference.get();
            if (mainActivity != null) {
                if (actionId == EditorInfo.IME_ACTION_GO && mainActivity.shouldShowError()) {
                    mainActivity.showError();
                } else {
                    mainActivity.hideError();
                }
            }
            return true;
        }
    }
}
