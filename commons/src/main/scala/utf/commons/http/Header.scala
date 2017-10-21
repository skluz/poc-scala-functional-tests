package utf.commons.http

/**
  * Created by Sławomir Kluz on 11/09/2017.
  */
case class Header(name: String, value: String) {
  def this(header: (String, String)) = this(header._1, header._2)
}
