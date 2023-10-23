package Equipment

class EquipmentSlot
{
    private var equippedItem: Item? = null

    fun equipItem(item: Item) {
        equippedItem = item
    }

    fun unequipItem() {
        equippedItem = null
    }

    fun getEquippedItem(): Item? {
        return equippedItem
    }
}