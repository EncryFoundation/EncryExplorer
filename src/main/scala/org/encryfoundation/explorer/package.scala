package org.encryfoundation

import supertagged.TaggedType

package object explorer {

  object ModifierId extends TaggedType[Array[Byte]]
  type ModifierId = ModifierId.Type

  object Address extends TaggedType[String]
  type Address = Address.Type
}
