package utf.commons.entity

import utf.commons.json.JsonWrapper

/**
  * Created by Sławomir Kluz on 12/10/2017.
  */
trait RestEntity {
  private lazy val wrapper = new JsonWrapper(this)
  def asJsonString(): String = wrapper.asJsonString()
  def asJsonDocument(): JsonWrapper = wrapper
}
