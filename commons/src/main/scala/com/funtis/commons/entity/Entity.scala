package com.funtis.commons.entity

import com.funtis.commons.json.JsonWrapper

/**
  * Created by Sławomir Kluz on 12/10/2017.
  */
trait Entity {
  private lazy val wrapper = new JsonWrapper(this)
  def asJsonString(): String = wrapper.asJsonString()
  def asJsonDocument(): JsonWrapper = wrapper
}
