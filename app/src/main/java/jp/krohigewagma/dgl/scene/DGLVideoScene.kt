package jp.krohigewagma.dgl.scene

import android.content.Context
import android.media.MediaPlayer
import android.widget.VideoView

/**
 *
 */
class DGLVideoScene : VideoView, IDGLScene, MediaPlayer.OnCompletionListener {

    /**
     *
     */
    constructor(context:Context) : super(context)

    /**
     *
     */
    override fun onCompletion(mp: MediaPlayer?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     *
     */
    override fun onResume() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     *
     */
    override fun onPause() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     *
     */
    override fun onStop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}