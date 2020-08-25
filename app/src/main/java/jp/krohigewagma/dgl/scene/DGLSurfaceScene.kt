package jp.krohigewagma.dgl.scene

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import jp.krohigewagma.dgl.DGLModel
import jp.krohigewagma.dgl.DGLSprite
import jp.krohigewagma.dgl.DrawMode

import kotlin.math.floor
import kotlin.math.roundToInt

// クラスにopenを付けないとfinalになるらしい
/**
 *　
 */
open class DGLSurfaceScene : SurfaceView, IDGLScene, SurfaceHolder.Callback{

    /**
     *
     */
    private var zoomVal = 1.0f

    /**
     *
     */
    private var bkCanvas : Canvas? = null

    fun getBackCanvas() : Canvas?{
        return this.bkCanvas
    }

    /**
     *
     */
    private var bkBuff : Bitmap?= null

    /**
     *
     */
    private var backgroundColor = Color.BLACK

    /**
     *
     */
    private val spriteList = ArrayList<DGLSprite>()

    /**
     *
     */
    constructor(context:Context):super(context)


    /**
     *
     */
    fun addSprite(sprite : DGLSprite){
        spriteList.add(sprite)
    }

    /**
     *
     */
    fun removeSprite(sprite : DGLSprite){
        spriteList.remove(sprite)
    }

    /**
     *
     */
    fun callSpriteList(){
        if(bkCanvas == null){
            return
        }
        val ite = spriteList.iterator()
        while(ite.hasNext()){
            ite.next().main(bkCanvas!!)
        }
    }

    /**
     *
     */
    override fun onResume() {
        this.holder.addCallback(this)
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
        this.holder.removeCallback(this)
        this.releaseBuffer()
    }

    /**
     *
     */
    override fun setBackgroundColor(color : Int){
        this.backgroundColor = color
    }

    /**
     *
     */
    fun drawBegin(){
        bkCanvas = null
//        bkBuff = null
        try{
            if(this.width <= 0 || this.height <= 0){
                return
            }

            this.calcZoom()
            if(bkBuff == null){
                //bkBuff = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888)
                bkBuff = Bitmap.createBitmap(DGLModel.drawMode.width, DGLModel.drawMode.height, Bitmap.Config.ARGB_8888)
                //Log.i("DGL", "create buffer success")
            }

            if(bkBuff != null && bkCanvas == null){
                bkCanvas = Canvas(this.bkBuff)
                //Log.i("DGL", "create canvas success")
            }

            var paint = Paint()
            paint.color = this.backgroundColor
            var r = Rect(0, 0, this.width, this.height)
            this.bkCanvas?.drawRect(r, paint)
            //Log.i("DGL", "clear canvas")
        }catch(e : Exception){
            bkCanvas = null
            Log.e("DGL", "", e)
        }
    }

