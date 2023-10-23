package Equipment

import Status.Status

class Weapon(name: String, attackModifier: Int, minDamage: Int, maxDamage: Int, defenseModifier: Int, healthModifier: Int, luckModifier: Int, partOfTheEquipment: String) :
    Item(name, attackModifier, minDamage, maxDamage, defenseModifier, healthModifier, luckModifier, partOfTheEquipment) {

    private var statuses: MutableList<Status> = ArrayList()

    fun addStatus(status: Status) {
        statuses.add(status)
    }

    fun getStatuses(): List<Status> {
        return statuses
    }
}