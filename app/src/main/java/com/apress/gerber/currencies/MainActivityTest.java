package com.apress.gerber.currencies;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by fwang5 on 2/25/2016.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity mActivity;
    private Button mCalcButton;
    private TextView mConvertedTextView;
    private EditText mAmountEditText;
    private Spinner mForSpinner, mHomSpinner;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        ArrayList<String> bogusCurrencies = new ArrayList<String>();
        bogusCurrencies.add("USD|United State Dollar");
        bogusCurrencies.add("EUR|Euro");
        Intent intent = new Intent();
        intent.putExtra(SplashActivity.KEY_ARRAYLIST, bogusCurrencies);
        setActivityIntent(intent);

        mActivity = getActivity();
        mConvertedTextView = (TextView) mActivity.findViewById(R.id.txt_converted);
        mAmountEditText = (EditText) mActivity.findViewById(R.id.edt_amount);
        mCalcButton = (Button) mActivity.findViewById(R.id.btn_calc);
        mForSpinner = (Spinner) mActivity. findViewById(R.id.spn_for);
        mHomSpinner = (Spinner) mActivity.findViewById(R.id.spn_hom);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testInteger() throws Throwable {
        proxyCurrencyConverterTask("12");
    }

    public void testFloat() throws Throwable {
        proxyCurrencyConverterTask("12.3");

    }

    public void proxyCurrencyConverterTask (final String str) throws Throwable {
        final CountDownLatch latch = new CountDownLatch(1);
        mActivity.setCurrencyTaskCallBack(new MainActivity.CurrencyTaskCallBack() {
            @Override
            public void executionDone() {
                latch.countDown();
                assertEquals(convertToDouble(mConvertedTextView.getText().toString().
                        substring(0, 5)), convertToDouble(str));
            }
        });
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAmountEditText.setText(str);
                mForSpinner.setSelection(0);
                mHomSpinner.setSelection(0);
                mCalcButton.performClick();
            }
        });latch.await(30, TimeUnit.SECONDS);
    }
    private double convertToDouble(String str) throws NumberFormatException{
        double dReturn = 0;
        try {
            dReturn = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            throw e;
        }
        return dReturn;
    }
}
