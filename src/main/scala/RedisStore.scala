package com.banno

import redis.clients.jedis._
import Transaction._
import sjson.json._
import DefaultProtocol._
import JsonSerialization._
import dispatch.json._

case class RedisTransactionStore(config: Configuration) extends Store[Transaction] {
  lazy val jedis = new Jedis(config.host, 6379)

  def fetch(transactionId: String) = {
    val tr = fromjson[Transaction](JsString(jedis.get(transactionId)))
    Option(tr)
  }

  def write(transaction: Transaction) = {
    jedis.set(transaction.transactionId, tojson[Transaction](transaction).toString)
  }
}
