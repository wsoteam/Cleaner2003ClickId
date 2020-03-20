package dev.mem.rocket.sanya.util.callback


import java.util.ArrayList

import dev.mem.rocket.sanya.util.model.JunkInfo

interface IScanCallback {
    fun onBegin()

    fun onProgress(info: JunkInfo)

    fun onFinish(children: ArrayList<JunkInfo>)
}
