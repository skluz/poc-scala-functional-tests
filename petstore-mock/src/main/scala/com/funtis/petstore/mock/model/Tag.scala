package com.funtis.petstore.mock.model

import com.funtis.commons.random.Randomizer

/**
  * Created by Sławomir Kluz on 13/10/2017.
  */
case class Tag(id: Long, name: String)

object Tag {
  def someTags() = (1 to Randomizer.Number.int(1, 5)).map(i => Tag(i, Randomizer.String.planet()))
}