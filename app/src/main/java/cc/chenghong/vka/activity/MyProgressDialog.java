package cc.chenghong.vka.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import cc.chenghong.vka.dialog.IndeterminateProgressBar;

public class MyProgressDialog extends Dialog{
	
	final static String TAG = "MyProgressDialog";
    private TextView tvMessage;
    private String message;
    private long number;
    private IndeterminateProgressBar progressBar;
    Context context;
    
    public MyProgressDialog(Context context) {
    	super(context,R.style.TouMingDialog);
    	this.context = context;
    	// TODO Auto-generated constructor stub
    }

    public MyProgressDialog(Context context, String message) {
        super(context, R.style.TouMingDialog);
        this.context = context;
        this.message = message;
    }

    public MyProgressDialog(Context context, int theme){
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_progress);
        progressBar = (IndeterminateProgressBar) findViewById(R.id.ipb);
        tvMessage = (TextView)findViewById(R.id.tv);
        if(message != null){
            tvMessage.setText(message);
        }
    }

    public void show(String message) {
    	try{
    		if(progressBar!= null){
                progressBar.start();
            }
            super.show();
            this.message = message;
            if(tvMessage != null && message != null){
                tvMessage.setText(message);
            }
    	}catch(Exception e){
    		Log.e(TAG, e.getMessage());
    	}
    }
    
    public void show(int msgResId){
        show(getContext().getString(msgResId));
    }

    @Override
    public void show() {
        if(progressBar!= null){
            progressBar.start();
        }
        super.show();
    }

    public MyProgressDialog setMesage(String message){
        this.message = message;
        if(tvMessage != null && message != null){
            tvMessage.setText(message);
        }
        return this;
    }

    public MyProgressDialog setMesage(int resId){
        this.message = getContext().getString(resId);
        if(tvMessage != null && message != null){
            tvMessage.setText(message);
        }
        return this;
    }

    public void setNumber(long number) {
        this.number = number;
    }
    public long getNumber() {
        return number;
    }

    @Override
    public void hide() {
        if(progressBar!= null){
            progressBar.stop();
        }
        super.hide();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
