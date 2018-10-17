| Route | get/post |
|-------|----------|
|1) `/history/bestHeaderAt/:height`                           | get  |
| Description: shows information about `header` with `best_chain` mark at the `'height'`. It means that on every height it's possible to be different number of headers, but on every height there is always only one with mark 'best_chain'.
|2) `/api/history/bestHeaderAt/:height`                       | get  | 
| Description: Same as 1) but this one returns JSON format instead view.
|3) `/history/headers/findByCount/:mode/:from/:count`         | get  |
| Description: Shows information about `number of headers`. It has 3 parameters. First - kind of sort ( by height, by date, by txsNum ).
| Second - first height from which information will be show. Third - number of headers. 
|4) `/api/history/headers/findByCount/:from/:count`           | get  |
|5) /api/history/headers/:modifierId                        | get  | 
|6) /history/headers/:modifierId                            | get  | 
|7) /history/headersAt/:mode/:param                         | get  | 
|8) /api/history/headersAt/:height                          | get  | 
|9) /history/lastHeaders/:mode/:param                       | get  |
|10) /api/history/lastHeaders/:qty                          | get  |
|11) /history/headers/:mode/range/:from/:to                 | get  |
|12) /api/history/headers/range/:from/:to                   | get  |
|13) /history/findHeadersByDate/:mode/:da                   | get  |
|14) /api/history/findHeadersByDate/:date/:count            | get  |
|15) /history/headers/findByDateFromTo/:mode/:from/:to      | get  |
|16) /api/history/headers/findByDateFromTo/:from/:to        | get  |
|17) /mainPage/:from/:to                                    | get  |
|18) /history/allHeaders/:from/:to                          | get  |
|19) /api/transactions/output/:modifierId                   | get  |
|20) /api/transactions/:address/outputs                     | get  |
|21) /api/transactions/:address/outputs/unspent             | get  |
|22) /api/transactions/tx/:modifierId/outputs               | get  |
|23) /api/transactions/input/:modifierId/outputs/unspent    | get  |
|24) /api/transactions/input/:modifierId                    | get  |
|25) /api/transactions/tx/:id/inputs                        | get  |
|26) /api/transactions/:id                                  | get  |
|27) /transactions/:id                                      | get  |
|28) /api/transactions/block/:id                            | get  |
|29) /transactions/block/:id                                | get  |
|30) /api/transactions/block/height/:height/outputs         | get  |
|31) /api/transactions/block/height/:height/outputs/unspent | get  |
|32) /api/transactions/block/:modifierId/outputs            | get  |
|33) /api/transactions/block/:modifierId/outputs/unspent    | get  |
|34) /api/transactions/tx/range/:from/:to                   | get  |
|35) /api/block/:id                                         | get  |
|36) /block/:id                                             | get  |


2)  
3)  
4)  Same as 3) but this one returns JSON format instead view.
5)  Show information about header by `header's ID`. Return format is JSON.
6)  Same as 5) but this one shows view with list of headers.
7)  Show all headers at the `:param` height.
8)  Same as 7) but this one returns JSON format.
9)  Show list of last _`:param`_ headers started from the end.
10) Same as 9) but this one returns JSON format.
11) Show list of headers with height more then _`:from`_ and less then _`:to`_.
12) Same as 11) but this one returns JSON format.
13) Show list of headers with _`:count`_ size with timestamp >= _:date_.
14) Same as 13) but this one returns JSON format.
15) Show all headers where timestamp >= _`:from`_ && timestamp =< _`:to`_
16) Same as 15) but this one returns JSON format.
17) The main explorer's page. Shows information about last 100 _`:best_chain`_ headers. Has _`next`_, _`prev`_ buttons
18) Show all headers doesn't matter _`best_chain`_ true or false ( from, to )
19) Show all information about single output with _`modID`_ in transaction
20) Show all information about outputs which belong to special wallet with _`address`_
21) Show all information about outputs which owner of special wallet with _`address`_ able to spend
22) Show all information about outputs which belong to transaction with _`modID`_
23) Show all information about unspent outputs with  
24) Show input with `ID`
25) Show all inputs belong to transaction with transaction `ID`
26) Same as 27). Return format is JSON.
27) Show view with input / outputs belong to transaction with _`ID`_
28) Same as 29). Return format is JSON.
29) Show all transactions belong to block with `ID`
30) Show information about outputs belong to block with _height_
31) Show information about unspent outputs belong to block with _height_
32) Show list of `outputs` which belong to `block` with `_:modID_`
33) Show list of `outputs` which belong to `block` with `_:modID_` and also unspent
34) Show list of `transactions` which belong to blocks in the `( from - to )` range in JSON.
35) Show all information about full `block` with _`:ID`_ in JSON format.
36) Show all information about full `block` with _`:ID`_ as a view.