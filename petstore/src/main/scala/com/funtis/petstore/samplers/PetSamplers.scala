package com.funtis.petstore.samplers

import com.funtis.petstore.model.PetInput

/**
  * Created by skluz@g2a.com on 19/10/2017.
  */
object PetSamplers {

  def garfield() =
    PetInput(
      1, "garfield",
      Seq("https://vignette.wikia.nocookie.net/garfield/images/f/f3/Garfield_alarm_clock.jpg/revision/latest/scale-to-width-down/464?cb=20140215142008", "https://www.thewrap.com/wp-content/uploads/2016/05/garfield-movie.jpg"),
      Seq(1, 2, 4), "available"
    )

}
