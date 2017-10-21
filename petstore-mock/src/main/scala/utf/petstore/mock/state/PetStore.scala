package utf.petstore.mock.state

import utf.commons.mock.servlet.servlet.MockState
import utf.commons.random.Randomizer
import utf.petstore.mock.model._

import scala.collection.mutable
import scala.util.Random

/**
  * Created by Sławomir Kluz on 13/10/2017.
  */
case class PetStore(pets: mutable.Buffer[Pet], tags: mutable.Buffer[Tag], categories: mutable.Buffer[Category]) extends MockState {

  def addPet(input: PetInput): Pet = {
    val fullCategory = categories.find(c => c.id == input.category).get
    val fullTags = input.tags.map(tId => tags.find(t => t.id == tId).get)
    val id = pets.maxBy(p => p.id).id + 1
    val status = Status.withName(input.status)
    val pet = Pet(id, fullCategory, input.name, input.photoUrls, fullTags, status)
    pets += pet
    pet
  }

}

object PetStore {

  def fullStore(): PetStore = {
    val categories = Category.someCategories().toBuffer
    val tags = Tag.someTags().toBuffer
    val pets = (1 to Randomizer.Number.int(1, 20)).map(i => {
      val id = i
      val category = Random.shuffle(categories).head
      val photos = Random.shuffle(
        Seq(
          "https://picsum.photos/300/200?image=1",
          "https://picsum.photos/300/200?image=2",
          "https://picsum.photos/300/200?image=3",
          "https://picsum.photos/300/200?image=4",
          "https://picsum.photos/300/200?image=5",
          "https://picsum.photos/300/200?image=6",
          "https://picsum.photos/300/200?image=7"
        )).take(Randomizer.Number.int(0, 7))
      val status = Status(Random.nextInt(Status.maxId))
      Pet(id, category, Randomizer.String.petName(), photos, tags, status)
    }).toBuffer
    PetStore(pets, tags, categories)
  }

}