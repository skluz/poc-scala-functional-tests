package model

import java.time.Instant

/**
  * Created by Sławomir Kluz on 04/09/2017.
  */
case class Person(
  firstName: String,
  lastName: Option[String],
  middleName: String,
  age: Int,
  birthDate: Instant,
  list: Seq[Any],
  address: Address
)

case class Address(
  active: Boolean,
  data: Map[Any, Any]
)
