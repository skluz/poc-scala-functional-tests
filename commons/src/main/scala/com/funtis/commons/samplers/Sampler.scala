package com.funtis.commons.samplers

import com.funtis.commons.json.JsonWrapper

/**
  * Created by skluz@g2a.com on 20/10/2017.
  */
trait Sampler {
  private lazy val wrapper = new JsonWrapper(this)
  def asJsonString(): String = wrapper.asJsonString()
  def asJsonDocument(): JsonWrapper = wrapper
}
