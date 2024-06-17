package com.example.dto

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.scheduling.support.CronTrigger

open class DefaultScheduler: Scheduler {
    private val scheduler = ThreadPoolTaskScheduler()
    private val jobs: MutableMap<String, Runnable> = mutableMapOf()

    init {
        scheduler.initialize()
        scheduler.start()
    }

    override fun addSchedule(scheduleKey: String, cronTime: String, execute: () -> Unit) {
        if (jobs.containsKey(scheduleKey)) throw RuntimeException("이미 등록된 스케쥴 키 입니다.")
        addJob(scheduleKey, cronTime) { execute() }
    }

    override fun updateSchedule(scheduleKey: String, execute: () -> Unit) {
        val job = jobs.getOrElse(scheduleKey) { throw IllegalArgumentException("존재하지 않는 스케쥴 입니다.") }
        scheduler.scheduledThreadPoolExecutor.remove(job)
    }

    override fun updateCron(scheduleKey: String, cronTime: String) {
        val runnable = jobs.getOrElse(scheduleKey) { throw IllegalArgumentException("존재하지 않는 스케쥴 입니다.") }
        removeSchedule(scheduleKey)
        addJob(scheduleKey, cronTime, runnable)
    }

    override fun removeSchedule(scheduleKey: String) {
        val job = jobs.getOrElse(scheduleKey) { throw IllegalArgumentException("존재하지 않는 스케쥴 입니다.") }
        scheduler.scheduledThreadPoolExecutor.remove(job)
    }

    private fun addJob(scheduleKey: String, cronTime: String, runnable: Runnable) {
        jobs[scheduleKey] = runnable

        scheduler.schedule(runnable, CronTrigger(cronTime))
    }
}