package test

import org.specs2.mutable
import org.specs2.execute.StandardResults
import org.specs2.specification.Step
import com.mongodb.casbah.Imports._

trait MongoSpecification extends mutable.Specification {
  override def is = Step(setup()) ^ super.is

  def setup() = {
    MongoConnection("localhost", 27017)("beno").dropDatabase()
  }

}
