package Equipment

class Armor(
    name: String,
    attackModifier: Int,
    minDamage: Int,
    maxDamage: Int,
    defenseModifier: Int,
    healthModifier: Int,
    luckModifier: Int,
    partOfTheEquipment: String
) : Item(name, attackModifier, minDamage, maxDamage, defenseModifier, healthModifier, luckModifier, partOfTheEquipment) {
    private var resistances: MutableList<ArmorResistance> = mutableListOf()

    fun addResistance(statusName: String, resistanceLevel: Int) {
        resistances.add(ArmorResistance(statusName, resistanceLevel))
    }

    fun getResistancesMap(): Map<String, Int> {
        val resistanceMap = mutableMapOf<String, Int>()
        for (resistance in resistances) {
            resistanceMap[resistance.getStatusName()] = resistance.getResistanceLevel()
        }
        return resistanceMap
    }
}