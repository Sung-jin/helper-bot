package com.example.dto

interface Scheduler {
    fun addSchedule(scheduleKey: String, cronTime: String, execute: () -> Unit)
    fun updateSchedule(scheduleKey: String, execute: () -> Unit)
    fun updateCron(scheduleKey: String, cronTime: String)
    fun removeSchedule(scheduleKey: String)
}