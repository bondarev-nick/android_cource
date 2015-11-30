package com.chebur.hw_7_2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements
        MyDialog.DlgEventListener {

    public static final String MY_KEY = "arwtht";
    private Button _btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _btn = (Button) findViewById(R.id.btn);
        _btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _showDialog();
            }
        });
    }

    void _showDialog() {
        MyDialog dlg = new MyDialog();
        String btnText = _btn.getText().toString();
        if (!btnText.equals(getResources().getString(R.string.btn_name))) {
            Bundle bnd = new Bundle();
            bnd.putCharSequence(MY_KEY, _btn.getText());
            dlg.setArguments(bnd);
        }
        dlg.show(getSupportFragmentManager(), "tag2");
    }

    @Override
    public void onYes(String str) {
        _btn.setText(str);
    }

    @Override
    public void onNo() {
        _btn.setText(getResources().getText(R.string.btn_name));
    }

    @Override
    public void onCancel() {

    }
}
