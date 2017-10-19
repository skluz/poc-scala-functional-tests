package com.funtis.petstore.mock.model

/**
  * Created by Sławomir Kluz on 13/10/2017.
  */
case class Pet(
  id: Long,
  category: Category,
  name: String,
  photoUrls: Seq[String],
  tags: Seq[Tag],
  status: Status.StatusType
)
