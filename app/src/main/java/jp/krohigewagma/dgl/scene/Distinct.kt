package jp.krohigewagma.dgl.scene

enum class Distinct(val dist : Int) {
    /**
     *
     */
    Unknow(0xFFFF),
    /**
     *
     */
    ToDown(0x0001),
    /**
     *
     */
    ToUp(0x0002),
    ToLeft(0x0101),
    ToRight(0x0102)

}