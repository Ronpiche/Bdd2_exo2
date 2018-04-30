import scala.collection.mutable.ArrayBuffer

object StoreCreature extends Serializable
{

  private var storage: ArrayBuffer[Creature] = ArrayBuffer.empty[Creature]

  def get(key: Int): Creature = {
    return storage(key)
  }

  def add(creature: Creature): Int = {
    storage += creature

    return storage.length - 1
  }

}