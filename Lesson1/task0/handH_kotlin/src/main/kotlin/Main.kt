import Controller.GameController
import Creature.Creature
import Creature.Demon
import Creature.Monster
import Creature.Player
import Equipment.Armor
import Equipment.Weapon
import Status.Status
import java.util.Random
import java.util.Scanner


fun main()
{
    val scanner = Scanner(System.`in`)
    val random = Random()

    println("Создание персонажа")

    println("Создание персонажа")

    // Запрашиваем никнейм
    print("Введите никнейм: ")
    val name = scanner.nextLine()

    // Запрашиваем атаку и проверяем, что она в диапазоне от 1 до 30
    var attack: Int
    while (true) {
        print("Введите атаку (1-30): ")
        if (scanner.hasNextInt()) {
            attack = scanner.nextInt()
            if (attack in 1..30) {
                break
            } else {
                println("Некорректные данные. Атака должна быть в диапазоне от 1 до 30.")
            }
        } else {
            println("Некорректные данные. Введите целое число.")
            scanner.next() // Очистка буфера от некорректного ввода
        }
    }

    // Запрашиваем защиту и проверяем, что она в диапазоне от 1 до 30
    var defense: Int
    while (true) {
        print("Введите защиту (1-30): ")
        if (scanner.hasNextInt()) {
            defense = scanner.nextInt()
            if (defense in 1..30) {
                break
            } else {
                println("Некорректные данные. Защита должна быть в диапазоне от 1 до 30.")
            }
        } else {
            println("Некорректные данные. Введите целое число.")
            scanner.next() // Очистка буфера от некорректного ввода
        }
    }

    // Запрашиваем здоровье (натуральное число)
    var health: Int
    while (true) {
        print("Введите здоровье: ")
        if (scanner.hasNextInt()) {
            health = scanner.nextInt()
            if (health > 0) {
                break
            } else {
                println("Некорректные данные. Здоровье должно быть натуральным числом.")
            }
        } else {
            println("Некорректные данные. Введите целое число.")
            scanner.next() // Очистка буфера от некорректного ввода
        }
    }

    // Генерируем случайный минимальный урон
    val minDamage = random.nextInt(10) + 1

    // Генерируем случайный максимальный урон от минимального до 20
    val maxDamage = minDamage + random.nextInt(20) + 1

    // Генерируем удачу от 0 до 2
    val luck = random.nextInt(3)
    println("Загенерированная удача: $luck")
    // Генерируем скорость от 20 до 53
    val speed = random.nextInt(34) + 20

    val player = Player(name, attack, defense, health, minDamage, maxDamage, luck, speed)
    val demon = Demon("Саргассо", 9, 30, 345, 3, 9, 56)
    val monster = Monster("Viktor", 12, 24, 345, 4, 16, 34, 0.22)

    // Создание снаряжения
    val sword = Weapon("Меч", 10, 5, 5, 10, 4, 1, "Hand") // Пример оружия
    val helmet = Armor("Шлем", 0, 0, 0, 5, 10, 0, "Head") // Пример брони
    val bestHelmet = Armor("Улучшенный шлем", 0, 0, 0, 20, 20, 1, "Head")
    val bigSword = Weapon("Бастер", 20, 9, 20, 20, 0, 0, "Hand")
    val poison = Status("Отравление", 2, 2, 0.2)
    val bleed = Status("Кровотечение", 3, 4, 0.4)

    // Добавляем эффект отравления на меч
    sword.addStatus(poison)
    // Добавляем кровотечения
    sword.addStatus(bleed)
    bigSword.addStatus(bleed)

    // Добавляем сопротивление к эффектам
    bestHelmet.addResistance("Отравление", 60)
    bestHelmet.addResistance("Кровотечение", 48)

    demon.equipItem(sword, "leftHand")
    monster.equipItem(sword, "rightHand")
    monster.equipItem(helmet, "Head")
    player.equipItem(bigSword, "rightHand")
    player.equipItem(bestHelmet, "Head")

    val enemiesList = mutableListOf<Creature>()
    enemiesList.add(monster)
    enemiesList.add(demon)

    val game = GameController(player, enemiesList)
    game.startGame()
}