package Managers

import Creature.Creature
import Equipment.Weapon
import Status.Status
import java.util.*

class StatusManager(private val creature: Creature) {
    private val activeStatuses: MutableList<Status> = ArrayList()

    fun applyStatusEffects() {
        for (status in activeStatuses) {
            val damage = status.getDamagePerTurn()
            creature.takeDamage(damage)
            println("Статус: ${status.getName()} нанес урон $damage ${creature.name()}")
        }
        updateStatuses()
    }

    fun applyStatus(status: Status, target: Creature) {
        var setStatus = true
        val equippedArmorResistances = target.equipmentManager().getEquippedArmorResistances()
        if (equippedArmorResistances.containsKey(status.getName())) {
            val resistance = equippedArmorResistances[status.getName()]!!
            val randomChance = Random().nextInt(101)
            if (randomChance <= resistance) {
                println("${target.name()} устойчив к статусу ${status.getName()}")
                setStatus = false
            }
        }
        if (setStatus) {
            var statusExists = false
            for (existingStatus in target.statusManager().getActiveStatus()) {
                if (existingStatus.getName().equals(status.getName())) {
                    existingStatus.resetDuration()
                    statusExists = true
                    break
                }
            }
            if (!statusExists) {
                println("На ${target.name()} наложился статус ${status.getName()}")
                target.statusManager().addStatus(status)
            }
        }
    }

    fun getActiveStatus(): List<Status> {
        return activeStatuses
    }

    fun addStatus(status: Status) {
        activeStatuses.add(status)
    }

    fun updateStatuses() {
        val statusesToRemove: MutableList<Status> = ArrayList()
        for (status in activeStatuses) {
            if (status.getDurationNow() > 0) {
                status.reduceDuration()
                if (status.getDurationNow() <= 0) {
                    statusesToRemove.add(status)
                }
            } else {
                statusesToRemove.add(status)
            }
        }
        for (status in statusesToRemove) {
            status.resetDuration()
        }
        activeStatuses.removeAll(statusesToRemove)
    }

    fun getEquippedItemStatuses(): List<Status> {
        val statusChances: MutableMap<String, Double> = HashMap()
        val statusMap: MutableMap<String, Status> = HashMap()
        for (slot in creature.equipmentManager().getEquipmentSlots().values) {
            val equippedItem = slot.getEquippedItem()
            if (equippedItem is Weapon) {
                for (status in equippedItem.getStatuses()) {
                    val statusName = status.getName()
                    val statusChance = status.getChance()
                    if (statusChances.containsKey(statusName)) {
                        val existingChance = statusChances[statusName]!!
                        statusChances[statusName] = existingChance * statusChance
                    } else {
                        statusChances[statusName] = statusChance
                        statusMap[statusName] = status
                    }
                }
            }
        }
        val equippedItemStatuses: MutableList<Status> = ArrayList()
        for ((statusName, combinedChance) in statusChances) {
            val status = statusMap[statusName]!!
            val newStatus = Status(statusName, status.getDuration(), status.getDamagePerTurn(), combinedChance)
            equippedItemStatuses.add(newStatus)
        }
        return equippedItemStatuses
    }
}