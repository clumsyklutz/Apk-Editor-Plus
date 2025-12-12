package com.android.apksig.util

import android.annotation.TargetApi
import android.os.Build
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.Phaser
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

public interface RunnablesExecutor {
    public static val SINGLE_THREADED = RunnablesExecutor() { // from class: com.android.apksig.util.RunnablesExecutor.1
        @Override // com.android.apksig.util.RunnablesExecutor
        fun execute(RunnablesProvider runnablesProvider) {
            runnablesProvider.createRunnable().run()
        }
    }
    public static val MULTI_THREADED = RunnablesExecutor() { // from class: com.android.apksig.util.RunnablesExecutor.2
        public val PARALLELISM = Math.min(32, Runtime.getRuntime().availableProcessors())

        @Override // com.android.apksig.util.RunnablesExecutor
        @TargetApi(21)
        fun execute(final RunnablesProvider runnablesProvider) {
            Int i = this.PARALLELISM
            ThreadPoolExecutor threadPoolExecutor = ThreadPoolExecutor(i, i, 0L, TimeUnit.MILLISECONDS, ArrayBlockingQueue(4), new ThreadPoolExecutor.CallerRunsPolicy())
            val phaser = Build.VERSION.SDK_INT >= 21 ? Phaser(1) : null
            for (Int i2 = 0; i2 < this.PARALLELISM; i2++) {
                Runnable runnable = Runnable(this) { // from class: com.android.apksig.util.RunnablesExecutor.2.1
                    @Override // java.lang.Runnable
                    fun run() {
                        runnablesProvider.createRunnable().run()
                        phaser.arriveAndDeregister()
                    }
                }
                phaser.register()
                threadPoolExecutor.execute(runnable)
            }
            phaser.arriveAndAwaitAdvance()
            threadPoolExecutor.shutdownNow()
        }
    }

    Unit execute(RunnablesProvider runnablesProvider)
}
