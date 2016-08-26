package cc.chenghong.vka.code.qr_codescan;

import java.io.IOException;
import java.util.Vector;

import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.Toast;

import cc.chenghong.vka.activity.BaseActivity;
import cc.chenghong.vka.activity.R;
import cc.chenghong.vka.code.camera.CameraManager;
import cc.chenghong.vka.code.decoding.CaptureActivityHandler;
import cc.chenghong.vka.code.decoding.InactivityTimer;
import cc.chenghong.vka.code.view.ViewfinderView;
import cc.chenghong.vka.entity.FirstEvent;
import cc.chenghong.vka.app.App;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import de.greenrobot.event.EventBus;

/**
 * Initial the camera
 *
 * @author Ryan.Tang
 */
public class MipcaActivityCapture extends BaseActivity implements Callback {

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;

    /**
     * 付款码
     */
    public String auth_code;
    /**
     * 商品描述
     */
    public String body;
    /**
     * 付款金额
     */
    public int total_fee;
    /**
     * accessToken码
     */
    public String accessToken;

    // /**返回按钮*/
    // private ImageButton ib_left;
    // /**标题*/
    // private TextView tv_center;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        // ViewUtil.addTopView(getApplicationContext(), this,
        // R.string.scan_card);
        CameraManager.init(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        body = "V-Ka收款POS";
        accessToken = App.getUser().data.accessToken;
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    /**
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        // playBeepSoundAndVibrate();
        final String resultString = result.getText();
        if (resultString.equals("")) {
            Toast.makeText(MipcaActivityCapture.this, "扫描失败，请重新扫描!",
                    Toast.LENGTH_SHORT).show();
            this.finish();
        } else {
            App.payCode = resultString;// 保存扫描的码
            MipcaActivityCapture.this.finish();
            if (App.payType == 1) {// 支付宝支付
                EventBus.getDefault().post(new FirstEvent(resultString, App.payType)); // 发送消息
            } else if (App.payType == 2) {// 微信支付
                EventBus.getDefault().post(new FirstEvent(resultString, App.payType)); // 发送消息
            } else if (App.payType == 4) {// qq支付
                EventBus.getDefault().post(new FirstEvent(resultString, App.payType)); // 发送消息
            } else if (App.payType == 5) {// 核销卡券
                EventBus.getDefault().post(new FirstEvent(resultString, App.payType)); // 发送消息
            } else if (App.payType == 6) {// 核销优惠券
                EventBus.getDefault().post(new FirstEvent(resultString, App.payType)); // 发送消息
            } else {// 会员卡支付扫码
                EventBus.getDefault().post(new FirstEvent(resultString, App.payType)); // 发送消息
            }

        }

    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            // AssetFileDescriptor file = getResources().openRawResourceFd(
            // R.raw.beep);
            // try {
            // mediaPlayer.setDataSource(file.getFileDescriptor(),
            // file.getStartOffset(), file.getLength());
            // file.close();
            // mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
            // mediaPlayer.prepare();
            // } catch (IOException e) {
            // mediaPlayer = null;
            // }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };
}