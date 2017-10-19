package com.funtis.petstore.mock.model

/**
  * Created by Sławomir Kluz on 13/10/2017.
  */
case class Category(id: Long, name: String)

object Category {
  def someCategories() = Seq(Category(1, "cat"), Category(2, "dog"), Category(3, "hamster"))
}
