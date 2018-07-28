package kevin.com.accelerometerapp


import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), SensorEventListener {

    private var mSensorManager : SensorManager ?= null
    private var mAccelerometer : Sensor?= null

    private var xValue = 0.0f
    private var yValue = 0.0f
    private var zValue = 0.0f



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContentView(R.layout.activity_main)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {

            x_value.text = event.values[0].toString()
            y_value.text = event.values[1].toString()
            z_value.text = event.values[2].toString()

        }
    }

    override fun onResume() {
        super.onResume()
        mSensorManager!!.registerListener(this,mAccelerometer, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        super.onPause()
        mSensorManager!!.unregisterListener(this)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // MotionEvent object holds X-Y values
        if (event.action == MotionEvent.ACTION_DOWN) {
            val text = "You touched at x = " + event.x + " and y = " + event.y
            Toast.makeText(this, text, Toast.LENGTH_LONG).show()
        }

        return super.onTouchEvent(event)
    }
}
