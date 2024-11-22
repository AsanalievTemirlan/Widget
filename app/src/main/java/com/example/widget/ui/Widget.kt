package com.example.widget.ui

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.widget.RemoteViews
import com.example.widget.R
import kotlin.random.Random

class Widget : AppWidgetProvider() {

    private val CLICK = "WidgetClick"

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        val random = Random.nextInt(2)
        val remoteViews = RemoteViews(context?.packageName, R.layout.widget)

        // Здесь создаем компонент с использованием имени класса в виде строки
        val componentName = ComponentName(context!!, Widget::class.java)

        // Например, для установки текста или числа в виджет
        remoteViews.setImageViewResource(R.id.img, setImage(random))
        remoteViews.setOnClickPendingIntent(R.id.img, getPendingSelfIntent(context, CLICK))

        // Обновляем все виджеты
        appWidgetManager?.updateAppWidget(componentName, remoteViews)
    }

    private fun getPendingSelfIntent(context: Context, action: String): PendingIntent {
        val intent = Intent(context, Widget::class.java)
        intent.action = action
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (CLICK.equals(intent?.action)) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val random = Random.nextInt(2)  // Генерируем случайное число (0 или 1)
            val remoteViews = RemoteViews(context?.packageName, R.layout.widget)

            val componentName = ComponentName(context!!, Widget::class.java)

            // Устанавливаем нужное изображение в зависимости от случайного числа
            remoteViews.setImageViewResource(R.id.img, setImage(random))
            appWidgetManager?.updateAppWidget(componentName, remoteViews)

            // Используем Handler для задержки 3 секунды (3000 миллисекунд)
            Handler().postDelayed({
                // Возвращаем дефолтное изображение через 3 секунды
                remoteViews.setImageViewResource(R.id.img, setImage(R.drawable.ic_hz))  // 0 — дефолтное изображение
                appWidgetManager?.updateAppWidget(componentName, remoteViews)
            }, 2000)  // Задержка 3 секунды
        }
    }


    private fun setImage(num: Int): Int {
        val def = R.drawable.ic_hz
        when (num) {
            0 -> return R.drawable.ic_yes
            1 -> return R.drawable.ic_no
            else -> return def;
        }
    }


}
