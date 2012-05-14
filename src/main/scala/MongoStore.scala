package com.banno

import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.conversions.scala._
import org.joda.time.DateTime
import com.novus.salat._
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.Imports._
import com.novus.salat.global._

case class MongoTransactionStore(config: Configuration) extends Store[Transaction] {
  lazy val collection = MongoConnection(config.host, 27017)("iafp")("transactions")

  def fetch(transactionId: String) = {
    val q = MongoDBObject("transactionId"  -> transactionId)
    collection.findOne(q).map(grater[Transaction].asObject(_))
  }

  def write(transaction: Transaction) = {
    val q = MongoDBObject("transactionId" -> transaction.transactionId)
    RegisterJodaTimeConversionHelpers()
    collection.update(q, grater[Transaction].asDBObject(transaction), true, false)
    transaction
  }
}
