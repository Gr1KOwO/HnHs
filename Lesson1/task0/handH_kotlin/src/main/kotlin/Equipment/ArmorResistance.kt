package Equipment

class ArmorResistance(private val statusName: String, private val resistanceLevel: Int) {
    fun getStatusName(): String {
        return statusName
    }

    fun getResistanceLevel(): Int {
        return resistanceLevel
    }
}