    /**
     *
     */
    fun drawEnd(){
        var holder : SurfaceHolder? = this.holder
        try{
            var canvas : Canvas? = holder?.lockCanvas()

            var frPaint = Paint()
            frPaint.color = Color.BLUE
            var r = Rect(0, 0, this.width, this.height)
            canvas?.drawRect(r, frPaint)

            var srcWidth = DGLModel.drawMode.width
            var srcHeight = DGLModel.drawMode.height

            // 描画モードに合わせて、キャンバスに描画する
            if(DGLModel.drawMode == DrawMode.DEVICE){
                canvas?.drawBitmap(this.bkBuff, 0.0f, 0.0f, Paint() )

            }else{// モード指定
                var scale : Int = 0
                var bkW : Int = bkBuff?.width ?: return
                var bkH : Int = bkBuff?.height ?: return
                var cvW : Int = canvas?.width ?: return
                var cvH : Int = canvas?.height ?: return

                scale = if(this.width < this.height){
                    // 幅に合わせる
                    (floor((cvW / bkW) * 10.0) / 10).roundToInt()
                }else{
                    // 高さに合わせる
                    (floor((cvH / bkH) * 10.0) / 10).roundToInt()
                }

/*
                scale = if(bkW > bkH){
                    //　縦に合わせる
                    (floor((cvH / bkH) * 10.0) / 10).roundToInt()
                }else{
                    // 横幅に合わせる
                    (floor((cvW / bkW) * 10.0) / 10).roundToInt()
                }
*/
                var outX : Int = floor((cvW - bkW * scale) / 2.0).roundToInt()
                var outY : Int = floor((cvH - bkH * scale) / 2.0).roundToInt()

                var srcRect : Rect = Rect(0, 0, bkW, bkH)
                var distRect : Rect = Rect(outX, outY, outX + bkW * scale, outY + bkH * scale)

                //Log.d("DGL", "scale    : $scale")
                //Log.d("DGL", "[src ] l : " + srcRect.left + " t : " + srcRect.top + " r : " + srcRect.right + " b : " + srcRect.bottom)
                //Log.d("DGL", "[dist] l : " + distRect.left + " t : " + distRect.top + " r : " + distRect.right + " b : " + distRect.bottom)

                // 多分これでスケールに合わせた画面表示ができる
                var paint : Paint = Paint()
                paint.isAntiAlias = false
                canvas?.drawBitmap(this.bkBuff, srcRect, distRect, paint)
            }
            holder?.unlockCanvasAndPost(canvas)

        }catch(e : Exception){
            Log.e("DGL", "", e)
        }
    }

    /**
     *
     */
    fun getImageWidth(imgName : String) : Int{
        var ctx = this.context
        var res = ctx.resources
        //var bmp : Bitmap? = BitmapFactory.decodeResource(res, m_imgMap[imgName]!!)
        var bmp : Bitmap? = DGLModel.getImage(imgName)
        return bmp!!.width
    }

    /**
     *
     */
    fun getImageHeight(imgName : String) : Int{
        var ctx = this.context
        var res = ctx.resources
        //var bmp : Bitmap? = BitmapFactory.decodeResource(res, m_imgMap[imgName]!!)
        var bmp : Bitmap? = DGLModel.getImage(imgName)
        return bmp!!.height
    }

    /**
     *
     */
    private fun calcZoom(){
/*
        var wm = this.context.getSystemService( Activity.WINDOW_SERVICE ) as WindowManager
        var metrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(metrics)

        zoomVal = 1.0f
        zoomVal = 1.0f / metrics.scaledDensity

 */
        zoomVal = 1.0f
    }

    /**
     *
     */
    fun drawImage(imgName : String, x: Int, y : Int, paint :Paint){
        val ctx = this.context
        val res = ctx.resources
        val bmp = DGLModel.getImage(imgName)

        val src = Rect(0, 0, bmp.width, bmp.height)
        val dist = Rect(x, y, x + bmp.width, y + bmp.height)
        bkCanvas?.drawBitmap(bmp, src, dist, paint) ?: return
    }

    /**
     *
     */
    fun drawImage(imgName:String, x:Int, y:Int, width:Int, height:Int, paint:Paint) {
        val ctx = this.context
        val res = ctx.resources
        val bmp = DGLModel.getImage(imgName)

        val bmpWidth = bmp.width
        val bmpHeight = bmp.height

        val srcRect = Rect(0, 0, bmpWidth, bmpHeight)
        val distRect = Rect(x, y, (width * zoomVal).toInt(), (height * zoomVal).toInt())

        this.bkCanvas?.drawBitmap(bmp, srcRect, distRect, paint) ?: return
    }

