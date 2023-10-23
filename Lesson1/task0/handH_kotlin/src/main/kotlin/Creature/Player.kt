package Creature

class Player(
    name: String,
    attack: Int,
    defense: Int,
    health: Int,
    minDamage: Int,
    maxDamage: Int,
    luck:Int,
    speed: Int
) : Creature(name, attack, defense, health, minDamage, maxDamage, luck,speed) {

    private val maxHealth: Int = health
    private var healCount: Int = 4

    fun heal() {
        if (healCount > 0 && health() > 0 && health() < maxHealth) {
            var healAmount = (0.3 * maxHealth).toInt()
            if (health() + healAmount > maxHealth) {
                healAmount = maxHealth - health()
            }
            setPlayerHealth(healAmount)
            healCount--
        } else {
            println("Вы не можете исцелиться в данный момент.")
        }
    }

    private fun setPlayerHealth(health: Int) {
        this.health += health
    }

    fun getHealCount(): Int {
        return healCount
    }
}