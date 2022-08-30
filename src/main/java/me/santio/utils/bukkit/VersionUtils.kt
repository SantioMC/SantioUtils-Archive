package me.santio.utils.bukkit

import org.bukkit.Bukkit

object VersionUtils {

    // Version format:
    // 1  . 16       .  5
    // 1  . <major>  .  <minor>
    // [0], [1]      ,  [2]

    private fun getVersion(): String {
        return Bukkit.getVersion()
    }

    fun getMajor(): Int {
        return getVersion().split(".").getOrNull(1)?.toInt() ?: 0
    }

    fun getMinor(): Int {
        return getVersion().split(".").getOrNull(2)?.toInt() ?: 0
    }

}

enum class PlayerVersion(val protocol: Int, val version: String) {
    v1_4_6(51, "1.4.6/7"),
    v1_5_1(60, "1.5.1"),
    v1_5_2(61, "1.5.2"),
    v_1_6_1(73, "1.6.1"),
    v_1_6_2(74, "1.6.2"),
    v_1_6_3(77, "1.6.3"),
    v_1_6_4(78, "1.6.4"),
    v1_7_1(4, "1.7-1.7.5"),
    v1_7_6(5, "1.7.6-1.7.10"),
    v1_8(47, "1.8.x"),
    v1_9(107, "1.9"),
    v1_9_1(108, "1.9.1"),
    v1_9_2(109, "1.9.2"),
    v1_9_3(110, "1.9.3/4"),
    v1_10(210, "1.10.x"),
    v1_11(315, "1.11"),
    v1_11_1(316, "1.11.1/2"),
    v1_12(335, "1.12"),
    v1_12_1(338, "1.12.1"),
    v1_12_2(340, "1.12.2"),
    v1_13(393, "1.13"),
    v1_13_1(401, "1.13.1"),
    v1_13_2(404, "1.13.2"),
    v1_14(477, "1.14"),
    v1_14_1(480, "1.14.1"),
    v1_14_2(485, "1.14.2"),
    v1_14_3(490, "1.14.3"),
    v1_14_4(498, "1.14.4"),
    v1_15(573, "1.15"),
    v1_15_1(575, "1.15.1"),
    v1_15_2(578, "1.15.2"),
    v1_16(735, "1.16"),
    v1_16_1(736, "1.16.1"),
    v1_16_2(751, "1.16.2"),
    v1_16_3(753, "1.16.3"),
    v1_16_4(754, "1.16.4/5"),
    v1_17(755, "1.17"),
    v1_17_1(756, "1.17.1"),
    v1_18(757, "1.18/1.18.1"),
    v1_18_2(758, "1.18.2"),
    v1_19(759, "1.19"),
    v1_19_1(760, "1.19.1/2"),
    UNKNOWN(-1, "UNKNOWN");


    companion object {
        fun getVersion(protocol: Int): PlayerVersion {
            return values().firstOrNull { v -> v.protocol == protocol } ?: UNKNOWN
        }
    }
}