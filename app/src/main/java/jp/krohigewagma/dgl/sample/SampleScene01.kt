package jp.krohigewagma.dgl.sample

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import jp.krohigewagma.dgl.DGLSoundManager
import jp.krohigewagma.dgl.DGLSprite
import jp.krohigewagma.dgl.R
import jp.krohigewagma.dgl.scene.DGLSurfaceScene
import java.util.*

class SampleScene01 : DGLSurfaceScene, Runnable {

    private var thread : Thread = Thread(this)

    //private var prevPich :double = 0.0

    constructor(context : Context) : super(context){
    }

    override fun onResume() {
        super.onResume()
        Log.d("SampleScene01","onResum")
        DGLSoundManager.bgmStart(this.context, "spajam2018", true)
        this.setBackgroundColor(Color.WHITE)

        addSprite(SampleSprite01())

        if(!thread.isAlive){
            thread.start()
        }
    }

    override fun onPause() {
        super.onPause()
        try{

        }catch(e : Exception){

        }
    }

    override fun onStop() {
        if(thread.isAlive){
            thread.interrupt()
        }
    }

    override fun run() {
        while (thread != null && thread?.isInterrupted == false) {
            var startDt = Calendar.getInstance()
            this.drawBegin()
            try {
                // ここにメイン処理を書く
                // 描画はここで
                this.callSpriteList();
/*
                var canvas = getBackCanvas()
                var paint = Paint()
                paint.color = Color.RED
                canvas?.drawText("TEST", 10f, 50f, paint)
*/
            } catch (e: Exception) {
                Log.e("DGL", "", e)
            } finally {
                try{
                    this.drawEnd()
                } catch (e: Exception) {
                    Log.e("DGL", e.message)
                }
                var endDt = Calendar.getInstance()
                var wait = (endDt.timeInMillis - startDt.timeInMillis)
                //Log.i("DGL", "$wait")
                if(wait > 0){
                    Thread.sleep(wait)
                }else{
                    Thread.sleep(33)
                }
            }
        }
    }


//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        var ret = super.onTouchEvent(event)
//
//        var cnt = event?.pointerCount
//
//        if(cnt?.compareTo(0) != 0){
//            // シングルタッチ
//            DGLSoundManager.sePlay("OK", false)
//        }else{
//            /*
//            // マルチタッチ
//            //var histCnt= event.historySize
//            var x1 = event.getX(0)
//            var y1 = event.getX(0)
//            var x2 = event.getX(1)
//            var y2 = event.getX(1)
//
//            // つかんでいるかどうかを判定するために二点間の距離を求める
//            var xPich = Math.abs(x1 - x2)
//            var yPich = Math.abs(y1 - y2)
//            var pich = Math.sqrt(xPich^2 + yPich^2)
//
//            if(prevPich - pich < 0){
//                // はなれた
//            }else{
//                // ちかづいた
//            }
//
//            if(pitch < 10){
//                // つかんでる可能性がある
//            }else{
//                // つかんでない
//            }
//*/
//
//        }
//        return true
//    }
}