package jp.krohigewagma.dgl

enum class DrawMode(val width:Int, val height:Int) {
    /**
     * デバイス依存
     */
    DEVICE(0, 0),

    /**
     * GameBoy(160 x 144 pixcel)
     */
    GB(160, 144),

    /**
     * Gameboy Advance (240 x 160)
     */
    GBA(240, 160),

    /**
     * Family Computer / NES(256 x 224)
     */
    FC(256, 224)
}