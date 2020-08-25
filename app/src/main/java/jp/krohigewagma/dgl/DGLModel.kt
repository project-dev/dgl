package jp.krohigewagma.dgl

import android.app.Activity
import android.app.ActivityManager
import android.app.Fragment
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.*
import jp.krohigewagma.dgl.exception.DGLIllegalImplementException
import jp.krohigewagma.dgl.exception.DGLNotFoundSceneException
import jp.krohigewagma.dgl.scene.IDGLScene
import java.lang.UnsupportedOperationException
import java.util.HashMap

open class DGLModel : GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener{
    companion object{
        /**
         * イメージマップ
         */
        private var imgMap = HashMap<String, Int>()

        /**
         *　シーンマップ
         */
        private val sceneMap = HashMap<String, IDGLScene>()

        /**
         * 次のシーン名
         */
        private var nextSceneName = ""

        /**
         * 現在のシーン名
         */
        private var currentSceneName = ""

        /**
         * 現在のActivity
         */
        private lateinit var currentActivity : Activity

        /**
         * モデル
         */
        var model : DGLModel = DGLModel()

        /**
         * 描画モード
         */
        var drawMode : DrawMode = DrawMode.DEVICE

        /**
         * 初期化
         */
        fun initalize(context : Context, drawMode : DrawMode){
            model._init(context)
            DGLModel.drawMode = drawMode
        }

        /**
         *
         */
        fun getImage(imgName : String) : Bitmap{
            var res = currentActivity.resources
            var options = BitmapFactory.Options()
            options.inScaled = false
            return BitmapFactory.decodeResource(res, imgMap[imgName]!!, options)
        }

        /**
         *
         */
        fun registImage(imgName : String, id : Int){
            imgMap[imgName] = id
        }

        /**
         *
         */
        fun setFullScreen(activity : Activity){
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            activity.requestWindowFeature(Window.FEATURE_NO_TITLE)
        }

        /**
         *
         */
        @Throws(DGLIllegalImplementException::class)
        fun addScene(name : String, scene : IDGLScene){
            if(scene !is View){
                throw DGLIllegalImplementException()
            }
            sceneMap[name] = scene
            Log.d("dgl", "add scene : " )
        }

        /**
         *
         */
        fun sceneChange(_nextSceneName : String){
            nextSceneName = _nextSceneName
        }

        /**
         *
         */
        fun sceneChangeFast(sceneName : String, context : Context){
            nextSceneName = sceneName

            Log.w("dgl", "scene change" )

            // UIスレッド以外から呼び出されることがあるので、UIスレッドで動作させるようにする
            lateinit var runFunc : Runnable
            if(context is Activity) {
                Log.d("DGL", "activity mode")
                currentActivity = context
                runFunc = getRunOnUiThreadForActivity()
            }else if(context is Fragment){
                Log.d("DGL", "fragment mode")
                currentActivity = context.activity
                runFunc = getRunOnUiThreadForFragment()
            }

            // メインスレッドか確認して、処理を分ける
            if(context.mainLooper.isCurrentThread){
                sceneChangeProc(currentActivity)
            }else{
                currentActivity?.runOnUiThread( runFunc )
            }
        }

        /**
         *
         */
        fun sceneChangeProc(activity:Activity){
            try{
                Log.d("DGL", "getRunOnUiThreadForActivity")
                var view = sceneMap[currentSceneName]
                //null比較はこう書けるみたい
                //if (view != null) {
                //    view.onStop()
                //}
                view?.onStop()
                view = sceneMap[nextSceneName]
                if (view != null) {
                    Log.d("DGL", "setContentView")
                    activity.setContentView(view as View)
                    currentSceneName = nextSceneName
                    view.onResume()
                }
            }catch(e : Exception){
                Log.d("DGL", "", e)
            }
        }

        /**
         *
         */
        private fun getRunOnUiThreadForActivity() : Runnable{
            return Runnable{
                fun run(){
                    sceneChangeProc(currentActivity)
                }
            }
        }

        /**
         *
         */
        @Deprecated("Unimplemented")
        private fun getRunOnUiThreadForFragment() : Runnable{
            return Runnable{
                fun run(){
/*
                try{
                    var view = sceneMap[currentSceneName]
                    view?.onStop()
                    view = sceneMap[nextSceneName]
                    currentActivity.setContentView(view as View)
                    currentSceneName = nextSceneName
                    view?.onResume()
                }catch(e : Exception){
                    Log.d("DGL", "", e)
                }
 */
                    throw UnsupportedOperationException()
                }
            }
        }

        /**
         * シーンを取得します
         */
        @Throws(DGLNotFoundSceneException::class)
        fun getScene(sceneName : String) : View {
            if(sceneMap.containsKey(sceneName)){
                throw DGLNotFoundSceneException()
            }
            val view = sceneMap[sceneName]

            // if(view instanceof View == false)は以下のように記述する
            if ( view !is View) {
                throw DGLNotFoundSceneException()
            }
            return view
        }
    }

    private var gestureDetector : GestureDetector? = null
    private var scaleGestureDetector : ScaleGestureDetector? = null

    /**
     *
     */
    private fun _init(context : Context){
        this.gestureDetector = GestureDetector(context, this)
    }

    /*
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //return super.onTouchEvent(event)
        this.gestureDetector?.onTouchEvent(event)
        return true
    }
    */

    /**
     *
     */
    override fun onDoubleTap(e: MotionEvent?): Boolean {
        Log.i("DGL", "onDoubleTap")
        return true;
    }

    /**
     *
     */
    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        Log.i("DGL", "onDoubleTapEvent")
        return true;
    }

    /**
     *
     */
    override fun onDown(e: MotionEvent?): Boolean {
        Log.i("DGL", "onDown")
        return true;
    }

    /**
     *
     */
    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        Log.i("DGL", "onFling")
        return true;
    }

    /**
     *
     */
    override fun onLongPress(e: MotionEvent?) {
        Log.i("DGL", "onLongPress")
    }

    /**
     *
     */
    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        Log.i("DGL", "onScroll")
        return true;
    }

    /**
     *
     */
    override fun onShowPress(e: MotionEvent?) {
        Log.i("DGL", "onShowPress")
    }

    /**
     *
     */
    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        Log.i("DGL", "onSingleTapConfirmed")
        return true;
    }

    /**
     *
     */
    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        Log.i("DGL", "onSingleTapUp")
        return true;
    }
}