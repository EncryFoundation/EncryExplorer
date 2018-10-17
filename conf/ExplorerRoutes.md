| Route | get/post |
|-------|----------|
|1) `/history/bestHeaderAt/:height`                           | get  |
| Description: shows information about `header` with `best_chain` mark at the `height`. It means that on every height it's possible to be different number of headers, but on every height there is always only one with mark `'best_chain'`.
|2) `/api/history/bestHeaderAt/:height`                       | get  | 
| Description: same as 1), but this one returns JSON format instead of view.
|3) `/history/headers/findByCount/:mode/:from/:count`         | get  |
| Description: shows information about `number of headers`. It has 3 parameters. First - kind of sort ( by height, by date, by txsNum ). Second - height of the header starting from which information will be shown. Third - number of headers. 
|4) `/api/history/headers/findByCount/:from/:count`           | get  |
| Description: same as 3), but this one returns JSON format instead of view.
|5) `/api/history/headers/:modifierId`                        | get  |
| Description: shows information about header by `header's ID` in JSON format.
|6) `/history/headers/:modifierId`                            | get  | 
| Description: same as 5) but this one shows view with list of headers.
|7) `/history/headersAt/:mode/:param`                         | get  |
| Description: shows all headers at the `:param` height.
|8) `/api/history/headersAt/:height`                          | get  |
| Description: same as 7), but this one returns JSON format. 
|9)  `/history/lastHeaders/:mode/:param`                      | get  |
| Description: shows list of last `:param` headers started at the end of the chain.
|10) `/api/history/lastHeaders/:qty`                          | get  |
| Description: same as 9) but this one returns JSON format.
|11) `/history/headers/:mode/range/:from/:to`                 | get  |
| Description: shows list of headers with height greater than `:from` and less then `:to`.
|12) `/api/history/headers/range/:from/:to`                   | get  |
| Description: same as 11), but this one returns JSON format.
|13) `/history/findHeadersByDate/:mode/:da`                   | get  |
| Description: shows list of headers with `:count` size and with timestamp >= `:date`.
|14) `/api/history/findHeadersByDate/:date/:count`            | get  |
| Description: same as 13), but this one returns JSON format.
|15) `/history/headers/findByDateFromTo/:mode/:from/:to`      | get  |
| Description: shows all headers where timestamp >= `:from` and timestamp =< `:to`.
|16) `/api/history/headers/findByDateFromTo/:from/:to`        | get  |
| Description: same as 15), but this one returns JSON format.
|17) `/mainPage/:from/:to`                                    | get  |
| Description: the main explorer's page. Shows information about last 100 `:best_chain` headers. It has `next`, `prev` buttons.
|18) `/history/allHeaders/:from/:to`                          | get  |
| Description: shows all headers in the range.
|19) `/api/transactions/output/:modifierId`                   | get  |
| Description: shows all information about single output with `modID` in transaction.
|20) `/api/transactions/:address/outputs`                     | get  |
| Description: shows all information about outputs which belong to special wallet with `address`.
|21) `/api/transactions/:address/outputs/unspent`             | get  |
| Description: shows all information about outputs which owner of special wallet with `address` able to spend
|22) `/api/transactions/tx/:modifierId/outputs`               | get  |
| Description: shows all information about outputs which belong to transaction with `modID`.
|23) `/api/transactions/input/:modifierId/outputs/unspent`    | get  |
| Description: shows all information about unspent outputs.
|24) `/api/transactions/input/:modifierId`                    | get  |
| Description: shows input with `ID`.
|25) `/api/transactions/tx/:id/inputs`                        | get  |
| Description: shows all inputs belong to transaction with `transaction ID`.
|26) `/api/transactions/:id`                                  | get  |
| Description: same as 27). Return format is JSON.
|27) `/transactions/:id`                                      | get  |
| Description: shows view with inputs / outputs belong to transaction with `ID`.
|28) `/api/transactions/block/:id`                            | get  |
| Description: same as 29), but this one returns JSON format.
|29) `/transactions/block/:id`                                | get  |
| Description: shows all transactions belong to block with `ID`.
|30) `/api/transactions/block/height/:height/outputs`         | get  |
| Description: shows information about outputs belong to block at the `height`
|31) `/api/transactions/block/height/:height/outputs/unspent` | get  |
| Description: shows information about unspent outputs belong to block at the `height`.
|32) `/api/transactions/block/:modifierId/outputs`            | get  |
| Description: shows list of `outputs` which belong to `block` with `:modID`.
|33) `/api/transactions/block/:modifierId/outputs/unspent`    | get  |
| Description: shows list of unspent `outputs` which belong to `block` with `:modID`.
|34) `/api/transactions/tx/range/:from/:to`                   | get  |
| Description: shows list of `transactions` which belong to blocks in the `( from - to )` range in JSON format.
|35) `/api/block/:id`                                         | get  |
| Description: shows all information about full `block` with `:ID` in JSON format.
|36) `/block/:id`                                             | get  |
| Description: shows all information about full `block` with `:ID` as a view.