package com.dipesh.raew.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.dipesh.raew.R

class DashBoardActivity : AppCompatActivity() {
    private lateinit var tvDisplay:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        tvDisplay=findViewById(R.id.tvDashboardDisplay)

    }

}