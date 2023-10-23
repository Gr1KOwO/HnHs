package Creature

import Equipment.Item
import Managers.EquipmentManager
import Managers.StatusManager
import java.util.Random

abstract class Creature(
    protected var name: String,
    protected var attack: Int,
    protected var defense: Int,
    protected var health: Int,
    protected var minDamage: Int,
    protected var maxDamage: Int,
    protected var luck: Int =0,
    protected var speed: Int
)
{
    protected var equipmentManager: EquipmentManager = EquipmentManager(this)
    protected var statusManager: StatusManager = StatusManager(this)

    fun takeDamage(damage: Int) {
        health -= damage
    }

    fun attack(target: Creature) {
        var attackModifier = attack - target.defense + 1
        var countDice = maxOf(1, attackModifier)
        var totalDamage = 0
        println("Количество кубиков существа $name: $countDice")
        var random = Random()
        var successfulRoll = false

        repeat(countDice) {
            var roll = random.nextInt(6) + 1

            if (random.nextDouble() < luck.toDouble() / 6) {
                roll = 6
            }

            if (roll >= 5) {
                successfulRoll = true
                val equippedStatuses = statusManager.getEquippedItemStatuses()
                for (status in equippedStatuses) {
                    val rand = Math.random()
                    if (rand <= status.getChance()) {
                        statusManager.applyStatus(status, target)
                    }
                }
                val damage = random.nextInt(maxDamage - minDamage + 1) + minDamage
                totalDamage += damage
                target.takeDamage(totalDamage)
                println("$name нанес ${target.name} урон равный $totalDamage")
                return
            }
        }

        if (!successfulRoll) {
            println("$name промахнулся")
        }
    }

    fun equipItem(item: Item, slotName: String) {
        equipmentManager.equipItem(item, slotName)
    }

    fun health(): Int {
        return health
    }

    fun defense(): Int {
        return defense
    }

    fun isAlive(): Boolean {
        return health > 0
    }

    fun printInfo() {
        println("Имя: $name")
        println("Здоровье: $health")
        println("Атака: $attack")
        println("Защита: $defense")
        println("Удача: $luck")
        println("Наложенные статусы: ")

        if (statusManager.getActiveStatus().isNotEmpty()) {
            for (status in statusManager.getActiveStatus()) {
                println(status.getName())
                println("Действие статуса: ${status.getDurationNow()}")
            }
        }
        println()
    }

    fun speed(): Int {
        return speed
    }

    fun name(): String {
        return name
    }

    fun setDefenseModifier(def: Int) {
        defense += def
    }

    fun setMinDamageModifier(minDam: Int) {
        minDamage += minDam
    }

    fun setMaxDamageModifier(maxDam: Int) {
        maxDamage += maxDam
    }

    fun setHealthModifier(dopHealth: Int) {
        health += dopHealth
    }

    fun setLuckModifier(dopLuck: Int) {
        luck += dopLuck
    }

    fun setAttackModifier(atk: Int) {
        attack += atk
    }

    fun delDefenseModifier(def: Int) {
        defense -= def
    }

    fun delMinDamageModifier(minDam: Int) {
        minDamage -= minDam
    }

    fun delMaxDamageModifier(maxDam: Int) {
        maxDamage -= maxDam
    }

    fun delHealthModifier(dopHealth: Int) {
        health -= dopHealth
    }

    fun delLuckModifier(dopLuck: Int) {
        luck -= dopLuck
    }

    fun delAttackModifier(atk: Int) {
        attack -= atk
    }

    fun equipmentManager(): EquipmentManager {
        return equipmentManager
    }

    fun statusManager(): StatusManager {
        return statusManager
    }
}