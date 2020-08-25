package jp.krohigewagma.dgl.sample

import android.graphics.Canvas
import android.graphics.Paint
import jp.krohigewagma.dgl.DGLSprite
import jp.krohigewagma.dgl.DGLSpriteData

class SampleSprite01 : DGLSprite {

    constructor(){
        patternTag = 4
        spriteTag = 0

        // 前向き直立
        addSprite(0, 0, DGLSpriteData("dotto", 0, 0, 32, 32))
        addSprite(0, 1, DGLSpriteData("dotto", 0, 32, 32, 32))
        addSprite(0, 2, DGLSpriteData("dotto", 0, 64, 32, 32))
        addSprite(0, 3, DGLSpriteData("dotto", 0, 32, 32, 32))

        // ビックリマーク
        addSprite(1, 0, DGLSpriteData("dotto", 32, 0, 32, 32))
        addSprite(1, 1, DGLSpriteData("dotto", 32, 32, 32, 32))
        addSprite(1, 2, DGLSpriteData("dotto", 32, 64, 32, 32))
        addSprite(1, 3, DGLSpriteData("dotto", 32, 96, 32, 32))

        // ・・・マーク
        addSprite(2, 0, DGLSpriteData("dotto", 64, 0, 32, 32))
        addSprite(2, 1, DGLSpriteData("dotto", 64, 32, 32, 32))
        addSprite(2, 2, DGLSpriteData("dotto", 64, 64, 32, 32))
        addSprite(2, 3, DGLSpriteData("dotto", 64, 96, 32, 32))

        // 怒り
        addSprite(3, 0, DGLSpriteData("dotto", 96, 0, 32, 32))
        addSprite(3, 1, DGLSpriteData("dotto", 96, 32, 32, 32))
        addSprite(3, 2, DGLSpriteData("dotto", 96, 64, 32, 32))
        addSprite(3, 3, DGLSpriteData("dotto", 96, 96, 32, 32))

        // 手を振る
        addSprite(4, 0, DGLSpriteData("dotto", 128, 0, 32, 32))
        addSprite(4, 1, DGLSpriteData("dotto", 128, 32, 32, 32))
        addSprite(4, 2, DGLSpriteData("dotto", 128, 64, 32, 32))
        addSprite(4, 3, DGLSpriteData("dotto", 128, 96, 32, 32))
        addSprite(4, 4, DGLSpriteData("dotto", 128, 64, 32, 32))
        addSprite(4, 5, DGLSpriteData("dotto", 128, 32, 32, 32))
    }


    override fun main(canvas: Canvas) {
        //Log.i("DGL", "Sprite01 call");
        var paint = Paint()
        paint.isAntiAlias = false
        drawSprite(canvas, 50,50, paint)

        spriteTag++
        if(spriteTag > 5){
            spriteTag = 0
        }
    }
}