package utf.petstore.model

import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.module.scala.JsonScalaEnumeration
import utf.commons.entity.RestEntity

/**
  * Created by Sławomir Kluz on 19/10/2017.
  */
case class Pet(
  id: Long,
  category: CategoryReference,
  name: String,
  photoUrls: Seq[String],
  tags: Seq[TagReference],
  @JsonScalaEnumeration(classOf[StatusTypeReference]) status: PetStatus.StatusType
) extends RestEntity

object PetStatus extends Enumeration {
  type StatusType = Value
  val available, pending, sold = Value
}

private class StatusTypeReference extends TypeReference[PetStatus.type]

case class CategoryReference(id: Long, name: String)
case class TagReference(id: Long, name: String)

case class PetInput(
  category: Long,
  name: String,
  photoUrls: Seq[String],
  tags: Seq[Long],
  status: String
) extends RestEntity
