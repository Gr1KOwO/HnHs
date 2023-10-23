package Managers

import Creature.Creature
import Equipment.Armor
import Equipment.EquipmentSlot
import Equipment.Item

class EquipmentManager(private val creature: Creature) {
    private val equipmentSlots: MutableMap<String, EquipmentSlot> = HashMap()
    private var canEquipItem = true

    init {
        initializedEquipmentSlots()
    }

    private fun initializedEquipmentSlots() {
        // Инициализация слотов для снаряжения
        equipmentSlots["Head"] = EquipmentSlot()
        equipmentSlots["leftHand"] = EquipmentSlot()
        equipmentSlots["rightHand"] = EquipmentSlot()
        equipmentSlots["Body"] = EquipmentSlot()
        equipmentSlots["Legs"] = EquipmentSlot()
    }

    fun equipItem(item: Item, slotName: String) {
        val slot = equipmentSlots[slotName]
        if (slot != null) {
            val existingItem = slot.getEquippedItem()
            if (existingItem != null) {
                if (!slotName.contains(item.getPartOfTheEquipment())) {
                    println("Нелья надеть снаряжение на неправильное место")
                    canEquipItem = false
                } else {
                    canEquipItem = true
                }
                if (canEquipItem) {
                    removeItemEffects(existingItem)
                }
            }
            if (canEquipItem) {
                slot.equipItem(item)
                applyItemEffects(item)
            }
        }
    }

    fun unequipItem(slotName: String) {
        val slot = equipmentSlots[slotName]
        if (slot != null) {
            val item = slot.getEquippedItem()
            if (item != null) {
                slot.unequipItem()
                removeItemEffects(item)
            }
        }
    }

    private fun removeItemEffects(item: Item) {
        creature.delDefenseModifier(item.getDefenseModifier())
        creature.delMinDamageModifier(item.getMinDamageModifier())
        creature.delMaxDamageModifier(item.getMaxDamageModifier())
        creature.delHealthModifier(item.getHealthModifier())
        creature.delLuckModifier(item.getLuckModifier())
        creature.delAttackModifier(item.getAttackModifier())
    }

    private fun applyItemEffects(item: Item) {
        creature.setDefenseModifier(item.getDefenseModifier())
        creature.setMinDamageModifier(item.getMinDamageModifier())
        creature.setMaxDamageModifier(item.getMaxDamageModifier())
        creature.setHealthModifier(item.getHealthModifier())
        creature.setLuckModifier(item.getLuckModifier())
        creature.setAttackModifier(item.getAttackModifier())
    }

    fun getEquipmentSlots(): Map<String, EquipmentSlot> {
        return equipmentSlots
    }

    fun getEquippedArmorResistances(): Map<String, Int> {
        val equippedResistances: MutableMap<String, Int> = HashMap()

        for (slot in equipmentSlots.values) {
            val equippedItem = slot.getEquippedItem()
            if (equippedItem is Armor) {
                val armor = equippedItem as Armor
                val resistances = armor.getResistancesMap()
                equippedResistances.putAll(resistances)
            }
        }
        return equippedResistances
    }
}