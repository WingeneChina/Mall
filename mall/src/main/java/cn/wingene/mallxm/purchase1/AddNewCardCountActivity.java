package cn.wingene.mallxm.purchase1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import cn.wingene.mall.R;


public class AddNewCardCountActivity extends AppCompatActivity {

    private ImageView backIcon;
    private TextView titleV;
    private Spinner bankSpinnerV;
    private EditText bankBranchV;
    private EditText bankCardNumberV;
    private EditText bankCardUserNameV;
    private TextView saveCardInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_card_count);
        initViews();
    }

    private void initViews() {
        backIcon = (ImageView) findViewById(R.id.backIcon);
        titleV = (TextView) findViewById(R.id.titleV);
        bankSpinnerV = (Spinner) findViewById(R.id.bankSpinnerV);
        bankBranchV = (EditText) findViewById(R.id.bankBranchV);
        bankCardNumberV = (EditText) findViewById(R.id.bankCardNumberV);
        bankCardUserNameV = (EditText) findViewById(R.id.bankCardUserNameV);
        saveCardInfo = (TextView) findViewById(R.id.saveCardInfo);
    }
}
