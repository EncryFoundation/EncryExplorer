package org.encryfoundation.explorer

import supertagged.TaggedType

package object protocol {

  object ModifierId extends TaggedType[Array[Byte]]
  type ModifierId = ModifierId.Type

  object Address extends TaggedType[String]
  type Address = Address.Type
}
