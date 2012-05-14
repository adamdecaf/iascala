package com.banno

import org.joda.time.DateTime
import org.joda.time.format.{DateTimeFormat, ISODateTimeFormat}
import org.joda.time.DateTimeZone
import sjson.json._
import DefaultProtocol._
import JsonSerialization._
import dispatch.json._

case class Transaction(transactionId: String, date: String, memo: String, amount: String, city: String, state: String)

object Transaction extends DefaultProtocol {
  implicit object TransactionJSONFormat extends Format[Transaction] {
    def writes(t: Transaction) = {
      JsObject(List(
        (tojson("transactionId").asInstanceOf[JsString], tojson(t.transactionId)),
        (tojson("date").asInstanceOf[JsString], tojson(t.date)),
        (tojson("memo").asInstanceOf[JsString], tojson(t.memo)),
        (tojson("amount").asInstanceOf[JsString], tojson(t.amount)),
        (tojson("city").asInstanceOf[JsString], tojson(t.city)),
        (tojson("state").asInstanceOf[JsString], tojson(t.state))
      ))
    }

    def reads(json: JsValue) = {
      json match {
        case JsObject(tr) =>
          Transaction(
            fromjson[String](tr(JsString("transactionId"))),
            fromjson[String](tr(JsString("date"))),
            fromjson[String](tr(JsString("memo"))),
            fromjson[String](tr(JsString("amount"))),
            fromjson[String](tr(JsString("city"))),
            fromjson[String](tr(JsString("state")))
          )
        case _ => throw new RuntimeException("JsObject expected")
      }
    }
  }
}

case class RawTransaction(date: DateTime, memo: Memo, amount: BigDecimal)
case class Memo(memo: String, city: String, state: String)
