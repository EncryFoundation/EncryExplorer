package org.encryfoundation.explorer.protocol

import org.encryfoundation.prismlang.compiler.CompiledContract
import org.encryfoundation.prismlang.core.Ast.{Expr, Ident}
import org.encryfoundation.prismlang.core.{Ast, Types}
import org.encryfoundation.prismlang.lib.predefined.signature.CheckSig
import scorex.crypto.encode.Base16

sealed trait RegularContract {
  val typeId: Byte
  val contract: CompiledContract
  lazy val contractHashHex: String = Base16.encode(contract.hash)
}

case object OpenContract extends RegularContract {
  val typeId: Byte = 0
  val contract: CompiledContract = CompiledContract(List.empty, Ast.Expr.True)
}

case class HeightLockedContract(height: Int) extends RegularContract {
  val typeId: Byte = HeightLockedContract.TypeId
  val contract: CompiledContract = CompiledContract(
    List("state" -> Types.EncryState),
    Expr.If(
      Expr.Compare(
        Expr.Attribute(
          Expr.Name(
            Ast.Ident("state"),
            Types.EncryState
          ),
          Ast.Ident("height"),
          Types.PInt
        ),
        List(Ast.CompOp.GtE),
        List(Expr.IntConst(height.toLong))
      ),
      Expr.True,
      Expr.False,
      Types.PBoolean
    )
  )
}
object HeightLockedContract { val TypeId: Byte = 1 }

case class AccountLockedContract(account: Account) extends RegularContract {
  val typeId: Byte = AccountLockedContract.TypeId
  val contract: CompiledContract = CompiledContract(
    List("tx" -> Types.EncryTransaction, "sig" -> Types.Signature25519),
    Expr.Call(
      Expr.Name(Ident("checkSig"), Types.PFunc(CheckSig.args.toList, Types.PBoolean)),
      List(
        Expr.Name(Ident("sig"), Types.Signature25519),
        Expr.Attribute(
          Expr.Name(Ident("tx"), Types.EncryTransaction),
          Ident("messageToSign"),
          Types.PCollection.ofByte
        ),
        Expr.Base16Str(Base16.encode(account.pubKey))
      ),
      Types.PBoolean
    )
  )
}
object AccountLockedContract {
  val TypeId: Byte = 2
  def apply(address: String): AccountLockedContract = AccountLockedContract(Account(address))
}