    /**
     *
     */
    fun drawImageToCenter(imgName : String, cnt : Int, maxCnt : Int, vDist : Distinct, hDist : Distinct, paint : Paint){
        val ctx = this.context
        val res = ctx.resources
        val bmp = DGLModel.getImage(imgName)
        var count = cnt

        val bmpWidth = bmp.width
        val bmpHeight = bmp.height

        val dispX = (this.width - bmpWidth) / 2
        val dispY = (this.height - bmpHeight) / 2

        var x = dispX
        var y = dispY

        if(maxCnt < count){
            count = maxCnt
        }

        when(vDist){
            Distinct.ToDown -> {
                y = (dispY + bmpHeight) / maxCnt* count - bmpHeight
            }
            Distinct.ToUp -> {
                y = this.height - dispY / maxCnt * count
            }
            else->{
            }
        }

        when(hDist){
            Distinct.ToLeft->{
                x = dispX / maxCnt* count
            }
            Distinct.ToRight->{
                x = this.width - dispX / maxCnt * count
            }
            else->{
            }
        }

        this.bkCanvas?.drawBitmap(bmp, x.toFloat(), y.toFloat(), paint) ?: return
    }

    /**
     *
     */
    fun drawImageCenter(imgName : String, paint : Paint, zoomPrm:Float){
        val ctx = this.context
        val res = ctx.resources
        var zoom = zoomPrm
        val bmp = DGLModel.getImage(imgName)

        val mtx = matrix
        if(zoom == 0.0f){
            zoom = 0.01f
        }

        mtx.postScale(zoom, zoom)

        try{
            var zoomMap = Bitmap.createBitmap(bmp, 0, 0, bmp.width, bmp.height, mtx, true)
            val dispX = (this.width - zoomMap.width) / 2
            val dispY = (this.height - zoomMap.height) / 2
            this.bkCanvas?.drawBitmap(zoomMap, dispX.toFloat(), dispY.toFloat(), paint) ?: return
        }catch(iae : IllegalArgumentException){
            throw iae
        }
    }

    /**
     *
     */
    fun drawImageCenter(imgName : String, paint : Paint){
        val ctx = this.context
        val res = ctx.resources
        val bmp = DGLModel.getImage(imgName)

        val dispX = (this.width - bmp!!.width) / 2
        val dispY = (this.height - bmp.height) / 2

        this.bkCanvas?.drawBitmap(bmp, dispX.toFloat(), dispY.toFloat(), paint) ?: return
    }

    /**
     *
     */
    fun drawText(text :String, x:Int, y:Int, paint : Paint){
        val mtx = paint.fontMetrics
        this.bkCanvas?.drawText(text, x * zoomVal, y * zoomVal - mtx.top, paint) ?: return
    }

    /**
     *
     */
    fun drawLine(sx : Int, sy : Int, ex : Int, ey : Int, paint : Paint){
        this.bkCanvas?.drawLine(sx.toFloat(), sy.toFloat(), ex.toFloat(), ey.toFloat(), paint) ?: return
    }

    /**
     *
     */
    override fun surfaceCreated(holder: SurfaceHolder?) {
        this.drawBackBuffer(this, holder!!)
    }

    /**
     *
     */
    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        this.createBackBuffer(width, height)
    }

    /**
     *
     */
    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        this.releaseBuffer()
    }

    /**
     *
     */
    private fun createBackBuffer(width : Int, height : Int){
        this.bkBuff = null
        if(DGLModel.drawMode == DrawMode.DEVICE) {
            Log.i("DGL", "Device Mode")
            this.bkBuff = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        }else{
            Log.i("DGL", "No Device Mode ")
            this.bkBuff = Bitmap.createBitmap(DGLModel.drawMode.width, DGLModel.drawMode.height, Bitmap.Config.ARGB_8888)
        }
    }

    /**
     *
     */
    private fun drawBackBuffer(view : View, holder : SurfaceHolder){
        val canvas = holder.lockCanvas() ?: return

        if(this.bkBuff == null){
            createBackBuffer(this.width, this.height)
        }else{
            canvas.drawBitmap(this.bkBuff, 0.0f, 0.0f, Paint())
        }
        holder.unlockCanvasAndPost(canvas)
    }

    /**
     *
     */
    private fun releaseBuffer(){
        bkBuff = null
    }
}