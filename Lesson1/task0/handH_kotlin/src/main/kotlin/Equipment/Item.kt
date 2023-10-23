package Equipment

open class Item(
    private var name: String,
    private var attackModifier: Int,
    private var minDamage: Int,
    private var maxDamage: Int,
    private var defenseModifier: Int,
    private var healthModifier: Int,
    private var luckModifier: Int,
    private var partOfTheEquipment: String
) {
    fun getAttackModifier(): Int {
        return attackModifier
    }

    fun getDefenseModifier(): Int {
        return defenseModifier
    }

    fun getHealthModifier(): Int {
        return healthModifier
    }

    fun getLuckModifier(): Int {
        return luckModifier
    }

    fun getMinDamageModifier(): Int {
        return minDamage
    }

    fun getMaxDamageModifier(): Int {
        return maxDamage
    }

    fun getPartOfTheEquipment(): String {
        return partOfTheEquipment
    }
}