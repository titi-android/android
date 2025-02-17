package com.busschedule.widget.widget

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.dataStoreFile
import androidx.glance.state.GlanceStateDefinition
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.File
import java.io.InputStream
import java.io.OutputStream

object ScheduleStateDefinition : GlanceStateDefinition<ScheduleInfo> {

    private const val DATA_STORE_FILENAME = "scheduleInfo"

    private val Context.datastore by dataStore(DATA_STORE_FILENAME, ScheduleWidgetSerializer)

    override suspend fun getDataStore(context: Context, fileKey: String): DataStore<ScheduleInfo> {
        return context.datastore
    }

    override fun getLocation(context: Context, fileKey: String): File {
        return context.dataStoreFile(DATA_STORE_FILENAME)
    }

    object ScheduleWidgetSerializer : Serializer<ScheduleInfo> {
        override val defaultValue: ScheduleInfo
            get() = ScheduleInfo.Loading

        override suspend fun readFrom(input: InputStream): ScheduleInfo = try {
            Json.decodeFromString(
                ScheduleInfo.serializer(),
                input.readBytes().decodeToString(),
            )
        } catch (exception: SerializationException) {
            throw CorruptionException("Could not read schedule data: ${exception.message}")
        }

        override suspend fun writeTo(t: ScheduleInfo, output: OutputStream) {
            output.use {
                it.write(
                    Json.encodeToString(ScheduleInfo.serializer(), t).encodeToByteArray(),
                )
            }
        }
    }
}