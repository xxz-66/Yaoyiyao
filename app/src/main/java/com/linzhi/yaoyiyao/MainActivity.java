package com.linzhi.yaoyiyao;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Vibrator vibrator;
    private static String strs[]={"石头","剪刀","布"};
    private static int pics[]={R.mipmap.shitou,R.mipmap.jiandao,R.mipmap.hand};
    private static final int SENSOR_SHAKE=15;
    private TextView text;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text=findViewById(R.id.txtlabel);
        img=findViewById(R.id.imageView);
        sensorManager= (SensorManager) getSystemService(SENSOR_SERVICE);
        vibrator= (Vibrator) getSystemService(VIBRATOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sensorManager!=null){
            sensorManager.registerListener(sensorEventListener,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (sensorManager!=null){
            sensorManager.unregisterListener(sensorEventListener);
        }
    }

    //重力感应监听
    private SensorEventListener sensorEventListener=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            //传感器信息改变时使用该方法
            float[] values=event.values;
            float x=values[0];
            float y=values[1];
            float z=values[2];
            int medumValue=15;
            if (Math.abs(x)>medumValue||Math.abs(y)>medumValue||Math.abs(z)>medumValue){
                vibrator.vibrate(200);
                Message msg=new Message();
                msg.what=SENSOR_SHAKE;
                handler.sendMessage(msg);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 15:
                    java.util.Random r=new java.util.Random();
                    int num=Math.abs(r.nextInt())%3;
                    text.setText(strs[num]);
                    img.setImageResource(pics[num]);
                    break;
            }
        }
    };
}
