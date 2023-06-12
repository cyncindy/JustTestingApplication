package com.example.justtestingapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var carouselAdapter: CarouselAdapter

    private val autoScrollTimer = Timer()
    private lateinit var autoScrollTimerTask: TimerTask
    private val AUTO_SCROLL_DELAY: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        carouselAdapter = CarouselAdapter(getItems())
        recyclerView.adapter = carouselAdapter

        startAutoScroll()
    }

    private fun startAutoScroll() {
        autoScrollTimerTask = object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    val currentPosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                    val nextPosition = (currentPosition + 1) % carouselAdapter.itemCount

                    recyclerView.smoothScrollToPosition(nextPosition)
                }
            }
        }

        autoScrollTimer.schedule(autoScrollTimerTask, AUTO_SCROLL_DELAY, AUTO_SCROLL_DELAY)
    }

    private fun stopAutoScroll() {
        autoScrollTimerTask.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopAutoScroll()
    }

    private fun getItems(): List<CarouselData> {
        val carouselDataList = mutableListOf<CarouselData>()
        carouselDataList.add(CarouselData(R.drawable.hydro1))
        carouselDataList.add(CarouselData(R.drawable.hydro2))
        carouselDataList.add(CarouselData(R.drawable.hydro3))
        return carouselDataList
    }
}