package jp.krohigewagma.dgl.scene

import android.content.Context
import android.opengl.GLSurfaceView
import android.opengl.GLSurfaceView.Renderer

import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

import jp.krohigewagma.dgl.DGLGLRenderer

/**
 * 参考
 * http://android.keicode.com/basics/opengl-overview.php
 * http://android.keicode.com/basics/opengl-drawing-basic-shapes.php
 * http://blog.oukasoft.com/%E3%83%97%E3%83%AD%E3%82%B0%E3%83%A9%E3%83%A0/%E3%80%90android%E3%80%91%E3%82%A2%E3%83%B3%E3%83%89%E3%83%AD%E3%82%A4%E3%83%89%E3%81%A7opengl%E3%82%92%E4%BD%BF%E3%81%A3%E3%81%A6%E3%81%BF%E3%82%8B%EF%BC%882d%E3%83%86%E3%82%AF%E3%82%B9%E3%83%81/
 * Created by dev-t on 2017/10/01.
 */
@Deprecated("実装中")
abstract class DGLGLScene : GLSurfaceView, IDGLScene, Renderer{

    /**
     *
     */
    private val renderer : DGLGLRenderer? = null

    /**
     *
     */
    constructor(context : Context) : super(context){
        // OpenGL ES2.0に設定
        this.setEGLContextClientVersion(2)
    }

    /**
     *
     */
    init{
        this.setRenderer(this)
    }

    /**
     *
     */
    override fun onDrawFrame(gl : GL10){
//        // バッファクリア
//        // 自動で呼び出されるので、バックバッファに書き込まれた結果を画面に表示するようにしたほうが良い
//        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
//        gl.glClearColor(255, 255, 255, 255);
//        gl.glMatrixMode(GL10.GL_MODELVIEW);
//        gl.glLoadIdentity();
//        gl.glTranslatef(0, 0, 0f);
    }

    /**
     *
     */
    override fun onSurfaceCreated(gl : GL10, config : EGLConfig){
    }

    /**
     *
     */
    override fun onSurfaceChanged(gl : GL10? , width : Int, height : Int){

    }
}