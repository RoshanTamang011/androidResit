package com.dipesh.onlinegadgetsstore.ui

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PowerManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dipesh.onlinegadgetsstore.R
import com.dipesh.onlinegadgetsstore.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigation:BottomNavigationView
    private lateinit var frameLayout:FrameLayout
    private lateinit var sensorManager: SensorManager
    private var sensorAccelerometer: Sensor? = null
    private var sensorProximity: Sensor? = null
    private var sensorGyroscope: Sensor?=null
    private var acclValue = 0f
    private var lastAcclValue: Float = 0f
    private var shake: Float = 0f
    private var powerManager: PowerManager? = null
    private var wakeLock: PowerManager.WakeLock? = null
    private var field = 0x00000020

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val homeFragment=HomeFragment()
        val cartFragment=CartFragment()
        val wishlistFragment= WishlistFragment()
        val profileFragment = ProfileFragment()

        bottomNavigation=findViewById(R.id.bottomNav)
        frameLayout=findViewById(R.id.frame)
        currentFragment(homeFragment)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        if (!checkSensor()) {
            Toast.makeText(this, "Sensor Not Available", Toast.LENGTH_SHORT).show()
        } else {
            sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            sensorProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
            sensorGyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        }
        try {
            // Yeah, this is hidden field.
            field = PowerManager::class.java.javaClass.getField("PROXIMITY_SCREEN_OFF_WAKE_LOCK").getInt(null)
        } catch (ignored: Throwable) {
        }
        powerManager = getSystemService(POWER_SERVICE) as PowerManager;
        wakeLock = powerManager!!.newWakeLock(field, getLocalClassName());



        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.icHome ->{
                    currentFragment(homeFragment)
                    true
                }

                R.id.icCart ->{
                    currentFragment(cartFragment)
                    true
                }
                R.id.icWish ->{
                    currentFragment(wishlistFragment)
                    true
                }
                R.id.icAccount->{
                    currentFragment(profileFragment)
                    true
                }
                else-> false
            }
        }
    }
    private fun currentFragment(fragment:Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame,fragment)
            commit()
        }
    }
    private fun logout(){
        Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }

    private fun checkSensor(): Boolean {
        var flag = true
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null) {
            flag = false
        } else if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) == null) {
            flag = false
        }
        return flag
    }
    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(accelerometerEventListener, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(proximityEventListener, sensorProximity, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(gyroSensorEventListener, sensorGyroscope, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(accelerometerEventListener)
        sensorManager.unregisterListener(proximityEventListener)
        sensorManager.unregisterListener(gyroSensorEventListener)
    }

    private val accelerometerEventListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            lastAcclValue = acclValue
            acclValue = Math.sqrt((x * x + y * y + z * z).toDouble()).toFloat()
            val delta = acclValue - lastAcclValue
            shake = shake * 0.9f + delta
            if (shake > 12) {
                logout()
            }
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }



    private val proximityEventListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val values = event.values[0]
            if (values <= 4) {
//                Toast.makeText(this@MainActivity, "Object is near", Toast.LENGTH_SHORT).show()
                logout()

            }
//
        }
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }

    private val gyroSensorEventListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val values = event.values[1]
            val cartFragment=CartFragment()
            val wishFragment= WishlistFragment()
            if (values < 0) {
                currentFragment(cartFragment)
            } else if (values > 0) {
                currentFragment(wishFragment)
            }
        }
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }

}