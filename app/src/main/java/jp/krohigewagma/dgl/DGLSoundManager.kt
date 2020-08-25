package jp.krohigewagma.dgl

import android.content.Context
import android.media.MediaPlayer
import android.media.SoundPool

import java.util.Hashtable

/**
 *
 */
class DGLSoundManager {

    companion object {
        /**
         *
         */
        private var bgmPlay : MediaPlayer? = null

        /**
         *
         */
        private var sePlay : SoundPool? = null

        /**
         *
         */
        private var bgmMap : Hashtable<String, Int> = Hashtable<String, Int>()

        /**
         *
         */
        private var seMap : Hashtable<String, Int> = Hashtable<String, Int>()

        /**
         *
         */
        fun addBGMResource(name : String, res : Int){
            this.bgmMap[name] = res
        }

        /**
         *
         */
        fun addSeResource(context:Context, name : String, res : Int){
//            this.seMap[name] = res
            if(this.sePlay == null){
                var sebuild = SoundPool.Builder()
                sebuild?.setMaxStreams(10)
                sebuild?.build()
                this.sePlay = sebuild?.build()
            }
            this.seMap[name] = this.sePlay?.load(context, res, 1)
        }

        /**
         *
         */
        fun sePlay(name : String, isLoop : Boolean){
            this.sePlay?.play(this.seMap[name]!!, 1f, 1f, 0, 0, 1f)
        }

        /**
         *
         */
        fun bgmStart(context : Context, name : String, isLoop : Boolean){
            if(!this.bgmMap.containsKey(name)){
                return
            }

            if(this.bgmPlay != null){
                this.bgmPlay?.stop()
            }

            this.bgmPlay = MediaPlayer.create(context, this.bgmMap[name]!!)
            this.bgmPlay?.isLooping = isLoop
            this.bgmPlay?.start()
        }

        /**
         *
         */
        fun bgmStop(){
            if(this.bgmPlay == null){
                return
            }

            bgmPlay?.stop()
        }
    }

}