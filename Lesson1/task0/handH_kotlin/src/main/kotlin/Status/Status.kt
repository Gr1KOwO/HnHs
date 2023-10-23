package Status

class Status(name: String, duration: Int, damagePerTurn: Int, chance: Double) {

    private val name: String = name
    private val initialDuration: Int = duration
    private var duration: Int = duration
    private val damagePerTurn: Int = damagePerTurn
    private val chance: Double = chance

    fun getName(): String {
        return name
    }

    fun getDuration(): Int {
        return initialDuration
    }

    fun getDurationNow(): Int {
        return duration
    }

    fun getDamagePerTurn(): Int {
        return damagePerTurn
    }

    fun reduceDuration() {
        if (duration > 0) {
            duration--
        }
    }

    fun getChance(): Double {
        return chance
    }

    fun resetDuration() {
        // Восстанавливаем изначальную длительность
        duration = initialDuration
    }
}