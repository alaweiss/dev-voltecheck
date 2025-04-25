package com.example.myvoltechecker

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.telephony.TelephonyManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var volteStatusText: TextView
    private val REQUEST_READ_PHONE_STATE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        volteStatusText = findViewById(R.id.volteStatusText)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_PHONE_STATE),
                REQUEST_READ_PHONE_STATE
            )
        } else {
            checkVolteStatus()
        }
    }

    private fun checkVolteStatus() {
        val telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        val isVolteAvailable = telephonyManager.isVolteAvailable

        volteStatusText.text = if (isVolteAvailable) {
            volteStatusText.setTextColor(Color.GREEN)
            "VoLTE: Available"
        } else {
            volteStatusText.setTextColor(Color.RED)
            "VoLTE: Not Available"
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_READ_PHONE_STATE && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            checkVolteStatus()
        } else {
            volteStatusText.text = "Permission denied"
            volteStatusText.setTextColor(Color.GRAY)
        }
    }
}
