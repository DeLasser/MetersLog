package ru.mininn.util

import kotlin.experimental.and

class Crc32 {


    fun add(buf: ByteArray): ByteArray {

        val POLYNOMIAL = 0x04C11DB7
        val constNumCycle = 8

        var crc32 = 0
        var cycleNum = 0

        val length = buf.size
        val resultByte = ByteArray(length + 4)
        var byteNum = 0

        while (byteNum < length) {

            resultByte[byteNum] = (buf[byteNum] and 0xFF.toByte())
            crc32 = crc32 xor (buf[byteNum].toInt().shl(24))

            byteNum++
        }

        while (cycleNum < constNumCycle) {
            crc32 = if (crc32 < 0) { (crc32.shl(1).xor(POLYNOMIAL)) }
                    else { crc32.shl(1) }
            cycleNum++
        }

        resultByte[length + 3] = (crc32 and 0xFF).toByte()
        resultByte[length + 2] = (crc32.ushr(8).toByte() and 0xFF.toByte())
        resultByte[length + 1] = (crc32.ushr(16).toByte() and 0xFF.toByte())
        resultByte[length ]    = (crc32.ushr(24).toByte() and 0xFF.toByte())

        return resultByte
    }

}