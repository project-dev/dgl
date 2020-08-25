package jp.krohigewagma.dgl

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log

/**
 * スプライトクラス
 * スプライトを実装する場合はこのクラスを継承する
 * スプライトは言え、たいした機能は持ち合わせていない
 */
abstract class DGLSprite {
    //var imageName : String = ""
    private var spriteMap : HashMap<Int, HashMap<Int, DGLSpriteData>> = HashMap<Int, HashMap<Int, DGLSpriteData>>()

    var patternTag : Int = -1
    var spriteTag : Int = -1

    abstract fun main(canvas : Canvas)

    /**
     * スプライト情報を追加
     */
    fun addSprite(patternTag : Int, spriteNo : Int, sprite : DGLSpriteData){
        if(!spriteMap.containsKey(patternTag)){
            spriteMap?.put(patternTag, HashMap<Int, DGLSpriteData>())
        }
        var spritePattern : HashMap<Int, DGLSpriteData>? = spriteMap?.get(patternTag)
       spritePattern?.put(spriteNo, sprite)
    }

    /**
     * パターンの登録数を返却する
     * @return　パターン数。
     */
    fun getPatternCount() : Int{
        return spriteMap?.size ?: 0
    }

    /**
     * 指定パターンの登録数を返却する
     * パターンを省略すると、現在のパターンがデフォルト値として利用される
     * @param _patternTag
     * @return 対象のパターンい登録されているスプライト数。存在しない場合は-1
     */
    fun getSpriteCount(_patternTag : Int = this.patternTag) : Int{
        if(!spriteMap.containsKey(_patternTag)){
            return -1
        }
        var spMap = spriteMap?.get(_patternTag) ?: return -1
        return spMap?.size ?: -1
    }

    /**
     * スプライト描画
     * @param canvas　描画対象のキャンバス「
     * @param x 出力X座標
     * @param y 出力Y座標
     * @param paint Paintオブジェクト
     */
    fun drawSprite(canvas : Canvas, x : Int, y : Int, paint : Paint){

        if(!spriteMap?.containsKey(this.patternTag)){
            Log.i("DGL", "unknow patternTag " + this.patternTag)
            return
        }
        var spMap = spriteMap?.get(this.patternTag)
        if(!spMap?.containsKey(this.spriteTag)!!){
            Log.i("DGL", "unknow spriteTag " + this.spriteTag)
            return
        }
        var spData = spMap?.get(this.spriteTag)

        var img = DGLModel.getImage(spData?.imgName!!)

        var srcRect = Rect(spData.x, spData.y, spData.x + spData.width, spData.y + spData.height)
        var distRect = Rect(x, y,x + spData.width, y + spData.height)

        try {
            //Log.i("DGL", "src  : " + srcRect.left +  ", " + srcRect.top +  ", " + srcRect.right +  ", " + srcRect.bottom + " -> dist : " + distRect.left + ", " + distRect.top + ", " + distRect.right + ", " + distRect.bottom)
            paint.isAntiAlias = false
            canvas.drawBitmap(img, srcRect, distRect, paint)
        }catch (e : Exception){
            Log.e("DGL", "draw error.", e)
        }
    }
}