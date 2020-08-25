package jp.krohigewagma.dgl

import android.opengl.GLSurfaceView
import android.opengl.GLU

import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 *
 */
@Deprecated("実装中")
class DGLGLRenderer : GLSurfaceView.Renderer{

    /**
     *
     */
    override fun onDrawFrame(gl: GL10?) {
        // バッファクリア
        gl?.glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)
        gl?.glClearColor(255f, 255f, 255f, 255f)
        gl?.glMatrixMode(GL10.GL_MODELVIEW)
        gl?.glLoadIdentity()
        gl?.glTranslatef(0f, 0f, 0f)
    }


    /**
     *
     */
    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        //ウインドウ全体に表示
        gl?.glViewport(0, 0, width, height)
        //投影変換モードへ
        gl?.glMatrixMode(GL10.GL_PROJECTION)
        //投影変換の変換行列を単位行列で初期化
        gl?.glLoadIdentity()
        GLU.gluPerspective(gl, 45f, width.toFloat() / height.toFloat(), 1f, 50f)
    }

    /**
     *
     */
    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        // 深度テストを有効
        gl?.glEnable(GL10.GL_DEPTH_TEST)
        // デプスバッファ比較
        gl?.glDepthFunc(GL10.GL_LEQUAL)
    }
}