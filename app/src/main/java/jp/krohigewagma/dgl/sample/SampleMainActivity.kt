package jp.krohigewagma.dgl.sample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import jp.krohigewagma.dgl.*
import jp.krohigewagma.dgl.exception.DGLIllegalImplementException
import jp.krohigewagma.dgl.exception.DGLNotFoundSceneException

class SampleMainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {

            DGLModel.initalize(this, DrawMode.GBA)
            DGLModel.setFullScreen(this)

            Log.d("SampleScene01","onCreate")

            // BGMの登録例
            DGLSoundManager.addBGMResource("spajam2018", R.raw.spajam2018)
            DGLSoundManager.addSeResource( this,"OK", R.raw.ok)

            // イメージの登録
            DGLModel.registImage("dotto", R.drawable.dottoboy)

            // シーン登録例
            DGLModel.addScene("scene1", SampleScene01(this))
            DGLModel.sceneChangeFast("scene1", this)
        } catch (e: DGLIllegalImplementException) {
            Log.e("DGL", "Sample Error", e)
        } catch(e:DGLNotFoundSceneException) {
            Log.e("DGL", "Sample Error", e)
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
//        var model = DGLModel.model
//        model.sceneChangeFast("scene1", this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            // OKの場合
        }

    }
}
