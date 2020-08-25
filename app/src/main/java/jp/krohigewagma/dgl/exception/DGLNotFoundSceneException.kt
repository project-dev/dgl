package jp.krohigewagma.dgl.exception;

/**
 * Created by dev-t on 2017/10/03.
 */

class DGLNotFoundSceneException : Exception {
    constructor():super()
    constructor(message:String?):super(message)
    constructor(message:String?, cause:Throwable?):super(message, cause)
    constructor(cause:Throwable):super(cause)

}
