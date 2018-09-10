package models

case class Output(id: String,
                  txId: String,
                  monetaryValue: Long,
                  coinId: String,
                  contractHash: String,
                  data: String)