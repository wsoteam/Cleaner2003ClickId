package ochen.mem.rocket.sanyakekes.util.callback


import java.util.ArrayList

import ochen.mem.rocket.sanyakekes.util.model.JunkInfo

interface IScanCallback {
    fun onBegin()

    fun onProgress(info: JunkInfo)

    fun onFinish(children: ArrayList<JunkInfo>)
}
