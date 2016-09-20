package com.twitter.manderson.mvc.models

import com.twitter.manderson.mvc.modules.Context
import scala.reflect.ClassTag


trait BaseModel {
  val id: Int
}

abstract class ModelStore[M <: BaseModel : ClassTag] {
  val context: Context
  import context._
  // val schema: Quoted[Query[M]]

  // def association[T <: BaseModel: ClassTag](schema: Quoted[Query[M]], children: Quoted[M => Query[T]], modelIds: Set[Int]) = quote {
  //   for {
  //     model <- schema if liftQuery(modelIds).contains(model.id)
  //     child <- children(model)
  //   } yield (model, child)
  // }

  // def findByIdsQuery(schema: Quoted[Query[M]], ids: Set[Int]) = quote {
  //   for {
  //     model <- schema if liftQuery(ids).contains(model.id)
  //   } yield model
  // }
}