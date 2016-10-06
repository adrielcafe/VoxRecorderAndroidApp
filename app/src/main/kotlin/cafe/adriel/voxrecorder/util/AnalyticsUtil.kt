package cafe.adriel.voxrecorder.util

import android.os.Bundle
import cafe.adriel.voxrecorder.App
import cafe.adriel.voxrecorder.model.entity.Recording
import com.google.firebase.analytics.FirebaseAnalytics
import khronos.toString

object AnalyticsUtil {

    val EVENT_NEW_RECORDING = "new_recording"
    val EVENT_DELETE_RECORDING = "delete_recording"

    val PARAM_DATE = "date"
    val PARAM_DURATION = "duration"

    val fbAnalytics = FirebaseAnalytics.getInstance(App.instance)

    fun viewRecordingEvent(recording: Recording) = Bundle().run {
        putString(FirebaseAnalytics.Param.ITEM_ID, recording.nameWithFormat)
        putString(FirebaseAnalytics.Param.ITEM_NAME, recording.name)
        putString(FirebaseAnalytics.Param.ITEM_CATEGORY, recording.format)
        putString(PARAM_DATE, recording.date.toString("yyyy-MM-dd"))
        putInt(PARAM_DURATION, recording.duration)
        fbAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, this)
    }

    fun newRecordingEvent(recording: Recording) = Bundle().run {
        putString(FirebaseAnalytics.Param.ITEM_ID, recording.nameWithFormat)
        putString(FirebaseAnalytics.Param.ITEM_NAME, recording.name)
        putString(FirebaseAnalytics.Param.ITEM_CATEGORY, recording.format)
        putString(PARAM_DATE, recording.date.toString("yyyy-MM-dd"))
        putInt(PARAM_DURATION, recording.duration)
        fbAnalytics.logEvent(EVENT_NEW_RECORDING, this)
    }

    fun deleteRecordingEvent(recording: Recording) = Bundle().run {
        putString(FirebaseAnalytics.Param.ITEM_ID, recording.nameWithFormat)
        putString(FirebaseAnalytics.Param.ITEM_NAME, recording.name)
        putString(FirebaseAnalytics.Param.ITEM_CATEGORY, recording.format)
        putString(PARAM_DATE, recording.date.toString("yyyy-MM-dd"))
        putInt(PARAM_DURATION, recording.duration)
        fbAnalytics.logEvent(EVENT_DELETE_RECORDING, this)
    }

    fun shareEvent(recording: Recording) = Bundle().run {
        putString(FirebaseAnalytics.Param.ITEM_ID, recording.nameWithFormat)
        putString(PARAM_DATE, recording.date.toString("yyyy-MM-dd"))
        putInt(PARAM_DURATION, recording.duration)
        fbAnalytics.logEvent(FirebaseAnalytics.Event.SHARE, this)
    }

    fun searchEvent(query: String) = Bundle().run {
        putString(FirebaseAnalytics.Param.SEARCH_TERM, query)
        fbAnalytics.logEvent(FirebaseAnalytics.Event.SEARCH, this)
    }

}