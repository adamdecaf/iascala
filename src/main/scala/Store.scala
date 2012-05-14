package com.banno

import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.conversions.scala._
import org.joda.time.DateTime
import com.novus.salat._
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.Imports._
import com.novus.salat.global._

case class Configuration(host: String)

trait Store[A] {
  def fetch(id: String): Option[A]
  def write(a:A): Unit
}

trait DataService[A] {
  type UUID = String

  val fetch: Store[A] => UUID => Option[A] =
    { store => uuid => store.fetch(uuid) }

  val write: Store[A] => A => Unit = { store => a => store.write(a) }

  def get: UUID => Option[A]
  def put: A => Unit
}

case class MultiWriteOperation[A](stores: Seq[DataService[A]]) {
  def multiwrite(a:A) = stores foreach (store => store.put(a))
}


