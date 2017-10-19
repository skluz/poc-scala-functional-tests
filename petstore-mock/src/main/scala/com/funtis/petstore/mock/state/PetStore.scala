package com.funtis.petstore.mock.state

import com.funtis.commons.mock.servlet.MockState
import com.funtis.commons.random.Randomizer
import com.funtis.petstore.mock.model.{Category, Pet, Status, Tag}

import scala.collection.mutable
import scala.util.Random

/**
  * Created by Sławomir Kluz on 13/10/2017.
  */
case class PetStore(pets: mutable.Buffer[Pet]) extends MockState

object PetStore {

  def fullStore(): PetStore = {
    val categories = Category.someCategories()
    val tags = Tag.someTags()
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
      val randomTags = Random.shuffle(tags).take(Randomizer.Number.int(1, tags.size))
      val status = Status(Random.nextInt(Status.maxId))
      Pet(id, category, Randomizer.String.petName(), photos, randomTags, status)
    }).toBuffer
    PetStore(pets)
  }

}