package com.banno

case class TransactionStore(store: Store[Transaction]) extends DataService[Transaction]{
  def get = fetch(store)
  def put = write(store)
}

object TransactionStore {
  def transactionServiceReader: Reader[Configuration, DataService[Transaction]] =
    Reader((c: Configuration) => TransactionStore(MongoTransactionStore(c)))

  def transactionCacheReader: Reader[Configuration, DataService[Transaction]] =
    Reader((c: Configuration) => TransactionStore(RedisTransactionStore(c)))

  def multiWriteReader: Reader[Configuration, MultiWriteOperation[Transaction]] = for {
    transactionService <- transactionServiceReader
    cacheService <- transactionCacheReader
  } yield (MultiWriteOperation(Seq(transactionService, cacheService)))
}
