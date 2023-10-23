package Creature

import java.util.Random

class Monster
    (
    name: String,
    attack: Int,
    defense: Int,
    health: Int,
    minDamage: Int,
    maxDamage: Int,
    speed: Int,
    private var healPercentage: Double): Creature(name, attack, defense, health, minDamage, maxDamage, 0,speed)
{
    private var maxHealth: Int = health

    fun desiccation(target: Creature) {
        var totalDamage = calculateDamage(target)
        target.takeDamage(totalDamage)

        var healAmount = (totalDamage * healPercentage).toInt()
        health1(healAmount)
        println("$name восстановил $healAmount здоровья.")
    }

    private fun calculateDamage(target: Creature): Int {
        var attackModifier = attack - target.defense() + 1
        var countDice = maxOf(1, attackModifier)
        var random = Random()
        var totalDamage = 0

        repeat(countDice) {
            val damage = (random.nextInt(maxDamage - minDamage + 1) + minDamage) - attackModifier
            totalDamage += damage
        }

        return totalDamage
    }

    private fun health1(health: Int) {
        this.health += health
    }

    fun getMaxHealth(): Int {
        return maxHealth
    }
}