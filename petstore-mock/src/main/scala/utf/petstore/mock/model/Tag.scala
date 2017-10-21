package utf.petstore.mock.model

/**
  * Created by Sławomir Kluz on 13/10/2017.
  */
case class Tag(id: Long, name: String)

object Tag {
  def someTags() = Seq(Tag(1, "red"), Tag(2, "lazy"), Tag(4, "sleepyhead"), Tag(3, "happy"))
}