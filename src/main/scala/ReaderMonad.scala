package com.banno

case class Reader[E,A](read: E => A) {
  type Environment = E

  def apply(e:E): A = read(e)
  def pure[A](a:A): E => A = e => a

  def map[B](f: A => B): Reader[E,B] = Reader[E,B]( s => f(read(s)))
  def flatMap[B](f: A => Reader[E,B]): Reader[E,B] = Reader[E,B](e => f(read(e))(e) )
}

