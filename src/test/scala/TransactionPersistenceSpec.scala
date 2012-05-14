package test

import org.specs2.mutable.Specification
import org.specs2.specification.{Scope, After, BeforeAfterEach}
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.conversions.scala._
import org.joda.time.DateTime
import com.novus.salat._
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.Imports._
import com.novus.salat.global._
import com.banno._

class TransactionPersistenceSpec extends MongoSpecification {
  "testing TransactionStore persistence" should {

    "prove storage by retrieval" in new persistenceContext {
      mongostore.put(t1)

      mongostore.get(t1.transactionId) map {
        transaction =>
          transaction.transactionId === "1"
      }
    }

    "experimenting with Reader Monad" in new persistenceContext {
      import TransactionStore._

      //demonstrate threading config through call stack to write to mult dbs
      multiWriteReader(kvconfig).multiwrite(t1)
    }
  }
}

trait persistenceContext extends Scope with transactionFixtures {
  val kvconfig        = Configuration("localhost")
  lazy val mongostore = TransactionStore(MongoTransactionStore(kvconfig))
  lazy val ts         = Seq(t1, t2)
}

trait transactionFixtures {
  val t1 = Transaction("1", "12/12/2012", "CITY OF DSM MUNI PKG GAR DES MOINES", "10.00", "DES MOINES", "IA")
  val t2 = Transaction("2", "12/13/2012", "CASEYS GEN STORE 2865    CEDAR FALLS", "21.45", "DES MOINES", "IA")
}
