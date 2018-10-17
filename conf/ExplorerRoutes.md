| Route | get/post |
|-------|----------|
|1) `/history/bestHeaderAt/:height`                           | get  |
| Description: shows information about `header` with `best_chain` mark at the `height`. It means that on every height it's possible to be different number of headers, but on every height there is always only one with mark `'best_chain'`.
|2) `/api/history/bestHeaderAt/:height`                       | get  | 
| Description: Same as 1) but this one returns JSON format instead view.
|3) `/history/headers/findByCount/:mode/:from/:count`         | get  |
| Description: Shows information about `number of headers`. It has 3 parameters. First - kind of sort ( by height, by date, by txsNum ). Second - height of the header with which will show information. Third - number of headers. 
|4) `/api/history/headers/findByCount/:from/:count`           | get  |
| Description: Same as 3) but this one returns JSON format instead view.
|5) `/api/history/headers/:modifierId`                        | get  |
| Description: Shows information about header by `header's ID`. Return format is JSON.
|6) `/history/headers/:modifierId`                            | get  | 
| Description: Same as 5) but this one shows view with list of headers.
|7) `/history/headersAt/:mode/:param`                         | get  |
| Description: Shows all headers at the `:param` height.
|8) `/api/history/headersAt/:height`                          | get  |
| Description: Same as 7) but this one returns JSON format. 
|9)  `/history/lastHeaders/:mode/:param`                      | get  |
| Description: Shows list of last _`:param`_ headers started from the end.
|10) `/api/history/lastHeaders/:qty`                          | get  |
| Description: Same as 9) but this one returns JSON format.
|11) `/history/headers/:mode/range/:from/:to`                 | get  |
| Description: Shows list of headers with height more then _`:from`_ and less then _`:to`_.
|12) `/api/history/headers/range/:from/:to`                   | get  |
| Description: Same as 11) but this one returns JSON format.
|13) `/history/findHeadersByDate/:mode/:da`                   | get  |
| Description: Shows list of headers with _`:count`_ size with timestamp >= _:date_.
|14) `/api/history/findHeadersByDate/:date/:count`            | get  |
| Description: Same as 13) but this one returns JSON format.
|15) `/history/headers/findByDateFromTo/:mode/:from/:to`      | get  |
| Description: Shows all headers where timestamp >= _`:from`_ && timestamp =< _`:to`_
|16) `/api/history/headers/findByDateFromTo/:from/:to`        | get  |
| Description: Same as 15) but this one returns JSON format.
|17) `/mainPage/:from/:to`                                    | get  |
| Description: The main explorer's page. Shows information about last 100 _`:best_chain`_ headers. It has _`next`_, _`prev`_ buttons
|18) `/history/allHeaders/:from/:to`                          | get  |
| Description: Shows all headers doesn't matter _`best_chain`_ true or false , ( from, to ) range
|19) `/api/transactions/output/:modifierId`                   | get  |
| Description: Shows all information about single output with _`modID`_ in transaction
|20) `/api/transactions/:address/outputs`                     | get  |
| Description: Shows all information about outputs which belong to special wallet with _`address`_
|21) `/api/transactions/:address/outputs/unspent`             | get  |
| Description: Shows all information about outputs which owner of special wallet with _`address`_ able to spend
|22) `/api/transactions/tx/:modifierId/outputs`               | get  |
| Description: Shows all information about outputs which belong to transaction with _`modID`_
|23) `/api/transactions/input/:modifierId/outputs/unspent`    | get  |
| Description: Shows all information about unspent outputs with
|24) `/api/transactions/input/:modifierId`                    | get  |
| Description: Shows input with `ID`
|25) `/api/transactions/tx/:id/inputs`                        | get  |
| Description: Shows all inputs belong to transaction with transaction `ID`
|26) `/api/transactions/:id`                                  | get  |
| Description: Same as 27). Return format is JSON.
|27) `/transactions/:id`                                      | get  |
| Description: Shows view with input / outputs belong to transaction with _`ID`_
|28) `/api/transactions/block/:id`                            | get  |
| Description: Same as 29). Return format is JSON.
|29) `/transactions/block/:id`                                | get  |
| Description: Shows all transactions belong to block with `ID`
|30) `/api/transactions/block/height/:height/outputs`         | get  |
| Description: Shows information about outputs belong to block with _height_
|31) `/api/transactions/block/height/:height/outputs/unspent` | get  |
| Description: Shows information about unspent outputs belong to block with _height_
|32) `/api/transactions/block/:modifierId/outputs`            | get  |
| Description: Shows list of `outputs` which belong to `block` with `_:modID_`
|33) `/api/transactions/block/:modifierId/outputs/unspent`    | get  |
| Description: Shows list of `outputs` which belong to `block` with `_:modID_` and also unspent
|34) `/api/transactions/tx/range/:from/:to`                   | get  |
| Description: Shows list of `transactions` which belong to blocks in the `( from - to )` range in JSON.
|35) `/api/block/:id`                                         | get  |
| Description: Shows all information about full `block` with _`:ID`_ in JSON format.
|36) `/block/:id`                                             | get  |
| Description: Shows all information about full `block` with _`:ID`_ as a view.