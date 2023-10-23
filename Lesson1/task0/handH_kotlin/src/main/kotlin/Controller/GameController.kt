package Controller

import Creature.*
import java.util.*


class GameController(private var player: Player, private var enemies: MutableList<Creature>)
{
    private var creatures = mutableListOf<Creature>() // Список существ, участвующих в раунде
    private var currentTurnIndex = 0 // Индекс текущего существа, которое совершает ход
    private var scanner = Scanner(System.`in`)
    private var random = Random()
    private var gameOver = false
    private lateinit var targetCreature: Creature
    private var turn = 1 // Счетчик ходов раунда

    // Метод для выполнения следующего хода
    fun performNextTurn() {
        println("Текущий ход: $turn")
        val currentTurnCreature = creatures[currentTurnIndex]

        if (currentTurnCreature is Player) {
            playerTurn(player, targetCreature)
        } else {
            // Атака врага направлена к игроку
            enemyTurn(currentTurnCreature, player)
        }

        // Проверка на победу или поражение
        if (checkWinCondition()) {
            enemies.distinctBy {targetCreature} // Удаление врага из списка enemies
            println("Вы победили монстра!")

            // Проверяем, остались ли ещё враги и добавляем нового случайного врага
            if (enemies.isEmpty()) {
                // Все монстры повержены, завершаем игру
                println("Вы победили всех монстров! Игра окончена.")
                gameOver = true
            } else {
                if (continueGame()) {
                    creatures.clear()
                    creatures.add(player)
                    addRandomEnemyToCreatures()
                } else {
                    gameOver = true
                }
            }
        } else if (checkLoseCondition()) {
            // Игрок проиграл, завершаем игру
            println("Вы проиграли! Игра окончена.")
            gameOver = true
        } else {
            // Переход к следующему существу
            currentTurnIndex = (currentTurnIndex + 1) % creatures.size
        }
    }

    // Методы для проверки условий победы и поражения
    private fun checkWinCondition(): Boolean {
        return targetCreature?.isAlive() == false
    }

    private fun checkLoseCondition(): Boolean {
        return !player.isAlive()
    }

    fun startGame() {
        gameOver = false

        // Добавляем игрока и врагов в список существ
        creatures.add(player)
        addRandomEnemyToCreatures()
        while (!gameOver) {
            performNextTurn()
        }
    }

    private fun playerTurn(player: Player, enemy: Creature) {
        player.statusManager().applyStatusEffects()
        println("Текущее состояние: ")
        player.printInfo()
        println()
        println("Текущий враг: ")
        enemy.printInfo()
        println()
        println("Выберите действие:")
        println("1. Атаковать")
        println("2. Восстановить здоровье")
        println("Количество возможностей восстановить здоровье: ${player.getHealCount()}")
        try {
            val choice = scanner.nextInt()
            when (choice) {
                1 -> player.attack(enemy)
                2 -> player.heal()
                else -> println("Некорректный выбор действия. Вы теряете ход.")
            }
            turn++
        } catch (ex: Exception) {
            println("Некорректный выбор действия. Вы теряете ход.")
            turn++
            scanner.next() // Очищаем буфер ввода
        }
    }

    private fun enemyTurn(enemy: Creature, player: Creature) {
        enemy.statusManager().applyStatusEffects()
        if (enemy is Demon) {
            enemy.attack(player)
        }
        if (enemy is Monster) {
            if (enemy.health() < enemy.getMaxHealth() * 0.35) {
                enemy.desiccation(player)
            }
            enemy.attack(player)
        }
        turn++
    }

    private fun continueGame(): Boolean {
        println("Желаете продолжить игру? (yes/no)")
        val choice = scanner.next().toLowerCase()
        return choice == "yes"
    }

    private fun addRandomEnemyToCreatures() {
        if (enemies.isNotEmpty()) {
            val randomIndex = random.nextInt(enemies.size)
            val randomEnemy = enemies.removeAt(randomIndex) // Удаляем и получаем элемент
            targetCreature = randomEnemy
            creatures.add(randomEnemy)
            creatures.sortByDescending { it.speed() }
        }
    }
}