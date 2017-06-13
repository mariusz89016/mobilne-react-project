package sri.mobile.template.parser

import shapeless.{::, Generic, HList, HNil}
import sri.core.ReactComponentBase

import scala.reflect.ClassTag
import scala.scalajs.js.ConstructorTag

trait ComponentsMapping[A] {
  def apply: ComponentsMapper
}

object ComponentsMapping {

  implicit def nestedComponentsMapping[Outer <: ({ type Component <: ReactComponentBase})](implicit
                                                                                                 constructorTag: ConstructorTag[Outer#Component],
                                                                                                 classTag: ClassTag[Outer],
                                                                                                 propsReader: upickle.default.Reader[Outer#Component#Props]
                                                                                                ) = new ComponentsMapping[Outer] {
    def apply = {
      val componentName = classTag.runtimeClass.getSimpleName
      val constructor = constructorTag.constructor
      val propsDeserializer = upickle.default.readJs[Outer#Component#Props] _

      Map(componentName -> ComponentInfo(constructor, propsDeserializer))
    }
  }



  implicit val nilComponentsMapping = new ComponentsMapping[HNil] {
    def apply = Map.empty
  }


  implicit def consComponentsMapping[Head, Tail <: HList](implicit
                                                          headMapping: ComponentsMapping[Head],
                                                          tailMapping: ComponentsMapping[Tail]
                                                         ) = new ComponentsMapping[Head :: Tail] {
    def apply = {
      headMapping.apply ++ tailMapping.apply
    }
  }

  def apply[Components](implicit mapping: ComponentsMapping[Components]): ComponentsMapper = {
    mapping.apply
  }

  def apply[Components](components: Components)(implicit mapping: ComponentsMapping[Components]): ComponentsMapper = {
    mapping.apply
  }

  def apply[P <: Product, L <: HList](components: P)(implicit
                                                     gen: Generic.Aux[P, L],
                                                     mapping: ComponentsMapping[L]): ComponentsMapper = {
    mapping.apply
  }
}
