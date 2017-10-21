package utf.commons.http

import scala.collection.mutable

/**
  * Created by Sławomir Kluz on 11/09/2017.
  */
class Headers private () {
  private val headers = mutable.Buffer[Header]()
  def this(headers: Header*) = {
    this()
    add(headers: _*)
  }
  def add(headers: Header*): Headers = {
    headers.foreach(header => {
      this.headers.find(h => h.name.equalsIgnoreCase(header.name)) match {
        case h: Some[Header] => this.headers -= h.get
        case _ =>
      }
      this.headers += header
    })
    this
  }

  def valueOf(name: String): String = {
    this.headers.find(h => h.name == name).get.value
  }

  def contains(name: String): Boolean = {
    this.headers.exists(h => h.name == name)
  }

  def asList(): Seq[Header] = headers

  override def equals(other: Any): Boolean = other match {
    case that: Headers => other.isInstanceOf[Headers] && headers == that.headers
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(headers)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  override def toString = s"Headers($headers)"

}